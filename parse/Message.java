package parse;

public class Message {
  
  private final String username;
  private final String messageContent;

  public Message(String username, String messageContent) {
    this.username = username;
    this.messageContent = messageContent;
  }

  public String getUsername() {
    return this.username;
  }

  public String getMessageContent() {
    return this.messageContent;
  }
}