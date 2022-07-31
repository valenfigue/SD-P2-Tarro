package Inventory;

import Common.Product;
import org.json.JSONObject;

/**
 * The actual stock for a specific product.
 * <p></p>
 * It can add to or subtract from the product's stock the indicated quantity.
 *
 * @author valen
 */
public final class ProductStock {
	private final Product product;
	private final int quantity;
	
	/**
	 * @param product A product.
	 * @param quantity How many of that product are left in the inventory.
	 */
	public ProductStock(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	
	/**
	 * @param jsonObject Product's stock information taken from the inventory file.
	 */
	public ProductStock(JSONObject jsonObject) {
		this.product = Product.getProduct(jsonObject.getString("c_product"));
		this.quantity = jsonObject.getInt("c_quantity");
	}
	
	public Product getProduct() {
		return product;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * Adds products to the product's stock.
	 *
	 * @param quantity How many products will be added to the product's stock.
	 * @return A new stock with the product's quantity updated.
	 */
	public ProductStock add(int quantity) {
		return new ProductStock(this.product, this.quantity + quantity);
	}
	
	/**
	 * Subtract products to the product's stock.
	 *
	 * @param quantity How many products will be subtracted from the product's stock.
	 * @return A new stock with the product's quantity updated.
	 */
	public ProductStock subtract(int quantity) {
		int quantityUpdate; // How many will be left after consuming.
		
		if (quantity > this.quantity) {
			quantityUpdate = 0;
		} else {
			quantityUpdate = this.quantity - quantity;
		}
		
		return new ProductStock(this.product, quantityUpdate);
	}
	
	/**
	 * @return A JSON object with product's stock information.
	 */
	public JSONObject getJSONProductStock() {
		JSONObject stock = new JSONObject();
		stock.put("c_product", this.getProduct().getProductName());
		stock.put("c_quantity", this.getQuantity());
		
		return stock;
	}
}
