package parse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import constant.Message;

public class MessageParser {
  
  private final Integer usernameIndex = 1;
  private final Integer privateMessageIndex = 2;
  private final Pattern privateMessagePattern = Pattern.compile(Message.Pattern.PRIVATE_MESSAGE_PATTERN);
  private final Pattern serverAnnouncementPattern = Pattern.compile(Message.Pattern.SERVER_ANNOUNCEMENT_PATTERN);
  private final Pattern privateMessageFormattedPattern = Pattern.compile(Message.Pattern.PRIVATE_MESSAGE_FORMATTED_PATTERN);

  public ParseResult<parse.Message> parsePrivateMessage(String message) {
    if (!message.contains(constant.Message.PRIVATE_MESSAGE_TOKEN)) {
      return new ParseResult<parse.Message>(false);
    }

    Matcher matcher = getMatcher(privateMessagePattern, message);

    if (!matcher.matches()) {
      return new ParseResult<parse.Message>(false);
    }

    String username = matcher.group(this.usernameIndex);
    String privateMessage = matcher.group(this.privateMessageIndex);

    parse.Message result = new parse.Message(username, privateMessage);

    return new ParseResult<parse.Message>(true, result);
  }

  public Boolean isServerAnnouncement(String message) {
    Matcher matcher = getMatcher(serverAnnouncementPattern, message);

    return matcher.matches();
  }
  
  public Boolean isPrivateMessage(String message) {
    Matcher matcher = getMatcher(privateMessageFormattedPattern, message);

    return matcher.matches();
  }

  private Matcher getMatcher(Pattern pattern, String message) {
    String trimmedMessage = message.trim();
    return pattern.matcher(trimmedMessage);
  }
}