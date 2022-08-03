package Inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InventoryFileManagerTest {
	
	@BeforeEach
	void setUp() {
	}
	
	@Test
	void getInventory() {
		InventoryFileManager manager = new InventoryFileManager();
		ArrayList<Transaction> transactions = manager.getInventory();
		
		for (Transaction transaction : transactions) {
			for (ProductStock productStock: transaction.getCurrentStock()) {
				System.out.println("Producto: " + productStock.getProduct() + "; Cantidad: " + productStock.getQuantity());
			}
		}
		
		assertFalse(transactions.isEmpty());
	}
}