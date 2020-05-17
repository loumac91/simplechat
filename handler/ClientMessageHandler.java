package handler;

import java.io.IOException;
import java.net.SocketException;

import constant.Server;
import format.StringFormatter;
import server.SimpleChatServer;
import server.SimpleChatUser;
import parse.*;
import strategy.Result;

// Runnable handler for transmitting messages received from a client socket

public class ClientMessageHandler extends BaseHandler {

  private final SimpleChatServer chatServer;
  private final SimpleChatUser chatUser;

  public ClientMessageHandler(SimpleChatServer chatServer, SimpleChatUser chatUser) {
    super();
    this.chatServer = chatServer;
    this.chatUser = chatUser;
  }

  public void run() {
    this.running = true;
    while (this.running) {
      try {
        String message = this.chatUser.readMessage(); // Wait for next message

        Result<Message> privateMessageResult = this.messageParser.parsePrivateMessage(message);
        if (privateMessageResult.getIsSuccess()) {
          handlePrivateMessage(privateMessageResult);
          continue;
        }

        this.chatServer.broadCastMessage(this.chatUser, message);
      } catch (SocketException socketException) {
        this.running = false;
        String formatted = StringFormatter.formatUserMessageHandlingInterruptedError(this.chatUser.getUsername());
        System.out.println(formatted);
      } catch (IOException ioException) {
        this.running = false;
        System.out.println(StringFormatter.formatException(ioException));
      }
    }
    
    disconnectUser();
  }

  private void handlePrivateMessage(Result<Message> privateMessageResult) {
    Message privateMessage = privateMessageResult.getValue();
    String recipientName = privateMessage.getUsername();

    if (recipientName.equals(Server.USERNAME)) {
      // If user private messages server, then respond with welcome message
      String welcomeMessage = StringFormatter.formatWelcomeMessage(this.chatUser.getUsername());
      this.chatServer.sendPrivateMessage(Server.USERNAME, welcomeMessage, this.chatUser);
      return;
    }

    SimpleChatUser recipient = this.chatServer.getChatUser(privateMessage.getUsername());

    if (recipient != null) { 
      this.chatServer.sendPrivateMessage(this.chatUser.getUsername(), privateMessage.getMessageContent(), recipient);
    } else { 
      // If can't find recipient, tell sender their message wasn't delivered
      String privateMessageError = StringFormatter.formatPrivateMessageRecipientNotFound(recipientName);
      this.chatServer.sendErrorMessage(this.chatUser, privateMessageError);
    }
  }

  private void disconnectUser() {
    String userDisconnectMessage = StringFormatter.formatUserDisconnectedMessage(this.chatUser.getUsername());
    System.out.println(StringFormatter.formatServerLog(userDisconnectMessage));
    this.chatServer.removeUser(this.chatUser);
    this.chatServer.broadCastMessage(Server.USERNAME, this.chatUser.getUserId(), userDisconnectMessage);
  }
}