package format;

import constant.MessageFormat;

public class MessageFormatter {

  // COLOUR
  public static String formatStringColour(String colour, String s) { // USE enum?
     return baseFormat(MessageFormat.COLOUR_FORMAT, colour, s);
  }

  // CHAT

  public static String formatChatMessage(String username, String message) {
    return baseFormat(MessageFormat.Chat.CHAT_MESSAGE_FORMAT, username, message);
  }

  // CLIENT

  public static String formatConnectingMessage(String host, int port) {
    return baseFormat(MessageFormat.Client.CONNECTING, host, port);
  }

  public static String formatConnectedMessage(String host, int port) {
    return baseFormat(MessageFormat.Client.CONNECTED, host, port);
  }

  // SERVER

  public static String formatServerRunningMessage(String host, int port) {
    return baseFormat(MessageFormat.Server.SERVER_RUNNING, host, port);
  }

  public static String formatWelcomeMessage(String username) {
    return baseFormat(MessageFormat.Server.USER_WELCOME_MESSAGE, username);
  }

  private static String baseFormat(String format, Object... args) {
    return String.format(format, args);
  }
}