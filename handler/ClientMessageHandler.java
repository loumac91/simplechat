package handler;

import java.io.IOException;

import constant.Server;
import format.MessageFormatter;
import server.SimpleChatServer;
import server.SimpleChatUser;
import parse.*;

public class ClientMessageHandler extends BaseHandler {

  private final SimpleChatServer chatServer;
  private final SimpleChatUser chatUser;
  private final MessageParser messageParser;

  public ClientMessageHandler(SimpleChatServer chatServer, SimpleChatUser chatUser) {
    super();
    this.chatServer = chatServer;
    this.chatUser = chatUser;
    this.messageParser = new MessageParser();
  }

  public void run() {
    this.running = true;
    while (this.running) {
      try {
        String message = this.chatUser.readMessage();

        ParseResult<Message> privateMessageParseResult = this.messageParser.parsePrivateMessage(message);
        if (privateMessageParseResult.getIsValid()) {
          Message privateMessage = privateMessageParseResult.getValue();
          String recipientName = privateMessage.getUsername();
          SimpleChatUser recipient = this.chatServer.getChatUser(privateMessage.getUsername());
          
          if (recipient != null) {
            this.chatServer.sendPrivateMessage(this.chatUser.getUsername(), privateMessage.getMessage(), recipient);            
          } else {
            String privateMessageError = MessageFormatter.formatPrivateMessageRecipientNotFound(recipientName);
            this.chatServer.sendErrorMessage(this.chatUser, privateMessageError);
          }

          continue;
        }

        this.chatServer.broadCastMessage(this.chatUser, message);
      } catch (IOException ioException) {
        this.running = false;
        String userDisconnectMessage = MessageFormatter.formatUserDisconnectedMessage(this.chatUser.getUsername());
        System.out.println(MessageFormatter.formatServerLog(userDisconnectMessage));
        this.chatServer.removeUser(this.chatUser);
        this.chatServer.broadCastMessage(Server.USERNAME, this.chatUser.getUserId(), userDisconnectMessage);
        
        System.out.println(MessageFormatter.formatException(ioException));
      }
    }
  }
}