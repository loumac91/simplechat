package handler;

import java.io.*;

public class ServerMessageHandler extends InputReaderHandler {

  public ServerMessageHandler(InputStream serverReadStream) {
    super(serverReadStream);
  }

  public void run() {
    this.running = true;
    while (this.running) {
      try {
        String serverMessage = this.inputReader.readLine();
        System.out.println(serverMessage);
      } catch (IOException ioException) {
        this.running = false;
        ioException.printStackTrace();
      }
    }
  }
}