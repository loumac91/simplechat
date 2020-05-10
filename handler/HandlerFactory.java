package handler;

import java.io.BufferedReader;

import server.SimpleChatServer;
import server.SimpleChatUser;

public class HandlerFactory {
  
  public static Runnable createClientConnectHandler(SimpleChatServer simpleChatServer) {
    return new ClientConnectHandler(simpleChatServer);
  }

  public static Runnable createClientMessageHandler(SimpleChatServer chatServer, SimpleChatUser chatUser) {
    return new ClientMessageHandler(chatServer, chatUser);
  }

  public static Runnable createServerMessageHandler(BufferedReader inputReader) {
    return new ServerMessageHandler(inputReader);
  }
}