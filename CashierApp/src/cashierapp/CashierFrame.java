package cashierapp;

import java.awt.GridLayout;
import javax.swing.*;

public class CashierFrame extends JFrame{
	
	public CashierFrame() {
		setSize(1200,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(2,2));
		setResizable(false);
		setVisible(true);
	}
	
}
