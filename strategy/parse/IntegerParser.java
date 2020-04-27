package strategy.parse;

public abstract class IntegerParser {

  protected Integer parseInteger(String input) {
    try {
      return Integer.parseInt(input);
    } catch (NumberFormatException e) {
      return null;
    }
  }
}