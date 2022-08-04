public class ReplicaRestorerServer2 {
	public static void main(String[] args) {
		ReplicaRestorerServer server = new ReplicaRestorerServer();
		server.startListening(2);
	}
}
