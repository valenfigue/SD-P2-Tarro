package Common;

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
		switch (this) {
			case A:
				return "Producto A";
			case B:
				return "Producto B";
			default:
				return null;
		}
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
}
