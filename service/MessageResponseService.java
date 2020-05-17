package service;

import format.StringFormatter;
import parse.MessageContentParser;

public class MessageResponseService {
  
  private final MessageContentParser messageContentParser;
  private final DateTimeService dateTimeService;

  public MessageResponseService() {
    this.messageContentParser = new MessageContentParser();
    this.dateTimeService = new DateTimeService();
  }

  // Used by ChatBot to determine whether a users message contains a query about the day/time etc
  public String getDateQueryResponse(String messageContent) {
    String result = "";

    if (this.messageContentParser.containsCurrentTimeQuery(messageContent)){
      result = getCurrentTimeResponse();
    } else if (this.messageContentParser.containsCurrentDayQuery(messageContent)) {
      result = getCurrentDayResponse();
    }

    return result;
  }

  private String getCurrentTimeResponse() {
    String currentTime = dateTimeService.getCurrentTimeStamp();
    return StringFormatter.formatCurrentTimeMessage(currentTime);
  }

  private String getCurrentDayResponse() {
    String currentDay = dateTimeService.getCurrentDay();
    return StringFormatter.formatCurrentDayMessage(currentDay);
  }
}