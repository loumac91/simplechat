package handler;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import constant.Client;
import constant.Server;
import format.StringFormatter;
import server.SimpleChatServer;
import server.SimpleChatUser;

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
        Socket chatClient = this.chatServer.getNextClient();

        SimpleChatUser chatUser = new SimpleChatUser(chatClient);

        String username = getUsername(chatUser);
        chatUser.setUsername(username);

        this.chatServer.addUser(chatUser);

        new Thread(HandlerFactory.createClientMessageHandler(this.chatServer, chatUser)).start();

        String formattedUserJoinedMessage = StringFormatter.formatUserJoinedMessage(username);
        System.out.println(StringFormatter.formatServerLog(formattedUserJoinedMessage));

        String welcomeMessage = StringFormatter.formatWelcomeMessage(username);
        chatUser.sendMessage(welcomeMessage);

        this.chatServer.broadCastMessage(Server.USERNAME, chatUser.getUserId(), formattedUserJoinedMessage);

      } catch (SocketException socketException) {
        System.out.println(Client.Error.CONNECTION_INTERRUPTED);
        System.out.println(StringFormatter.formatException(socketException));
      } catch (Exception e) {
        System.out.println(StringFormatter.formatException(e));
      } finally {
        this.running = !Thread.interrupted();
      }
    }
  }

  private String getUsername(SimpleChatUser chatUser) throws IOException {
    String username = chatUser.readMessage();
    SimpleChatUser existingUser = this.chatServer.getChatUser(username);
    
    if (existingUser != null || username.equals(Server.USERNAME)) {
      Boolean invalidUsername = true;
      while (invalidUsername) {
        String invalidUsernameMessage = StringFormatter.formatUsernameAlreadyExistsMessage(username);
        this.chatServer.sendErrorMessage(chatUser, invalidUsernameMessage);
        username = chatUser.readMessage();
        invalidUsername = this.chatServer.getChatUser(username) != null;
      }
    }

    return username;
  }
}