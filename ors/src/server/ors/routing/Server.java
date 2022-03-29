package server.ors.routing;




import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

// Server class
public class Server
{
    public static void main(String[] args) throws IOException {
        Timer timer = new Timer();
        TimerTask task = new TestOne();
        timer.schedule(task, 2000, 5000);



    }
}

