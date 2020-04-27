package configuration;

public class SocketConfiguration {

  public String address;
  public Integer port;

  public SocketConfiguration(String address, Integer port) {
    this.address = address;
    this.port = port;
  }
}