package configuration;

import java.util.function.BiConsumer;
import strategy.parse.AddressParseStrategy;
import strategy.parse.PortParseStrategy;
import strategy.Result;

public class SocketConfigurationSetterFactory {

  public static final BiConsumer<String, SocketConfiguration> addressSetter = 
    (value, socketConfiguration) -> {
      Result<String> parseResult = new AddressParseStrategy().parse(value);
      if (parseResult.getIsSuccess()) {
        socketConfiguration.address = parseResult.getValue();
      }
    };

  public static final BiConsumer<String, SocketConfiguration> portSetter = 
    (value, socketConfiguration) -> {
      Result<Integer> parseResult = new PortParseStrategy().parse(value);
      if (parseResult.getIsSuccess()) {
        socketConfiguration.port = parseResult.getValue();
      }
    };
}