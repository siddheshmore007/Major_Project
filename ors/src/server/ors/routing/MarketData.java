package server.ors.routing;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MarketData {
    //private static String REQUEST_URL = "https://api.twelvedata.com/time_series?symbol=AAPL&interval=1min&outputsize=12&apikey=607c5e586ada4097ae0ca64a4622ac45";
    public String REQUEST_URL = "";

    public ArrayList get_data(String symbol) throws Exception {
        REQUEST_URL = "https://api.twelvedata.com/time_series?symbol=" + symbol + "&interval=1min&outputsize=12&apikey=607c5e586ada4097ae0ca64a4622ac45";

        URL requestURL = new URL(REQUEST_URL);
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();
        StringBuffer responseData = new StringBuffer();
        JSONParser parser = new JSONParser();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "twelve_java/1.0");
        connection.connect();

        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Request failed. Error: " + connection.getResponseMessage());
        }

        Scanner scanner = new Scanner(requestURL.openStream());
        while (scanner.hasNextLine()) {
            responseData.append(scanner.nextLine());
        }

        JSONObject json = (JSONObject) parser.parse(responseData.toString());
        JSONObject meta = (JSONObject) json.get("meta");
        JSONArray values = (JSONArray) json.get("values");

        ArrayList response = new ArrayList();
        for (int i = 0; i < 12; i++) {

            response.add(values.get(i));
        }

        return response;
    }
}
