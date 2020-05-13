package server;

import java.io.*;
import java.net.Socket;

import util.StringUtils;

public class SimpleChatUser {
  
  private static int userIdCount = 0; // Counter across all SimpleChatUser instances
  
  private final Socket userSocket;
  private final BufferedReader inputReader;
  private final PrintWriter streamWriter;
  private final int userId;
  private String username;

  public SimpleChatUser(Socket userSocket) throws IOException {
    this.userSocket = userSocket;
    this.inputReader = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
    this.streamWriter = new PrintWriter(userSocket.getOutputStream(), true);
    this.userId = ++userIdCount;
  }

  public int getUserId() {
    return this.userId;
  }

  public String getUsername() {
    return StringUtils.isNullOrEmpty(this.username) 
      ? Integer.toString(this.userId) 
      : this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void sendMessage(String message) {
    this.streamWriter.println(message);;
  }

  public String readMessage() throws IOException {
    return this.inputReader.readLine();
  }

  public void disconnect() throws IOException {
    this.userSocket.close();
  }
}