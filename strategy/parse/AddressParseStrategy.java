package strategy.parse;

import util.StringUtils;
import constant.Server;
import format.StringFormatter;
import strategy.Result;

public class AddressParseStrategy implements ParseStrategy<String> {

  private final String valueName = "address";

  public Result<String> parse(String input) {
    Result<String> result = new Result<String>();
    String errorMessage = "";

    if (StringUtils.IsNull(input)) {
      errorMessage = StringFormatter.formatEmptyValueNotPermittedError(this.valueName);
    } else if (!isValidAddressName(input)) {
      errorMessage = StringFormatter.formatInvalidAddressError(input);
    }

    result.setErrorMessage(errorMessage);
    result.setValue(input);

    return result;
  }

  // Currently only allows "localhost" or ip4 addresses e.g. 192.168.10.250
  private Boolean isValidAddressName(String input) {    
    Integer length = input.length();
    Integer noDotsLength = input.replace(".", "").length();
    Boolean containsThreeDots = (length - noDotsLength) == 3;
    
    return input == Server.DEFAULT_ADDRESS || containsThreeDots;
  }
}