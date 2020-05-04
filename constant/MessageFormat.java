package constant;

public class MessageFormat {
  
  public static final String COLOUR_FORMAT = "%s%s" + Colour.TERMINATE_COLOUR;

  public class Chat {
    public static final String CHAT_MESSAGE_FORMAT = "[%s]: %s"; // e.g. [User1]: Hey
    public static final String PRIVATE_MESSAGE_USERNAME_FORMAT = "private:%s"; // e.g. [private:User1]
  }

  public class Client {
    public static final String CONNECTING = "Trying to connect to \"%s:%d\" ...";
    public static final String CONNECTED = "Connected to \"%s:%d\"";
  }

  public class Server {
    public static final String SERVER_RUNNING = "simplechat Server running on \"%s:%d\"";
    public static final String USER_WELCOME_MESSAGE = "Welcome to SimpleChat %s!";
  }
}