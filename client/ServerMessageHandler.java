package client;

import java.io.InputStream;
import java.io.*;

public class ServerMessageHandler implements Runnable {

  private BufferedReader serverReader;

  public ServerMessageHandler(InputStream serverReadStream) {
    this.serverReader = new BufferedReader(new InputStreamReader(serverReadStream));
  }

  public void run() {
    while (true) {
      try {
        System.out.println(this.serverReader.readLine());
      } catch (Exception e) {
      }
    }
  }
}