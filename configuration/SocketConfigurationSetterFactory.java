package configuration;

import java.util.function.BiConsumer;
import strategy.parse.AddressParseStrategy;
import strategy.parse.PortParseStrategy;
import parse.ParseResult;

public class SocketConfigurationSetterFactory {

  public static final BiConsumer<String, SocketConfiguration> addressSetter = 
    (value, socketConfiguration) -> {
      ParseResult<String> parseResult = new AddressParseStrategy().parse(value);
      if (parseResult.getIsValid()) {
        socketConfiguration.address = parseResult.getValue();
      }
    };

  public static final BiConsumer<String, SocketConfiguration> portSetter = 
    (value, socketConfiguration) -> {
      ParseResult<Integer> parseResult = new PortParseStrategy().parse(value);
      if (parseResult.getIsValid()) {
        socketConfiguration.port = parseResult.getValue();
      }
    };
}