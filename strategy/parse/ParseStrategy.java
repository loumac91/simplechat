package strategy.parse;

import parse.ParseResult;

public interface ParseStrategy<T> {

  ParseResult<T> parse(String input);
}