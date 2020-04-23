import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket implements AutoCloseable  {
  private final String address;
  private final int port;

  private Socket socket;
  private BufferedReader readStream;
  private PrintWriter writeStream;

  public ClientSocket(String address, int port) {
    this.address = address;
    this.port = port;
  }

  public void connect() throws UnknownHostException, IOException {
    this.connectSocket();
    this.readStream = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    this.writeStream = new PrintWriter(this.socket.getOutputStream(), true);
  }

  public String readNextMessage() throws IOException {
    return this.readStream.readLine();
  }

  public void sendMessage(String input) {
    this.writeStream.println(input);
  }

  public void close() throws IOException {
    this.readStream.close();
    this.writeStream.close();
    this.socket.close();
  }

  private void connectSocket() throws UnknownHostException, IOException {
    this.socket = new Socket(this.address, this.port);
  }
}