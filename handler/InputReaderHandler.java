package handler;

import java.io.*;

public abstract class InputReaderHandler extends BaseHandler {

  protected BufferedReader inputReader;

  public InputReaderHandler(InputStream inputStream) {
    super();
    this.inputReader = new BufferedReader(new InputStreamReader(inputStream));
  }
}