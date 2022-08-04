import java.io.IOException;

/**
 * Helps the coordinator to back up the inventory file.
 *
 * @author valen
 */
public class Backer {
	private final RRServerConnection server1 = new RRServerConnection(1);
	private final RRServerConnection server2 = new RRServerConnection(2);
	
	public Backer() throws IOException {}
	
	/**
	 * Backs up the inventory file simulating two-phases replication.
	 * <p></p>
	 * The backer sends to the replica-and-restore servers a vote request.
	 * If both of them vote to do a commit, the backer will send them the inventory file
	 * for them to save locally.
	 * If one of them votes to abort the replication, the backer will send to both servers
	 * a message, saying to abort it
	 *
	 * @throws IOException if some I/O error occurs.
	 */
	public void backUpInventory() throws IOException {
		boolean globalCommit = this.askingServersVote();
		
		String globalMessage = globalCommit ? "GLOBAL_COMMIT" : "GLOBAL_ABORT";
		
		if (globalCommit) {
			this.server1.sendInventoryFile(globalMessage);
			this.server2.sendInventoryFile(globalMessage);
		} else {
			this.server1.sendVoteRequestToRRServer(globalMessage);
			this.server2.sendVoteRequestToRRServer(globalMessage);
		}
	}
	
	/**
	 * Requests to the replica-and-restore servers to vote to replicate the inventory file.
	 *
	 * @return True if both replica-and-restore servers vote to commit
	 *          and replicate the inventory file.
	 * @throws IOException if some I/O error occurs.
	 */
	private boolean askingServersVote() throws IOException {
		String request = "VOTE_REQUEST";
		String commit = "VOTE_COMMIT"; // A vote to do the replications.
		//noinspection unused
		String abort = "VOTE_ABORT"; // A vote to don't do the replication.
		
		return server1.sendVoteRequestToRRServer(request).equals(commit)
              && server2.sendVoteRequestToRRServer(request).equals(commit);
	}
}
