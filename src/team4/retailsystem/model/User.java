package team4.retailsystem.model;

/**
 * A User class used for login, authentication and access rights control.
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
	 * 
	 * @param authorizationLevel
	 *            the access level of the user as an integer
	 */
	public User(int authorizationLevel, String username, String passwordDigest, String salt) {
		setAuthorizationLevel(authorizationLevel);
		setUsername(username);
		setId(++idCounter);
		setPasswordDigest(passwordDigest);
		setSalt(salt);
	}

	/**
	 * Used by the database to re-create a user object form existing user data.
	 * 
	 * @param id
	 *            unique int ID of the user
	 * @param authorizationLevel
	 *            the access level of the user as an integer
	 */
	public User(int id, int authorizationLevel, String username, String passwordDigest, String salt) {
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
	
	public String getSalt() {
		return salt;
	}
	
	public String getPasswordDigest(){
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

	public void setPasswordDigest(String passwordDigest) {
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
	
	@Override
	public boolean equals(Object o){
		if(! (o instanceof User)){
			return false;
		}
		else{
			return this.id == ((User)o).getID();
		}
	}
}
