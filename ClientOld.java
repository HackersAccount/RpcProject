import java.io.*;
import java.net.*;

public class ClientOld {
    public static void main(String[] args) {
        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 12345);

            // Get input and output streams
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send request to server
            String requestType = "UPPERCASE"; // Change request type here
            String requestData = "hello world"; // Change request data here
            writer.println(requestType);
            writer.println(requestData);

            // Receive response from server
            String response = reader.readLine();
            System.out.println("Response from server: " + response);

            // Close streams and socket
            writer.close();
            reader.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
