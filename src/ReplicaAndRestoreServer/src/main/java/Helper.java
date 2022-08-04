import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Helper {
	/**
	 * Replica and restore coordinator's socket.
	 */
	protected final Socket coordinatorSocket;
	
	/**
	 * Inventory file's path in this module.
	 */
	protected final String inventoryFilePath;
	
	/**
	 * Reads coordinator's messages.
	 */
	protected final DataInputStream inputStream;
	
	/**
	 * Sends messages to the coordinator.
	 */
	protected final DataOutputStream outputStream;
	
	/**
	 * @param serverNumber Indicates if the server will be in machine 1 or machine 2.
	 *                        Used if both servers will be run in the same machine.
	 * @param coordinatorSocket Connection with the coordinator.
	 * @throws IOException if some I/O error occurs.
	 */
	public Helper(int serverNumber, Socket coordinatorSocket) throws IOException {
		this.coordinatorSocket = coordinatorSocket;
		this.inventoryFilePath = "src/main/resources/inventory" + serverNumber + ".json";
		
		this.inputStream = new DataInputStream(this.coordinatorSocket.getInputStream());
		this.outputStream = new DataOutputStream(this.coordinatorSocket.getOutputStream());
	}
}
