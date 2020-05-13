package strategy;

import util.StringUtils;

public class Result<T> {
  
  private String errorMessage;
  private T value;
  
  // Didn't use constructors to set values here because T could be string
  // This would make the signature a bit ambiguous, so I opted for a more 
  // verbose style with mutators and getters
  public Result() { }

  public Boolean getIsSuccess() {
    return StringUtils.isNullOrEmpty(this.errorMessage);
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return this.errorMessage;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public T getValue() {
    return this.value;
  }
}