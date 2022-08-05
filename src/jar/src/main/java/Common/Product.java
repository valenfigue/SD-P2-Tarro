package Common;

import Inventory.ProductStock;

/**
 * Has all the products to use in the system.
 *
 * @author valen
 */
public enum Product {
	A, B;
	
	/**
	 * Returns the product's long name (for storage purposes).
	 *
	 * @return Product name
	 */
	public String getProductName() {
		return switch (this) {
			case A -> "Producto A";
			case B -> "Producto B";
			default -> null;
		};
	}
	
	/**
	 * Takes a product's long name (from the inventory file) and returns a product that match with the given name.
	 *
	 * @param name Product's long name.
	 * @return A product.
	 */
	public static Product getProduct(String name) {
		if (name.equals("Producto A")) {
			return Product.A;
		}
		if (name.equals("Producto B")) {
			return Product.B;
		}
		
		return null;
	}
	
	/**
	 * Takes a product's long name (from the inventory file) and returns a product that match with the given name.
	 *
	 * @param choice User selection of a product.
	 * @return A product.
	 */
	public static Product getProduct(int choice) {
		if (choice == 1) {
			return Product.A;
		}
		if (choice == 2) {
			return Product.B;
		}
		
		return null;
	}
	
	/**
	 * Takes a product's long name (from the inventory file) and returns a product that match with the given name.
	 *
	 * @param name Product's name.
	 * @return A product.
	 */
	public static Product getProduct(char name) {
		if (name == 'A') {
			return Product.A;
		}
		if (name == 'B') {
			return Product.B;
		}
		
		return null;
	}
	
	/**
	 * @return True if the product is A, false if not.
	 */
	public boolean isProductA() {
		if (this.getProductName() != null) {
			return this.getProductName().equals(Product.A.getProductName());
		}
		
		return false;
	}
	
	/**
	 * @return True if the product is B, false if not.
	 */
	public boolean isProductB() {
		if (this.getProductName() != null) {
			return this.getProductName().equals(Product.B.getProductName());
		}
		
		return false;
	}
	
	/**
	 * Compares two products.
	 *
	 * @param productStock Product to compare.
	 * @return True if they have the same name, false if they not.
	 */
	public boolean isEqualTo(ProductStock productStock) {
		Product product = productStock.getProduct();
		
		return this.getProductName().equals(product.getProductName());
	}
}
