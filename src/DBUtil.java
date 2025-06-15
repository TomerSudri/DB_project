
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBUtil is a utility class for managing database connections.
 * It provides a single static method to establish a connection
 * to a PostgreSQL database using JDBC.
 */
public class DBUtil {

    // JDBC connection URL to the PostgreSQL database
    private static final String URL = "jdbc:postgresql://localhost:5432/ecommerce_db";

    // Database username
    private static final String USER = "postgres";

    // Database password
    private static final String PASSWORD = "123456";

    /**
     * Establishes a connection to the PostgreSQL database.
     *
     * @return a Connection object to interact with the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
