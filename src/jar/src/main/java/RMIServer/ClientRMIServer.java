package RMIServer;

import Common.Client;
import Common.Product;
import Inventory.Jar;
import RMICommon.IClient;
import org.json.JSONArray;

/**
 * Encapsulates most of the behaviors that a consumer and a producer do.
 *
 * @author valen
 */
abstract class ClientRMIServer extends Thread implements IClient {
	
	protected Product product;
	protected int quantity;
	protected Jar jar;
	
	protected Client client;
	
	/**
	 * Creates a RMIServer Client with the system-single jar for the producer or a consumer.
	 *
	 * @param jar Jar that contains products and a consumer will take those products from
	 *            or a producer will add more products to.
	 * @param client The type of client.
	 */
	public ClientRMIServer(Jar jar, Client client) {
		this.jar = jar;
		this.client = client;
	}
	
	@Override
	public void run() {
		this.updateInventory();
	}
	
	/**
	 * Sets the product the consumer will consume or the producer will producer.
	 *
	 * @param product A product to consume or produce.
	 */
	@Override
	public void setProduct(Product product) {
		this.product = product;
	}
	
	/**
	 * Sets how many of the desire product the consumer will take from or the producer will add to the jar.
	 *
	 * @param quantity Product's quantity.
	 */
	@Override
	public void setProductQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * Returns all the inventory registered.
	 *
	 * @return The inventory.
	 */
	@Override
	public JSONArray checkInventory() {
		return jar.getInventory();
	}
}
