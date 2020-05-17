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

  // Why System.exit(0)? Issue lies in main thread of SimpleChatClient, the BufferedReader.readLine() within the while loop that reads user input
  // will block forever. It uses the System.in input stream which does not unblock the readLine() call even when the stream is closed or if it's thread is interrupted.
  // Decision here is that the application should end if the connection is interrupted - before doing so, any open streams/sockets are closed either explicitly
  // or via Autocloseable implementation
  // This saves the user having to enter anything else on the command line once the server has disconnected them
  private void shutdownClient() {
    try {
      this.inputReader.close(); 
    } catch (IOException ioException) {  }
    System.out.println(Client.SHUTDOWN);
    System.exit(0);
  }
}