import java.io.*;
import java.net.*;
import java.util.logging.*;
import java.util.Base64;
import java.util.UUID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.*;
import java.net.*;
import org.json.JSONObject;
import org.json.XML;



public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) {
        try {
            // Set up logger
            FileHandler fileHandler = new FileHandler("server.log");
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO);

            // Create a server socket
            ServerSocket serverSocket;
            serverSocket = new ServerSocket(12345);
            logger.info("Server started. Listening for incoming connections...");

            while (true) {
                // Accept client connection
                Socket clientSocket = serverSocket.accept();
                logger.info("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Start a new thread to handle the client request
                Thread clientHandler = new Thread(new ClientHandler(clientSocket));
                clientHandler.start();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred in server", e);
        }
    }
}

class ClientHandler implements Runnable {
    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // Get input and output streams
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            // Read request type from client
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String requestType = reader.readLine();
            logger.info("Received request from client: " + requestType);

            // Process request based on request type
            String responseStr = "";
            // Read request data
            String requestData = reader.readLine();
            switch (requestType) {
                case "UPPERCASE":
                    responseStr = requestData.toUpperCase();
                    logger.info("Processed UPPERCASE request. Response: " + responseStr);
                    break;
                case "REVERSE":
                    responseStr = new StringBuilder(requestData).reverse().toString();
                    logger.info("Processed REVERSE request. Response: " + responseStr);
                    break;
                case "COUNT_WORDS":
                    responseStr = String.valueOf(requestData.split("\\s+").length);
                    logger.info("Processed COUNT_WORDS request. Response: " + responseStr);
                    break;
                case "COUNT_CHARACTERS":
                    responseStr = String.valueOf(requestData.length());
                    logger.info("Processed COUNT_CHARACTERS request. Response: " + responseStr);
                    break;
                case "REMOVE_SPACES":
                    responseStr = requestData.replaceAll("\\s+", "");
                    logger.info("Processed REMOVE_SPACES request. Response: " + responseStr);
                    break;
                case "STRIP_HTML":
                    responseStr = requestData.replaceAll("\\<.*?\\>", "");
                    logger.info("Processed STRIP_HTML request. Response: " + responseStr);
                    break;
                case "CALCULATE":
                    responseStr = "Not implemented"; // Implement this method
                    logger.info("Processed CALCULATE request. Response: " + responseStr);
                    break;
                case "SHA256_HASH":
                    responseStr = "Not implemented"; // Implement this method
                    logger.info("Processed SHA256_HASH request. Response: " + responseStr);
                    break;
                case "BASE64_ENCODE":
                    responseStr = Base64.getEncoder().encodeToString(requestData.getBytes());
                    logger.info("Processed BASE64_ENCODE request. Response: " + responseStr);
                    break;
                case "BASE64_DECODE":
                    logger.info("Received BASE64_DECODE request with data: " + requestData);
                    try {
                        responseStr = new String(Base64.getDecoder().decode(requestData));
                        logger.info("Processed BASE64_DECODE request. Response: " + responseStr);
                    } catch (IllegalArgumentException e) {
                        logger.warning("Illegal Base64 input: " + e.getMessage());
                        responseStr = "Error: Illegal Base64 input";
                    }
                    break;
                case "JSON_FORMAT":
                    responseStr = "Not implemented"; // Implement this method
                    logger.info("Processed JSON_FORMAT request. Response: " + responseStr);
                    break;
                case "XML_FORMAT":
                    responseStr = "Not implemented"; // Implement this method
                    logger.info("Processed XML_FORMAT request. Response: " + responseStr);
                    break;
                case "ROT13":
                    responseStr = "Not implemented"; // Implement this method
                    logger.info("Processed ROT13 request. Response: " + responseStr);
                    break;
                case "UUID_GENERATE":
                    responseStr = UUID.randomUUID().toString();
                    logger.info("Processed UUID_GENERATE request. Response: " + responseStr);
                    break;
                case "IP_LOOKUP":
                    responseStr = InetAddress.getByName(requestData).getHostAddress();
                    logger.info("Processed IP_LOOKUP request. Response: " + responseStr);
                    break;
                case "WEATHER_FORECAST":
                    responseStr = "Not implemented"; // Implement this method
                    logger.info("Processed WEATHER_FORECAST request. Response: " + responseStr);
                    break;
                case "FILE_READ":
                    responseStr = "Not implemented"; // Implement this method
                    logger.info("Processed FILE_READ request. Response: " + responseStr);
                    break;
                case "FILE_WRITE":
                    responseStr = "Not implemented"; // Implement this method
                    logger.info("Processed FILE_WRITE request. Response: " + responseStr);
                    break;
                case "IMAGE_PROCESS":
                    responseStr = "Not implemented"; // Implement this method
                    logger.info("Processed IMAGE_PROCESS request. Response: " + responseStr);
                    break;
                case "AUDIO_CONVERT":
                    responseStr = "Not implemented"; // Implement this method
                    logger.info("Processed AUDIO_CONVERT request. Response: " + responseStr);
                    break;
                case "VIDEO_CONVERT":
                    responseStr = "Not implemented"; // Implement this method
                    logger.info("Processed VIDEO_CONVERT request. Response: " + responseStr);
                    break;
                default:
                    logger.warning("Unknown request type: " + requestType);
                    break;
            }

            // Send response to client
            PrintWriter writer = new PrintWriter(outputStream, true);
            writer.println(responseStr);

            // Close streams and socket
            writer.close();
            reader.close();
            outputStream.close();
            inputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred in client handler", e);
        }
    }
}
