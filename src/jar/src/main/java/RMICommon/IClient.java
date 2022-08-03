package RMICommon;

import Common.Product;
import org.json.JSONArray;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
	
	/**
	 * Sets the product the consumer will consume or the producer will producer.
	 *
	 * @param product A product to consume or produce.
	 */
	void setProduct(Product product) throws RemoteException;
	
	/**
	 * Sets how many of the desire product the consumer will take from or the producer will add to the jar.
	 *
	 * @param quantity Product's quantity.
	 */
	void setProductQuantity(int quantity) throws RemoteException;
	
	/**
	 * Returns all the inventory registered.
	 *
	 * @return The inventory.
	 */
	JSONArray checkInventory() throws RemoteException;
	
	/**
	 * Updates the inventory according to the client (consumer or producer) that calls the method.
	 */
	void updateInventory() throws RemoteException;
}
