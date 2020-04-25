package constants;

public class MessageFormats {
  
  public class Client {
    public static final String CONNECTING = "Trying to connect to \"%s:%d\" ...";
    public static final String CONNECTED = "Connected to \"%s:%d\"";
    public static final String USERNAME_PROMPT = "Provide your username: ";
  }

  public class Server {
    public static final String SERVER_RUNNING = "simplechat Server running on \"%s:%d\"";
  }
}