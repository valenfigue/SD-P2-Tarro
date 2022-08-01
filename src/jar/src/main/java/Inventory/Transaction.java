package Inventory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Has all information about a transaction done on the inventory.
 *
 * @author valen
 */
public final class Transaction {
	/**
	 * How many products has been taken from or added to the inventory.
	 */
	private final int quantity;
	/**
	 * The product type that has been taken from or added to the inventory.
	 */
	private final String product;
	/**
	 * Who took from or added to the inventory.
	 */
	private final String actor;
	/**
	 * When the transaction happened.
	 */
	private final String actionDateTime;
	/**
	 * How many of each product are left.
	 */
	private final ArrayList<ProductStock> currentStock;
	
	/**
	 * @param quantity How many products has been taken from or added to the inventory.
	 * @param product The product type that has been taken from or added to the inventory.
	 * @param actor Who took from or added to the inventory.
	 * @param actionDateTime When the transaction happened.
	 * @param currentStock How many of each product are left.
	 */
	public Transaction(int quantity, String product, String actor, String actionDateTime, ArrayList<ProductStock> currentStock) {
		this.quantity = quantity;
		this.product = product;
		this.actor = actor;
		this.actionDateTime = actionDateTime;
		this.currentStock = currentStock;
	}
	
	/**
	 * @param jsonTransaction The transaction taken from the inventory file.
	 */
	public Transaction(JSONObject jsonTransaction) {
		this.quantity = jsonTransaction.getInt("quantity");
		this.product = jsonTransaction.getString("product");
		this.actor = jsonTransaction.getString("actor");
		this.actionDateTime = jsonTransaction.getString("action_datetime");
		
		JSONArray stock = jsonTransaction.getJSONArray("current_stock");
		
		ArrayList<ProductStock> currentStock = new ArrayList<ProductStock>();
		for (int i = 0; i < 2; i++) {
			JSONObject stockProduct = stock.getJSONObject(i);
			currentStock.add(new ProductStock(stockProduct));
		}
		this.currentStock = currentStock;
	}
	
	/**
	 * @return A JSON with the transaction information.
	 */
	public JSONObject getJSONTransaction() {
		JSONObject transaction = new JSONObject();
		
		transaction.put("quantity", this.quantity);
		transaction.put("product", this.product);
		transaction.put("actor", this.actor);
		transaction.put("action_datetime", this.actionDateTime);
		
		ArrayList<JSONObject> currentStock = new ArrayList<JSONObject>();
		for (ProductStock productStock : this.currentStock) {
			currentStock.add(productStock.getJSONProductStock());
		}
		transaction.put("current_stock", currentStock);
		
		return transaction;
	}
}
