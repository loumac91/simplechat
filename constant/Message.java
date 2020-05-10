package constant;

public class Message {

  public static final String TERMINAL_NAME_PREFIX = "[YOU]";
  public static final String PRIVATE_MESSAGE_TOKEN = "@";
  public static final String PRIVATE_MESSAGE_USERNAME_PREFIX = "from:";
  
  public class Pattern {

    // Example valid private message format: "@joe-bloggs hey joe !"
    // Regex breakdown:
    // 1. ^(?:@) - non capture group, string must start with (^) @ character
    // 2. ([\w\d\S]+) - capture group for username, any number of alphanumeric character (\w or \d) and non-whitespace character (\S)
    // 3. (?:[\s]+) - non capture group, any number of whitespace characters
    // 4. (.*$) - capture group for message, any input characters
    public static final String PRIVATE_MESSAGE_PATTERN = "^(?:" + PRIVATE_MESSAGE_TOKEN + ")([\\w\\d\\S]+)(?:[\\s]+)(.*$)";
    
    // Example valid server message: "[SERVER]: Server shutting down."
    public static final String SERVER_ANNOUNCEMENT_PATTERN = "^(\\[" + Server.USERNAME + "\\]:)(?:[\\s]+)(.*$)";

    // Example valid formatted private message: "[from:User1]: hey joe !"
    public static final String PRIVATE_MESSAGE_FORMATTED_PATTERN = "^(\\[" + PRIVATE_MESSAGE_USERNAME_PREFIX + "([\\w\\d\\S]+)\\]:)(?:[\\s]+)(.*$)";
  
    // Example valid formatted message: "[User1]: hey everyone!"
    public static final String MESSAGE_FORMATTED_PATTERN = "^(?:\\[)([\\w\\d\\S]+)(?:\\]):(?:[\\s]+)(.*$)";
  }
}