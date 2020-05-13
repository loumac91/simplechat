package handler;

import parse.MessageParser;

public abstract class BaseHandler implements Runnable {

  protected Boolean running = false;

  protected final MessageParser messageParser;

  public BaseHandler() { 
    this.messageParser = new MessageParser();
   }
}