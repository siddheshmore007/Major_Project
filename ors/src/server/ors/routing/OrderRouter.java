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

public class OrderRouter extends TimerTask {
    public ArrayList order_queue = new ArrayList<Integer>(50);
    int current = 0;
    int current_order_id = 0;
    String symbol = "";
    String query = "";
    String side = "";
    String type = "";
    String SenderCompID = "BROKER";
    String TargetCompID = "";
    Double limit = 0.0;
    Double stop = 0.0;
    String in_queue = "";

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
            ResultSet res = st.executeQuery("select * from orderQueue");

            int i = 0;
            //order_queue.add(res.getInt(1));
            while(res.next()) {
                in_queue = res.getNString(10);
                if (in_queue.equals("false"))
                {
                    order_queue.add(res.getInt(1));
                    int current = res.getInt(1);
                    try {
                        Connection upcon = ConnectionProvider.getCon();
                        Statement upst = upcon.createStatement();
                        upst.executeUpdate("update orderQueue set in_queue = 'true' where messageID = " + current);
                    } catch (Exception e) {
                        System.out.println("Update failed!");
                    }
                    System.out.println(res.getInt(1) + res.getNString(4));
                    // System.out.println(order_queue.get(i));
                    i++;
                    symbol = res.getNString(4);
                    quant = res.getInt(5);
                    side = res.getNString(6);
                    type = res.getNString(7);
                    limit = res.getDouble(8);
                    stop = res.getDouble(9);


                    System.out.println(side);
                    System.out.println(sym.indexOf(symbol));
                    int sym_index = sym.indexOf(symbol);

                    if (side.equals("Buy")) {
                        if ((int) nprice.get(sym_index) < (int) bprice.get(sym_index)) {
                            TargetCompID = "EONE";
                            String Exchange = "NSE";
                            try {
                                Connection new_con = ConnectionProvider.getCon();
                                Statement stmt = new_con.createStatement();
                                stmt.executeUpdate("insert into eone(SenderCompID, TargetCompID, symbol, quantity, side, order_type, limit_price, stop_price, Exchange)" + "values('" + SenderCompID + "', '" + TargetCompID + "', '" + symbol + "', '" + quant + "', '" + side + "', '" + type + "', '"+limit+"', '"+stop+"', '" + Exchange + "')");
                                stmt.executeUpdate("insert into order_routing_history(SenderCompID, TargetCompID, symbol, quantity, side, order_type, limit_price, stop_price, Exchange)" + "values('" + SenderCompID + "', '" + TargetCompID + "', '" + symbol + "', '" + quant + "', '" + side + "', '" + type + "', '"+limit+"', '"+stop+"', '" + Exchange + "')");

                            } catch (Exception e) {
                                System.out.println("nse failed!");
                            }
                        } else {
                            TargetCompID = "ETWO";
                            String Exchange = "BSE";
                            try {
                                Connection f_con = ConnectionProvider.getCon();
                                Statement state = f_con.createStatement();
                                state.executeUpdate("insert into etwo(SenderCompID, TargetCompID, symbol, quantity, side, order_type, limit_price, stop_price, Exchange)" + "values('" + SenderCompID + "', '" + TargetCompID + "', '" + symbol + "', '" + quant + "', '" + side + "', '" + type + "', '"+limit+"', '"+stop+"', '" + Exchange + "')");
                                state.executeUpdate("insert into order_routing_history(SenderCompID, TargetCompID, symbol, quantity, side, order_type, limit_price, stop_price, Exchange)" + "values('" + SenderCompID + "', '" + TargetCompID + "', '" + symbol + "', '" + quant + "', '" + side + "', '" + type + "', '"+limit+"', '"+stop+"', '" + Exchange + "')");

                            } catch (Exception e) {
                                System.out.println("bse failed!");
                            }
                        }
                    } else if (side.equals("Sell")) {
                        if ((int) nprice.get(sym_index) > (int) bprice.get(sym_index)) {
                            TargetCompID = "EONE";
                            String Exchange = "NSE";
                            try {
                                Connection new_con = ConnectionProvider.getCon();
                                Statement stmt = new_con.createStatement();
                                stmt.executeUpdate("insert into eone(SenderCompID, TargetCompID, symbol, quantity, side, order_type, limit_price, stop_price, Exchange)" + "values('" + SenderCompID + "', '" + TargetCompID + "', '" + symbol + "', '" + quant + "', '" + side + "', '" + type + "', '"+limit+"', '"+stop+"', '" + Exchange + "')");
                                stmt.executeUpdate("insert into order_routing_history(SenderCompID, TargetCompID, symbol, quantity, side, order_type, limit_price, stop_price, Exchange)" + "values('" + SenderCompID + "', '" + TargetCompID + "', '" + symbol + "', '" + quant + "', '" + side + "', '" + type + "', '"+limit+"', '"+stop+"', '" + Exchange + "')");

                            } catch (Exception e) {
                                System.out.println("nse failed!");
                            }
                        } else {
                            TargetCompID = "ETWO";
                            String Exchange = "BSE";
                            try {
                                Connection f_con = ConnectionProvider.getCon();
                                Statement state = f_con.createStatement();
                                state.executeUpdate("insert into etwo(SenderCompID, TargetCompID, symbol, quantity, side, order_type, limit_price, stop_price, Exchange)" + "values('" + SenderCompID + "', '" + TargetCompID + "', '" + symbol + "', '" + quant + "', '" + side + "', '" + type + "', '"+limit+"', '"+stop+"', '" + Exchange + "')");
                                state.executeUpdate("insert into order_routing_history(SenderCompID, TargetCompID, symbol, quantity, side, order_type, limit_price, stop_price, Exchange)" + "values('" + SenderCompID + "', '" + TargetCompID + "', '" + symbol + "', '" + quant + "', '" + side + "', '" + type + "', '"+limit+"', '"+stop+"', '" + Exchange + "')");

                            } catch (Exception e) {
                                System.out.println("bse failed!");
                            }
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


