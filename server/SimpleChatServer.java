package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.net.BindException;
import configuration.SocketConfiguration;
import constant.Server;
import format.MessageFormatter;

public class SimpleChatServer implements AutoCloseable {
  private final int port;

  private final ArrayList<SimpleChatUser> simpleChatUsers; // this might not be thread safe
  private final ServerSocket serverSocket;

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

  public void shutdown() {
    for (SimpleChatUser simpleChatUser : this.simpleChatUsers) {
      SendMessage(Server.USERNAME, Server.Message.SHUTDOWN_MESSAGE, simpleChatUser);
      try {
        simpleChatUser.disconnect();
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }

  public void broadCastMessage(SimpleChatUser user, String message) {
    String username = user.getUsername();
    for (SimpleChatUser simpleChatUser : this.simpleChatUsers) {
      if (simpleChatUser.getUserId() != user.getUserId()) {
        SendMessage(username, message, simpleChatUser);
      }
    }
  }

  public Boolean sendPrivateMessage(SimpleChatUser user, String recipientUsername, String message) {
    SimpleChatUser recipient = this.simpleChatUsers.stream() // Convert the collection to a stream
      .filter(scu -> scu.getUsername().equals(recipientUsername)) // Filter by the username we want
      .findFirst()
      .orElse(null);

    if (recipient == null) {
      return false;
    }

    String formattedUsername = MessageFormatter.formatPrivateMessageUsername(user.getUsername());
    SendMessage(formattedUsername, message, recipient);
    return true;
  }

  private void SendMessage(String username, String message, SimpleChatUser recipient) {
    String formattedChatMessage = MessageFormatter.formatChatMessage(username, message);
    recipient.sendMessage(formattedChatMessage);
  }
}