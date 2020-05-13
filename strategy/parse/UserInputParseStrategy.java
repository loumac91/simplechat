package strategy.parse;

import strategy.Result;
import util.StringUtils;
import format.StringFormatter;

public class UserInputParseStrategy implements ParseStrategy<String> {

  private final String valueName;
  private final Boolean allowNull;
  
  public UserInputParseStrategy(String valueName) {
    super();
    this.valueName = valueName;
    this.allowNull = false;
  }

  public UserInputParseStrategy(String valueName, Boolean allowNull) {
    super();
    this.valueName = valueName;
    this.allowNull = allowNull;
  }

  public Result<String> parse(String input) {
    Result<String> result = new Result<String>();
    String errorMessage = "";

    Boolean usernameIsEmpty = StringUtils.isNullEmptyOrWhitespace(input);

    if (!allowNull && usernameIsEmpty) {
      errorMessage = StringFormatter.formatEmptyValueNotPermittedError(this.valueName);
    }
    
    result.setValue(input);
    result.setErrorMessage(errorMessage);

    return result;
  }  
}