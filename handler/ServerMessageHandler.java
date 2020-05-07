package handler;

import java.io.*;
import java.net.SocketException;

import util.StringUtils;
import client.SimpleChatClient;
import constant.*;
import format.MessageFormatter;
import parse.MessageParser;

public class ServerMessageHandler extends InputReaderHandler {

  private final MessageParser messageParser;

  public ServerMessageHandler(BufferedReader inputReader) {
    super(inputReader);
    this.messageParser = new MessageParser();
  }

  public void run() {
    this.running = true;
    while (this.running) {
      try {
        String serverMessage = this.inputReader.readLine();

        if (StringUtils.IsNull(serverMessage)) {
          running = false;
          System.exit(0); // Scenario here is that server has closed socket, there is no further reason to run ChatClient so shutdown.
          // Execution on main thread will have blocked on BufferedReader.readLine() - it's underlying readstream is System.in
          // Why System.exit(0)? Issue lies in main thread, the BufferedReader.readLine() within the while loop that reads user input
          // will block forever. It uses the System.in stream which does not unblock the readLine() call even when the stream is closed.
          // Decision here is that the application should end if the connection is interrupted - could be extended to have a retry policy.
          // however this was not included in spec
          continue;
        }

        String formatted = getColouredServerMessage(serverMessage);
        System.out.println(formatted);
      } catch (SocketException socketException) {
        System.out.println(Client.Error.CONNECTION_INTERRUPTED);
        System.out.println(MessageFormatter.formatException(socketException));
      } catch (IOException ioException) {
        System.out.println(Client.Error.CONNECTION_INTERRUPTED);
        System.out.println(MessageFormatter.formatException(ioException));
      } finally {
        this.running = false;
        System.exit(0);
      }
    }
  }

  private String getColouredServerMessage(String serverMessage) {
    String colour = "";

    if (isServerAnnouncement(serverMessage)) {
      colour = Colour.WHITE;
    } else if (isPrivateMessage(serverMessage)) {
      colour = Colour.CYAN;
    }

    return colour.length() > 0 
      ? MessageFormatter.formatStringColour(colour, serverMessage)
      : serverMessage;
  }

  private Boolean isServerAnnouncement(String message) {
    return this.messageParser.isServerAnnouncement(message);
  }

  private Boolean isPrivateMessage(String message) {
    return this.messageParser.isPrivateMessage(message);
  }
}