package configuration;

import java.util.function.BiConsumer;

public class SocketConfigurationMapper {

  private final String key;
  private final BiConsumer<String, SocketConfiguration> setter;

  public SocketConfigurationMapper(String key, BiConsumer<String, SocketConfiguration> setter) {
    this.key = key;
    this.setter = setter;
  }

  public Boolean isMatch(String input) {
    return this.key.equals(input);
  }
  
  public void map(String input, SocketConfiguration socketConfiguration) {
    this.setter.accept(input, socketConfiguration);
  }
}