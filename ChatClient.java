import java.io.*;
import java.net.ConnectException;
import java.net.UnknownHostException;

import handler.HandlerFactory;
import input.BaseInputParser;
import input.UserInputParser;
import strategy.parse.UserInputParseStrategy;
import strategy.validate.UsernameValidationStrategy;
import configuration.*;
import constant.Server;
import constant.Client;
import constant.Ansi.Colour;
import format.StringFormatter;
import client.SimpleChatClient;

public class ChatClient {

  public static void main(String[] args) {
    BaseInputParser<String> userInputParser = new UserInputParser<String>(System.in);

    // 1. Read socket configuration from command line arguments (default values are also provided)
    SocketConfiguration socketConfiguration = new SocketConfigurationBuilder()
      .withDefaultAddress(Server.DEFAULT_ADDRESS)
      .withDefaultPort(Server.DEFAULT_PORT)
      .withAddressMapper(new SocketConfigurationMapper(Client.Param.HOST, SocketConfigurationSetterFactory.addressSetter))
      .withPortMapper(new SocketConfigurationMapper(Client.Param.PORT, SocketConfigurationSetterFactory.portSetter))
      .buildFromCommandLine(args);

    // 2. Attempt to connect to chat server
    String connectingMessage = StringFormatter.formatConnectingMessage(socketConfiguration.address, socketConfiguration.port);
    System.out.println(StringFormatter.formatStringColour(Colour.YELLOW, connectingMessage));

    try (SimpleChatClient chatClient = new SimpleChatClient(socketConfiguration)) {
      String connectedMessage = StringFormatter.formatConnectedMessage(socketConfiguration.address, socketConfiguration.port);
      System.out.println(StringFormatter.formatStringColour(Colour.GREEN, connectedMessage));

      BufferedReader serverInputReader = new BufferedReader(new InputStreamReader(chatClient.getReadStream()));

      // 2. Get Client username
      String username = userInputParser.parseInput(
        Client.Prompt.USERNAME_PROMPT, 
        new UserInputParseStrategy(Client.USERNAME),
        new UsernameValidationStrategy(chatClient, serverInputReader)
      );
      

      //TODO use username to print message 
      // your messages will appear as [username]: xsa
      // to recipients
      String serverAnnouncementsInfoMessage = StringFormatter.formatServerAnnouncementsInfoMessage(Colour.WHITE, "WHITE");
      String privateMessagesInfoMessage = StringFormatter.formatPrivateMessagesInfoMessage(Colour.CYAN, "CYAN");
      System.out.println(serverAnnouncementsInfoMessage);
      System.out.println(privateMessagesInfoMessage);

      // 4. Start (Runnable) handler for simplechat Server messages
      new Thread(HandlerFactory.createServerMessageHandler(serverInputReader)).start();

      // 5. Handle user input for sending messages and parsing commands
      while (true) {        
        String message = userInputParser.getUnparsedInput();
        chatClient.sendMessage(message);
      }
    } catch (UnknownHostException unknownHostException) {
      System.out.println(Client.Error.UNKNOWN_HOST);
    } catch (ConnectException connectException) {
      System.out.println(Client.Error.CONNECT);
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