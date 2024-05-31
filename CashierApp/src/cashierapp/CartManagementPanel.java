package cashierapp;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class CartManagementPanel extends JPanel{
	
	private JPanel panelTop;
	private JPanel panelCenter;
	private JPanel panelBottom;
	
	private JTable cartTable;
	private DefaultTableModel tableModel;
	
	private JLabel labelId;
	private JLabel labelAmount;
	
	private JTextField fieldId;
	private JTextField fieldAmount;
	
	private JButton addCartBtn;
	private JButton removeCartBtn;
	private JButton resetBtn;
	private JButton checkoutBtn;
	
	public CartManagementPanel() {
		
		//PANEL TOP AkA CART LIST
		panelTop = new JPanel();
		panelTop.setBorder(new EmptyBorder(15,10,15,15));
		panelTop.setBounds(0,0,600, 350);
		panelTop.setLayout(new GridLayout(1,1));
		String[] columnName = {"No", "Id", "Name", "Price", "Amount", "Total Price"};
		tableModel = new DefaultTableModel(columnName,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addRow(columnName);
		cartTable = new JTable(tableModel);
		panelTop.add(cartTable);
		
		//PANEL CENTER AkA FIELD
		panelCenter = new JPanel();
		panelCenter.setBorder(new EmptyBorder(15,10,15,15));
		panelCenter.setBounds(0,350,600,150);
		panelCenter.setLayout(new GridLayout(2,2,5,5));
		labelId = new JLabel("Product Id : ");
		labelAmount = new JLabel("Purchase Amount : ");
		fieldId = new JTextField();
		fieldAmount = new JTextField();
		panelCenter.add(labelId);
		panelCenter.add(fieldId);
		panelCenter.add(labelAmount);
		panelCenter.add(fieldAmount);
		
		//PANEL BOTTOM AkA BUTTON
		panelBottom = new JPanel();
		panelBottom.setBorder(new EmptyBorder(15,10,15,15));
		panelBottom.setBounds(0,500,600,250);
		panelBottom.setLayout(new GridLayout(4,1,5,5));
		addCartBtn = new JButton("Add To Cart");
		removeCartBtn = new JButton("Remove From Cart");
		resetBtn = new JButton("Reset Field");
		checkoutBtn = new JButton("CheckOut");
		panelBottom.add(addCartBtn);
		panelBottom.add(removeCartBtn);
		panelBottom.add(resetBtn);
		panelBottom.add(checkoutBtn);
		
		setBounds(600,0,600,800);
		setLayout(null);
		add(panelTop);
		add(panelCenter);
		add(panelBottom);
	}
	
}
