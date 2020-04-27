package server;

import java.io.IOException;
import java.net.Socket;
import java.net.BindException;
import constant.*;
import format.MessageFormatter;
import configuration.*;

public class ChatServer {
  public static void main(String[] args) {
    SocketConfiguration socketConfiguration = new SocketConfigurationBuilder()
      .setDefaultAddress(Server.DEFAULT_ADDRESS)
      .setDefaultPort(Server.DEFAULT_PORT)
      .setPortMapper(new SocketConfigurationMapper(Server.Param.PORT, SocketConfigurationSetterFactory.portSetter))
      .buildFromCommandLine(args);

    // 1. Run (AutoCloseable) Host
    try (SimpleChatServer chatServer = new SimpleChatServer(socketConfiguration)) {
      System.out.println(MessageFormatter.getServerRunningMessage(socketConfiguration.address, socketConfiguration.port));

      // 2. Wait for incoming connections
      Boolean running = true;
      while (running) {
        // 3. Accept Client
        Socket chatClient = chatServer.getNextClient();

        // 4. Create User
        SimpleChatUser chatUser = new SimpleChatUser(chatClient);

        // 5. Add User to User pool
        chatServer.addUser(chatUser);
      
        // 6. Welcome User
        String userName = chatUser.getUserName();
        chatUser.sendMessage(MessageFormatter.getWelcomeMessage(userName));

        // 7. Start (Runnable) handler for simplechat Client Messages
        ClientMessageHandler clientMessageHandler = new ClientMessageHandler(chatServer, chatUser);
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