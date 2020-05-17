import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.UnknownHostException;

import configuration.SocketConfiguration;
import configuration.SocketConfigurationBuilder;
import configuration.SocketConfigurationMapper;
import configuration.SocketConfigurationSetterFactory;
import constant.Client;
import constant.Ansi.Colour;
import constant.Server;
import format.StringFormatter;
import parse.MessageParser;
import service.MessageResponseService;
import parse.Message;
import strategy.Result;
import util.StringUtils;
import client.SimpleChatBot;

public class ChatBot {
  
  public static void main(String[] args) {
    // 1. Read socket configuration from command line arguments (default values are also provided)
    SocketConfiguration socketConfiguration = new SocketConfigurationBuilder()
      .withDefaultAddress(Server.DEFAULT_ADDRESS)
      .withDefaultPort(Server.DEFAULT_PORT)
      .withAddressMapper(new SocketConfigurationMapper(Client.Param.HOST, SocketConfigurationSetterFactory.addressSetter))
      .withPortMapper(new SocketConfigurationMapper(Client.Param.PORT, SocketConfigurationSetterFactory.portSetter))
      .buildFromCommandLine(args);

    // 2. Initialise chat with configurations - will close socket on any exception
    try (SimpleChatBot bot = new SimpleChatBot(socketConfiguration)) {
      BufferedReader serverInputReader = new BufferedReader(new InputStreamReader(bot.getReadStream())); 
      MessageParser messageParser = new MessageParser();
      MessageResponseService messageResponseService = new MessageResponseService();

      // 3. Send bot name to server to register
      bot.sendMessage(constant.SimpleChatBot.NAME);
      String formattedWelcomeMessage = StringFormatter.formatStringColour(Colour.GREEN, serverInputReader.readLine());
      System.out.println(formattedWelcomeMessage);
      
      // 4. Observe all user messages sent from server
      while (true) {
        String response = serverInputReader.readLine();

        if (messageParser.isServerAnnouncement(response)) {
          continue;
        }

        Result<Message> parsedMessage = messageParser.tryParseMessage(response);
        if (!parsedMessage.getIsSuccess()) {
          continue;
        }

        Message message = parsedMessage.getValue();
        String messageContent = message.getMessageContent(); 
        String botResponse = messageResponseService.getDateQueryResponse(messageContent);

        if (StringUtils.isNullEmptyOrWhitespace(botResponse)) {
          continue;
        }

        Boolean isPrivate = messageParser.isPrivateMessage(response);  
        if (isPrivate) {
          bot.sendPrivateMessage(message.getUsername(), botResponse);
        } else {
          bot.sendMessage(botResponse);
        }
      }
    } catch (UnknownHostException unknownHostException) {
      System.out.println(Client.Error.UNKNOWN_HOST);
    } catch (ConnectException connectException) {
      System.out.println(Client.Error.CONNECT);
    } catch (IOException ioException) {
      System.out.println(StringFormatter.formatException(ioException));
    } catch (Exception exception) {
      System.out.println(StringFormatter.formatException(exception));
    }
  }
}