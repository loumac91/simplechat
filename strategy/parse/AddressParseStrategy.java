package strategy.parse;

import util.StringUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import format.StringFormatter;
import strategy.Result;

public class AddressParseStrategy implements ParseStrategy<String> {

  private final String valueName = "address";

  public Result<String> parse(String input) {
    Result<String> result = new Result<String>();
    String address = "";
    String errorMessage = "";

    if (StringUtils.isNull(input)) {
      errorMessage = StringFormatter.formatEmptyValueNotPermittedError(this.valueName);
    } else {
      address = tryParseHostAddress(input);

      if (StringUtils.isNull(address)) {
        errorMessage = StringFormatter.formatInvalidAddressError(input);
      }
    }

    result.setErrorMessage(errorMessage);
    result.setValue(address);

    return result;
  }

  private String tryParseHostAddress(String input) {
    try {
      InetAddress inetAddress = Inet4Address.getByName(input);
      return inetAddress.getHostAddress();
    } catch (UnknownHostException unknownHostException) {
      return null;
    }
  }
}