package client;

import java.io.*;
import java.net.UnknownHostException;
import constants.*;

public class Main {
    public static void main(String[] args) {
      BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

      // 1. Establish (AutoCloseable) connection to simplechat Server 
      try (SimpleChatClient chatClient = new SimpleChatClient(Server.ADDRESS, Server.PORT)) {
        System.out.println(String.format(MessageFormats.Client.CONNECTING, Server.ADDRESS, Server.PORT));
        chatClient.connect();
        System.out.println(String.format(MessageFormats.Client.CONNECTED, Server.ADDRESS, Server.PORT));

        // 2. Get Client username
        System.out.println(String.format(MessageFormats.Client.USERNAME_PROMPT));
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