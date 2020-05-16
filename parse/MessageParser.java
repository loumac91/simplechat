package parse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import constant.Message;
import strategy.Result;

public class MessageParser {
  
  private final Integer usernameIndex = 1;
  private final Integer messageIndex = 2;
  private final Pattern privateMessagePattern = Pattern.compile(Message.Pattern.PRIVATE_MESSAGE_PATTERN);
  private final Pattern serverAnnouncementPattern = Pattern.compile(Message.Pattern.SERVER_ANNOUNCEMENT_PATTERN);
  private final Pattern privateMessageFormattedPattern = Pattern.compile(Message.Pattern.PRIVATE_MESSAGE_FORMATTED_PATTERN);
  private final Pattern messageFormattedPattern = Pattern.compile(Message.Pattern.MESSAGE_FORMATTED_PATTERN);

  public Result<parse.Message> parsePrivateMessage(String message) {
    Result<parse.Message> result = new Result<parse.Message>();
    String errorMessage = "";
    parse.Message parseMessage = null;

    if (!message.contains(constant.Message.PRIVATE_MESSAGE_TOKEN)) {
      errorMessage = Message.Pattern.Error.MESSAGE_CONTAINS_NO_PRIVATE_TOKEN;
    } else {
      Matcher matcher = getMatcher(this.privateMessagePattern, message);

      if (!matcher.matches()) {
        errorMessage = Message.Pattern.Error.MESSAGE_IS_NOT_PRIVATE;
      } else {
        parseMessage = getMessage(matcher);
      }
    }

    result.setErrorMessage(errorMessage);
    result.setValue(parseMessage);

    return result;
  }

  public Result<parse.Message> parseRecievedPrivateMessage(String message) {
    Result<parse.Message> result = new Result<parse.Message>();
    String errorMessage = "";
    parse.Message parseMessage = null;

    if (!message.contains(constant.Message.PRIVATE_MESSAGE_USERNAME_PREFIX)) {
      errorMessage = Message.Pattern.Error.MESSAGE_CONTAINS_NO_PRIVATE_MESSAGE_USERNAME_PREFIX;
    } else {
      Matcher matcher = getMatcher(this.privateMessageFormattedPattern, message);

      if (!matcher.matches()) {
        errorMessage = Message.Pattern.Error.MESSAGE_IS_NOT_PRIVATE;
      } else {    
        parseMessage = getMessage(matcher);
      }
    }

    result.setErrorMessage(errorMessage);
    result.setValue(parseMessage);

    return result;
  }

  public Result<parse.Message> parseReceivedMessage(String message) {
    Result<parse.Message> result = new Result<parse.Message>();
    String errorMessage = "";
    parse.Message parseMessage = null;

    Matcher matcher = getMatcher(this.messageFormattedPattern, message);

    if (!matcher.matches()) {
      errorMessage = Message.Pattern.Error.MESSAGE_COULD_NOT_BE_PARSED;
    } else {    
      parseMessage = getMessage(matcher);
    }

    result.setErrorMessage(errorMessage);
    result.setValue(parseMessage);

    return result;
  }

  public Boolean isServerAnnouncement(String message) {
    Matcher matcher = getMatcher(this.serverAnnouncementPattern, message);

    return matcher.matches();
  }
  
  public Boolean isPrivateMessage(String message) {
    Matcher matcher = getMatcher(this.privateMessageFormattedPattern, message);

    return matcher.matches();
  }

  private Matcher getMatcher(Pattern pattern, String message) {
    String trimmedMessage = message.trim();
    return pattern.matcher(trimmedMessage);
  }

  private parse.Message getMessage(Matcher matcher) {
    String username = matcher.group(this.usernameIndex);
    String message = matcher.group(this.messageIndex);

    return new parse.Message(username, message);
  }
}