package server.ors.routing;

import server.ors.database.ConnectionProvider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;



public class FetchOrder extends TimerTask {
    public ArrayList order_queue = new ArrayList<Integer>(50);


    @Override
    public void run() {
        System.out.println("Hello World!");
        int[] m_id = new int[10];
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from mesges");
            int i = 1;
            while(rs.next()) {
                m_id[i] = rs.getInt(1);
                System.out.println(m_id[i]);
                order_queue.add(m_id[i]);
                i++;
            }
        }
        catch (Exception e) {
            System.out.println("Query Failed!");
        }


    }
    public static void main(String[] args) {
        ArrayList order_queue = new ArrayList<Integer>(50);

        Timer timer = new Timer();
        TimerTask task = new FetchOrder();
        timer.schedule(task, 0, 5000);

        System.out.println(order_queue);

    }

}
