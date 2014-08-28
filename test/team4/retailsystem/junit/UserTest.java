package team4.retailsystem.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import team4.retailsystem.model.Database;
import team4.retailsystem.model.User;

public class UserTest {

	@Test
	public void createAdminUser() {
		Database db = Database.getInstance();
		assertTrue(db.addUser(User.ADMINISTRATOR, "admin", "admin"));
	}
	
	@Test
	public void createNormalUser() {
		Database db = Database.getInstance();
		assertTrue(db.addUser(User.NORMAL_USER, "user", "user"));
	}

}
