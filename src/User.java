
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstract base class for all user types (e.g., Buyer, Seller).
 * Stores basic user credentials and provides password validation logic.
 */
public abstract class User {
	private String username;
	private String password;
	private int userId;

	/**
	 * Constructor for creating a User.
	 *
	 * @param username the user's username
	 * @param password the user's password
	 * @param id the unique ID of the user in the database
	 */
	public User(String username, String password, int id) {
		this.userId = id;
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the username of the user
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the user's unique ID
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @return the password of the user (in-memory value)
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the user's ID (used when assigned from DB).
	 *
	 * @param userId the ID to assign
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Validates the provided password against the one stored in the database.
	 *
	 * @param conn active SQL connection
	 * @param password the password to verify
	 * @return true if the password matches the one in the DB, false otherwise
	 */
	public boolean validatePassword(Connection conn, String password) {
		String sql = "SELECT password FROM Users WHERE userId = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, this.userId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String dbPassword = rs.getString("password");
				return dbPassword.equals(password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}

