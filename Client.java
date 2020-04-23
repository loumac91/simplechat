import java.io.*;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
      BufferedReader inputStream = new BufferedReader(new InputStreamReader(System.in));

      // Prompt for user name

      try (ClientSocket clientSocket = new ClientSocket(Constants.SERVER_ADDRESS, Constants.SERVER_PORT)) {
        // Print connecting to localhost:14001...
        clientSocket.connect();
        // Print successful connection to server ...

        // Place into separate class 
        String userInput = inputStream.readLine();
        String response;

        while (userInput != null) {
          clientSocket.sendMessage(userInput);

          response = clientSocket.readNextMessage();
          System.out.println(response);

          userInput = inputStream.readLine();
        }
      } catch (UnknownHostException unknownHostException) {
        unknownHostException.getStackTrace();
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    }
}