package team4.retailsystem.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import team4.retailsystem.model.LineItem;
import team4.retailsystem.model.User;

public class UserTest {

	@Test
	public void testConstructorSG() {
		int id1 = 5;
		int authorizationLevel1 = 10;
		String username1 = "Thomas Kane";
		String passwordDigest1 = "jansgioweng";
		String salt1 = "goweangw";

		int authorizationLevel2 = 101;
		String username2 = "Thomas Kane1";
		String passwordDigest2 = "jansgioweng1";
		
		User u = new User(id1, authorizationLevel1, username1, passwordDigest1, salt1);

		assertEquals(authorizationLevel1, u.getAuthorizationLevel());
		assertEquals(id1, u.getID());
		assertEquals(passwordDigest1, u.getPasswordDigest());
		assertEquals(salt1, u.getSalt());
		assertEquals(username1, u.getUsername());
		
		u.setAuthorizationLevel(authorizationLevel2);
		u.setPasswordDigest(passwordDigest2);
		u.setUsername(username2);

		assertEquals(authorizationLevel2, u.getAuthorizationLevel());
		assertEquals(id1, u.getID());
		assertEquals(passwordDigest2, u.getPasswordDigest());
		assertEquals(salt1, u.getSalt());
		assertEquals(username2, u.getUsername());
	}
}