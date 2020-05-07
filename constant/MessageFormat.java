package constant;

public class MessageFormat {
  
  public static final String EXCEPTION = "Exception {%s} : %s";
  public static final String COLOUR = "%s%s" + Colour.TERMINATE_COLOUR;
  public static final String TIMESTAMP = "yyyy.MM.dd HH:mm:ss";

  public class Chat {
    public static final String CHAT_MESSAGE = "[%s]: %s"; // e.g. [User1]: Hey
    public static final String PRIVATE_MESSAGE_USERNAME = Message.PRIVATE_MESSAGE_USERNAME_PREFIX + "%s"; // e.g. [from:User1]
  }

  public class Client {
    public static final String CONNECTING = "Trying to connect to \"%s:%d\" ...";
    public static final String CONNECTED = "Connected to \"%s:%d\"";
    public static final String USER_WELCOME_MESSAGE = "Welcome to SimpleChat %s!";
    public static final String SERVER_ANNOUNCEMENT_INFO = "Server messages will be shown in %s";
    public static final String PRIVATE_MESSAGES_INFO = "Private messages will be prefixed with \"" + Message.PRIVATE_MESSAGE_USERNAME_PREFIX + "\" and shown in %s";
  }

  public class Server {
    public static final String LOG = "[%s]: %s";
    public static final String SERVER_STARTING_UP = "simplechat server attempting to start up on \"%s:%d\"";
    public static final String SERVER_RUNNING = "simplechat Server running on \"%s:%d\"";
    public static final String USERNAME_ALREADY_EXISTS = "\"%s\" is a username that already exists, please provide a different one";
    public static final String USER_JOINED_MESSAGE = "%s has joined the chat";
    public static final String USER_DISCONNECTED_MESSAGE = "%s has left the chat";
  }
}