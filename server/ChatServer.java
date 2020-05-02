package server;

import java.io.*;
import java.net.BindException;
import constant.*;
import format.MessageFormatter;
import configuration.*;
import handler.ClientConnectHandler;
import util.StringUtils;

public class ChatServer {
  public static void main(String[] args) {
    BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
    SocketConfiguration socketConfiguration = new SocketConfigurationBuilder()
      .withDefaultAddress(Server.DEFAULT_ADDRESS)
      .withDefaultPort(Server.DEFAULT_PORT)
      .withPortMapper(new SocketConfigurationMapper(Server.Param.PORT, SocketConfigurationSetterFactory.portSetter))
      .buildFromCommandLine(args);

    // 1. Run (AutoCloseable) Host
    try (SimpleChatServer chatServer = new SimpleChatServer(socketConfiguration)) {
      System.out.println(MessageFormatter.formatServerRunningMessage(socketConfiguration.address, socketConfiguration.port));

      // Thread for handling Connectings
      ClientConnectHandler clientConnectHandler = new ClientConnectHandler(chatServer);
      Thread clientConnectHandlerThread = new Thread(clientConnectHandler);
      clientConnectHandlerThread.start();

      Boolean running = true;
      while (running) {
        try {
          String adminInput = inputReader.readLine();
          if (!StringUtils.isNullOrEmpty(adminInput) && adminInput.equals(Command.EXIT)) {  
            running = false;
            chatServer.shutdown();
          }
        } catch (Exception e) {
          running = false;
        }
      }
    } catch (BindException bindException) {
      bindException.getStackTrace();
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }
}