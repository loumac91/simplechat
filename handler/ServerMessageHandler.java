package handler;

import java.io.*;
import java.net.SocketException;

import util.StringUtils;
import constant.Ansi.Colour;
import constant.Client;
import format.StringFormatter;

public class ServerMessageHandler extends InputReaderHandler {

  public ServerMessageHandler(BufferedReader inputReader) {
    super(inputReader);
  }

  // TODO REVIEW
  public void run() {
    this.running = true;
    while (this.running) {
      try {
        String serverMessage = this.inputReader.readLine();

        if (StringUtils.IsNull(serverMessage)) {
          shutdownClient(); // Scenario here is that server has closed socket, there is no further reason to run ChatClient so shutdown.
          // Execution on main thread will have blocked on BufferedReader.readLine() - it's underlying readstream is System.in
          // Why System.exit(0)? Issue lies in main thread, the BufferedReader.readLine() within the while loop that reads user input
          // will block forever. It uses the System.in stream which does not unblock the readLine() call even when the stream is closed.
          // Decision here is that the application should end if the connection is interrupted - could be extended to have a retry policy.
          // however this was not included in spec
        }

        String formatted = getColouredServerMessage(serverMessage);
        System.out.println(formatted);

      } catch (SocketException socketException) {
        this.running = false;
        System.out.println(Client.Error.CONNECTION_INTERRUPTED);
        System.out.println(StringFormatter.formatException(socketException));
      } catch (IOException ioException) {
        this.running = false;
        System.out.println(Client.Error.CONNECTION_INTERRUPTED);
        System.out.println(StringFormatter.formatException(ioException));
      }
    }

    shutdownClient();
  }

  private String getColouredServerMessage(String serverMessage) {
    String colour = getMessageColour(serverMessage);

    return !StringUtils.isNullEmptyOrWhitespace(colour)
      ? StringFormatter.formatStringColour(colour, serverMessage)
      : serverMessage;
  }

  private String getMessageColour(String message) {
    if (isServerAnnouncement(message)) {
      return Colour.WHITE;
    } else if (isPrivateMessage(message)) {
      return Colour.CYAN;
    }

    return "";
  }

  private Boolean isServerAnnouncement(String message) {
    return this.messageParser.isServerAnnouncement(message);
  }

  private Boolean isPrivateMessage(String message) {
    return this.messageParser.isPrivateMessage(message);
  }

  // TODO Why/how this is clean
  private void shutdownClient() {
    try {
      this.inputReader.close(); 
    } catch (IOException ioException) {  }
    System.out.println(Client.SHUTDOWN);
    System.exit(0);
  }
}