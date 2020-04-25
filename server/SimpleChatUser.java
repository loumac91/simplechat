package server;

import java.io.*;
import java.net.Socket;

public class SimpleChatUser {
  private static int userIdCount = 0; // Counter across all SimpleChatUser instances
  
  private Socket userSocket;
  private BufferedReader inputReader;
  private PrintWriter streamWriter;
  private int userId;
  private String userName;

  public SimpleChatUser(Socket userSocket) throws IOException {
    this.userSocket = userSocket;
    this.inputReader = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
    this.streamWriter = new PrintWriter(userSocket.getOutputStream(), true);
    this.userId = ++userIdCount;
    this.userName = this.inputReader.readLine();
  }

  public int getUserId() {
    return this.userId;
  }

  public String getUserName() {
    return this.userName;
  }

  public void sendMessage(String message) {
    this.streamWriter.println(message);;
  }

  public String readMessage() throws IOException {
    return this.inputReader.readLine();
  }
}