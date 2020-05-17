package constant;

public class Message {

  public static final String TERMINAL_NAME_PREFIX = "[YOU]";
  public static final String PRIVATE_MESSAGE_TOKEN = "@";
  public static final String PRIVATE_MESSAGE_USERNAME_PREFIX = "from:";
  
  public class Pattern {
    
    // Example valid private message format: "@joe-bloggs hey joe !"
    // Regex breakdown:
    // 1. ^(?:@) - non capture group (inclusion of ?:), string must start with (^) @ character
    // 2. ([\w\d\S]+) - capture group for username, any number of alphanumeric characters (\w or \d) and non-whitespace characters (\S)
    // 3. (?:[\s]+) - non capture group, any number of whitespace characters
    // 4. (.*$) - capture group for message, any length of input characters to the end of the string (. any character but newline, * any number, $ to end of string)
    public static final String PRIVATE_MESSAGE_PATTERN = "^(?:" + PRIVATE_MESSAGE_TOKEN + ")([\\w\\d\\S]+)(?:[\\s]+)(.*$)";
    
    // Example valid server message: "[SERVER]: Server shutting down."
    // Regex breakdown:
    // 1. ^(\[SERVER\]:) - capture group, string must start with (^) [SERVER]:
    // 2. (?:[\s]+) - non capture group, and be followed by whitespace
    // 3. (.*$) - capture group, and contain any non newline character input to the end of the string
    public static final String SERVER_ANNOUNCEMENT_PATTERN = "^(\\[" + Server.USERNAME + "\\]:)(?:[\\s]+)(.*$)";

    // Example valid formatted private message: "[from:User1]: hey joe !"
    // Regex breakdown:
    // 1. ^(\[from:([\w\d\S]+)\]:) - capture group, string must start with [from:, then any number of alphanumeric characters and non-whitespace characters, followed by ]
    // 2. (?:[\s]+) - non capture group, and be followed by whitespace
    // 3. (.*$) - capture group, and contain any non newline character input to the end of the string
    public static final String PRIVATE_MESSAGE_FORMATTED_PATTERN = "^(\\[" + PRIVATE_MESSAGE_USERNAME_PREFIX + "([\\w\\d\\S]+)\\]:)(?:[\\s]+)(.*$)";
  
    // Example valid formatted message: "[User1]: hey everyone!"
    // Regex breakdown:
    // 1. ^(?:\\[) - non capture group - string must start with [
    // 2. ([\w\d\S]+) - capture group for username, any number of alphanumeric character and non-whitespace character
    // 3. (?:\]) - non capture group - followed by a ]
    // 4. : - followed by a colon
    // 5. (?:[\s]+) - non capture group - followed by whitespace
    // 6. (.*$) - capture group, and contain any non newline character input to the end of the string
    public static final String MESSAGE_FORMATTED_PATTERN = "^(?:\\[)([\\w\\d\\S]+)(?:\\]):(?:[\\s]+)(.*$)";

    public class Error {

      public static final String MESSAGE_CONTAINS_NO_PRIVATE_TOKEN = "Message does not contain private token";
      public static final String MESSAGE_IS_NOT_PRIVATE = "Message does not parse to private message";
      public static final String MESSAGE_CONTAINS_NO_PRIVATE_MESSAGE_USERNAME_PREFIX = "Message does not contain a private message username prefix";
      public static final String MESSAGE_COULD_NOT_BE_PARSED = "Message could not be parsed";
    }
  }
}