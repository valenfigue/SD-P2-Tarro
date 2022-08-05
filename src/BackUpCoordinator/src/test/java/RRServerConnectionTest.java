import junit.framework.TestCase;

import java.io.*;

public class RRServerConnectionTest extends TestCase {
	
	public void testReadInventoryFile() {
		try {
			FileInputStream fis = new FileInputStream("D:\\Proyectos\\(1)Universidad\\2022_Sistemas_Distribuidos\\SD-P2-Tarro\\src\\jar\\src\\main\\resources\\inventory.json");
		} catch (FileNotFoundException e) {
			fail("El archivo de inventario no ha sido encontrado");
		}
		
		assertTrue("El archivo de inventario sí fue encontrado", true);
	}
	
	/**
	 * WARNING: this method updates the inventory left it empty.
	 * <p>So A ROLLBACK MUST BE DONE.</p>
	 */
	public void testWriteInventoryFile() {
		String FILE_PATH = "D:\\Proyectos\\(1)Universidad\\2022_Sistemas_Distribuidos\\SD-P2-Tarro\\src\\jar\\src\\main\\resources\\inventory.json";
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(FILE_PATH));
		} catch (FileNotFoundException e) {
			fail("El archivo de inventario no ha sido encontrado");
		}
		
		assertTrue("El archivo de inventario sí fue encontrado", true);
	}
}