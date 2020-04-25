package client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class SimpleChatClient implements AutoCloseable  {
  private final String address;
  private final int port;

  private Socket socket;
  private PrintWriter streamWriter;

  public SimpleChatClient(String address, int port) {
    this.address = address;
    this.port = port;
  }

  public void connect() throws UnknownHostException, IOException {
    this.connectSocket();
    this.streamWriter = new PrintWriter(this.socket.getOutputStream(), true);
  }

  public InputStream getReadStream() throws IOException {
    return this.socket.getInputStream();
  }

  public void sendMessage(String message) {
    this.streamWriter.println(message);
  }

  public void close() throws IOException {
    this.streamWriter.close();
    this.socket.close();
  }

  private void connectSocket() throws UnknownHostException, IOException {
    this.socket = new Socket(this.address, this.port);
  }
}