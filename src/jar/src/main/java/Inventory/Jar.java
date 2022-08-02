package Inventory;

import Common.Product;
import org.json.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Gets and updates the inventory.
 *
 * @author valen
 */
public final class Jar {
	
	/**
	 * An attribute to use Singleton pattern with threads.
	 */
	private static volatile Jar instance;
	
	/**
	 * Manage reading and writing the inventory file.
	 */
	private final InventoryFileManager manager = new InventoryFileManager();
	
	private Jar() {}
	
	/**
	 * @return Single jar instance.
	 */
	public static Jar getInstance() {
		/*
		* Returns the jar instance using Double-Checked Locking to prevent race condition between
		* multiple threads that may attempt to get jar instance at the same time, creating separate
		* instances as a result.
		*
		* The Singleton pattern with this approach is used because Consumer and Producer (in the RMI server)
		* need to use a Jar instance in their respective run method.
		 * */
		
		Jar result = instance;
		if (result != null) {
			return result;
		}
		
		synchronized (Jar.class) {
			if (instance == null) {
				instance = new Jar();
			}
			return instance;
		}
	}
	
	/**
	 * Returns all the transactions registered in the inventory file, with a JSON format.
	 *
	 * @return A JSON with all the transactions.
	 */
	public JSONArray getInventory() {
		try {
			return manager.getJSONInventory();
		} catch (IOException | ParseException e) {
			return null;
		}
	}
	
	/**
	 * @param inventory List of all transaction registered in the inventory file.
	 * @return The last transaction registered in the inventory.
	 */
	private Transaction getLastTransaction(ArrayList<Transaction> inventory) {
		return inventory.get(inventory.size() - 1);
	}
	
	/**
	 * Updates the jar's inventory.
	 *
	 * <p></p>
	 * If the transaction was requested by the consumer, the quantity will be subtracted from the inventory.
	 * If the transaction was requested by the producer, the quantity will be added to the inventory.
	 *
	 * @param client The actor who wants to do the transaction.
	 * @param product The desire product to be updated.
	 * @param quantity How many of said product will be added or subtracted.
	 */
	public synchronized void updateInventory(Client client, Product product, int quantity) {
		ArrayList<Transaction> inventory = manager.getInventory();
		Transaction lastTransaction = this.getLastTransaction(inventory);
		ArrayList<ProductStock> productStocks = lastTransaction.getCurrentStock();
		
		// updating stocks
		ArrayList<ProductStock> newProductStocks = new ArrayList<>();
		if (client.isConsumer()) {
			newProductStocks = this.updateInventoryIfConsumer(productStocks, product, quantity);
		}
		if (client.isProducer()) {
			newProductStocks = this.updateInventoryIfProducer(productStocks, product, quantity);
		}
		
		// creating new transaction.
		String dateTimeFormat = "dd/MM/yyyy hh:mm:ss:S a";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateTimeFormat);
		String actionDateTime = dtf.format(LocalDateTime.now());
		
		Transaction newTransaction = new Transaction(
			quantity, product.getProductName(), client.getClientName(),
			actionDateTime, newProductStocks
		);
		
		// Saving new transaction
		inventory.add(newTransaction);
		manager.setInventory(inventory);
	}
	
	/**
	 * Updates inventory if the client is a consumer.
	 *
	 * @param productStocks All product's stock from last transaction.
	 * @param product       Product which stock will be subtracted.
	 * @param quantity      How many from that product's stock will be subtracted.
	 * @return Updated product's stocks.
	 */
	private ArrayList<ProductStock> updateInventoryIfConsumer(ArrayList<ProductStock> productStocks, Product product, int quantity) {
		ArrayList<ProductStock> newProductStock = new ArrayList<>();
		for (ProductStock productStock : productStocks) {
			if (product.isEqualTo(productStock)) {
				newProductStock.add(productStock.subtract(quantity));
			} else {
				newProductStock.add(productStock);
			}
		}
		
		return newProductStock;
	}
	
	/**
	 * Updates inventory if the client is a producer.
	 *
	 * @param productStocks All product's stock from last transaction.
	 * @param product       Product which stock will be added.
	 * @param quantity      How many from that product's stock will be added.
	 * @return Updated product's stocks.
	 */
	private ArrayList<ProductStock> updateInventoryIfProducer(ArrayList<ProductStock> productStocks, Product product, int quantity) {
		ArrayList<ProductStock> newProductStock = new ArrayList<>();
		for (ProductStock productStock : productStocks) {
			if (product.isEqualTo(productStock)) {
				newProductStock.add(productStock.add(quantity));
			} else {
				newProductStock.add(productStock);
			}
		}
		
		return newProductStock;
	}
}
