package strategy.parse;

import format.StringFormatter;
import server.ServerCommand;
import strategy.Result;
import util.StringUtils;

public class ServerCommandParseStrategy implements ParseStrategy<ServerCommand> {

  private final String valueName = "server command";

  public ServerCommandParseStrategy() {
    super();
  }

  public Result<ServerCommand> parse(String input) {
    Result<ServerCommand> result = new Result<ServerCommand>();
    ServerCommand serverCommand = null;
    String errorMessage = "";

    if (StringUtils.isNullEmptyOrWhitespace(input)) {
      errorMessage = StringFormatter.formatEmptyValueNotPermittedError(this.valueName);
    } else {
      serverCommand = tryParseServerCommand(input.trim());
      if (serverCommand == null) {
        errorMessage = StringFormatter.formatValueCannotBeParsed(input, ServerCommand.class);
      }
    }

    result.setValue(serverCommand);
    result.setErrorMessage(errorMessage);

    return result;
  }

  private ServerCommand tryParseServerCommand(String input) {
    try {
      return ServerCommand.valueOf(input);
    } catch (IllegalArgumentException illegalArgumentException) {
      return null;
    }
  }
}