package cashierapp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginFrame extends JFrame implements ActionListener{

	private JLabel labelTitle;
	private JLabel labelUsername;
	private JLabel labelPassword;
	
	private JTextField fieldUsername;
	private JTextField fieldPassword;
	
	private JButton buttonLogin;
	private JButton buttonReset;
	
	private Font titleFont;
	
	public LoginFrame() {
		titleFont = new Font("Mono",Font.BOLD,25);
		labelTitle = new JLabel("LOGIN");
		labelUsername = new JLabel("Username : ");
		labelPassword = new JLabel("Password : ");
		
		fieldUsername = new JTextField();
		fieldPassword = new JTextField();		
		
		buttonLogin = new JButton("LOGIN");
		buttonReset = new JButton("RESET");
		
		setSize(500,550);
		setLayout(null);
		setResizable(false);
		setTitle("Login");
		
		labelTitle.setFont(titleFont);
		labelTitle.setBounds(200,100,150,50);
		
		labelUsername.setBounds(70,160,100,50);
		labelPassword.setBounds(70,230,100,50);
		
		fieldUsername.setBounds(200,175,200,30);
		fieldPassword.setBounds(200,245,200,30);
		
		buttonLogin.setBounds(180,320,150,50);
		buttonReset.setBounds(180,390,150,50);
		
		add(labelTitle);
		add(labelUsername);
		add(labelPassword);
		add(fieldUsername);
		add(fieldPassword);
		add(buttonLogin);
		add(buttonReset);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new LoginFrame();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
}
