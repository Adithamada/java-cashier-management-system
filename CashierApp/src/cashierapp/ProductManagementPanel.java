package cashierapp;

import java.awt.Color;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProductManagementPanel extends JPanel{
	
	private JPanel topPanel;
	private JPanel centerPanel;
	private JPanel bottomPanel;
	
	private JTable productTable;
	private DefaultTableModel tableModel;
	
	private JTextField fieldName;
	private JTextField fieldPrice;
	private JTextField fieldStock;
	
	private JLabel labelName;
	private JLabel labelPrice;
	private JLabel labelStock;
	
	private JButton addBtn;
	private JButton updateBtn;
	private JButton deleteBtn;
	private JButton resetBtn;
	
	private String[] columnName = {"Id","Name","Price","Stock"};
	private Object[][] data = {};
	
	public ProductManagementPanel() {
		//TOP PANEL
		topPanel = new JPanel();
		topPanel.setBorder(new EmptyBorder(15,10,15,15));
		topPanel.setBounds(0,0,600,350);
		topPanel.setLayout(new GridLayout(1,1));
		tableList();
		tableModel = new DefaultTableModel(data,columnName){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
		productTable =new JTable(tableModel);
		topPanel.add(productTable);
		tableModel.addRow(columnName);
		JScrollPane scrollPane = new JScrollPane(productTable);
		productTable.getTableHeader().setReorderingAllowed(false);
		topPanel.add(scrollPane);
		
		//CENTER PANEL
		centerPanel = new JPanel();
		centerPanel.setBorder(new EmptyBorder(15,10,15,15));
		centerPanel.setBounds(0,350,600,200);
		centerPanel.setLayout(new GridLayout(3,2,10,10));
		fieldName = new JTextField();
		labelName = new JLabel("Product Name : ");
		fieldPrice = new JTextField();
		labelPrice = new JLabel("Product Price : ");
		fieldStock = new JTextField();
		labelStock = new JLabel("Product Stock : ");
		centerPanel.add(labelName);
		centerPanel.add(fieldName);
		centerPanel.add(labelPrice);
		centerPanel.add(fieldPrice);
		centerPanel.add(labelStock);
		centerPanel.add(fieldStock);
		
		//BOTTOM PANEL
		bottomPanel = new JPanel();
		bottomPanel.setBounds(0,550,600,250);
		bottomPanel.setBorder(new EmptyBorder(15,10,15,15));
		bottomPanel.setLayout(new GridLayout(5,1,10,10));
		addBtn =new JButton("Add Product");
		updateBtn =new JButton("Update Product");
		deleteBtn =new JButton("Delete Product");
		resetBtn =new JButton("Reset Product");
		bottomPanel.add(addBtn);
		bottomPanel.add(updateBtn);
		bottomPanel.add(deleteBtn);
		bottomPanel.add(resetBtn);
	
		setBounds(0,0,600,800);
		setLayout(null);
		add(topPanel);
		add(centerPanel);
		add(bottomPanel);
	}
	
	public void tableList() {
        String query = "SELECT * FROM product";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            ArrayList<Object[]> dataList = new ArrayList<>();
            while (rs.next()) {
                Object[] row = new Object[columnName.length];
                for (int i = 0; i < columnName.length; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                dataList.add(row);
            }
            // Convert ArrayList to array
            data = dataList.toArray(new Object[0][]);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
}