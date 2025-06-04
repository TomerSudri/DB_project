//Submitters: Fabiana Daeem-211914783 , Tomer Sudri-316466432
public abstract class User {
	private String username;
	private String password;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public boolean validatePassword(String password) {
		return this.password.equals(password);
	}
}
