package cashierapp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import org.mindrot.jbcrypt.BCrypt;


public class LoginFrame extends JFrame implements ActionListener{

	private JLabel labelTitle;
	private JLabel labelUsername;
	private JLabel labelPassword;
	
	private JTextField fieldUsername;
	private JPasswordField fieldPassword;
	
	private JButton buttonLogin;
	private JButton buttonReset;
	
	private Font titleFont;
	static Cashier cashier= new Cashier();
	
	static Scanner input= new Scanner(System.in);
	
	private boolean isLogin;
	
	public LoginFrame() {
		isLogin = false;
		titleFont = new Font("Mono",Font.BOLD,25);
		labelTitle = new JLabel("LOGIN");
		labelUsername = new JLabel("Username : ");
		labelPassword = new JLabel("Password : ");
		
		fieldUsername = new JTextField();
		fieldPassword = new JPasswordField();		
		
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
		
		buttonLogin.addActionListener(this);
		buttonReset.addActionListener(this);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		//registration();
		new LoginFrame();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==buttonLogin) {
			String userName = fieldUsername.getText();
			String userPass = String.valueOf(fieldPassword.getPassword());
			
			isLogin =cashier.validateLogin(userName, userPass);
			
			if(!isLogin) {
				JOptionPane.showMessageDialog(null,"Login Failed, Please check username and password");
			}else {
				JOptionPane.showMessageDialog(null,"Login Success, Welcome " + userName);
				new CashierFrame();
				dispose();
			}
		}else if(e.getSource()==buttonReset) {
			fieldUsername.setText("");
			fieldPassword.setText("");
		}
	}
	
	public static void registration() {
		String userName;
		String password;
		System.out.println("Input UserName : ");
		userName = input.nextLine();
		System.out.println("Input Password : ");
		password = input.nextLine();
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		cashier.registration(userName, hashedPassword);
		
		System.out.println("Welcome " + userName);
	}
	
}
