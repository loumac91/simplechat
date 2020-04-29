package handler;

import server.SimpleChatServer;
import server.SimpleChatUser;
import java.net.Socket;
import format.MessageFormatter;

public class ClientConnectHandler extends BaseHandler {

  private SimpleChatServer chatServer;

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

        this.chatServer.addUser(chatUser);

        String username = chatUser.getUsername();
        chatUser.sendMessage(MessageFormatter.getWelcomeMessage(username));

        ClientMessageHandler clientMessageHandler = new ClientMessageHandler(this.chatServer, chatUser);
        Thread clientMessageHandlerThread = new Thread(clientMessageHandler);
        clientMessageHandlerThread.start();
      } catch (Exception e) {
        this.running = false;
        System.out.println("ClientConnectHandler.run - " + e.getClass().getCanonicalName());
        e.printStackTrace();
      }
    }
  }
}