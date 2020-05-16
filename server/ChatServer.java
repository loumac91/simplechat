package server;

import java.io.*;
import java.net.BindException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import constant.Ansi.Colour;
import constant.Server;
import format.StringFormatter;
import configuration.SocketConfiguration;
import configuration.SocketConfigurationBuilder;
import configuration.SocketConfigurationMapper;
import configuration.SocketConfigurationSetterFactory;
import handler.HandlerFactory;
import input.BaseInputParser;
import input.UserInputParser;
import strategy.parse.ServerCommandParseStrategy;

public class ChatServer {
  
  public static void main(String[] args) {
    BaseInputParser<ServerCommand> userInputParser = new UserInputParser<ServerCommand>(System.in);
    SocketConfiguration socketConfiguration = new SocketConfigurationBuilder()
      .withDefaultAddress(Server.DEFAULT_ADDRESS)
      .withDefaultPort(Server.DEFAULT_PORT)
      .withPortMapper(new SocketConfigurationMapper(Server.Param.PORT, SocketConfigurationSetterFactory.portSetter))
      .buildFromCommandLine(args);

    String serverStartingUpMessage = StringFormatter.formatServerStartingUpMessage(socketConfiguration.address, socketConfiguration.port);
    System.out.println(StringFormatter.formatStringColour(Colour.YELLOW, serverStartingUpMessage));

    // 1. Run (AutoCloseable) Host
    try (SimpleChatServer chatServer = new SimpleChatServer(socketConfiguration)) {
      String serverRunningMessage = StringFormatter.formatServerRunningMessage(socketConfiguration.address, socketConfiguration.port);
      System.out.println(StringFormatter.formatStringColour(Colour.GREEN, serverRunningMessage));

      // Thread for handling Connectings
      ExecutorService executorService = Executors.newSingleThreadExecutor();
      executorService.execute(HandlerFactory.createClientConnectHandler(chatServer));

      Boolean running = true;
      while (running) {
        try {
          ServerCommand serverCommand = userInputParser.parseInput(new ServerCommandParseStrategy());
          if (serverCommand != null) {
            switch (serverCommand) {
              case EXIT:
                running = false;
                executorService.shutdownNow(); // stop accepting any new connections, sets ClientConnectHandler thread to interrupted
                chatServer.shutdown(); // disconnect each user
                String formatted = StringFormatter.formatStringColour(Colour.YELLOW, Server.Message.SHUTDOWN_MESSAGE);
                System.out.println(formatted);
                break;
              default: 
                continue;
            }
          }
        } catch (IOException ioException) {
          running = false;
          System.out.println(StringFormatter.formatException(ioException));
        }
      }
    } catch (BindException bindException) {
      System.out.println(StringFormatter.formatException(bindException));
    } catch (IOException ioException) {
      System.out.println(StringFormatter.formatException(ioException));
    } catch (Exception exception) {
      System.out.println(StringFormatter.formatException(exception));  
    } finally {
      try {
        userInputParser.close();
      } catch (IOException ioException) {  }
    }
  }
}