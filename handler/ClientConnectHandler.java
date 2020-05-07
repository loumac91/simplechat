package handler;

import server.SimpleChatServer;
import server.SimpleChatUser;
import java.net.Socket;

import constant.Server;
import format.MessageFormatter;

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

        // By design, username is first message from client due to constructor calling .readLine()
        SimpleChatUser chatUser = new SimpleChatUser(chatClient);

        this.chatServer.addUser(chatUser);

        String username = chatUser.getUsername(); 
        String formattedUserJoinedMessage = MessageFormatter.formatUserJoinedMessage(username);
        System.out.println(MessageFormatter.formatServerLog(formattedUserJoinedMessage));
        
        String welcomeMessage = MessageFormatter.formatWelcomeMessage(username);
        chatUser.sendMessage(welcomeMessage);

        this.chatServer.broadCastMessage(Server.USERNAME, chatUser.getUserId(), formattedUserJoinedMessage);

        ClientMessageHandler clientMessageHandler = new ClientMessageHandler(this.chatServer, chatUser);
        Thread clientMessageHandlerThread = new Thread(clientMessageHandler);
        clientMessageHandlerThread.start();
      } catch (Exception e) {
        this.running = false;
        System.out.println("Exception in ClientConnectHandler" + e.getClass().getCanonicalName());
      }
    }
  }
}