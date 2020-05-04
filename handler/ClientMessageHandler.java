package handler;

import java.io.IOException;
import server.SimpleChatServer;
import server.SimpleChatUser;
import constant.Message;
import parse.*;

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
        String message = this.chatUser.readMessage();

        Boolean containsPrivateMessageToken = message.contains(Message.PRIVATE_MESSAGE_TOKEN);
        if (containsPrivateMessageToken) {
          ParseResult<parse.Message> privateMessageParseResult = new MessageParser().parsePrivateMessage(message);
          if (privateMessageParseResult.getIsValid()) {
            parse.Message privateMessage = privateMessageParseResult.getValue();
            Boolean sent = this.chatServer.sendPrivateMessage(
              this.chatUser, 
              privateMessage.getUsername(), 
              privateMessage.getMessage()
            );

            if (sent) {
              continue;
            }
          }
        }

        this.chatServer.broadCastMessage(this.chatUser, message);
      } catch (IOException ioException) {
        this.running = false;
        ioException.printStackTrace();
      }
    }
  }

  // private Boolean isPrivateMessage(String message) {
  //   // trim start?
  // }
}