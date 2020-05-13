package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import configuration.SocketConfiguration;
import configuration.SocketConfigurationBuilder;
import configuration.SocketConfigurationMapper;
import configuration.SocketConfigurationSetterFactory;
import constant.Client;
import constant.Ansi.Colour;
import constant.Server;
import format.StringFormatter;

public class ChatBot {
  
  public static void main(String[] args) {
    SocketConfiguration socketConfiguration = new SocketConfigurationBuilder()
      .withDefaultAddress(Server.DEFAULT_ADDRESS)
      .withDefaultPort(Server.DEFAULT_PORT)
      .withAddressMapper(new SocketConfigurationMapper(Client.Param.HOST, SocketConfigurationSetterFactory.addressSetter))
      .withPortMapper(new SocketConfigurationMapper(Client.Param.PORT, SocketConfigurationSetterFactory.portSetter))
      .buildFromCommandLine(args);

    try (SimpleChatBot bot = new SimpleChatBot(socketConfiguration)) {
      BufferedReader serverInputReader = new BufferedReader(new InputStreamReader(bot.getReadStream()));      

      bot.sendMessage(constant.SimpleChatBot.NAME);
      String formattedWelcomeMessage = StringFormatter.formatStringColour(Colour.GREEN, serverInputReader.readLine());
      System.out.println(formattedWelcomeMessage);

      while (true) {
        String response = serverInputReader.readLine();
        bot.sendMessage(constant.SimpleChatBot.NAME, response + "from meeee");
      }
    } catch (Exception exception) {
      System.out.println(StringFormatter.formatException(exception));
    }
  }
}