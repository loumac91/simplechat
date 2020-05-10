package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.net.BindException;

import collection.ThreadSafeCollection;
import configuration.SocketConfiguration;
import constant.Server;
import format.MessageFormatter;

public class SimpleChatServer implements AutoCloseable {
  private final int port;

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

  public SimpleChatUser getChatUser(String username) {
    synchronized (this.simpleChatUsers) {
      return this.simpleChatUsers
        .stream()
        .filter(scu -> scu.getUsername().equals(username))
        .findFirst()
        .orElse(null);
    }
  }

  public ArrayList<SimpleChatUser> getChatUsers() {
    synchronized (this.simpleChatUsers) {
      return new ArrayList<SimpleChatUser>(this.simpleChatUsers);
    }
  }

  public void shutdown() {
    synchronized (this.simpleChatUsers) {
      for (SimpleChatUser simpleChatUser : this.simpleChatUsers) {
        sendMessage(Server.USERNAME, Server.Message.SHUTDOWN_MESSAGE, simpleChatUser);
        try {
          simpleChatUser.disconnect();
        } catch (IOException e) {
          System.out.println("Error disconnecting user: " + simpleChatUser.getUsername());
        }
      }   
    } 
  }

  public void broadCastMessage(SimpleChatUser user, String message) {
    broadCastMessage(user.getUsername(), user.getUserId(), message);
  }

  public void broadCastMessage(String senderUsername, Integer excludedUserId, String message) {
    synchronized (this.simpleChatUsers) {
      for (SimpleChatUser simpleChatUser : this.simpleChatUsers) {
        if (simpleChatUser.getUserId() != excludedUserId) {
          sendMessage(senderUsername, message, simpleChatUser);
        }
      }
    }
  }

  public void sendPrivateMessage(String sender, String message, SimpleChatUser recipient) {
    String formattedUsername = MessageFormatter.formatPrivateMessageUsername(sender);
    sendMessage(formattedUsername, message, recipient);
  }

  public void sendErrorMessage(SimpleChatUser user, String error) {
    sendMessage(Server.USERNAME, error, user);
  }

  private void sendMessage(String username, String message, SimpleChatUser recipient) {
    String formattedChatMessage = MessageFormatter.formatChatMessage(username, message);
    recipient.sendMessage(formattedChatMessage);
  }
}