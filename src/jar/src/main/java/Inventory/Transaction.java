package Inventory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public final class Transaction {
	private final int quantity;
	private final String product;
	private final String actor;
	private final String actionDateTime;
	private final ArrayList<ProductStock> currentStock;
	
	public Transaction(int quantity, String product, String actor, String actionDateTime, ArrayList<ProductStock> currentStock) {
		this.quantity = quantity;
		this.product = product;
		this.actor = actor;
		this.actionDateTime = actionDateTime;
		this.currentStock = currentStock;
	}
	
	public Transaction(JSONObject jsonObject) {
		this.quantity = jsonObject.getInt("quantity");
		this.product = jsonObject.getString("product");
		this.actor = jsonObject.getString("actor");
		this.actionDateTime = jsonObject.getString("action_datetime");
		
		JSONArray stock = jsonObject.getJSONArray("current_stock");
		
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
