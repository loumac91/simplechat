package handler;

import java.io.IOException;
import server.SimpleChatServer;
import server.SimpleChatUser;

public class ClientMessageHandler extends BaseHandler {

  private SimpleChatServer chatServer;
  private SimpleChatUser chatUser;

  public ClientMessageHandler(SimpleChatServer chatServer, SimpleChatUser chatUser) {
    super();
    this.chatServer = chatServer;
    this.chatUser = chatUser;    
  }

  public void run() {
    this.running = true;
    while (this.running) {
      try {
        String message = this.chatUser.readMessage();
        // Parse message

        this.chatServer.broadCastMessage(this.chatUser, message);
      } catch (IOException ioException) {
        this.running = false;
        ioException.printStackTrace();
      }
    }
  }
}