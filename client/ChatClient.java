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
        System.out.println(MessageFormatter.getConnectingMessage(socketConfiguration.address, socketConfiguration.port));
        chatClient.connect();
        System.out.println(MessageFormatter.getConnectedMessage(socketConfiguration.address, socketConfiguration.port));

        // 2. Get Client username
        System.out.print(Client.Prompt.USERNAME_PROMPT);
        String username = inputReader.readLine();

        // 3. Post username to simplechat Server
        chatClient.sendMessage(username); // No contract here, just convention driven
        BufferedReader serverInputReader = new BufferedReader(new InputStreamReader(chatClient.getReadStream()));
        
        // Wait for Server Welcome Message
        String serverMessage = serverInputReader.readLine();
        System.out.println(serverMessage);

        // 4. Start (Runnable) handler for simplechat Server messages
        ServerMessageHandler serverMessageHandler = new ServerMessageHandler(serverInputReader);
        Thread serverMessageHandlerThread = new Thread(serverMessageHandler);
        serverMessageHandlerThread.start();

        // 5. Handle user input for sending messages and parsing commands
        String message = "";
        Boolean connected = true; // This is never set to false
        while (connected) {
          message = inputReader.readLine();
          chatClient.sendMessage(message);
        }
      } catch (UnknownHostException unknownHostException) {
        unknownHostException.getStackTrace();
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    }
}