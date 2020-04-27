package client;

import java.io.*;
import java.net.UnknownHostException;

import configuration.*;
import constant.*;
import format.MessageFormatter;

public class ChatClient {
    public static void main(String[] args) {
      BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
      SocketConfiguration socketConfiguration = new SocketConfigurationBuilder()
        .setDefaultAddress(Server.DEFAULT_ADDRESS)
        .setDefaultPort(Server.DEFAULT_PORT)
        .setAddressMapper(new SocketConfigurationMapper(Client.Param.HOST, SocketConfigurationSetterFactory.addressSetter))
        .setPortMapper(new SocketConfigurationMapper(Client.Param.PORT, SocketConfigurationSetterFactory.portSetter))
        .buildFromCommandLine(args);

      // 1. Establish (AutoCloseable) connection to simplechat Server 
      try (SimpleChatClient chatClient = new SimpleChatClient(socketConfiguration)) {
        System.out.println(MessageFormatter.getConnectingMessage(socketConfiguration.address, socketConfiguration.port));
        chatClient.connect();
        System.out.println(MessageFormatter.getConnectedMessage(socketConfiguration.address, socketConfiguration.port));

        // 2. Get Client username
        System.out.println(MessageFormats.Client.USERNAME_PROMPT);
        String userName = inputReader.readLine();

        // 3. Post username to simplechat Server
        chatClient.sendMessage(userName);

        // 4. Start (Runnable) handler for simplechat Server messages
        ServerMessageHandler serverMessageHandler = new ServerMessageHandler(chatClient.getReadStream());
        Thread serverMessageHandlerThread = new Thread(serverMessageHandler);
        serverMessageHandlerThread.start();

        // 5. Handle user input for sending messages and parsing commands
        String userInput = "";
        Boolean chatting = true;
        while (chatting) {
          userInput = inputReader.readLine();
          chatClient.sendMessage(userInput);
        }
      } catch (UnknownHostException unknownHostException) {
        unknownHostException.getStackTrace();
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    }
}