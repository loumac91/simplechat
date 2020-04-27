package format;

import constant.MessageFormats;

public class MessageFormatter {

  // CLIENT

  public static String getConnectingMessage(String host, int port) {
    return baseFormat(MessageFormats.Client.CONNECTING, host, port);
  }

  public static String getConnectedMessage(String host, int port) {
    return baseFormat(MessageFormats.Client.CONNECTED, host, port);
  }

  // SERVER

  public static String getServerRunningMessage(String host, int port) {
    return baseFormat(MessageFormats.Server.SERVER_RUNNING, host, port);
  }

  public static String getWelcomeMessage(String userName) {
    return baseFormat(MessageFormats.Server.USER_WELCOME_MESSAGE, userName);
  }

  private static String baseFormat(String format, Object... args) {
    return String.format(format, args);
  }
}