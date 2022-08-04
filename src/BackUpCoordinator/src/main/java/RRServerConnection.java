import java.io.*;
import java.net.Socket;

/**
 * Connects and communicates with replica-and-restore servers.
 *
 * @author valen
 */
public class RRServerConnection {
	/**
	 * Socket that connects with replica-and-restore servers.
	 */
	private final Socket serverSocket;
	/**
	 * Sends data to the RR Servers.
	 */
	private final DataOutputStream outputStream;
	/**
	 * Receive data from the RR Servers.
	 */
	private final DataInputStream inputStream;
	
	
	/**
	 * @param server The server that the coordinator chose to connect with.
	 * @throws IOException  if an I/O error occurs when creating the socket.
	 */
	public RRServerConnection(int server) throws IOException {
		this.serverSocket = this.getServerSocket(server);
		this.outputStream = new DataOutputStream(this.serverSocket.getOutputStream());
		this.inputStream = new DataInputStream(this.serverSocket.getInputStream());
	}
	
	/**
	 * @param server The number of the chosen replica-and-restore server.
	 * @return The socket to communicate with the chosen replica-and-restore server
	 * @throws IOException if an I/O error occurs when creating the socket.
	 */
	private Socket getServerSocket(int server) throws IOException {
		String SERVER_IP = "localhost";
		
		String serverIP = "";
		int port = 0;
		
		switch (server) {
			case 1 -> { // Server 1.
				serverIP = SERVER_IP;
				port = 6000;
			}
			case 2 -> { // Server 2.
				serverIP = SERVER_IP;
				port = 6001;
			}
		}
		
		return new Socket(serverIP, port);
	}
	
	/**
	 * @param message The request to send to the replica-and-restore server.
	 * @return Replica-and-restore server's reply.
	 * @throws IOException if some I/O error occurs during communication with the server.
	 */
	public String sendVoteRequestToRRServer(String message) throws IOException {
		this.outputStream.writeUTF(message);
		
		return this.inputStream.readUTF();
	}
	
	/**
	 * @param globalMessage A message to tell the replica-and-restore server that the replication is going to happen.
	 * @throws IOException if some I/O error occurs during communication with the server.
	 */
	public void sendInventoryFile(String globalMessage) throws IOException {
		this.outputStream.writeUTF(globalMessage);
		
		int MAX_BYTES = 8192; // Max read bytes from the inventory file.
		byte[] fileContent = new byte[MAX_BYTES]; // File's read bytes array.
		int in; // The number of bytes to write.
		
		try (FileInputStream fis = new FileInputStream("inventory.json")) {
			BufferedInputStream bis = new BufferedInputStream(fis);
			BufferedOutputStream bos = new BufferedOutputStream(this.serverSocket.getOutputStream());
			
			while ((in = bis.read(fileContent)) != -1) {
				bos.write(fileContent, 0, in);
			}
			
			bis.close();
			bos.close();
		} catch (FileNotFoundException e) {
			System.out.println("El archivo de inventario no ha sido encontrado.");
		}
	}
}
