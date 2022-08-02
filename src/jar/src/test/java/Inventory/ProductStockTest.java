package Inventory;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductStockTest {
	private ProductStock productStock;
	
	@BeforeEach
	void setUp() {
		String json = """
			{
			  "c_product": "Producto A",
			  "c_quantity": 60
			}""";
		
		JSONObject jsonProductStock = new JSONObject(json);
		productStock = new ProductStock(jsonProductStock);
	}
	
	@Test
	void createProductFromJSON() {
		assertEquals("Producto A", productStock.getProduct().getProductName());
		assertEquals(60, productStock.getQuantity());
	}
	
	@Test
	void add() {
		ProductStock newProductStock = productStock.add(40);
		
		assertTrue(newProductStock.getQuantity() > productStock.getQuantity());
		assertEquals(100, newProductStock.getQuantity());
	}
	
	@Test
	void subtractLessThanInStock() {
		ProductStock newProductStock = productStock.subtract(40);
		
		assertTrue(newProductStock.getQuantity() < productStock.getQuantity());
		assertEquals(60 - 40, newProductStock.getQuantity());
	}
	
	@Test
	void subtractMoreThanInStock() {
		ProductStock newProductStock = productStock.subtract(80);
		
		assertTrue(newProductStock.getQuantity() < productStock.getQuantity());
		assertEquals(0, newProductStock.getQuantity());
	}
	
	@Test
	void getJSONProductStock() {
		String json = """
			{
			  "c_product": "Producto A",
			  "c_quantity": 60
			}""";
		JSONObject jsonProductStock = new JSONObject(json);
		
		assertEquals(
			productStock.getJSONProductStock().getString("c_product"),
			jsonProductStock.getString("c_product"));
	}
}