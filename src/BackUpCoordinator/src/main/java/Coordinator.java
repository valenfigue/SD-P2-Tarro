import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.util.Scanner;

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
		while (true) {
			System.out.print("Continuar: ");
			String continuar = new Scanner(System.in).next();
			System.out.println();
			if (continuar.equals("s")) {
				String webServerRequest = "RESPALDAR".toUpperCase();
				
				try {
					System.out.println("El servidor web pidió " + webServerRequest + " el archivo del inventario.\n");
					
					switch (webServerRequest) {
						case "RESTAURAR":
							Restorer restorer = new Restorer();
							restorer.restoreInventory();
							break;
						case "RESPALDAR":
							Backer backer = new Backer();
							backer.backUpInventory();
							break;
					}
				} catch (ConnectException e) {
					System.out.println("El servidor que ha elegido no se encuentra disponible en este momento.\n");
				} catch (SocketException e) {
					System.out.println("El servidor de réplicas y restauración se desconectó.");
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}
