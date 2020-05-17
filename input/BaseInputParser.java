package input;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import strategy.Result;
import strategy.parse.ParseStrategy;
import strategy.validate.ValidationStrategy;

// Abstract base class for parsing string input to a given type <T>

public abstract class BaseInputParser<T> implements Closeable {
  
  private final BufferedReader inputReader;

  protected BaseInputParser(InputStream inputStream) {
    this(new BufferedReader(new InputStreamReader(inputStream)));
  }

  protected BaseInputParser(BufferedReader inputReader) {
    this.inputReader = inputReader;
  }

  public abstract T parseInput(ParseStrategy<T> parseStrategy) throws IOException;

  public abstract T parseInput(String username, ParseStrategy<T> parseStrategy, ValidationStrategy<T> validationStrategy) throws IOException;

  public String getUnparsedInput() throws IOException {
    return this.inputReader.readLine();
  }

  protected Result<T> parseResponse(ParseStrategy<T> parseStrategy) throws IOException {
    String unparsedInput = this.getUnparsedInput();
    return parseStrategy.parse(unparsedInput);
  }

  protected Result<T> parsePromptResponse(String prompt, ParseStrategy<T> parseStrategy) throws IOException {
    System.out.print(prompt);
    String unparsedInput = this.getUnparsedInput();
    return parseStrategy.parse(unparsedInput);
  }

  @Override
  public void close() throws IOException {
    this.inputReader.close();
  }
}
