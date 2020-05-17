package handler;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import constant.Server;
import format.StringFormatter;
import server.SimpleChatServer;
import server.SimpleChatUser;

// Runnable handler for accepting new client socket connections

public class ClientConnectHandler extends BaseHandler {

  private final SimpleChatServer chatServer;

  public ClientConnectHandler(SimpleChatServer chatServer) {
    super();
    this.chatServer = chatServer;
  }

  public void run() {
    this.running = true;    
    while (this.running) {
      try {
        Socket chatClient = this.chatServer.getNextClient(); // Wait until new connection

        SimpleChatUser chatUser = new SimpleChatUser(chatClient);

        String username = getUsername(chatUser); // Validate username
        chatUser.setUsername(username);

        this.chatServer.addUser(chatUser);

        new Thread(HandlerFactory.createClientMessageHandler(this.chatServer, chatUser)).start(); // Start another thread to handle messages coming from this user

        String formattedUserJoinedMessage = StringFormatter.formatUserJoinedMessage(username);
        System.out.println(StringFormatter.formatServerLog(formattedUserJoinedMessage));

        String welcomeMessage = StringFormatter.formatWelcomeMessage(username);
        chatUser.sendMessage(welcomeMessage);

        this.chatServer.broadCastMessage(Server.USERNAME, chatUser.getUserId(), formattedUserJoinedMessage); // Inform every other user that a new user has joined

      } catch (SocketException socketException) {
        System.out.println(Server.Error.CLIENT_UNABLE_TO_CONNECT);
        System.out.println(StringFormatter.formatException(socketException));
      } catch (Exception e) {
        System.out.println(StringFormatter.formatException(e));
      } finally {
        this.running = !Thread.interrupted();
      }
    }
  }

  // Function for ensuring usernames are unique
  private String getUsername(SimpleChatUser chatUser) throws IOException {
    String username = chatUser.readMessage();
    SimpleChatUser existingUser = this.chatServer.getChatUser(username);
    
    if (existingUser != null || username.equals(Server.USERNAME)) {
      Boolean invalidUsername = true;
      while (invalidUsername) {
        String invalidUsernameMessage = StringFormatter.formatUsernameAlreadyExistsMessage(username);
        this.chatServer.sendErrorMessage(chatUser, invalidUsernameMessage);
        username = chatUser.readMessage();
        // In theory another another different user could join whilst we wait for the current user to provide 
        // a username. Therefor you cant save a copy of the server.chatUsers in scope locally here - it must
        // be validated against against the current set of users
        invalidUsername = this.chatServer.getChatUser(username) != null;
      }
    }

    return username;
  }
}