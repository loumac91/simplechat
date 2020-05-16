package service;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import constant.Format;
import util.StringUtils;

public class DateTimeService {

  private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Format.TIMESTAMP);

  public ZonedDateTime getDateTime() {
    return ZonedDateTime.now(ZoneId.systemDefault());
  }

  public String getCurrentTimeStamp() {
    ZonedDateTime dateTime = getDateTime();
    return dateTime.format(dateTimeFormatter);
  }

  public String getCurrentDay() {
    ZonedDateTime dateTime = getDateTime();
    DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
    return StringUtils.capitalise(dayOfWeek.toString());
  }
}