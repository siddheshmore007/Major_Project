package server.ors.routing;

import java.util.Timer;
import java.util.TimerTask;

public class StartTask {
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new OrderRouter();
        timer.schedule(task, 20, 120000);


    }
}
