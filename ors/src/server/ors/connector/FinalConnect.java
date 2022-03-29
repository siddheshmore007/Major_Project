package server.ors.connector;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;

import server.ors.database.ConnectionProvider;
import server.ors.routing.MarketData;

public class FinalConnect {


    private static final String REQUEST_URL = "https://api.twelvedata.com/time_series?symbol=INFY&interval=1min&outputsize=12&apikey=607c5e586ada4097ae0ca64a4622ac45";

    //private static final String REQUEST_URL = "https://eodhistoricaldata.com/api/eod/INFY.NSE?from=2017-01-05&to=2017-02-10&period=d&fmt=json&api_token={OeAFFmMliFG5orCUuwAKQ8l4WWFQ67YX}";

    public static void main(String[] args) throws Exception {
        URL requestURL = new URL(REQUEST_URL);
        HttpURLConnection connection = (HttpURLConnection)requestURL.openConnection();
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
        for (int i=0; i<12; i++) {

            response.add(values.get(i));
        }
        System.out.println(response);
        connection.disconnect();


    }






}


