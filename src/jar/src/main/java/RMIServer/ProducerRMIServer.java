package RMIServer;

import Common.Client;
import Inventory.Jar;

/**
 * Remote producer to be used in an RMI system.
 * <p></p>
 * ATTENTION: just for tests purposes.
 *
 * @author valen
 */
public class ProducerRMIServer extends ClientRMIServer {
	/**
	 * Creates a RMIServer Client with the system-single jar for the producer or a consumer.
	 *
	 * @param jar Jar that contains products and a consumer will take those products from
	 *            or a producer will add more products to.
	 */
	public ProducerRMIServer(Jar jar) {
		super(jar, Client.PRODUCER);
	}
}
