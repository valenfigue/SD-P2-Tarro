import java.io.IOException;

/**
 * Main program for the replicas-and-restoring coordinator.
 * <p></p>
 * Depending on Web Server's request, the coordinator will back up or restore
 * the inventory file.
 *
 * @author valen
 */
public class Coordinator {
	public static void main(String[] args) {
		System.out.println("Coordinador de réplicas y restauración, iniciado.");
		
		// TODO: to implement socket communication with the Web Server.
		String webServerRequest = "RESPALDAR".toUpperCase();
		
		try {
			System.out.println("El servidor web pidió " + webServerRequest + "el archivo del inventario.");
			
			switch (webServerRequest) {
				case "RESTAURAR":
					Restorer restorer = new Restorer();
					restorer.restoreInventory();
				case "RESPALDAR":
					Backer backer = new Backer();
					backer.backUpInventory();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
