package cashierapp;

import java.awt.GridLayout;
import javax.swing.*;

public class CashierFrame extends JFrame{
	
	private ProductManagementPanel leftPanel;
	private CartManagementPanel rightPanel;
	
	public CashierFrame() {
		leftPanel = new ProductManagementPanel();
		rightPanel=  new CartManagementPanel();
		setSize(1200,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(1,2));
		setResizable(false);
		
		add(leftPanel);
		add(rightPanel);
		
		setVisible(true);
	}
	
}
