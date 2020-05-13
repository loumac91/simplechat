package client;

import java.io.IOException;
import java.net.UnknownHostException;

import configuration.SocketConfiguration;
import format.StringFormatter;

public class SimpleChatClient extends SimpleChatClientBase  {

  public SimpleChatClient(SocketConfiguration clientSocketConfiguration) throws UnknownHostException, IOException {
    super(clientSocketConfiguration);
  }

  public void sendMessage(String message) {
    this.streamWriter.println(message);
  }

  @Override
  public void sendMessage(String username, String message) {
    String formatted = StringFormatter.formatChatMessage(username, message);
    this.sendMessage(formatted);
  }
}