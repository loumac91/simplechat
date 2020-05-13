package input;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;

import strategy.Result;
import strategy.parse.ParseStrategy;
import strategy.validate.ValidationStrategy;
import strategy.validate.ValidationResult;

public class UserInputParser<T> extends BaseInputParser<T> {
  
  public UserInputParser(InputStream inputStream) {
    super(inputStream);
  }

  public UserInputParser(BufferedReader inputReader) {
    super(inputReader);
  }

  public T parseInput(ParseStrategy<T> parseStrategy) throws IOException {
    Result<T> parseResult = this.parseResponse(parseStrategy);

    return parseResult.getValue();
  }

  public T parseInput(String prompt, ParseStrategy<T> parseStrategy, ValidationStrategy<T> validationStrategy) throws IOException {
    Result<T> parseResult = null;
    Boolean invalidInput = true;
    while (invalidInput) {
      parseResult = this.parsePromptResponse(prompt, parseStrategy);
      if (!parseResult.getIsSuccess()) {
        System.out.println(parseResult.getErrorMessage());
        continue;
      }
      
      ValidationResult<Boolean> validationResult = validationStrategy.isValid(parseResult.getValue());
      invalidInput = !validationResult.getValue();
      if (invalidInput) {
        System.out.println(validationResult.getErrorMessage());
      } else {
        System.out.println(validationResult.getValidationMessage());
      }
    }

    return parseResult.getValue();
  }
}