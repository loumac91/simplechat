package format;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import constant.MessageFormat;

public class MessageFormatter {

  private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(MessageFormat.TIMESTAMP);

  public static String formatException(Exception exception) {
    return baseFormat(MessageFormat.EXCEPTION, exception.getClass().getName(), exception.getMessage());
  }

  //#region COLOUR

  public static String formatStringColour(String colour, String s) {
     return baseFormat(MessageFormat.COLOUR, colour, s);
  }

  //#endregion

  //#region CHAT

  public static String formatChatMessage(String username, String message) {
    return baseFormat(MessageFormat.Chat.CHAT_MESSAGE, username, message);
  }

  public static String formatPrivateMessageUsername(String username) {
    return baseFormat(MessageFormat.Chat.PRIVATE_MESSAGE_USERNAME, username);
  }

  //#endregion

  //#region CLIENT

  public static String formatConnectingMessage(String host, int port) {
    return baseFormat(MessageFormat.Client.CONNECTING, host, port);
  }

  public static String formatConnectedMessage(String host, int port) {
    return baseFormat(MessageFormat.Client.CONNECTED, host, port);
  }

  public static String formatWelcomeMessage(String username) {
    return baseFormat(MessageFormat.Client.USER_WELCOME_MESSAGE, username);
  }

  public static String formatServerAnnouncementsInfoMessage(String colour, String colourName) {
    String colourFormatted = formatStringColour(colour, colourName);
    return baseFormat(MessageFormat.Client.SERVER_ANNOUNCEMENT_INFO, colourFormatted);
  }

  public static String formatPrivateMessagesInfoMessage(String colour, String colourName) {
    String colourFormatted = formatStringColour(colour, colourName);
    return baseFormat(MessageFormat.Client.PRIVATE_MESSAGES_INFO, colourFormatted);
  }

  //#endregion

  //#region SERVER

  public static String formatServerLog(String log) {
    String timeStamp = ZonedDateTime.now(ZoneId.systemDefault()).format(dateTimeFormatter);
    return baseFormat(MessageFormat.Server.LOG, timeStamp, log);
  }

  public static String formatServerStartingUpMessage(String host, int port) {
    return baseFormat(MessageFormat.Server.SERVER_STARTING_UP, host, port);
  }

  public static String formatServerRunningMessage(String host, int port) {
    return baseFormat(MessageFormat.Server.SERVER_RUNNING, host, port);
  }

  public static String formatUsernameAlreadyExistsMessage(String username) {
    return baseFormat(MessageFormat.Server.USERNAME_ALREADY_EXISTS, username);
  }

  public static String formatUserJoinedMessage(String username) {
    return baseFormat(MessageFormat.Server.USER_JOINED_MESSAGE, username);
  }

  public static String formatUserDisconnectedMessage(String username) {
    return baseFormat(MessageFormat.Server.USER_DISCONNECTED_MESSAGE, username);
  }

  public static String formatPrivateMessageRecipientNotFound(String username) {
    return baseFormat(MessageFormat.Server.PRIVATE_MESSAGE_RECIPIENT_NOT_FOUND, username);
  }

  private static String baseFormat(String format, Object... args) {
    return String.format(format, args);
  }

  //#endregion
}