import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ReplicaRestorerServer {
	
	@SuppressWarnings("InfiniteLoopStatement")
	public void startListening(int serverNumber, int serverPort) {
		try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
			System.out.println("\nServidor de réplica y restauración, iniciado.\n");
			
			while (true) {
				Socket coordinatorSocket = serverSocket.accept();
				coordinatorSocket.setSoLinger(true, 10);
				CoordinatorManager coordinatorManager = new CoordinatorManager(serverNumber, coordinatorSocket);
				coordinatorManager.start();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
