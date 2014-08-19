package team4.retailsystem.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Inner class that uses MD5 to generate password digests.
 * 
 * @author Szymon
 */
public class EncryptionModule {
	private MessageDigest md;

	public EncryptionModule() throws NoSuchAlgorithmException {
		md = MessageDigest.getInstance("MD5");
	}

	/**
	 * Returns a random 8-byte salt as a String.
	 * 
	 * @return Random 8-byte string.
	 */
	public String getRandomSalt() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[8];
		random.nextBytes(bytes);
		return new String(bytes);
	}

	/**
	 * Returns the password digest, created using the given salt.
	 * 
	 * @param password
	 *            The password to encrypt.
	 * @param salt
	 *            The salt used to protect against dictionary attacks.
	 * @return Password digest as a String.
	 */
	public String encrypt(String password, String salt) {
		md.reset();
		md.update(salt.getBytes());
		return new String(md.digest(password.getBytes()));
	}
}