package quickfix.client.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import quickfix.client.ClientApplication;
import quickfix.client.OrderTableModel;

public class OrderPanel extends JPanel {
    private JTable orderTable = null;

    public OrderPanel(OrderTableModel orderTableModel,
                      ClientApplication application) {
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;

        orderTable = new OrderTable(orderTableModel, application);
        add(new JScrollPane(orderTable), constraints);
    }

    public JTable orderTable() {
        return orderTable;
    }
}
