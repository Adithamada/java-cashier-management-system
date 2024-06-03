package cashierapp;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import cashierapp.data.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.*;


public class ProductManagementPanel extends JPanel implements ActionListener{
	
	private JPanel topPanel;
	private JPanel centerPanel;
	private JPanel bottomPanel;
	
	private JTable productTable;
	private DefaultTableModel tableModel;
	
	private JTextField fieldId;
	private JTextField fieldName;
	private JTextField fieldPrice;
	private JTextField fieldStock;
	
	private JLabel labelId;
	private JLabel labelName;
	private JLabel labelPrice;
	private JLabel labelStock;
	
	private JButton addBtn;
	private JButton updateBtn;
	private JButton deleteBtn;
	private JButton resetBtn;
	
	private String[] columnName = {"Id","Name","Price","Stock"};
	private Object[][] data = {};
	
	private Cashier cashier;
	
	public ProductManagementPanel() {
		cashier = new Cashier();
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
		JScrollPane scrollPane = new JScrollPane(productTable);
		productTable.getTableHeader().setReorderingAllowed(false);
		topPanel.add(scrollPane);
		
		//CENTER PANEL
		centerPanel = new JPanel();
		centerPanel.setBorder(new EmptyBorder(15,10,15,15));
		centerPanel.setBounds(0,350,600,200);
		centerPanel.setLayout(new GridLayout(4,2,5,5));
		fieldId = new JTextField();
		labelId = new JLabel("Product Id : ");
		fieldName = new JTextField();
		labelName = new JLabel("Product Name : ");
		fieldPrice = new JTextField();
		labelPrice = new JLabel("Product Price : ");
		fieldStock = new JTextField();
		labelStock = new JLabel("Product Stock : ");
		centerPanel.add(labelId);
		centerPanel.add(fieldId);
		centerPanel.add(labelName);
		centerPanel.add(fieldName);
		centerPanel.add(labelPrice);
		centerPanel.add(fieldPrice);
		centerPanel.add(labelStock);
		centerPanel.add(fieldStock);
		
		((PlainDocument) fieldId.getDocument()).setDocumentFilter(new NumericDocumentFilter());
        ((PlainDocument) fieldPrice.getDocument()).setDocumentFilter(new NumericDocumentFilter());
        ((PlainDocument) fieldStock.getDocument()).setDocumentFilter(new NumericDocumentFilter());
		
		//BOTTOM PANEL
		bottomPanel = new JPanel();
		bottomPanel.setBounds(0,550,600,250);
		bottomPanel.setBorder(new EmptyBorder(15,10,15,15));
		bottomPanel.setLayout(new GridLayout(5,1,10,10));
		addBtn =new JButton("Add Product");
		updateBtn =new JButton("Update Product");
		deleteBtn =new JButton("Delete Product");
		resetBtn =new JButton("Reset Field");
		bottomPanel.add(addBtn);
		bottomPanel.add(updateBtn);
		bottomPanel.add(deleteBtn);
		bottomPanel.add(resetBtn);
	
		productTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && productTable.getSelectedRow() != -1) {
                int selectedRow = productTable.getSelectedRow();
                fieldId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                fieldName.setText(tableModel.getValueAt(selectedRow, 1).toString());
                fieldPrice.setText(tableModel.getValueAt(selectedRow, 2).toString());
                fieldStock.setText(tableModel.getValueAt(selectedRow, 3).toString());
            }
        });
		
		resetBtn.addActionListener(this);
		addBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		
		setBounds(0,0,600,800);
		setLayout(null);
		add(topPanel);
		add(centerPanel);
		add(bottomPanel);
	}
	//SELECT ALL DATA IN TABLE
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== resetBtn) {
			fieldId.setText("");
			fieldName.setText("");
			fieldPrice.setText("");
			fieldStock.setText("");
		}else if(e.getSource()==addBtn) {
			String name = fieldName.getText();
			double price = Double.parseDouble(fieldPrice.getText());
			int stock = Integer.parseInt(fieldStock.getText());
			if(name != null || price != 0||stock != 0) {				
				cashier.addProduct(name, price, stock);
				JOptionPane.showMessageDialog(centerPanel, "Product Has Been Added to Database!");
				tableList();
				updateTableModel();
			}else {
				JOptionPane.showMessageDialog(null, "Product Fail to Added!");
			}
		}else if(e.getSource() == updateBtn) {
			int id = Integer.parseInt(fieldId.getText());
			String name = fieldName.getText();
			double price = Double.parseDouble(fieldPrice.getText());
			int stock = Integer.parseInt(fieldStock.getText());
			if(name != null || price == 0||stock == 0) {				
				cashier.updateProduct(id,name, price, stock);
				JOptionPane.showMessageDialog(centerPanel, "Product Has Been Updated!");
				tableList();
				updateTableModel();
			}else {
				JOptionPane.showMessageDialog(null, "Product Fail to Updated!");
			}
		}else if(e.getSource()==deleteBtn) {
			int id = Integer.parseInt(fieldId.getText());
			if(id != 0) {				
				cashier.deleteProduct(id);
				JOptionPane.showMessageDialog(centerPanel, "Product Has Been Deleted!");
				tableList();
				updateTableModel();
			}else {
				JOptionPane.showMessageDialog(null, "Product Fail to Deleted!");
			}
		}
	}
	
	private void updateTableModel() {
        tableModel.setRowCount(0); // Clear existing data
        for (Object[] rowData : data) {
            tableModel.addRow(rowData);
        }
    }
	
}