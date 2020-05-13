package util;

public class StringUtils {

  public static Boolean IsNull(String s) {
    return s == null;
  }

  public static Boolean isNullOrEmpty(String s) {
    return IsNull(s) || s.isEmpty();
  }

  public static Boolean isNullEmptyOrWhitespace(String s) {
    return isNullOrEmpty(s) || s.trim().isEmpty();
  }
}