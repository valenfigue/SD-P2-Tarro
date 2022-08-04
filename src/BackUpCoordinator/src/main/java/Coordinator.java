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
		// TODO: to implement socket communication with the Web Server.
		String webServerRequest = "".toUpperCase();
		
		try {
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
