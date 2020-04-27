package client;

import java.io.*;

public class ServerMessageHandler implements Runnable {

  private BufferedReader serverReader;

  public ServerMessageHandler(InputStream serverReadStream) {
    this.serverReader = new BufferedReader(new InputStreamReader(serverReadStream));
  }

  public void run() {
    Boolean connected = true;
    while (connected) {
      try {
        System.out.println(this.serverReader.readLine());
      } catch (Exception e) {
        connected = false;
      }
    }
  }
}