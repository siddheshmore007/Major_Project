package server.ors.routing;

import javax.imageio.IIOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class FetchOrders extends TimerTask {
    private static ServerSocket nse;

    public static ServerSocket getNse() {
        return nse;
    }

    public void run() {
        System.out.println("");

    }

    public static void main(String[] args) throws IOException {
        nse = new ServerSocket(5056);

        while (true)
        {
            Socket national = null;

            try
            {
                // socket object to receive incoming client requests
                national = nse.accept();

                System.out.println("A new client is connected : " + national);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(national.getInputStream());
                DataOutputStream dos = new DataOutputStream(national.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                Thread t = new ExchangeHandler(national, dis, dos);

                // Invoking the start() method
                t.start();


            }
            catch (Exception e){
                national.close();
                e.printStackTrace();
            }
        }

    }
}


