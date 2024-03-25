import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Client <requestType> <requestData>");
            return;
        }

        String requestType = args[0];
        String requestData = args[1];

        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 12345);

            // Get input and output streams
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send request to server
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
