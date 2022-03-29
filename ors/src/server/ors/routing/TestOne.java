package server.ors.routing;

// import quickfix.field.SenderCompID;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import quickfix.field.Quantity;
import quickfix.field.Side;
import quickfix.field.Symbol;
import server.ors.connector.OrderType;
import server.ors.database.ConnectionProvider;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TimerTask;

public class TestOne extends TimerTask {

    public void run() {
        int current_order_id = 1;
        String SenderCompID = "";
        String TargetCompID = "";
        String Symbol = "";
        int Quantity = 0;
        String Side = "";
        String OrderType = "";
        MarketData new_data_request;
        ArrayList data = new ArrayList<>(15);


        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from mesges");

            while (rs.next()) {
                current_order_id = rs.getRow();
                SenderCompID = rs.getNString(2);
                TargetCompID = rs.getNString(3);
                Symbol = rs.getNString(4);
                Quantity = rs.getInt(5);
                Side = rs.getNString(6);
                OrderType = rs.getNString(7);


                String REQUEST_URL = "https://api.twelvedata.com/time_series?symbol=" + Symbol+ "&interval=1min&outputsize=12&apikey=607c5e586ada4097ae0ca64a4622ac45";

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
                String Exchange = "NASDAQ";

                try {
                    Connection con_two = ConnectionProvider.getCon();
                    Statement statement = con.createStatement();
                    statement.executeUpdate("insert into order_routing_history values('"+current_order_id+"', '"+SenderCompID+"', '"+TargetCompID+"', '"+Symbol+"', '"+Quantity+"', '"+Side+"', '"+OrderType+"', '"+Exchange+"')");
                }
                catch (Exception e) {
                    System.out.println("Yor are stupid");
                }

                System.out.println(values);


                //System.out.println(data);
                //System.out.println("This is Tesla");

//                System.out.println(current_order_id);
//                System.out.println(SenderCompID);
//                System.out.println(TargetCompID);
//                System.out.println(Symbol);
//                System.out.println(Quantity);
//                System.out.println(Side);
//                System.out.println(OrderType);
                //st.executeQuery("Delete from mesges where messageID=" + current_order_id);
            }
        }
        catch (Exception e) {
            System.out.println("Shut the fuck up!");
        }
    }

}
