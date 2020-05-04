package constant;

public class Message {

  public final static String PRIVATE_MESSAGE_TOKEN = "@";
  public final static String PRIVATE_MESSAGE_USERNAME_PREFIX = "private:";

  // Example valid private message format: "@joe-bloggs hey joe !"
  // Regex breakdown:
  // 1. ^(?:@) - non capture group, string must start with @
  // 2. ([\\w\\d0-9\\S]+) - capture group for username, any number of alphanumeric character (\w or \d) and non-whitespace character (\S)
  // 3. (?:[\\s]+) - non capture group, any number of whitespace characters
  // 4. (.*$) - capture group for message, any input characters
  public final static String PRIVATE_MESSAGE_PATTERN = "^(?:" + PRIVATE_MESSAGE_TOKEN + ")([\\w\\d\\S]+)(?:[\\s]+)(.*$)";
  
  // Example valid server message: "[SERVER]: Server shutting down."
  public final static String SERVER_ANNOUNCEMENT_PATTERN = "^(\\[" + Server.USERNAME + "\\]:)(?:[\\s]+)(.*$)";

  // Example valid formatted private message: "[private:User1]: hey joe !"
  public final static String PRIVATE_MESSAGE_FORMATTED_PATTERN = "^(\\[" + PRIVATE_MESSAGE_USERNAME_PREFIX + "([\\w\\d\\S]+)\\]:)(?:[\\s]+)(.*$)";
}