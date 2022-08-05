package Inventory;

import org.json.JSONArray;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Reads and rewrites the inventory file, and gets a json from it.
 *
 * @author valen
 */
public class InventoryFileManager {
	private final String INVENTORY_FILE_PATH = "D:\\Proyectos\\(1)Universidad\\2022_Sistemas_Distribuidos\\SD-P2-Tarro\\src\\jar\\src\\main\\resources\\inventory.json";
	
	/**
	 * Gets a list of all the transactions registered in the inventory file.
	 *
	 * @return A list with all the transactions.
	 */
	public ArrayList<Transaction> getInventory() {
		JSONArray jsonInventory = new JSONArray();
		try {
			jsonInventory = this.getJSONInventory();
		} catch (IOException | ParseException e) {
			System.out.println("Hubo un problema al intentar leer el archivo de inventario.");
		}
		
		ArrayList<Transaction> transactions = new ArrayList<>();
		for (int i = 0; i < jsonInventory.length(); i++) {
			Transaction transaction = new Transaction(jsonInventory.getJSONObject(i));
			transactions.add(transaction);
		}
		
		return transactions;
	}
	
	/**
	 * Reads the inventory file and gets an array of json that contains all the transactions.
	 *
	 * @return All the transactions in a JSON array.
	 * @throws IOException Thrown back when the inventory file doesn't exist.
	 * @throws ParseException When there's a problem parsing the inventory file to JSON.
	 */
	public JSONArray getJSONInventory() throws IOException, ParseException {
		// Taking file content with json-simple Maven library.
		Object fileContent = new JSONParser().parse(new FileReader(INVENTORY_FILE_PATH));
		org.json.simple.JSONArray jsonFileContent = (org.json.simple.JSONArray) fileContent;
		
		return new JSONArray(jsonFileContent.toJSONString()); // "Parsing" from json-simple to org-json library.
	}
	
	/**
	 * Rewrites the inventory file with all the transactions.
	 *
	 * @param transactions Lists of transactions to register in the inventory file.
	 */
	public void setInventory(ArrayList<Transaction> transactions) {
		JSONArray jsonTransactions = new JSONArray();
		
		for (Transaction transaction : transactions) {
			jsonTransactions.put(transaction.getJSONTransaction());
		}
		
		try (PrintWriter writer = new PrintWriter(new FileWriter(INVENTORY_FILE_PATH))) {
			writer.write(jsonTransactions.toString(2));
		} catch (IOException e) {
			System.out.println("Hubo un problema al intentar crear el archivo de inventario.");
		}
	}
}
