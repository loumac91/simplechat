package format;

import constant.MessageFormat;

public class MessageFormatter {

  // COLOUR
  public static String getColouredString(String colour, String s) { // USE enum?
     return baseFormat(MessageFormat.COLOUR_FORMAT, colour, s);
  }

  // CHAT

  public static String formatChatMessage(String username, String message) {
    return baseFormat(MessageFormat.Chat.CHAT_MESSAGE_FORMAT, username, message);
  }

  // CLIENT

  public static String getConnectingMessage(String host, int port) {
    return baseFormat(MessageFormat.Client.CONNECTING, host, port);
  }

  public static String getConnectedMessage(String host, int port) {
    return baseFormat(MessageFormat.Client.CONNECTED, host, port);
  }

  // SERVER

  public static String getServerRunningMessage(String host, int port) {
    return baseFormat(MessageFormat.Server.SERVER_RUNNING, host, port);
  }

  public static String getWelcomeMessage(String username) {
    return baseFormat(MessageFormat.Server.USER_WELCOME_MESSAGE, username);
  }

  private static String baseFormat(String format, Object... args) {
    return String.format(format, args);
  }
}