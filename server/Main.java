package server;

import java.io.IOException;
import java.net.Socket;
import java.net.BindException;
import constants.*;

public class Main {
  public static void main(String[] args) {
    // 1. Run (AutoCloseable) Host
    try (SimpleChatServer chatServer = new SimpleChatServer(Server.PORT)) {
      System.out.print(String.format(MessageFormats.Server.SERVER_RUNNING, Server.ADDRESS, Server.PORT));

      // 2. Wait for incoming connections
      while (true) {
        // 3. Accept Client
        Socket chatClient = chatServer.getNextClient();

        // 4. Create User
        SimpleChatUser user = new SimpleChatUser(chatClient);

        // 5. Add User to User pool
        chatServer.addUser(user);
      
        // 6. Welcome User
        user.sendMessage("Welcome " + user.getUserName());

        // 7. Start (Runnable) handler for simplechat Client Messages
        ClientMessageHandler clientMessageHandler = new ClientMessageHandler(chatServer, user);
        Thread clientMessageHandlerThread = new Thread(clientMessageHandler);
        clientMessageHandlerThread.start();
      } 

    } catch (BindException bindException) {
      bindException.getStackTrace();
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }
}