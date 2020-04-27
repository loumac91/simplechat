package parse;

public class ParseResult<T> {
  private Boolean isValid;
  private T value;

  public ParseResult(Boolean isValid, T value) {
    this.isValid = isValid;
    this.value = value;
  }

  public Boolean getIsValid() {
    return this.isValid;
  }

  public T getValue() {
    return this.value;
  }
}