package sait.mms.drivers;

import java.sql.*;

import sait.mms.contracts.DatabaseDriver;
/**
 * Used to make a connection and perform queries on database
 */
public class MariaDBDriver implements DatabaseDriver {
	
	private static final String SERVER = "localhost";
	private static final int PORT = 3306;
	private static final String DATABASE = "cprg251";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "password";
	Connection conn;
	
	final String DB_URL = String.format("jdbc:mariadb://%s:%d/%s?user=%s&password=%s",
			SERVER, PORT, DATABASE, USERNAME, PASSWORD);

	/**
	 * Used to connect to the database
	 * @throws SQLException
	 */
	@Override
	public void connect() throws SQLException {
		conn = DriverManager.getConnection(DB_URL);
	}

	/**
	 * Used to resolve a Select query
	 * @param query Query to send to database.
	 * @return ResultSet
	 * @throws SQLException Thrown if problem performing query.
	 */
	@Override
	public ResultSet get(String query) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet result = stmt.executeQuery(query);
		return result;
	}

	/**
	 * Used to resolve an update query
	 * @param query Query to send to database.
	 * @return number of rows modified
	 * @throws SQLException Thrown if problem performing query.
	 */
	@Override
	public int update(String query) throws SQLException {
		Statement stmt = conn.createStatement();
		int rows = stmt.executeUpdate(query);
		return rows;
	}

	/**
	 * Disconnects from the database.
	 * @throws SQLException
	 */
	@Override
	public void disconnect() throws SQLException {
		conn.close();
	}

}
