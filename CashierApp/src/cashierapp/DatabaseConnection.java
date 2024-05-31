package cashierapp;

import java.sql.*;

public class DatabaseConnection {
	
	private static final String URL = "jdbc:mysql://localhost:3306/db_kasir?serverTimezone=Asia/Jakarta";
	private static final String USER = "root";
	private static final String PASS = "";
	
	public static Connection connect() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(URL,USER,PASS);
		}catch(SQLException e) {
			System.out.println("Fail Connect to Database");
			e.printStackTrace();
		}
		return connection;
	}
	
}
