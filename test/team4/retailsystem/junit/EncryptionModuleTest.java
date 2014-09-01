package team4.retailsystem.junit;

import static org.junit.Assert.*;
import java.security.NoSuchAlgorithmException;
import org.junit.Test;
import team4.retailsystem.utils.EncryptionModule;

/**
 * jUnit tests for EncryptionModule.java
 * 
 * @author szymon
 */
public class EncryptionModuleTest {

	@Test
	public void testGetRandomSalt() {
		EncryptionModule em = null;
		try {
			em = new EncryptionModule();
		} catch (NoSuchAlgorithmException e) {
			fail("EncryptionModule failed to initialize." + e.getMessage());
		}
		assertNotNull(em.getRandomSalt());
		assertNotSame(em.getRandomSalt(), em.getRandomSalt());
	}

	@Test
	public void testEncrypt() {
		EncryptionModule em = null;
		try {
			em = new EncryptionModule();
		} catch (NoSuchAlgorithmException e) {
			fail("EncryptionModule failed to initialize." + e.getMessage());
		}

		String password1 = "password";
		String salt1 = em.getRandomSalt();
		String password2 = "passwor";
		String salt2 = em.getRandomSalt();
		String digest1 = em.encrypt(password1, salt1);
		String digest2 = em.encrypt(password2, salt2);

		assertNotSame(digest1, digest2);
		assertNotSame(em.encrypt(password1, salt2), digest1);
		assertNotSame(em.encrypt(password1, salt2), digest2);
		assertNotSame(em.encrypt(password2, salt1), digest1);
		assertNotSame(em.encrypt(password2, salt1), digest2);
		assertNotSame(password1, digest1);
		assertNotSame(password2, digest2);
	}
}
