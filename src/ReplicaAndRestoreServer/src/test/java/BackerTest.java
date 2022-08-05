import junit.framework.TestCase;

import java.io.IOException;

public class BackerTest extends TestCase {
	
	public void testVote() {
		try {
			Backer backer = new Backer(1, null);
			String vote = backer.vote();
			
			if (vote.equals("VOTE_COMMIT")) {
				System.out.println(vote);
				assertTrue(true);
			} else if (vote.equals("VOTE_ABORT")) {
				System.out.println(vote);
				assertTrue(true);
			} else {
				fail();
			}
		} catch (IOException e) {
			fail();
			throw new RuntimeException(e);
		}
	}
}