public class ReplicaRestorerServer1 {
	public static void main(String[] args) {
		ReplicaRestorerServer server = new ReplicaRestorerServer();
		server.startListening(1);
	}
}
