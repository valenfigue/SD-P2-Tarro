package Common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author valen
 */
class ProductTest {
	
	@Test
	void getProductAName() {
		Product product = Product.A;
		String productName = "Producto A";
		
		assertEquals(productName, product.getProductName());
	}
	
	@Test
	void getProductBName() {
		Product product = Product.B;
		String productName = "Producto B";
		
		assertEquals(productName, product.getProductName());
	}
	
	@Test
	void getProductA() {
		String productName = "Producto A";
		Product product = Product.getProduct(productName);
		
		assert product != null;
		assertEquals(productName, product.getProductName());
	}
	
	@Test
	void getProductB() {
		String productName = "Producto B";
		Product product = Product.getProduct(productName);
		
		assert product != null;
		assertEquals(productName, product.getProductName());
	}
	
	@Test
	void getProductNull() {
		String productName = "Producto";
		Product product = Product.getProduct(productName);
		
		try {
			assertEquals(productName, product.getProductName());
		} catch (NullPointerException e) {
			if (product == null) {
				assertTrue(true);
			}
		}
	}
	
	@Test
	void isProductA() {
		Product product = Product.A;
		
		assertTrue(product.isProductA());
		assertFalse(product.isProductB());
	}
	
	@Test
	void isProductB() {
		Product product = Product.B;
		
		assertTrue(product.isProductB());
		assertFalse(product.isProductA());
	}
}