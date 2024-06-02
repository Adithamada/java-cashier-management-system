package cashierapp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import cashierapp.data.*;

public class CheckOutFrame extends JFrame implements ActionListener {

    private JTable checkoutList;
    private DefaultTableModel tableModel;

    private JLabel labelTotalPrice;
    private JLabel labelChange;

    private JButton buttonBack;

    private JPanel topPanel;
    private JPanel bottomPanel;

    private String[] columnName = {"No", "Id", "Name", "Price", "Amount", "Total Price"};
    private Cart cart;
    private double payment;
    private CartManagementPanel cartManagementPanel;

    public CheckOutFrame(Cart cart, double payment,CartManagementPanel cartManagementPanel) {
        this.cart = cart;
        this.payment = payment;
        this.cartManagementPanel= cartManagementPanel;
        
        setSize(500, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("Checkout");

        topPanel = new JPanel();
        topPanel.setBounds(0, 0, 500, 500);
        topPanel.setLayout(new BorderLayout());
        
        tableModel = new DefaultTableModel(columnName, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        checkoutList = new JTable(tableModel);
        checkoutList.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(checkoutList);

        labelTotalPrice = new JLabel();
        labelChange = new JLabel();
        
        topPanel.add(scrollPane, BorderLayout.NORTH);
        topPanel.add(labelTotalPrice, BorderLayout.CENTER);
        topPanel.add(labelChange, BorderLayout.SOUTH);

        bottomPanel = new JPanel();
        bottomPanel.setBounds(0, 500, 500, 200);
        bottomPanel.setBorder(new EmptyBorder(5, 20, 50, 25));
        bottomPanel.setLayout(new GridLayout(1, 1));
        buttonBack = new JButton("Finish");
        bottomPanel.add(buttonBack);

        buttonBack.addActionListener(this);

        add(topPanel);
        add(bottomPanel);
        setVisible(true);
        
        tableList();
    }

    public void tableList() {
        tableModel.setRowCount(0);
        int index = 1;
        for (Product product : cart.productList()) {
            Object[] rowData = {
                index++, product.getId(),
                product.getName(),
                product.getPrice(),
                product.getPurchaseAmount(),
                product.getPrice() * product.getPurchaseAmount()
            };
            tableModel.addRow(rowData);
        }
        labelTotalPrice.setText("Total Price: $" + cart.calculateTotal());
        labelChange.setText("Change: $" + (payment - cart.calculateTotal()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonBack) {
        	cartManagementPanel.clearCartAndRefreshTable();
            dispose();
        }
    }
}
