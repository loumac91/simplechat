package client;

import java.io.IOException;
import java.net.UnknownHostException;

import configuration.SocketConfiguration;

public class SimpleChatClient extends SimpleChatClientBase  {

  public SimpleChatClient(SocketConfiguration clientSocketConfiguration) throws UnknownHostException, IOException {
    super(clientSocketConfiguration);
  }

  public void sendMessage(String message) {
    this.streamWriter.println(message);
  }
}