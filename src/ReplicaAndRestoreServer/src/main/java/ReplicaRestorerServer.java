import java.io.DataInputStream;
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
				DataInputStream inputStream = new DataInputStream(coordinatorSocket.getInputStream());
				
				String coordinatorRequest = inputStream.readUTF().toUpperCase();
				
				switch (coordinatorRequest) {
					case "VOTE_REQUEST":
						Backer backer = new Backer(serverNumber, coordinatorSocket);
						backer.backUpInventory();
					case "RESTORE":
						Restorer restorer = new Restorer(serverNumber, coordinatorSocket);
						restorer.restoreInventory();
					default:
						coordinatorSocket.close();
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
