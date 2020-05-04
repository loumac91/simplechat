package client;

import java.io.*;
import java.net.UnknownHostException;
import handler.ServerMessageHandler;
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

      // 1. Establish (AutoCloseable) connection to simplechat Server 
      try (SimpleChatClient chatClient = new SimpleChatClient(socketConfiguration)) {
        System.out.println(MessageFormatter.formatConnectingMessage(socketConfiguration.address, socketConfiguration.port));
        chatClient.connect();
        System.out.println(MessageFormatter.formatConnectedMessage(socketConfiguration.address, socketConfiguration.port));

        // 2. Get Client username
        System.out.print(Client.Prompt.USERNAME_PROMPT);
        String username = inputReader.readLine();

        // 3. Post username to simplechat Server
        chatClient.sendMessage(username); // No contract here, just convention driven
        BufferedReader serverInputReader = new BufferedReader(new InputStreamReader(chatClient.getReadStream()));
        
        // Wait for Server Welcome Message
        String welcomeMessage = serverInputReader.readLine();
        String formattedWelcomeMessage = MessageFormatter.formatStringColour(Client.Display.PRIVATE_MESSAGE_COLOUR, welcomeMessage);
        System.out.println(formattedWelcomeMessage);

        // Then print out instructions
        String serverMessageInformation = "";

        // Server messages will appear in white
        // private messages will appear in blue with a private: username prefix

        // 4. Start (Runnable) handler for simplechat Server messages
        ServerMessageHandler serverMessageHandler = new ServerMessageHandler(chatClient, inputReader, serverInputReader);
        Thread serverMessageHandlerThread = new Thread(serverMessageHandler);
        serverMessageHandlerThread.start();

        // 5. Handle user input for sending messages and parsing commands
        String message = "";
        while (true) {
          message = inputReader.readLine();
          if (!chatClient.isConnected()) break;
          chatClient.sendMessage(message);
        }
      } catch (UnknownHostException unknownHostException) {
        unknownHostException.getStackTrace();
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    }
}