package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.net.BindException;

import collection.ThreadSafeCollection;
import configuration.SocketConfiguration;
import constant.Server;
import format.StringFormatter;

public class SimpleChatServer implements AutoCloseable {
  
  private final int port;
  private final Object lock = new Object();
  private final ThreadSafeCollection<SimpleChatUser> simpleChatUsers;
  private final ServerSocket serverSocket;

  public SimpleChatServer(SocketConfiguration socketConfiguration) throws BindException, IOException {
    this.port = socketConfiguration.port;
    this.simpleChatUsers = new ThreadSafeCollection<SimpleChatUser>(new ArrayList<SimpleChatUser>());
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

  public void removeUser(SimpleChatUser user) {
    this.simpleChatUsers.remove(user);
  }

  // Synchronized used to ensure thread safety
  public SimpleChatUser getChatUser(String username) {
    synchronized (this.lock) {
      return this.simpleChatUsers
        .stream()
        .filter(scu -> scu.getUsername().equals(username))
        .findFirst()
        .orElse(null);
    }
  }

  public ArrayList<SimpleChatUser> getChatUsers() {
    synchronized (this.lock) {
      return new ArrayList<SimpleChatUser>(this.simpleChatUsers);
    }
  }

  public void shutdown() {
    synchronized (this.lock) {
      for (SimpleChatUser simpleChatUser : this.simpleChatUsers) {
        sendMessage(Server.USERNAME, Server.Message.SHUTDOWN_MESSAGE, simpleChatUser);
        try {
          simpleChatUser.disconnect();
        } catch (IOException e) {
          String formatted = StringFormatter.formatExceptionDisconnectingUser(simpleChatUser.getUsername());
          System.out.println(formatted);
        }
      }   
    } 
  }

  public void broadCastMessage(SimpleChatUser user, String message) {
    broadCastMessage(user.getUsername(), user.getUserId(), message);
  }

  public void broadCastMessage(String senderUsername, Integer excludedUserId, String message) {
    synchronized (this.lock) {
      for (SimpleChatUser simpleChatUser : this.simpleChatUsers) {
        if (simpleChatUser.getUserId() != excludedUserId) {
          sendMessage(senderUsername, message, simpleChatUser);
        }
      }
    }
  }

  public void sendPrivateMessage(String sender, String message, SimpleChatUser recipient) {
    String formattedUsername = StringFormatter.formatPrivateMessageUsername(sender);
    sendMessage(formattedUsername, message, recipient);
  }

  public void sendPrivateErrorMessage(SimpleChatUser user, String error) {
    String formattedUsername = StringFormatter.formatPrivateMessageUsername(Server.USERNAME);
    sendMessage(formattedUsername, error, user);
  }

  public void sendErrorMessage(SimpleChatUser user, String error) {    
    sendMessage(Server.USERNAME, error, user);
  }

  private void sendMessage(String username, String message, SimpleChatUser recipient) {
    String formattedChatMessage = StringFormatter.formatChatMessage(username, message);
    recipient.sendMessage(formattedChatMessage);
  }
}