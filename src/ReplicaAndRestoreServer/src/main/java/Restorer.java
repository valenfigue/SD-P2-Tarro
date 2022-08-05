import java.io.*;
import java.net.Socket;

/**
 * Helps to the replica-and-restore server to restore the inventory file.
 *
 * @author valen
 */
public class Restorer extends Helper{
	
	/**
	 * @param serverNumber      Indicates if the server will be in machine 1 or machine 2.
	 *                          Used if both servers will be run in the same machine.
	 * @param coordinatorSocket Connection with the coordinator.
	 * @throws IOException if some I/O error occurs.
	 */
	public Restorer(int serverNumber, Socket coordinatorSocket) throws IOException {
		super(serverNumber, coordinatorSocket);
	}
	
	/**
	 * Sends the local inventory file back to the coordinator.
	 *
	 * @throws IOException if some I/O error occurs during communication with the server.
	 */
	public void restoreInventory() throws IOException {
		if (new File(this.inventoryFilePath).exists()) {
			int MAX_BYTES = 8192; // Max read bytes from the inventory file.
			byte[] fileContent = new byte[MAX_BYTES]; // File's read bytes array.
			int in; // The number of bytes to write.
			
			try (FileInputStream fis = new FileInputStream(this.inventoryFilePath)) {
				BufferedInputStream bis = new BufferedInputStream(fis);
				BufferedOutputStream bos = new BufferedOutputStream(this.coordinatorSocket.getOutputStream());
				
				System.out.println("Enviando archivo...\n");
				while ((in = bis.read(fileContent)) != -1) {
					bos.write(fileContent, 0, in);
				}
				
				bis.close();
				bos.close();
				
				System.out.println("Archivo enviado.\n\n");
			} catch (FileNotFoundException e) {
				System.out.println("El archivo de inventario no ha sido encontrado.\n");
			}
		} else {
			System.out.println("La réplica del archivo de inventario todavía no ha sido creada. Por favor, haga una réplica, primero.\n");
		}
	}
}
