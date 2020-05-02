package util;

public class StringUtils {

  public static Boolean IsNull(String s) {
    return s == null;
  }

  public static Boolean isNullOrEmpty(String s) {
    return IsNull(s) || s.isEmpty();
  }
}