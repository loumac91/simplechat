package strategy.parse;

import strategy.Result;

public interface ParseStrategy<T> {

  Result<T> parse(String input);
}