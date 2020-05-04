package parse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageParser {
  
  private final Pattern privateMessagePattern = Pattern.compile(constant.Message.PRIVATE_MESSAGE_PATTERN);

  public ParseResult<Message> parsePrivateMessage(String message) {
    Matcher matcher = getMatcher(privateMessagePattern, message);

    if (!matcher.matches()) {
      return new ParseResult<Message>(false);
    }

    String username = matcher.group(1);
    String privateMessage = matcher.group(2);

    Message result = new parse.Message(username, privateMessage);

    return new ParseResult<Message>(true, result);
  }

  private final Pattern serverAnnouncementPattern = Pattern.compile(constant.Message.SERVER_ANNOUNCEMENT_PATTERN);

  public Boolean isServerAnnouncement(String message) {
    Matcher matcher = getMatcher(serverAnnouncementPattern, message);

    return matcher.matches();
  }

  private final Pattern privateMessageFormattedPattern = Pattern.compile(constant.Message.PRIVATE_MESSAGE_FORMATTED_PATTERN);

  public Boolean isPrivateMessage(String message) {
    Matcher matcher = getMatcher(privateMessageFormattedPattern, message);

    return matcher.matches();
  }

  private Matcher getMatcher(Pattern pattern, String message) {
    String trimmedMessage = message.trim();
    return pattern.matcher(trimmedMessage);
  }
}