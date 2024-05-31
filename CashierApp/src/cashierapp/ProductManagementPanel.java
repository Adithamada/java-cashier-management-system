package cashierapp;

import java.awt.Color;
import java.awt.GridLayout;

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
	private JTextField fieldstock;
	
	private JLabel labelName;
	private JLabel labelPrice;
	private JLabel labelStock;
	
	private JButton addbtn;
	private JButton updatebtn;
	private JButton deletebtn;
	private JButton resetbtn;
	
	public ProductManagementPanel() {
		setBounds(0,0,600,800);
		setLayout(new GridLayout(2,1,5,5));
		setBackground(Color.BLUE);
	}
	
}
