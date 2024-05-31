package cashierapp.data;

import java.util.*;

public class Cart {
	public List<Product> listProducts;
	
	public Cart() {
		listProducts = new ArrayList<>();
	}
	public void addToCart(Product product,int purchaseAmount) {
		product.setPurchaseAmount(purchaseAmount);
		listProducts.add(product);
	}
	
	public void removeFromCart(Product product) {
		product.setPurchaseAmount(0);
		listProducts.removeIf(p -> p.getId() == product.getId());
	}
	
	public void clearCart() {
		listProducts.clear();
	}
	public double calculateTotal() {
		double total = 0;
		for (Product product : listProducts) {
            total += product.getPrice() * product.getPurchaseAmount();
        }
		return total;
	}
	
	
	public List<Product> productList() {
		return listProducts;
	}
}
