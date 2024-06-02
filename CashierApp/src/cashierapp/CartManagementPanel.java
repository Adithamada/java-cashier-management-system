package cashierapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import cashierapp.data.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class CartManagementPanel extends JPanel implements ActionListener {

    private JPanel panelTop;
    private JPanel panelCenter;
    private JPanel panelBottom;

    private JTable cartTable;
    private DefaultTableModel tableModel;

    private JLabel labelId;
    private JLabel labelAmount;
    private JLabel labelTotalPrice;

    private JTextField fieldId;
    private JTextField fieldAmount;

    private JButton addCartBtn;
    private JButton removeCartBtn;
    private JButton resetBtn;
    private JButton checkoutBtn;
    private Cart cart;
    private Cashier cashier;
    private String[] columnName = {"No", "Id", "Name", "Price", "Amount", "Total Price"};

    public CartManagementPanel() {

        // PANEL TOP AKA CART LIST
        panelTop = new JPanel();
        cart = new Cart();
        cashier = new Cashier();
        panelTop.setBorder(new EmptyBorder(15, 10, 15, 15));
        panelTop.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(columnName, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        cartTable = new JTable(tableModel);
        cartTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(cartTable);

        labelTotalPrice = new JLabel("Total Price: $" + cart.calculateTotal());
        labelTotalPrice.setFont(new Font("Mono", Font.BOLD, 20));

        panelTop.add(scrollPane, BorderLayout.CENTER);
        panelTop.add(labelTotalPrice, BorderLayout.SOUTH);

        // PANEL CENTER AKA FIELD
        panelCenter = new JPanel();
        panelCenter.setBorder(new EmptyBorder(15, 10, 15, 15));
        panelCenter.setLayout(new GridLayout(2, 2, 5, 5));
        labelId = new JLabel("Product Id: ");
        labelAmount = new JLabel("Purchase Amount: ");
        fieldId = new JTextField();
        fieldAmount = new JTextField();
        panelCenter.add(labelId);
        panelCenter.add(fieldId);
        panelCenter.add(labelAmount);
        panelCenter.add(fieldAmount);

        // PANEL BOTTOM AKA BUTTON
        panelBottom = new JPanel();
        panelBottom.setBorder(new EmptyBorder(15, 10, 15, 15));
        panelBottom.setLayout(new GridLayout(4, 1, 5, 5));
        addCartBtn = new JButton("Add To Cart");
        removeCartBtn = new JButton("Remove From Cart");
        resetBtn = new JButton("Reset Field");
        checkoutBtn = new JButton("CheckOut");
        panelBottom.add(addCartBtn);
        panelBottom.add(removeCartBtn);
        panelBottom.add(resetBtn);
        panelBottom.add(checkoutBtn);

        addCartBtn.addActionListener(this);
        removeCartBtn.addActionListener(this);
        resetBtn.addActionListener(this);
        checkoutBtn.addActionListener(this);

        setLayout(new BorderLayout());
        add(panelTop, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
    }

    public void tableList() {
        tableModel.setRowCount(0);
        int index = 1;
        for (Product product : cart.listProducts) {
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addCartBtn) {
            int id = Integer.parseInt(fieldId.getText());
            int amount = Integer.parseInt(fieldAmount.getText());
            if (id != 0 || amount != 0) {
                cashier.addProductToCart(cart, id, amount);
                JOptionPane.showMessageDialog(panelCenter, "Product added to Cart");
                tableList(); // Update the table after adding a product
            } else {
                JOptionPane.showMessageDialog(panelCenter, "Product failed to be added to Cart");
            }
        }else if(e.getSource()==removeCartBtn) {
            int id = Integer.parseInt(fieldId.getText());
            if(id != 0) {
            	cashier.removeProductFromCart(cart, id);
                JOptionPane.showMessageDialog(panelCenter, "Product removed from Cart");
            }else {
                JOptionPane.showMessageDialog(panelCenter, "Product fail to Removed");
            }
        }else if(e.getSource()==resetBtn) {
        	fieldId.setText("");
        	fieldAmount.setText("");
        }else if(e.getSource()==checkoutBtn) {
        	new CheckOutFrame();
        	tableList();
        }
    }
}
