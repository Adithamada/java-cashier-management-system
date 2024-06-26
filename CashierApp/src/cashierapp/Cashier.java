package cashierapp;

import cashierapp.data.*;
import java.sql.*;
import java.time.LocalDateTime;
import org.mindrot.jbcrypt.*;

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
	
	public int newTransaction(Connection conn,double totalPrice)throws SQLException {
		 String insertTransaction = "INSERT INTO `transaction` (date,totalPrice) VALUES (?, ?)";
	        try (PreparedStatement stmt = conn.prepareStatement(insertTransaction, PreparedStatement.RETURN_GENERATED_KEYS)) {
	            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
	            stmt.setDouble(2, totalPrice);
	            stmt.executeUpdate();

	            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    return generatedKeys.getInt(1); // Return the generated transaction ID
	                } else {
	                    throw new SQLException("Creating transaction failed, no ID obtained.");
	                }
	            }
           }
	}
	private void newTransactionDetail(Connection conn, int transactionId, int productId, int quantity,double price,double payment) throws SQLException {
       String insertDetail = "INSERT INTO transactiondetail (transaction_id, product_id, quantity,price,payment) VALUES (?,?,?,?,?)";
       try (PreparedStatement stmt = conn.prepareStatement(insertDetail)) {
           stmt.setInt(1, transactionId);
           stmt.setInt(2, productId);
           stmt.setInt(3, quantity);
           stmt.setDouble(4, price);
           stmt.setDouble(5, payment);
           stmt.executeUpdate();
       }
   }
	
	public void checkOutTransaction(Cart cart,double payment) {
       try (Connection conn = DatabaseConnection.connect()) {
           if (conn != null) {
               conn.setAutoCommit(false); // Start transaction

               int transactionId = newTransaction(conn, cart.calculateTotal());
               
               for (Product product : cart.productList()) {
                   newTransactionDetail(conn, transactionId, product.getId(), product.getPurchaseAmount(),
                   		product.getPurchaseAmount() * product.getPrice(),payment);
               }

               conn.commit(); // Commit transaction
               System.out.println("Transaction completed.");
               cart.clearCart();
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
	}
	
	public boolean validateLogin(String userName, String userPass) {
		String checkUser = "SELECT password FROM user WHERE username = ?";
		try{
			Connection conn = (Connection) DatabaseConnection.connect();
			PreparedStatement stmt = conn.prepareStatement(checkUser);
			
			stmt.setString(1, userName);
			
			try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("password");
                    // Check if the hashed password matches
                    if (BCrypt.checkpw(userPass, hashedPassword)) {
                        return true;
                    }
                }
            }
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void registration(String userName, String password) {
		String registerQuery = "INSERT INTO user (username,password) VALUES (?,?)";
		try(Connection conn = (Connection) DatabaseConnection.connect();
			PreparedStatement stmt = conn.prepareStatement(registerQuery)){
			
			stmt.setString(1,userName);
			stmt.setString(2, password);
			
			int rowsAffect = stmt.executeUpdate();
			
			if(rowsAffect > 0) {
				System.out.println("Registration success!");
			}else {
				System.out.println("Registration failed!");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}
