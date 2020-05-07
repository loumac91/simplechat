package client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import configuration.SocketConfiguration;
import format.MessageFormatter;

public class SimpleChatClient implements AutoCloseable  {
  private final String address;
  private final int port;

  private Socket socket;
  private PrintWriter streamWriter;

  public SimpleChatClient(SocketConfiguration clientSocketConfiguration) throws UnknownHostException, IOException {
    this.address = clientSocketConfiguration.address;
    this.port = clientSocketConfiguration.port;
    this.socket = new Socket(this.address, this.port);
    this.streamWriter = new PrintWriter(this.socket.getOutputStream(), true);
  }

  public InputStream getReadStream() throws IOException {
    return this.socket.getInputStream();
  }

  public void sendMessage(String message) {
    this.streamWriter.println(message);
  }

  public void sendMessage(String username, String message) {
    String formatted = MessageFormatter.formatChatMessage(username, message);
    this.sendMessage(formatted);
  }

  public Boolean isConnected() {
    return !this.socket.isClosed();
  }

  @Override
  public void close() throws IOException {
    this.socket.close();
  }
}