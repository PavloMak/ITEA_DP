package ua.itea;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

	public DBConnector() {
		System.out.println("Trying to load driver.");
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("Driver successfully loaded.");
		} catch (Exception ex) {
			System.out.println("Error loading driver.");
		}
	}

	public Connection getConnection() {
		Connection conn = null;
		// System.out.print("Connecting...");
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/shopmobile?" + "user=root&password=");
			// System.out.println("success");
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;
	}

}
