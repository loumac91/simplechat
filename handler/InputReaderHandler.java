package handler;

import java.io.*;

public abstract class InputReaderHandler extends BaseHandler {

  protected final BufferedReader inputReader;

  public InputReaderHandler(InputStream inputStream) {
    super();
    this.inputReader = new BufferedReader(new InputStreamReader(inputStream));
  }

  public InputReaderHandler(BufferedReader inputReader) {
    super();
    this.inputReader = inputReader;
  }
}