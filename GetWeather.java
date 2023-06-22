import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.*;

//for data use https://openweathermap.org/

public class GetWeather {

    ReadFile configData = new ReadFile();

    private String apiKey = configData.readFile().get("apiKey");
    private String location = "your prefered location";

    public Map<String, Object> jsonToMap(String str) {

        // convert Json to a Map
        Map<String, Object> map = new Gson().fromJson(str, new TypeToken<HashMap<String, Object>>() {}.getType());
        return map;
    }

    public void getInfo() {

        String urlString = "https://api.openweathermap.org/data/2.5/weather?q="
                + location + "&appid=" + apiKey; // add "&lang={countrycode} es IT"

        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;

            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            //

            Map<String, Object> respMap = jsonToMap(result.toString());

            //For the weather section, that contains an array, which instead needs to be casted to an ArrayList of maps

            ArrayList<Map<String, Object>> weathers = (ArrayList<Map<String, Object>>) respMap.get("weather");
            Map<String, Object> weatherMap = weathers.get(0);

            System.out.println("Current main weather in " + location + " : " + weatherMap.get("main"));
            System.out.println("More details : ");
            System.out.println("Detailed description: " + weatherMap.get("description"));
            System.out.println(" ");

            //Other section

            Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
            Map<String, Object> windMap = jsonToMap(respMap.get("wind").toString());

            System.out.println("Humidity: " + mainMap.get("humidity"));
            System.out.println("Wind speed: " + windMap.get("speed"));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getForecastURL() throws IOException {

        // lat and lon required to get forecast weather for the location

        String urlString = "https://api.openweathermap.org/data/2.5/weather?q="
                + location + "&appid=" + apiKey;

        String forecastURL = null;

        try {
            StringBuilder getCoordinatesResult = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                getCoordinatesResult.append(line);
            }
            rd.close();
            Map<String, Object> respMap = jsonToMap(getCoordinatesResult.toString());
            Map<String, Object> coordMap = jsonToMap(respMap.get("coord").toString());
            String lat = coordMap.get("lat").toString();
            String lon = coordMap.get("lon").toString();
            forecastURL = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey;

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return forecastURL;
    }

    public String getForecastWeather() throws IOException {

        String coordinate = getForecastURL();

        String forecastMessage = null;

        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(coordinate);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;

            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            Map<String, Object> respMap = jsonToMap(result.toString());

            // access weather info
            ArrayList<Map<String, Object>> weathers = (ArrayList<Map<String, Object>>) respMap.get("weather");
            Map<String, Object> weatherMap = weathers.get(0);

            forecastMessage = ("Hi buddy! In " + location + ", for the next 3 hours the weather will be: " + weatherMap.get("description") + "!");
            System.out.println(" ");
        }

        catch (IOException e) {
        System.out.println(e.getMessage());
    }

        return forecastMessage;
    }
}
