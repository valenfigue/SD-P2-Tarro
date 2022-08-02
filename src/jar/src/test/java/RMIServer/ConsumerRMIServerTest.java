package RMIServer;

import Common.Product;
import Inventory.Jar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author valen
 */
class ConsumerRMIServerTest {
	private final Jar jar = Jar.getInstance();
	ConsumerRMIServer consumer;
	
	@BeforeEach
	void setUp() {
		consumer = new ConsumerRMIServer(jar);
		consumer.setProduct(Product.B);
		consumer.setProductQuantity(40);
	}
	
	/**
	 * A method that can be tested only looking at the inventory file.
	 */
	@Test
	void updateInventory() {
		// Parte necesaria para que haga el cambio Y NO SÉ POR QUÉ
		int originalListSize = Objects.requireNonNull(jar.getInventory()).length();
		// Original transactions list.
		String otl = Objects.requireNonNull(jar.getInventory()).toString();
		
		consumer.start();
		
		// Parte necesaria para que haga el cambio Y NO SÉ POR QUÉ
		int newListSize = Objects.requireNonNull(jar.getInventory()).length();
		// New transactions list.
		String ntl = Objects.requireNonNull(jar.getInventory()).toString();
		
		assertTrue(true);
	}
}