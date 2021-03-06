package constant;

public class Client {

  public static final String SHUTDOWN = "Client shutting down";
  public static final String USERNAME = "username";

  public class Error {
    public static final String UNKNOWN_HOST = "Unable to determine IP address of host";
    public static final String CONNECT = "Error occured while attempting to connect to the specified server socket";
    public static final String CONNECTION_INTERRUPTED = "Connection to the server was interrupted";
  }

  public class Prompt {
    public static final String USERNAME_PROMPT = "Provide your "+ USERNAME + ": ";
  }

  public class Param {
    public static final String PORT = "-ccp";
    public static final String HOST = "-cca";
  }
}