package team4.retailsystem.model;

/**
 * A User class used for login, authentication and access right control.
 * <p>
 * Communicates with the database to authenticate the user.
 * 
 * @author Szymon
 */
public class User {
	public static final int NO_AUTHORIZATION = -1;
	public static final int NORMAL_USER = 1;
	public static final int ADMINISTRATOR = 2;
	
	private static int idCounter = 0;
	private int id;
	private String username;
	private int authorizationLevel;
	private String passwordDigest;
	private String salt;

	/**
	 * Creates a new user given the provided access rights level.
	 * <p>
	 * Assumption - User objects will be accessed/created through the Database
	 * method authorizeUser(String, String). Hence, for security, "protected"
	 * access to the constructor should suffice.
	 * 
	 * @param authorizationLevel
	 *            the access level of the user as an integer.
	 */
	protected User(int authorizationLevel, String username, String passwordDigest, String salt) {
		setAuthorizationLevel(authorizationLevel);
		setUsername(username);
		setId(++idCounter);
		setPasswordDigest(passwordDigest);
		setSalt(salt);
	}
	
	public User()
	{
		setAuthorizationLevel(1);
		setId(++idCounter);
		setUsername("operator"+idCounter);
		setPasswordDigest(null);
		setSalt(null);
	}

	/**
	 * Used by the database to re-create a user object form existing user data.
	 * 
	 * @param id
	 *            the unique int id of the user.
	 * @param authorizationLevel
	 *            the access level of the user as an integer.
	 */
	protected User(int id, int authorizationLevel, String username, String passwordDigest, String salt) {
		setAuthorizationLevel(authorizationLevel);
		setUsername(username);
		setId(id);
		setPasswordDigest(passwordDigest);
		setSalt(salt);
	}

	public int getAuthorizationLevel() {
		return authorizationLevel;
	}

	public int getID() {
		return id;
	}
	
	public String getUsername(){
		return username;
	}
	
	protected String getSalt() {
		return salt;
	}
	
	protected String getPasswordDigest(){
		return passwordDigest;
	}

	private void setId(int id) {
		this.id = id;
	}

	public void setAuthorizationLevel(int authorizationLevel) {
		this.authorizationLevel = authorizationLevel;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private void setPasswordDigest(String passwordDigest) {
		this.passwordDigest = passwordDigest;
	}
	
	private void setSalt(String salt){
		this.salt = salt;
	}
	
	public String toString()
	{
		return this.username;
	}
	
	public void resetPassword(){
		setPasswordDigest(null);
	}
	
	public boolean isPasswordEmpty(){
		return getPasswordDigest()==null;
	}
}
