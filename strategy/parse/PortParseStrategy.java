package strategy.parse;

import strategy.Result;
import constant.Port;
import format.StringFormatter;

public class PortParseStrategy extends IntegerParser implements ParseStrategy<Integer> {

  private final String valueName = "port";

  public Result<Integer> parse(String input) {
    Result<Integer> result = new Result<Integer>();
    String errorMessage = "";

    Integer value = this.parseInteger(input);

    if (value == null) {
      errorMessage = StringFormatter.formatEmptyValueNotPermittedError(this.valueName);
    } else if (!isValidPort(value)) {
      errorMessage = StringFormatter.formatInvalidPortError(value);
    }

    result.setErrorMessage(errorMessage);
    result.setValue(value);
    
    return result;
  }

  private Boolean isValidPort(Integer port) {
    return port >= Port.LOWER_PORT_RANGE && port <= Port.UPPER_PORT_RANGE;
  }
}