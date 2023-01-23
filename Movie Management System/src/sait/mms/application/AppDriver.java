package sait.mms.application;

import java.sql.ResultSet;
import java.sql.SQLException;

import sait.mms.drivers.MariaDBDriver;
import sait.mms.managers.MovieManagementSystem;
/**
 * @author Scott Normore, Gao Liu, Christian Lay, Kin Shing Chong
 * Used to Start the application. Is the main method. 
 */
public class AppDriver {
	/**
	 * The main/driver method
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		MovieManagementSystem manager = new MovieManagementSystem();
		manager.displayMenu();

	}

}
