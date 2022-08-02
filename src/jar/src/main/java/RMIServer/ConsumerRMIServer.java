package RMIServer;

import Common.Client;
import Inventory.Jar;

public class ConsumerRMIServer extends RMIServerClient {
	
	/**
	 * Creates a RMIServer Client with the system-single jar for the producer or a consumer.
	 *
	 * @param jar Jar that contains products and a consumer will take those products from
	 *            or a producer will add more products to.
	 */
	public ConsumerRMIServer(Jar jar) {
		super(jar, Client.CONSUMER);
	}
	
	/**
	 * Updates the inventory according to the client (consumer or producer) that calls the method.
	 */
	@Override
	public void updateInventory() {
		this.jar.updateInventory(this.client, this.product, this.quantity);
	}
}
