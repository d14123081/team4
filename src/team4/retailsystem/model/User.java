package team4.retailsystem.model;

public class User {
	private static int idCounter = 0;
	private int id;
	private int authorizationLevel;

	/*
	 * Assumption - Users will be accessed through the Database method
	 * authorizeUser(String, String). Hence, for security protected access to
	 * constructors.
	 */
	protected User(int authorizationLevel) {
		setAuthorizationLevel(authorizationLevel);
		setId(idCounter++);
	}

	protected User(int id, int authorizationLevel) {
		setAuthorizationLevel(authorizationLevel);
		setId(id);
	}

	public int getAuthorizationLevel() {
		return authorizationLevel;
	}

	public int getID() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	private void setAuthorizationLevel(int authorizationLevel) {
		this.authorizationLevel = authorizationLevel;
	}
}
