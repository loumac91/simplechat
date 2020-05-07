package client;

import java.io.*;
import java.net.ConnectException;
import java.net.UnknownHostException;
import handler.ServerMessageHandler;
import util.StringUtils;
import configuration.*;
import constant.*;
import format.MessageFormatter;

public class ChatClient {
    public static void main(String[] args) {
      BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
      SocketConfiguration socketConfiguration = new SocketConfigurationBuilder()
        .withDefaultAddress(Server.DEFAULT_ADDRESS)
        .withDefaultPort(Server.DEFAULT_PORT)
        .withAddressMapper(new SocketConfigurationMapper(Client.Param.HOST, SocketConfigurationSetterFactory.addressSetter))
        .withPortMapper(new SocketConfigurationMapper(Client.Param.PORT, SocketConfigurationSetterFactory.portSetter))
        .buildFromCommandLine(args);

      String connectingMessage = MessageFormatter.formatConnectingMessage(socketConfiguration.address, socketConfiguration.port);
      System.out.println(MessageFormatter.formatStringColour(Colour.YELLOW, connectingMessage));
      // 1. Establish (AutoCloseable) connection to simplechat Server 
      try (SimpleChatClient chatClient = new SimpleChatClient(socketConfiguration)) {
        String connectedMessage = MessageFormatter.formatConnectedMessage(socketConfiguration.address, socketConfiguration.port);
        System.out.println(MessageFormatter.formatStringColour(Colour.GREEN, connectedMessage));

        BufferedReader serverInputReader = new BufferedReader(new InputStreamReader(chatClient.getReadStream()));
        
        // 2. Get Client username
        String username = "";
        String response = "";
        Boolean invalidUsername = true;
        while (invalidUsername) {
          System.out.print(Client.Prompt.USERNAME_PROMPT);          
          username = inputReader.readLine();
          if (StringUtils.isNullOrEmpty(username)) {
            System.out.println("Cannot be null or empty");
            continue;
          }
          chatClient.sendMessage(username);
          response = serverInputReader.readLine();
          invalidUsername = response.startsWith("[SERVER]");
          if (invalidUsername) {
            String formatted = MessageFormatter.formatStringColour(Colour.WHITE, response);
            System.out.println(formatted);
          }
        }

        // Wait for Server Welcome Message
        String formattedWelcomeMessage = MessageFormatter.formatStringColour(Colour.GREEN, response);
        System.out.println(formattedWelcomeMessage);

        String serverAnnouncementsInfoMessage = MessageFormatter.formatServerAnnouncementsInfoMessage(Colour.WHITE, "WHITE");
        String privateMessagesInfoMessage = MessageFormatter.formatPrivateMessagesInfoMessage(Colour.CYAN, "CYAN");
        System.out.println(serverAnnouncementsInfoMessage);
        System.out.println(privateMessagesInfoMessage);

        // 4. Start (Runnable) handler for simplechat Server messages
        ServerMessageHandler serverMessageHandler = new ServerMessageHandler(serverInputReader);
        Thread serverMessageHandlerThread = new Thread(serverMessageHandler);
        serverMessageHandlerThread.start();

        // 5. Handle user input for sending messages and parsing commands
        while (true) {
          String message = inputReader.readLine();
          chatClient.sendMessage(message);
        }
      } catch (UnknownHostException unknownHostException) {
        System.out.println(Client.Error.UNKNOWN_HOST);
      } catch (ConnectException connectException) {
        System.out.println(Client.Error.CONNECT);
      } catch (IOException ioException) {   
        System.out.println(MessageFormatter.formatException(ioException));
      } catch (Exception exception) {
        System.out.println(MessageFormatter.formatException(exception));
      }
    }
}