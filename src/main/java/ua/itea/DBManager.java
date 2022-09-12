package ua.itea;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DBManager {

	private DBConnector conection;

	private static final String GET_SHOP = "SELECT id FROM shops WHERE s_name = '%s';";
	private static final String GET_PHONE = "SELECT id FROM phones WHERE p_name = '%s';";
	private static final String GET_STP = "SELECT stp_id FROM shopstophones WHERE s_id = '%d' AND p_id = '%d';";
	private static final String GET_ALL = "SELECT s.s_name, p.p_name, stf.price FROM shopstophones AS stf JOIN shops AS s ON s.id = stf.s_id JOIN phones AS p ON p.id = stf.p_id";
	private static final String INSERT_IN_SHOPS = "INSERT INTO shops (s_name) VALUES ('%s');";
	private static final String INSERT_IN_PHONES = "INSERT INTO phones (p_name, p_brand, p_system, p_ram, p_memory, p_processor, p_cores, p_diagonal) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');";
	private static final String INSERT_IN_SHOPSTOPHONES = "INSERT INTO shopstophones (s_id, p_id, price) VALUES ('%d', '%d', '%s');";

	public DBManager(DBConnector conection) {
		this.conection = conection;
	}

	public void createBD(boolean toClean) {
		Connection conn = null;
		conn = conection.getConnection();
		if (conn != null) {
			Statement st = null;
			try {
				st = conn.createStatement();

				st.execute(
						"CREATE TABLE IF NOT EXISTS phones (id INT PRIMARY KEY AUTO_INCREMENT, p_name VARCHAR(100) NOT NULL UNIQUE, p_brand VARCHAR(100) NOT NULL, p_system VARCHAR(100) NOT NULL, p_ram INT NOT NULL,"
								+ " p_memory INT NOT NULL, p_processor VARCHAR(100) NOT NULL, p_cores INT NOT NULL, p_diagonal DOUBLE NOT NULL);");
				st.execute(
						"CREATE TABLE IF NOT EXISTS shops (id INT PRIMARY KEY AUTO_INCREMENT, s_name VARCHAR(255) NOT NULL UNIQUE);");
				st.execute(
						"CREATE TABLE IF NOT EXISTS shopstophones (stp_id INT PRIMARY KEY AUTO_INCREMENT, s_id INT NOT NULL, p_id INT NOT NULL, price INT NOT NULL, FOREIGN KEY (s_id) REFERENCES shops(id), FOREIGN KEY (p_id) REFERENCES phones(id));");

				if (toClean) {
					st.execute("SET FOREIGN_KEY_CHECKS = 0;");
					st.execute("TRUNCATE TABLE phones;");
					st.execute("TRUNCATE TABLE shops;");
					st.execute("TRUNCATE TABLE shopstophones;");
					st.execute("SET FOREIGN_KEY_CHECKS = 1;");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (st != null) {
						st.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("DBManager returned null conection.");
		}
	}

	public synchronized void addToBD(Shop shop) {
		Connection conn = null;
		conn = conection.getConnection();
		if (conn != null) {
			Statement st = null;
			try {
				st = conn.createStatement();

				int shopId = getShop(shop.getName());
				if (shopId == -1) {
					st.execute(String.format(INSERT_IN_SHOPS, shop.getName()));
					shopId = getShop(shop.getName());
				}
				for (Phone phone : shop.getPhones()) {
					int phoneId = getPhone(phone.getName());
					if (phoneId == -1) {
						st.execute(String.format(INSERT_IN_PHONES, phone.getName(), phone.getBrand(), phone.getSystem(),
								phone.getRam(), phone.getMemory(), phone.getProcessor(), phone.getCores(),
								phone.getDiagonal()));
						phoneId = getPhone(phone.getName());
					}
					if (!isAny(shopId, phoneId)) {
						st.execute(String.format(INSERT_IN_SHOPSTOPHONES, shopId, phoneId, phone.getPrice()));
					}

				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (st != null) {
						st.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("DBManager returned null conection.");
		}
	}

	private int getShop(String name) {

		Connection conn = null;
		conn = conection.getConnection();

		int shopId = -1;

		if (conn != null) {
			Statement st = null;
			ResultSet rs = null;
			try {

				st = conn.createStatement();
				rs = st.executeQuery(String.format(GET_SHOP, name));

				if (rs.next()) {
					shopId = rs.getInt("id");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (st != null) {
						st.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("DBManager returned null conection.");
		}

		return shopId;
	}

	private int getPhone(String name) {

		Connection conn = null;
		conn = conection.getConnection();

		int phoneId = -1;

		if (conn != null) {
			Statement st = null;
			ResultSet rs = null;
			try {

				st = conn.createStatement();
				rs = st.executeQuery(String.format(GET_PHONE, name));

				if (rs.next()) {
					phoneId = rs.getInt("id");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (st != null) {
						st.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("DBManager returned null conection.");
		}

		return phoneId;
	}

	private boolean isAny(int sId, int pId) {

		Connection conn = null;
		conn = conection.getConnection();

		boolean result = false;

		if (conn != null) {
			Statement st = null;
			ResultSet rs = null;
			try {

				st = conn.createStatement();
				rs = st.executeQuery(String.format(GET_STP, sId, pId));

				if (rs.next()) {
					result = true;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (st != null) {
						st.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("DBManager returned null conection.");
		}

		return result;
	}

	public void getAllInfo() {

		Connection conn = null;
		conn = conection.getConnection();

		if (conn != null) {
			Statement st = null;
			ResultSet rs = null;
			try {

				st = conn.createStatement();
				rs = st.executeQuery(GET_ALL);

				while (rs.next()) {
					System.out.println("Shop: " + rs.getString("s_name") + " Phone: " + rs.getString("p_name")
							+ " Price: " + rs.getInt("price"));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (st != null) {
						st.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("DBManager returned null conection.");
		}
	}

}
