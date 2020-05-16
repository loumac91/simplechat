package parse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import constant.MessageContent;

public class MessageContentParser {
  
  private final Pattern currentTimeQueryPattern = Pattern.compile(MessageContent.Pattern.CURRENT_TIME_QUERY, Pattern.CASE_INSENSITIVE);
  private final Pattern currentDayQueryPattern = Pattern.compile(MessageContent.Pattern.CURRENT_DAY_QUERY, Pattern.CASE_INSENSITIVE);

  public Boolean containsCurrentTimeQuery(String messageContent) {
    Matcher matcher = getMatcher(this.currentTimeQueryPattern, messageContent);
    
    return matcher.find();
  }

  public Boolean containsCurrentDayQuery(String messageContent) {
    Matcher matcher = getMatcher(this.currentDayQueryPattern, messageContent);
    
    return matcher.find();
  }

  private Matcher getMatcher(Pattern pattern, String messageContent) {
    String trimmedMessage = messageContent.trim();
    return pattern.matcher(trimmedMessage);
  }
}