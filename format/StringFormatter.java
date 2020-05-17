package format;

import constant.Format;
import service.DateTimeService;

// Formatting class for provided strongly typed messages

public class StringFormatter {

  //#region ERROR

  public static String formatException(Exception exception) {
    return baseFormat(Format.Error.EXCEPTION, exception.getClass().getName(), exception.getMessage());
  }

  public static String formatInvalidPortError(Integer port) {
    return baseFormat(Format.Error.INVALID_PORT, port);
  }

  public static String formatInvalidAddressError(String address) {
    return baseFormat(Format.Error.INVALID_ADDRESS, address);
  }

  public static String formatEmptyValueNotPermittedError(String valueName) {
    return baseFormat(Format.Error.EMPTY_VALUE_NOT_PERMITTED, valueName);
  }

  public static String formatExceptionDisconnectingUser(String username) {
    return baseFormat(Format.Error.ERROR_DISCONNECTING_USER, username);
  }

  public static <T> String formatValueCannotBeParsed(String value, Class<T> cls) {
    String className = cls.getName();
    return baseFormat(Format.Error.VALUE_CANNOT_BE_PARSED, value, className);
  }

  public static String formatUserMessageHandlingInterruptedError(String username) {
    return baseFormat(Format.Error.USER_MESSAGE_HANDLING_INTERRUPTED, username);
  }

  //#endregion

  //#region COLOUR

  public static String formatStringColour(String colour, String s) {
     return baseFormat(Format.COLOUR, colour, s);
  }

  //#endregion

  //#region CHAT

  public static String formatChatMessage(String username, String message) {
    return baseFormat(Format.Chat.CHAT_MESSAGE, username, message);
  }

  public static String formatPrivateMessageUsername(String username) {
    return baseFormat(Format.Chat.PRIVATE_MESSAGE_USERNAME, username);
  }

  public static String formatPrivateMessage(String recipient, String message) {
    return baseFormat(Format.Chat.PRIVATE_MESSAGE, recipient, message);
  }

  //#endregion

  //#region CLIENT

  public static String formatConnectingMessage(String host, int port) {
    return baseFormat(Format.Client.CONNECTING, host, port);
  }

  public static String formatConnectedMessage(String host, int port) {
    return baseFormat(Format.Client.CONNECTED, host, port);
  }

  public static String formatWelcomeMessage(String username) {
    return baseFormat(Format.Client.USER_WELCOME_MESSAGE, username);
  }

  public static String formatUserMessagesInfoMessage(String username) {
    return baseFormat(Format.Client.USER_MESSAGES_INFO, username);
  }

  public static String formatServerAnnouncementsInfoMessage(String colour, String colourName) {
    String colourFormatted = formatStringColour(colour, colourName);
    return baseFormat(Format.Client.SERVER_ANNOUNCEMENT_INFO, colourFormatted);
  }

  public static String formatPrivateMessagesInfoMessage(String colour, String colourName) {
    String colourFormatted = formatStringColour(colour, colourName);
    return baseFormat(Format.Client.PRIVATE_MESSAGES_INFO, colourFormatted);
  }

  //#endregion

  //#region SERVER

  public static String formatServerLog(String log) {
    String timeStamp = new DateTimeService().getCurrentTimeStamp();
    return baseFormat(Format.Server.LOG, timeStamp, log);
  }

  public static String formatServerStartingUpMessage(String host, int port) {
    return baseFormat(Format.Server.SERVER_STARTING_UP, host, port);
  }

  public static String formatServerRunningMessage(String host, int port) {
    return baseFormat(Format.Server.SERVER_RUNNING, host, port);
  }

  public static String formatUsernameAlreadyExistsMessage(String username) {
    return baseFormat(Format.Server.USERNAME_ALREADY_EXISTS, username);
  }

  public static String formatUserJoinedMessage(String username) {
    return baseFormat(Format.Server.USER_JOINED_MESSAGE, username);
  }

  public static String formatUserDisconnectedMessage(String username) {
    return baseFormat(Format.Server.USER_DISCONNECTED_MESSAGE, username);
  }

  public static String formatPrivateMessageRecipientNotFound(String username) {
    return baseFormat(Format.Server.PRIVATE_MESSAGE_RECIPIENT_NOT_FOUND, username);
  }

  //#endregion

  //#region

  public static String formatCurrentTimeMessage(String currentTime) {
    return baseFormat(Format.Bot.CURRENT_TIME_MESSAGE, currentTime);
  }

  public static String formatCurrentDayMessage(String currentDay) {
    return baseFormat(Format.Bot.CURRENT_DAY_MESSAGE, currentDay);
  }

  //#endregion

  private static String baseFormat(String format, Object... args) {
    return String.format(format, args);
  }
}