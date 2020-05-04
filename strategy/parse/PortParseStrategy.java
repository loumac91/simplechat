package strategy.parse;

import parse.ParseResult;
import constant.Port;;

public class PortParseStrategy extends IntegerParser implements ParseStrategy<Integer> {

  public ParseResult<Integer> parse(String input) {
    Integer value = super.parseInteger(input);
    
    Boolean isValid = value != null 
      && value >= Port.LOWER_PORT_RANGE 
      && value <= Port.UPPER_PORT_RANGE;
    
    return new ParseResult<Integer>(isValid, value);
  }
}