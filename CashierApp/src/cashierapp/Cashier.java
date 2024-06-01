package cashierapp;

import cashierapp.data.*;
import java.sql.*;

public class Cashier {
	
	public static Product getProductById(int productId) {
        String query = "SELECT * FROM product WHERE id = ?";
        try (Connection conn = (Connection) DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
	
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
	
	public void addProductToCart(Cart cart,int id,int amount) {
		Product product = getProductById(id);
		if(product != null && product.getStock() >= amount) {
			cart.addToCart(product, amount);
			System.out.println("Product added to cart");
		}else {
			System.out.println("Product not added to cart");
		}
	}
	
	public void removeProductFromCart(Cart cart,int id) {
		Product product = getProductById(id);
		if(product != null ) {
			cart.removeFromCart(product);
			System.out.println("Product removed from cart");
		}else {
			System.out.println("Product not removed from cart");
		}
	}
	
	
}
