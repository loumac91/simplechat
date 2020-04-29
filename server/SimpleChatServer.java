package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Collection;
import java.net.BindException;
import configuration.SocketConfiguration;

public class SimpleChatServer implements AutoCloseable {
  private final int port;

  private ArrayList<SimpleChatUser> simpleChatUsers; // this might not be thread safe
  private ServerSocket serverSocket;

  public SimpleChatServer(SocketConfiguration socketConfiguration) throws BindException, IOException {
    this.port = socketConfiguration.port;
    this.simpleChatUsers = new ArrayList<SimpleChatUser>();
    this.serverSocket = new ServerSocket(this.port);
  }

  public Socket getNextClient() throws IOException {
    return this.serverSocket.accept();
  }

  public void close() throws IOException {
    this.serverSocket.close();
  }

  public void addUser(SimpleChatUser user) {
    this.simpleChatUsers.add(user);
  }

  public void broadCastMessage(SimpleChatUser user, String message) {
    Collection<SimpleChatUser> filtered = this.simpleChatUsers.stream()
      .filter(u -> u.getUserId() != user.getUserId())
      .collect(Collectors.toList());
    
    
    System.out.println(filtered);
    for (SimpleChatUser simpleChatUser : filtered) {
      simpleChatUser.sendMessage(message);
    }
  }
}