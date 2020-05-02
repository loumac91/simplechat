package handler;

import java.io.*;
import java.net.SocketException;

import util.StringUtils;
import client.SimpleChatClient;
import constant.*;
import format.MessageFormatter;

public class ServerMessageHandler extends InputReaderHandler {

  private final SimpleChatClient chatClient;
  private final BufferedReader commandLineInputReader;

  public ServerMessageHandler(
    SimpleChatClient chatClient, 
    BufferedReader commandLineInputReader, 
    BufferedReader inputReader) {
    super(inputReader);
    this.chatClient = chatClient;
    this.commandLineInputReader = commandLineInputReader;
  }

  public void run() {
    this.running = true;
    while (this.running) {
      try {
        String serverMessage = this.inputReader.readLine();

        if (StringUtils.IsNull(serverMessage)) {
          running = false;
          this.chatClient.close();
          this.commandLineInputReader.close();
          continue;
        }

        String formatted = formatServerMessage(serverMessage);
        System.out.println(formatted);
      } catch (SocketException socketException) {
        this.running = false;
        socketException.printStackTrace();
      } catch (IOException ioException) {
        this.running = false;
        ioException.printStackTrace();
      }
    }
  }

  private String formatServerMessage(String serverMessage) {
    String result = serverMessage;
    if (serverMessage.equals(Server.Message.SHUTDOWN_MESSAGE)) {
      result = MessageFormatter.formatStringColour(Display.Colour.WHITE, result);
    }

    return result;
  }
}