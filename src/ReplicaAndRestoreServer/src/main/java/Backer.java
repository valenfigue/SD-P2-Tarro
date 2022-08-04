import java.io.*;
import java.net.Socket;
import java.util.Random;

/**
 * Helps to the replica-and-restore server to back up the inventory file.
 *
 * @author valen
 */
public class Backer extends Helper{
	
	/**
	 * @param serverNumber Indicates if the server will be in machine 1 or machine 2.
	 *                        Used if both servers will be run in the same machine.
	 * @param coordinatorSocket Connection with the coordinator.
	 * @throws IOException if some I/O error occurs.
	 */
	public Backer(int serverNumber, Socket coordinatorSocket) throws IOException {
		super(serverNumber, coordinatorSocket);
	}
	
	/**
	 * Backs up the inventory file that the coordinator sends, following a two-phases replication simulation.
	 * <p></p>
	 * The server receives coordinator's request to do a back-up and the backer sends its (randomly chosen) vote to
	 * commit the replication or abort it.
	 * If both servers choose to commit, the backer will receive the inventory file from the coordinator.
	 *
	 * @throws IOException if some I/O error occurs.
	 */
	public void backUpInventory() throws IOException {
		outputStream.writeUTF(this.vote()); // Sending vote to coordinator.
		String globalMessage = inputStream.readUTF(); // Receiving global message from coordinator.
		
		if (globalMessage.equals("GLOBAL_COMMIT")) {
			System.out.println("Iniciando respaldo del inventario.");
			this.receiveInventoryFile();
		} else if (globalMessage.equals("GLOBAL_ABORT")) {
			System.out.println("Abortando respaldo del inventario.");
			this.outputStream.writeUTF("OK");
		}
	}
	
	/**
	 * Choose, randomly, between VOTE_COMMIT or VOTE_ABORT.
	 *
	 * @return Server's vote to reply to the coordinator: VOTE_COMMIT or VOTE_ABORT.
	 */
	String vote() {
		Random random = new Random();
		return random.nextBoolean() ? "VOTE_COMMIT" : "VOTE_ABORT";
	}
	
	/**
	 * Receive the inventory file from the coordinator.
	 *
	 * @throws IOException if some I/O error occurs.
	 */
	private void receiveInventoryFile() throws IOException {
		try {
			File inventoryFile = new File(this.inventoryFilePath);
			BufferedInputStream bis = new BufferedInputStream(this.coordinatorSocket.getInputStream());
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(inventoryFile));
			byte[] receivedData = new byte[1024];
			int in;
			
			while ((in = bis.read(receivedData)) != -1) {
				bos.write(receivedData, 0, in);
			}
			bos.close();
		} catch (FileNotFoundException e) {
			System.out.println("El archivo de inventario no ha sido encontrado.");
		}
	}
}
