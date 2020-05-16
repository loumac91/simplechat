package service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import constant.Format;

public class DateTimeService {
  
  private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Format.TIMESTAMP);

  public ZonedDateTime getDateTime() {
    return ZonedDateTime.now(ZoneId.systemDefault());
  }

  public String getCurrentTimeStamp() {
    ZonedDateTime dateTime = getDateTime();
    return dateTime.format(dateTimeFormatter);
  }
}