package handler;

import java.io.*;
import java.net.SocketException;

public class ServerMessageHandler extends InputReaderHandler {

  public ServerMessageHandler(BufferedReader inputReader) {
    super(inputReader);
  }

  public void run() {
    this.running = true;
    while (this.running) {
      try {
        String serverMessage = this.inputReader.readLine();
        System.out.println(serverMessage);
      } catch (SocketException socketException) {
        this.running = false;
        socketException.printStackTrace();
      } catch (IOException ioException) {
        this.running = false;
        ioException.printStackTrace();
      }
    }
  }
}