import java.io.IOException;

/**
 * Helps the coordinator to restore the inventory file.
 *
 * @author valen
 */
public class Restorer {
	private final RRServerConnection server1 = new RRServerConnection(1);
	private final RRServerConnection server2 = new RRServerConnection(2);
	
	public Restorer() throws IOException {}
	
	/**
	 * Asks the replica-and-restore servers to send back the inventory file replica they have to restore the
	 * one in the Web Server.
	 *
	 * @throws IOException if some I/O error occurs.
	 */
	public void restoreInventory() throws IOException {
		String globalMessage = "RESTORE";
		
		this.server1.receiveInventoryFile(globalMessage);
		this.server2.receiveInventoryFile(globalMessage);
	}
}
