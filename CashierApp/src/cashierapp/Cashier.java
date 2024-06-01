package cashierapp;

import cashierapp.data.*;
import java.sql.*;

public class Cashier {
	
	public void addProduct(String name,double price,int stock) {
		String insertQuery = "INSERT INTO product (name,price,stock) VALUES (?,?,?)";
		try(Connection conn= (Connection) DatabaseConnection.connect();
			PreparedStatement stmt = conn.prepareStatement(insertQuery)){
			stmt.setString(1, name);
			stmt.setDouble(2,price);
			stmt.setInt(3, stock);
			int rowAffected = stmt.executeUpdate();
		}catch(SQLException e) {
			System.out.println("ERROR QUERY!!!");
			e.printStackTrace();
		}
	}
	
	public void updateProduct(int id,String name, double price,int stock) {
		String updateQuery = "UPDATE product SET name=?,price=?,stock=? WHERE id=?";
		try(Connection conn= (Connection) DatabaseConnection.connect();
			PreparedStatement stmt = conn.prepareStatement(updateQuery)){
			stmt.setString(1, name);
			stmt.setDouble(2,price);
			stmt.setInt(3, stock);
			stmt.setInt(4,id);
			int rowAffected = stmt.executeUpdate();
		}catch(SQLException e) {
			System.out.println("ERROR QUERY!!!");
			e.printStackTrace();
		}
	}
	
	public void deleteProduct(int id) {
		String deleteQuery="DELETE FROM product WHERE id=?";
		try(Connection conn= (Connection) DatabaseConnection.connect();
				PreparedStatement stmt = conn.prepareStatement(deleteQuery)){
				stmt.setInt(1,id);
				int rowAffected = stmt.executeUpdate();
			}catch(SQLException e) {
				System.out.println("ERROR QUERY!!!");
				e.printStackTrace();
			}
	}
}
