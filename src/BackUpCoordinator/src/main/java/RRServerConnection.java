import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

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
	 * Inventory file's absolute path (because I couldn't import it with Maven.
	 * <p></p>
	 * <b>IMPORTANT: change the path if the program is run from another location.</b>
	 */
	private final String INVENTORY_FILE_ABSPATH = "D:\\Proyectos\\(1)Universidad\\2022_Sistemas_Distribuidos\\SD-P2-Tarro\\src\\jar\\src\\main\\resources\\inventory.json";
	
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
				port = 6001;
			}
			case 2 -> { // Server 2.
				serverIP = SERVER_IP;
				port = 6002;
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
		
		try (FileInputStream fis = new FileInputStream(this.INVENTORY_FILE_ABSPATH)) {
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
	
	/**
	 * Sends a message to the replica-and-restore server to send back the inventory file replica it has,
	 * to restore it in the Web Server.
	 * <p></p>
	 * The coordinator receive it and moved from its local resources folder to the Jar module.
	 * The method doesn't write it directly on the original because the buffered output stream
	 * overwrites the file when is created.
	 *
	 * @param globalMessage A message to send to the replica-and-restore server.
	 * @throws IOException if some I/O error occurs.
	 */
	public void receiveInventoryFile(String globalMessage) throws IOException {
		this.outputStream.writeUTF(globalMessage);
		
		final String LOCAL_INVENTORY_PATH = "src/main/resources/inventory.json";
		
		try {
			File localInventoryFile = new File(LOCAL_INVENTORY_PATH);
			BufferedInputStream bis = new BufferedInputStream(this.serverSocket.getInputStream());
			// Creating a local inventory file to not overwrite the real one. It will be moved later.
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(localInventoryFile));
			byte[] receivedData = new byte[1024];
			int in;
			
			while ((in = bis.read(receivedData)) != -1) {
				bos.write(receivedData, 0, in);
			}
			bos.close();
			
			// Then, the local inventory file is moved to the Jar module, where the real one should be.
			if (localInventoryFile.length() > 0) {
				File inventoryFile = new File(this.INVENTORY_FILE_ABSPATH);
				
				Files.move(localInventoryFile.toPath(), inventoryFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (FileNotFoundException e) {
			System.out.println("El archivo de inventario no ha sido encontrado.");
		}
	}
}
