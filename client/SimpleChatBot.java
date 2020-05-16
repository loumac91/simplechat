package client;

import java.io.IOException;
import java.net.UnknownHostException;

import configuration.SocketConfiguration;
import format.StringFormatter;

public class SimpleChatBot extends SimpleChatClientBase {

  public SimpleChatBot(SocketConfiguration clientSocketConfiguration) throws UnknownHostException, IOException {
    super(clientSocketConfiguration);
  }

  public void sendMessage(String message) {
    this.streamWriter.println(message);
  }

  public void sendPrivateMessage(String recipient, String message) {
    String formatted = StringFormatter.formatPrivateMessage(recipient, message);
    sendMessage(formatted);
  }
}