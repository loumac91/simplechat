package constant;

public class Server {
  public static final String DEFAULT_ADDRESS = "localhost";
  public static final int DEFAULT_PORT = 14001;
  public static final String USERNAME = "SERVER";

  public class Message {
    public static final String SHUTDOWN_MESSAGE = "Server is shutting down";
  }

  public class Param {
    public static final String PORT = "-csp"; 
  }
}