package RMIServer;

import Common.Client;
import Common.Product;
import Inventory.Jar;
import RMICommon.IClient;
import org.json.JSONArray;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * The server that will be communicating with the Web Service through RMI protocol.
 * <p></p>
 * This server will manage the jar and the inventory.
 *
 * @author valen
 */
public class RMIServer {
	/**
	 * The port's number where the server and the client will be communicating.
	 */
	private static final int PORT = 1100;
	
	public static void main(String[] args) {
		try {
			Remote consumer = getRemoteConsumer();
			Remote producer = getRemoteProducer();
			
			Registry registry = LocateRegistry.createRegistry(PORT);
			System.out.println("Servidor RMI escuchando en el puerto " + PORT);
			
			// Binding
			registry.bind("ConsumerService", consumer);
			registry.bind("ProducerService", producer);
		} catch (RemoteException e) {
			System.out.println("Hubo un problema al intentar exportar uno de los clientes.");
		} catch (AlreadyBoundException e) {
			System.out.println("Uno de los servicios ya estaba conectado con el cliente RMI.");
		}
	}
	
	/**
	 * Exports a consumer object to make it available to receive incoming calls.
	 *
	 * @return Remote instance of consumer to receive incoming calls.
	 * @throws RemoteException If the export fails.
	 */
	private static Remote getRemoteConsumer() throws RemoteException {
		Jar jar = Jar.getInstance();
		IClient consumer = new IClient() {
			private Product product;
			private int quantity;
			private final Client client = Client.CONSUMER;
			
			@Override
			public void setProduct(Product product) throws RemoteException {
				this.product = product;
			}
			
			@Override
			public void setProductQuantity(int quantity) throws RemoteException {
				this.quantity = quantity;
			}
			
			@Override
			public JSONArray checkInventory() throws RemoteException {
				return jar.getInventory();
			}
			
			/**
			 * Updates the inventory according to the client (consumer or producer) that calls the method.
			 */
			@Override
			public void updateInventory() throws RemoteException {
				jar.updateInventory(this.client, this.product, this.quantity);
			}
		};
		
		return UnicastRemoteObject.exportObject(consumer, 0);
	}
	
	/**
	 * Exports a producer object to make it available to receive incoming calls.
	 *
	 * @return Remote instance of producer to receive incoming calls.
	 * @throws RemoteException If the export fails.
	 */
	private static Remote getRemoteProducer() throws RemoteException {
		Jar jar = Jar.getInstance();
		IClient producer = new IClient() {
			private Product product;
			private int quantity;
			private final Client client = Client.PRODUCER;
			
			@Override
			public void setProduct(Product product) throws RemoteException {
				this.product = product;
			}
			
			@Override
			public void setProductQuantity(int quantity) throws RemoteException {
				this.quantity = quantity;
			}
			
			@Override
			public JSONArray checkInventory() throws RemoteException {
				return jar.getInventory();
			}
			
			/**
			 * Updates the inventory according to the client (consumer or producer) that calls the method.
			 */
			@Override
			public void updateInventory() throws RemoteException {
				jar.updateInventory(this.client, this.product, this.quantity);
			}
		};
		
		return UnicastRemoteObject.exportObject(producer, 0);
	}
}
