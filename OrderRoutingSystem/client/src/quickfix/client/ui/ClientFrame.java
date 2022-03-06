package quickfix.client.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import quickfix.client.Client;
import quickfix.client.ClientApplication;

import quickfix.client.ExecutionTableModel;
import quickfix.client.OrderTableModel;


public class ClientFrame extends JFrame {
    public ClientFrame(OrderTableModel orderTableModel,
                       ExecutionTableModel executionTableModel,
                       final ClientApplication application) {
        super();
        setTitle("Broker!");
        setSize(600, 400);

        if (System.getProperties().containsKey("openfix")) {
            createMenuBar(application);
        }
        getContentPane().add(new ClientPanel(
                        orderTableModel,
                        executionTableModel,
                        application),
                BorderLayout.CENTER);
        setVisible(true);
    }

    private void createMenuBar(final ClientApplication application) {
        JMenuBar menubar = new JMenuBar();

        JMenu sessionMenu = new JMenu("Session");
        menubar.add(sessionMenu);

        JMenuItem logonItem = new JMenuItem("Logon");
        logonItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client.get().logon();
            }
        });
        sessionMenu.add(logonItem);

        JMenuItem logoffItem = new JMenuItem("Logoff");
        logoffItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client.get().logout();
            }
        });
        sessionMenu.add(logoffItem);

        JMenu appMenu = new JMenu("Application");
        menubar.add(appMenu);

        JMenuItem appAvailableItem = new JCheckBoxMenuItem("Available");
        appAvailableItem.setSelected(application.isAvailable());
        appAvailableItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                application.setAvailable(((JCheckBoxMenuItem)
                        e.getSource()).isSelected());
            }
        });
        appMenu.add(appAvailableItem);

        JMenuItem sendMissingFieldRejectItem =
                new JCheckBoxMenuItem("Send Missing Field Reject");
        sendMissingFieldRejectItem.setSelected(application.isMissingField());
        sendMissingFieldRejectItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                application.setMissingField(((JCheckBoxMenuItem)
                        e.getSource()).isSelected());
            }
        });
        appMenu.add(sendMissingFieldRejectItem);

        setJMenuBar(menubar);
    }
}
