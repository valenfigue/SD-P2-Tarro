package Inventory;

import Common.Client;
import Common.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JarTest {
	Jar jar;
	
	@BeforeEach
	void setUp() {
		jar = Jar.getInstance();
	}
	
	@Test
	void updateInventoryConsumer() {
		try {
			int originalNumberTransactions = jar.getInventory().length();
			
			jar.updateInventory(Client.CONSUMER, Product.A, 10);
			
			int actualNumberTransactions = jar.getInventory().length();
			
			assertTrue(originalNumberTransactions < actualNumberTransactions);
		} catch (NullPointerException e) {
			fail("No se han realizado transacciones todavía");
		}
	}
	
	@Test
	void updateInventoryProducer() {
		try {
			int originalNumberTransactions = jar.getInventory().length();
			
			jar.updateInventory(Client.PRODUCER, Product.A, 10);
			
			int actualNumberTransactions = jar.getInventory().length();
			
			assertTrue(originalNumberTransactions < actualNumberTransactions);
		} catch (NullPointerException e) {
			fail("No se han realizado transacciones todavía");
		}
	}
}