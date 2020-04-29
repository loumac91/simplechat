package server;

import java.io.*;
import java.net.Socket;

public class SimpleChatUser {
  private static int userIdCount = 0; // Counter across all SimpleChatUser instances
  
  private final Socket userSocket;
  private final BufferedReader inputReader;
  private final PrintWriter streamWriter;
  private final int userId;
  private final String username;

  public SimpleChatUser(Socket userSocket) throws IOException {
    this.userSocket = userSocket;
    this.inputReader = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
    this.streamWriter = new PrintWriter(userSocket.getOutputStream(), true);
    this.userId = ++userIdCount;
    this.username = this.inputReader.readLine();
  }

  public int getUserId() {
    return this.userId;
  }

  public String getUsername() {
    return this.username;
  }

  public void sendMessage(String message) {
    this.streamWriter.println(message);;
  }

  public String readMessage() throws IOException {
    return this.inputReader.readLine();
  }
}