package strategy.parse;

import parse.ParseResult;
import util.StringUtils;
import constant.Server;

public class AddressParseStrategy implements ParseStrategy<String> {

  public ParseResult<String> parse(String input) {
    Boolean isValid = isValidAddressName(input);
    return new ParseResult<String>(isValid, input);
  }

  private Boolean isValidAddressName(String input) {
    if (StringUtils.IsNull(input)) {
      return false;
    }
    
    Integer length = input.length();
    Integer noDotsLength = input.replace(".", "").length();
    Boolean containsThreeDots = (length - noDotsLength) == 3;
    
    return input == Server.DEFAULT_ADDRESS || containsThreeDots;
  }
}