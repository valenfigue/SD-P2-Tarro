package Inventory;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
	private Transaction transaction;
	
	@BeforeEach
	void setUp() {
		String json = """
			{
			  "quantity": 60,
			  "product": "Producto A",
			  "actor": "Productor",
			  "action_datetime": "27/07/2022 7:35:29 PM",
			  "current_stock": [
			    {
			      "c_product": "Producto A",
			      "c_quantity": 60
			    },
			    {
			      "c_product": "Producto B",
			      "c_quantity": 0
			    }
			  ]
			}""";
		
		JSONObject jsonTransaction = new JSONObject(json);
		transaction = new Transaction(jsonTransaction);
	}
	
	@Test
	void getJSONTransaction() {
		String json = """
			{
			  "quantity": 60,
			  "product": "Producto A",
			  "actor": "Productor",
			  "action_datetime": "27/07/2022 7:35:29 PM",
			  "current_stock": [
			    {
			      "c_product": "Producto A",
			      "c_quantity": 60
			    },
			    {
			      "c_product": "Producto B",
			      "c_quantity": 0
			    }
			  ]
			}""";
		
		JSONObject jsonTransaction = new JSONObject(json);
		
		assertEquals(
			transaction.getJSONTransaction().getString("product"),
			jsonTransaction.getString("product")
		);
	}
	
	@Test
	void getJSONProductStockFromTransaction() {
		String json = """
			{
			  "quantity": 60,
			  "product": "Producto A",
			  "actor": "Productor",
			  "action_datetime": "27/07/2022 7:35:29 PM",
			  "current_stock": [
			    {
			      "c_product": "Producto A",
			      "c_quantity": 60
			    },
			    {
			      "c_product": "Producto B",
			      "c_quantity": 0
			    }
			  ]
			}""";
		
		JSONObject jsonTransaction = new JSONObject(json);
		JSONObject jsonProductStock = jsonTransaction.getJSONArray("current_stock").getJSONObject(0);
		
		JSONObject productStock = transaction.getJSONTransaction()
			                          .getJSONArray("current_stock").getJSONObject(0);
		
		assertEquals(
			jsonProductStock.getString("c_product"),
			productStock.getString("c_product")
		);
	}
}