package strategy.validate;

import strategy.Result;

public class ValidationResult<T> extends Result<T> {
  
  private String validationMessage;

  public ValidationResult() {
    super();
  }

  public void setValidationMessage(String validationMessage) {
    this.validationMessage = validationMessage;
  }

  public String getValidationMessage() {
    return this.validationMessage;
  }  
}