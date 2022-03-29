/*
package server.ors.connector;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.DefaultMessageFactory;
import quickfix.FileStoreFactory;
import quickfix.Initiator;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.ScreenLogFactory;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;
import server.ors.ui.OrderRoutingFrame;
import server.ors.ui.MessageTableModel;


public class ConnectToExchange {



    private static final CountDownLatch shutdownLatch = new CountDownLatch(1);

    private static Logger log = LoggerFactory.getLogger(ConnectToExchange.class);
    private static ConnectToExchange connect;
    private boolean initiatorStarted = false;
    private Initiator initiator = null;
    private JFrame frame = null;

    public ConnectToExchange(String[] args)throws Exception {
        InputStream inputStream = null;
        if (args.length == 0) {
            inputStream = new BufferedInputStream(
                    new FileInputStream(
                            new File( "config/ors_exchanges.cfg" )));
        } else if (args.length == 1) {
            inputStream = new FileInputStream(args[0]);
        }
        if (inputStream == null) {
            System.out.println("usage: " + ConnectToExchange.class.getName() + " [configFile].");
            return;
        }
        SessionSettings settings = new SessionSettings(inputStream);
        inputStream.close();

        boolean logHeartbeats = Boolean.valueOf(System.getProperty("logHeartbeats", "true")).booleanValue();


        //OrderTableModel orderTableModel = new OrderTableModel();
        //ExecutionTableModel executionTableModel = new ExecutionTableModel();
        //ClientApplication application = new ClientApplication(orderTableModel, executionTableModel);
        MessageTableModel conMessageTableModel = new MessageTableModel();
        ConnectToExchangeApplication application = new ConnectToExchangeApplication(conMessageTableModel);
        //MessageStoreFactory messageStoreFactory = new FileStoreFactory(settings);
        //LogFactory logFactory = new ScreenLogFactory(true, true, true, logHeartbeats);
        //MessageFactory messageFactory = new DefaultMessageFactory();

        MessageStoreFactory messageStoreFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new ScreenLogFactory(true, true, true, logHeartbeats);
        MessageFactory messageFactory = new DefaultMessageFactory();


        initiator = new SocketInitiator(application, messageStoreFactory, settings, logFactory, messageFactory);

        //frame = new ClientFrame(orderTableModel, executionTableModel, application);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
    public synchronized void logon() {
        if (!initiatorStarted) {
            try {
                initiator.start();
                initiatorStarted = true;
            } catch (Exception e) {
                log.error("Logon failed", e);
            }
        } else {
            Iterator<SessionID> sessionIds = initiator.getSessions().iterator();
            while (sessionIds.hasNext()) {
                SessionID sessionId = (SessionID) sessionIds.next();
                Session.lookupSession(sessionId).logon();
            }
        }
    }

    public void logout() {
        Iterator<SessionID> sessionIds = initiator.getSessions().iterator();
        while (sessionIds.hasNext()) {
            SessionID sessionId = (SessionID) sessionIds.next();
            Session.lookupSession(sessionId).logout("user requested");
        }
    }

    public void stop() {
        shutdownLatch.countDown();
    }

    public JFrame getFrame() {
        return frame;
    }

    public static ConnectToExchange get() {
        return connect;
    }

    public static void main(String args[]) throws Exception {
        System.out.println("Hello");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
        connect = new ConnectToExchange(args);
        if (!System.getProperties().containsKey("openfix")) {
            connect.logon();
        }
        shutdownLatch.await();
    }
}


 */