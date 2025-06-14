import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Seller extends User {

	// בנאי עם userId שנשמר באובייקט
	public Seller(String username, String password, int id) {
		super(username, password, id);
	}

	// שליפת כל המוצרים של המוכר מה-DB
	public Product[] getProducts(Connection conn) {
		List<Product> productList = new ArrayList<>();
		String sql = "SELECT * FROM Products WHERE SellerId = ?";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, this.getUserId());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Product product = new Product(
						rs.getString("name"),
						rs.getDouble("price"),
						rs.getString("category"),
						rs.getBoolean("specialPackaging"),
						rs.getDouble("packagingCost")
				);
				productList.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productList.toArray(new Product[0]);
	}

//	// הוספת מוצר חדש ל-DB תחת המוכר הנוכחי
//	public void addProduct(Product product, Connection conn) {
//		String sql = "INSERT INTO Products (SellerId, name, price, category, specialPackaging, packagingCost) " +
//				"VALUES (?, ?, ?, ?, ?, ?)";
//
//		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//			stmt.setInt(1, this.getUserId());
//			stmt.setString(2, product.getName());
//			stmt.setDouble(3, product.getPrice());
//			stmt.setString(4, product.getCategory().toString());
//			stmt.setBoolean(5, product.hasSpecialPackaging());
//			stmt.setDouble(6, product.getPackagingCost());
//
//			stmt.executeUpdate();
//			System.out.println("✅ Product added successfully to the database.");
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("❌ Failed to add product.");
//		}
//	}
// הוספת מוצר חדש ל-DB תחת המוכר הנוכחי
public boolean addProduct(Product product, Connection conn) {
	String sql = "INSERT INTO Products (SellerId, name, price, category, specialPackaging, packagingCost) " +
			"VALUES (?, ?, ?, ?, ?, ?)";

	try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		stmt.setInt(1, this.getUserId());
		stmt.setString(2, product.getName());
		stmt.setDouble(3, product.getPrice());
		stmt.setString(4, product.getCategory().toString());
		stmt.setBoolean(5, product.hasSpecialPackaging());
		stmt.setDouble(6, product.getPackagingCost());

		stmt.executeUpdate();
		return true; // ✅ הצלחה
	} catch (SQLException e) {
		System.out.println("❌ Failed to add product: " + e.getMessage());
		return false; // ❌ כשלון
	}
}

	// שליפת מספר המוצרים של המוכר מה-DB
	public int getProductCount(Connection conn) {
		String sql = "SELECT COUNT(*) FROM Products WHERE SellerId = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, this.getUserId());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String toString() {
		return "Seller{" +
				"userId=" + getUserId() +
				", username='" + getUsername() + '\'' +
				'}';
	}

}
