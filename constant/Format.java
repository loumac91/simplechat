package constant;

public class Format {
  
  public static final String COLOUR = "%s%s" + Ansi.Colour.TERMINATE_COLOUR;
  public static final String TIMESTAMP = "yyyy.MM.dd HH:mm:ss";

  public class Error {
    public static final String EXCEPTION = "Exception {%s}: \"%s\"";
    public static final String INVALID_PORT = "%d is not a valid port number";
    public static final String INVALID_ADDRESS = "\"%s\" could not be resolved to a host";
    public static final String EMPTY_VALUE_NOT_PERMITTED = "\"%s\" must be a non empty value";
    public static final String ERROR_DISCONNECTING_USER = "Exception thrown when trying to disconnect user: \"%s\"";
    public static final String VALUE_CANNOT_BE_PARSED = "\"%s\" could not be parsed to type \"%s\"";
    public static final String USER_MESSAGE_HANDLING_INTERRUPTED = "The connection was interrupted for user \"%s\", message handling has terminated for this user";
  }

  public class Chat {
    public static final String CHAT_MESSAGE = "[%s]: %s"; // e.g. [User1]: Hey
    public static final String PRIVATE_MESSAGE_USERNAME = Message.PRIVATE_MESSAGE_USERNAME_PREFIX + "%s"; // e.g. [from:User1]
    public static final String PRIVATE_MESSAGE = Message.PRIVATE_MESSAGE_TOKEN + "%s %s"; // @{Username} hey
  }

  public class Client {
    public static final String CONNECTING = "Trying to connect to \"%s:%d\" ...";
    public static final String CONNECTED = "Connected to \"%s:%d\"";
    public static final String USER_WELCOME_MESSAGE = "Welcome to SimpleChat %s!";
    public static final String USER_MESSAGES_INFO = "Your messages will appear in the following format in the chat window of other connected clients: [%s]: {YOUR MESSAGE}";
    public static final String SERVER_ANNOUNCEMENT_INFO = "Server messages will be shown in %s";
    public static final String PRIVATE_MESSAGES_INFO = "Private messages will be prefixed with \"" + Message.PRIVATE_MESSAGE_USERNAME_PREFIX + "\" and shown in %s";  }

  public class Server {
    public static final String LOG = "[%s]: %s";
    public static final String SERVER_STARTING_UP = "simplechat server attempting to start up on \"%s:%d\"";
    public static final String SERVER_RUNNING = "simplechat Server running on \"%s:%d\"";
    public static final String USERNAME_ALREADY_EXISTS = "\"%s\" is a username that already exists, please provide a different one";
    public static final String USER_JOINED_MESSAGE = "%s has joined the chat";
    public static final String USER_DISCONNECTED_MESSAGE = "%s has left the chat";
    public static final String PRIVATE_MESSAGE_RECIPIENT_NOT_FOUND = "Your private message was not delivered as no user with the name \"%s\" was found";
  }

  public class Bot {
    public static final String CURRENT_TIME_MESSAGE = "The time now is: %s";
    public static final String CURRENT_DAY_MESSAGE = "Today is %s, :)";
  }
}