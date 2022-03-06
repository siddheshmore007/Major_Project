package quickfix.client.ui;

import quickfix.client.ClientApplication;
import quickfix.client.Order;
import quickfix.client.OrderTableModel;

import javax.swing.table.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OrderTable extends JTable implements MouseListener {
    private transient ClientApplication application;

    public OrderTable(OrderTableModel orderTableModel,
                      ClientApplication application) {
        super(orderTableModel);
        this.application = application;
        addMouseListener(this);
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer,
                                     int row, int column) {
        Order order = (Order)((OrderTableModel)dataModel).getOrder(row);

        int open = order.getOpen();
        int executed = order.getExecuted();
        boolean rejected = order.getRejected();
        boolean canceled = order.getCanceled();

        DefaultTableCellRenderer r = (DefaultTableCellRenderer)renderer;
        r.setForeground(Color.black);

        if(rejected)
            r.setBackground(Color.red);
        else if(canceled)
            r.setBackground(Color.white);
        else if(open == 0 && executed == 0)
            r.setBackground(Color.yellow);
        else if(open > 0)
            r.setBackground(Color.green);
        else if(open == 0)
            r.setBackground(Color.white);

        return super.prepareRenderer(renderer, row, column);
    }

    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() != 2)
            return;
        int row = rowAtPoint(e.getPoint());
        Order order = (Order)((OrderTableModel)dataModel).getOrder(row);
        application.cancel(order);
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}
