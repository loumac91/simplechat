package constant;

public class Server {
  
  public static final String DEFAULT_ADDRESS = "localhost";
  public static final int DEFAULT_PORT = 14001;
  public static final String USERNAME = "SERVER";

  public class Error {
    public static final String CLIENT_UNABLE_TO_CONNECT = "A new clients connection was interrupted";
  }

  public class Message {
    public static final String SHUTDOWN_MESSAGE = "Server is shutting down";
  }

  public class Param {
    public static final String PORT = "-csp"; 
  }
}