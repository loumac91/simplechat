package server;

import java.io.IOException;

public class ClientMessageHandler implements Runnable {

  private SimpleChatServer chatServer;
  private SimpleChatUser chatUser;

  public ClientMessageHandler(SimpleChatServer chatServer, SimpleChatUser chatUser) {
    this.chatServer = chatServer;
    this.chatUser = chatUser;    
  }

  public void run() {
    Boolean connected = true;
    while (connected) {
      try {
        String message = this.chatUser.readMessage();
        this.chatServer.broadCastMessage(message);
      } catch (IOException ioException) {
        connected = false;
        ioException.printStackTrace();
      }
    }
  }
}