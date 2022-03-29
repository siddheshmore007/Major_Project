package server.ors.routing;

import server.ors.database.ConnectionProvider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TimerTask;

public class Hope extends TimerTask {
    public ArrayList order_queue = new ArrayList<Integer>(50);
    int current = 0;
    int current_order_id = 0;
    String symbol = "";
    String query = "";
    String side = "";
    String type = "";
    String SenderCompID = "BROKER";
    String TargetCompID = "";

    public void run() {
        String path = "C:\\Users\\morel\\Desktop\\client-server\\src\\test\\prices.csv";
        String line = "";
        ArrayList sym = new ArrayList<>(10);
        ArrayList nprice = new ArrayList(10);
        ArrayList bprice = new ArrayList<>(10);
        int quant = 0;


        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                //ArrayList sym = new ArrayList<>(10);
                sym.add(values[0]);
                nprice.add(Integer.parseInt(values[1]));
                bprice.add(Integer.parseInt(values[2]));
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sym);
        System.out.println(nprice);
        System.out.println(bprice);


        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from mesges");
            int i = 0;
            //order_queue.add(rs.getInt(1));
            while(rs.next()) {
                order_queue.add(rs.getInt(1));
                System.out.println(rs.getInt(1) + rs.getNString(4));
                // System.out.println(order_queue.get(i));
                i++;
                symbol = rs.getNString(4);
                quant = rs.getInt(5);
                side = rs.getNString(6);
                type = rs.getNString(7);

                System.out.println(side);
                System.out.println(sym.indexOf(symbol));
                int sym_index = sym.indexOf(symbol);

                if (side.equals("Buy")) {
                    if ((int) nprice.get(sym_index) < (int)bprice.get(sym_index)) {
                        TargetCompID = "EONE";
                        String Exchange = "NSE";
                        try {
                            Connection new_con = ConnectionProvider.getCon();
                            Statement stmt = new_con.createStatement();
                            stmt.executeUpdate("insert into eone(SenderCompID, TargetCompID, symbol, quantity, side, order_type, Exchange)" + "values('" + SenderCompID + "', '" + TargetCompID + "', '" + symbol + "', '" + quant + "', '" + side + "', '" + type + "', '" + Exchange + "')");
                            stmt.executeUpdate("insert into order_routing_history(SenderCompID, TargetCompID, symbol, quantity, side, order_type, Exchange)" + "values('" + SenderCompID + "', '" + TargetCompID + "', '" + symbol + "', '" + quant + "', '" + side + "', '" + type + "', '" + Exchange + "')");

                        }
                        catch (Exception e) {
                            System.out.println("nse failed!");
                        }
                    }
                    else {
                        TargetCompID = "ETWO";
                        String Exchange = "BSE";
                        try {
                            Connection f_con = ConnectionProvider.getCon();
                            Statement state = f_con.createStatement();
                            state.executeUpdate("insert into etwo(SenderCompID, TargetCompID, symbol, quantity, side, order_type, Exchange)" + "values('" + SenderCompID + "', '" + TargetCompID + "', '" + symbol + "', '" + quant + "', '" + side + "', '" + type + "', '" + Exchange + "')");
                            state.executeUpdate("insert into order_routing_history(SenderCompID, TargetCompID, symbol, quantity, side, order_type, Exchange)" + "values('" + SenderCompID + "', '" + TargetCompID + "', '" + symbol + "', '" + quant + "', '" + side + "', '" + type + "', '" + Exchange + "')");
                        }
                        catch (Exception e) {
                            System.out.println("You are worthless");
                        }
                    }
                }

            }



        }
        catch (Exception e) {
            System.out.println(e);
        }

        System.out.println(order_queue);



        /*Router lost_cause = new Router();*/
        /*lost_cause.route(order_queue);*/
//        Timer newTimer = new Timer();
//        TimerTask routerTask = new Router();
//        newTimer.schedule(routerTask, 0, 120000);

        //route(order_queue);
    }


}


