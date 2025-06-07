////Submitters: Fabiana Daeem-211914783 , Tomer Sudri-316466432
//public abstract class User {
//	private String username;
//	private String password;
//
//	private int userId;
//	public User(String username, String password) {
//		this.username = username;
//		this.password = password;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public boolean validatePassword(String password) {
//		return this.password.equals(password);
//	}
//}

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class User {
	private String username;
	private String password;

	private int userId;
	public User(String username, String password,int id) {
		this.userId=id;
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public int getUserId( ) {
		return userId;
	}


	public String getPassword( ) {
		return password;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

		// אימות סיסמה מתוך ה־DB
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



