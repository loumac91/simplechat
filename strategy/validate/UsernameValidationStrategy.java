package strategy.validate;

import java.io.BufferedReader;
import java.io.IOException;

import client.SimpleChatClient;
import constant.Ansi.Colour;
import format.StringFormatter;
import parse.MessageParser;

public class UsernameValidationStrategy implements ValidationStrategy<String> {

  private final SimpleChatClient chatClient;
  private final BufferedReader serverInputReader;
  private final MessageParser messageParser;

  public UsernameValidationStrategy(SimpleChatClient chatClient, BufferedReader serverInputReader) {
    super();
    this.chatClient = chatClient;
    this.serverInputReader = serverInputReader;
    this.messageParser = new MessageParser();
  }  

  public ValidationResult<Boolean> isValid(String username) throws IOException {
    ValidationResult<Boolean> result = new ValidationResult<Boolean>();
    
    chatClient.sendMessage(username);

    String serverResponse = this.serverInputReader.readLine();

    Boolean isSuccess = !this.messageParser.isServerAnnouncement(serverResponse);

    result.setValue(isSuccess);

    if (isSuccess) {
      result.setValidationMessage(serverResponse);
    } else {
      String formatted = StringFormatter.formatStringColour(Colour.WHITE, serverResponse);
      result.setErrorMessage(formatted);
    }

    return result;
  }
}