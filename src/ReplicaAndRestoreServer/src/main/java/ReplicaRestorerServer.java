import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ReplicaRestorerServer {
	
	@SuppressWarnings("InfiniteLoopStatement")
	public void startListening(int serverNumber, int serverPort) {
		try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
			System.out.println("\nServidor de réplica y restauración, iniciado.\n");
			
			while (true) {
				Socket coordinatorSocket = serverSocket.accept();
				
				if (!coordinatorSocket.isClosed()) {
					DataInputStream inputStream = new DataInputStream(coordinatorSocket.getInputStream());
					
					String coordinatorRequest = inputStream.readUTF().toUpperCase();
					
					switch (coordinatorRequest) {
						case "VOTE_REQUEST":
							System.out.println("El coordinador pidió respaldar el archivo de inventario.\n");
							Backer backer = new Backer(serverNumber, coordinatorSocket);
							backer.backUpInventory();
//							coordinatorSocket.close();
							break;
						case "RESTORE":
							System.out.println("El coordinador pidió restaurar el archivo de inventario.\n");
							Restorer restorer = new Restorer(serverNumber, coordinatorSocket);
							restorer.restoreInventory();
							coordinatorSocket.close();
							break;
						default:
							coordinatorSocket.close();
							break;
					}
				}
				
			}
		} catch (SocketException e) {
			System.out.println("El coordinador se desconectó.");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
