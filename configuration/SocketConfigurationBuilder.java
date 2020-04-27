package configuration;

import java.util.ArrayList;

public class SocketConfigurationBuilder {

  private String defaultAddress;
  private Integer defaultPort;
  private SocketConfigurationMapper addressConfigurationMapper;
  private SocketConfigurationMapper portConfigurationMapper;

  public SocketConfigurationBuilder setDefaultAddress(String address) {
    this.defaultAddress = address;
    return this;
  }

  public SocketConfigurationBuilder setDefaultPort(Integer port) {
    this.defaultPort = port;
    return this;
  }

  public SocketConfigurationBuilder setAddressMapper(SocketConfigurationMapper addressConfigurationMapper) {
    this.addressConfigurationMapper = addressConfigurationMapper;
    return this;
  }

  public SocketConfigurationBuilder setPortMapper(SocketConfigurationMapper portConfigurationMapper) {
    this.portConfigurationMapper = portConfigurationMapper;
    return this;
  }

  public SocketConfiguration buildFromCommandLine(String[] args) {
    SocketConfiguration defaultConfiguration = new SocketConfiguration(this.defaultAddress, this.defaultPort);
    
    mapStrategies(args, defaultConfiguration);

    return defaultConfiguration;
  }

  private void mapStrategies(String[] args, SocketConfiguration socketConfiguration) {
    Boolean skipNextArg = false;

    for (int i = 0; i < args.length; i++) {
      if (skipNextArg) {
        skipNextArg = false;
        continue;
      }

      String arg = args[i];
      for (SocketConfigurationMapper socketConfiguratioMapper : createConfigurationMappers()) {
        if (socketConfiguratioMapper.isMatch(arg)) {
          skipNextArg = true;
          String unParsedValue = args[i + 1]; // Assumption that next value in list is commands value
          socketConfiguratioMapper.map(unParsedValue, socketConfiguration);
          break;
        }
      }
    }
  }

  private ArrayList<SocketConfigurationMapper> createConfigurationMappers() {
    ArrayList<SocketConfigurationMapper> configurationMappers = new ArrayList<>();
    if (this.addressConfigurationMapper != null) {
      configurationMappers.add(this.addressConfigurationMapper);
    }

    if (this.portConfigurationMapper != null) {
      configurationMappers.add(this.portConfigurationMapper);
    }

    return configurationMappers;
  }
}