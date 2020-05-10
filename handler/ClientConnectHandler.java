package handler;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import constant.Client;
import constant.Server;
import format.MessageFormatter;
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

        String formattedUserJoinedMessage = MessageFormatter.formatUserJoinedMessage(username);
        System.out.println(MessageFormatter.formatServerLog(formattedUserJoinedMessage));

        String welcomeMessage = MessageFormatter.formatWelcomeMessage(username);
        chatUser.sendMessage(welcomeMessage);

        this.chatServer.broadCastMessage(Server.USERNAME, chatUser.getUserId(), formattedUserJoinedMessage);

        new Thread(HandlerFactory.createClientMessageHandler(this.chatServer, chatUser)).start();

      } catch (SocketException socketException) {
        System.out.println(Client.Error.CONNECTION_INTERRUPTED);
      } catch (Exception e) {
        // this.running = false;
        System.out.println(MessageFormatter.formatException(e));
      }
    }
  }

  private String getUsername(SimpleChatUser chatUser) throws IOException {
    String username = chatUser.readMessage();
    SimpleChatUser existingUser = this.chatServer.getChatUser(username);
    if (existingUser != null) {
      Boolean invalidUsername = true;
      while (invalidUsername) {
        String invalidUsernameMessage = MessageFormatter.formatUsernameAlreadyExistsMessage(username);
        this.chatServer.sendErrorMessage(chatUser, invalidUsernameMessage);
        username = chatUser.readMessage();
        invalidUsername = this.chatServer.getChatUser(username) != null;
      }
    }

    return username;
  }
}