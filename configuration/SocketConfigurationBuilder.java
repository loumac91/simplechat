package configuration;

import java.util.ArrayList;

public class SocketConfigurationBuilder {

  private String defaultAddress;
  private Integer defaultPort;
  private SocketConfigurationMapper addressConfigurationMapper;
  private SocketConfigurationMapper portConfigurationMapper;

  public SocketConfigurationBuilder withDefaultAddress(String address) {
    this.defaultAddress = address;
    return this;
  }

  public SocketConfigurationBuilder withDefaultPort(Integer port) {
    this.defaultPort = port;
    return this;
  }

  public SocketConfigurationBuilder withAddressMapper(SocketConfigurationMapper addressConfigurationMapper) {
    this.addressConfigurationMapper = addressConfigurationMapper;
    return this;
  }

  public SocketConfigurationBuilder withPortMapper(SocketConfigurationMapper portConfigurationMapper) {
    this.portConfigurationMapper = portConfigurationMapper;
    return this;
  }

  public SocketConfiguration buildFromCommandLine(String[] args) {
    // Set default values to begin with
    SocketConfiguration defaultConfiguration = new SocketConfiguration(this.defaultAddress, this.defaultPort);
    
    // override with provided arguments (only when they are provided)
    mapConfiguration(args, defaultConfiguration);

    return defaultConfiguration;
  }

  private void mapConfiguration(String[] args, SocketConfiguration socketConfiguration) {
    Boolean skipNextArg = false;

    // Loop through every argument
    for (int i = 0; i < args.length; i++) {
      if (skipNextArg) {
        skipNextArg = false;
        continue;
      }

      String arg = args[i];
      // Loop through each mapper, if the argument matches a mappers get, execute the mapper on the socket configuration
      for (SocketConfigurationMapper socketConfiguratioMapper : getConfigurationMappers()) {
        if (socketConfiguratioMapper.isMatch(arg)) {
          skipNextArg = true;
          String unParsedValue = args[i + 1]; // Assumption that next value in list is commands value
          socketConfiguratioMapper.map(unParsedValue, socketConfiguration);
          break;
        }
      }
    }
  }

  private ArrayList<SocketConfigurationMapper> getConfigurationMappers() {
    ArrayList<SocketConfigurationMapper> configurationMappers = new ArrayList<SocketConfigurationMapper>();
    if (this.addressConfigurationMapper != null) {
      configurationMappers.add(this.addressConfigurationMapper);
    }

    if (this.portConfigurationMapper != null) {
      configurationMappers.add(this.portConfigurationMapper);
    }

    return configurationMappers;
  }
}