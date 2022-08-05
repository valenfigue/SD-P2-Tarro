import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

/**
 * An auxiliar class to handler many requests from the coordinator.
 *
 * @author valen
 */
public class CoordinatorManager extends Thread {
	private final Socket coordinatorSocket;
	private final int serverNumber;
	
	public CoordinatorManager(int serverNumber, Socket coordinatorSocket) {
		this.coordinatorSocket = coordinatorSocket;
		this.serverNumber = serverNumber;
	}
	
	@Override
	public void run() {
		try {
			DataInputStream inputStream = new DataInputStream(coordinatorSocket.getInputStream());
			String coordinatorRequest = inputStream.readUTF().toUpperCase();
			
			if (coordinatorRequest.equals("VOTE_REQUEST")) {
				System.out.println("El coordinador pidió respaldar el archivo de inventario.\n");
				Backer backer = new Backer(serverNumber, coordinatorSocket);
				backer.backUpInventory();
				coordinatorSocket.close();
			} else if (coordinatorRequest.equals("RESTORE")) {
				System.out.println("El coordinador pidió restaurar el archivo de inventario.\n");
				Restorer restorer = new Restorer(serverNumber, coordinatorSocket);
				restorer.restoreInventory();
				coordinatorSocket.close();
			}
		} catch (SocketException e) {
			System.out.println("El coordinador se desconectó.");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
