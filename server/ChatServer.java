package server;

import java.io.*;
import java.net.BindException;
import constant.Ansi.Colour;
import constant.Server;
import constant.Command;
import format.MessageFormatter;
import configuration.*;
import handler.ClientConnectHandler;
import handler.HandlerFactory;
import util.StringUtils;

public class ChatServer {
  public static void main(String[] args) {
    BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
    SocketConfiguration socketConfiguration = new SocketConfigurationBuilder()
      .withDefaultAddress(Server.DEFAULT_ADDRESS)
      .withDefaultPort(Server.DEFAULT_PORT)
      .withPortMapper(new SocketConfigurationMapper(Server.Param.PORT, SocketConfigurationSetterFactory.portSetter))
      .buildFromCommandLine(args);

    String serverStartingUpMessage = MessageFormatter.formatServerStartingUpMessage(socketConfiguration.address, socketConfiguration.port);
    System.out.println(MessageFormatter.formatStringColour(Colour.YELLOW, serverStartingUpMessage));

    // 1. Run (AutoCloseable) Host
    try (SimpleChatServer chatServer = new SimpleChatServer(socketConfiguration)) {
      String serverRunningMessage = MessageFormatter.formatServerRunningMessage(socketConfiguration.address, socketConfiguration.port);
      System.out.println(MessageFormatter.formatStringColour(Colour.GREEN, serverRunningMessage));

      // Thread for handling Connectings
      new Thread(HandlerFactory.createClientConnectHandler(chatServer)).start();

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
          System.out.println("Exception in ChatServer" + e.getClass().getCanonicalName());
        }
      }
    } catch (BindException bindException) {
      System.out.println(MessageFormatter.formatException(bindException));
    } catch (IOException ioException) {
      System.out.println(MessageFormatter.formatException(ioException));
    } catch (Exception exception) {
      System.out.println(MessageFormatter.formatException(exception));  
    }
  }
}