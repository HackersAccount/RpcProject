import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class WeatherService {
    private static final String API_KEY_ENV_VAR = "API_KEY";

    public static void main(String[] args) {
        try {
            // Load environment variables from .env file
            Map<String, String> envMap = EnvParser.parseEnvFile("rpcserver/src/main/java/.env");

            // Get API key from environment variables
            String apiKey = envMap.get(API_KEY_ENV_VAR);

            // Use the API key in your application
            System.out.println("API key: " + apiKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getApiKey() {
        String apiKey = "";
        try {
            // Load environment variables from .env file
            Map<String, String> envMap = EnvParser.parseEnvFile("rpcserver/src/main/java/.env");

            // Get API key from environment variable
            apiKey = envMap.get(API_KEY_ENV_VAR);

            // Use the API key in your application
            System.out.println(fetchWeatherForecast("London"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiKey;
    }

    static String fetchWeatherForecast(String city) throws IOException {
        // Replace "YOUR_API_KEY" with your actual API key
        String apiKey = WeatherService.getApiKey();
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

        // Make HTTP request to fetch weather forecast data
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Read response
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Parse JSON response and extract forecast data
        JSONObject json = new JSONObject(response.toString());
        JSONObject main = json.getJSONObject("main");
        double temperature = main.getDouble("temp");
        double humidity = main.getDouble("humidity");
        JSONArray weatherArray = json.getJSONArray("weather");
        String description = weatherArray.getJSONObject(0).getString("description");

        // Format forecast data
        return "Temperature: " + temperature + "°C\nHumidity: " + humidity + "%\nDescription: " + description;
    }
}
