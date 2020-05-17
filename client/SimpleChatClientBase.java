package client;

import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import configuration.SocketConfiguration;

// Base client class which implements autocloseable
// This ensures that the socket is closed if an exception occurs

public abstract class SimpleChatClientBase implements AutoCloseable {
  
  private final String address;
  private final int port;
  
  protected final Socket socket; // Protected so available to children but not publicly accessible
  protected final PrintWriter streamWriter;

  protected SimpleChatClientBase(SocketConfiguration clientSocketConfiguration) throws UnknownHostException, IOException {
    this.address = clientSocketConfiguration.address;
    this.port = clientSocketConfiguration.port;
    this.socket = new Socket(this.address, this.port);
    this.streamWriter = new PrintWriter(this.socket.getOutputStream(), true);
  }

  public abstract void sendMessage(String message);

  public InputStream getReadStream() throws IOException {
    return this.socket.getInputStream();
  }

  public Boolean isConnected() {
    return !this.socket.isClosed();
  }

  @Override
  public void close() throws IOException {
    this.socket.close(); // This will close the this.streamWriter aswell
  }
}