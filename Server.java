import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.BindException;

public class Server {
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
      Server server = new Server(14001);
      server.run();
    }

    private void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.port);
            Socket client = serverSocket.accept();
            
            PrintWriter sendStream = new PrintWriter(client.getOutputStream());
            BufferedReader readStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
           
            String inputLine = readStream.readLine();
            
            while (inputLine != null) {
                sendStream.println(inputLine);
                sendStream.flush();

                inputLine = readStream.readLine();
            }

            serverSocket.close();

        } catch (BindException bindException) {
          bindException.getStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}