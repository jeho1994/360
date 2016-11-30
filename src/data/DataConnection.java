package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DataConnection {
	
	private static String userName = "tvriper"; 
	private static String password = "mytfags%2B!"; 
	private static String serverName = "cssgate.insttech.washington.edu";
	private static Connection sConnection;
	
	// Creates once instance of the connection to be reused in the different places in the
	// system. 
	private static void createConnection() throws SQLException {
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);
		sConnection =  DriverManager
				.getConnection("jdbc:mysql://" + serverName + "/" + userName + "?user=" + userName + "&password=" + password);
				
		
		//For debugging - System.out.println("Connected to database");
	}
	
	/**
	 * Returns a connection to the database so that queries can be executed.
	 * @return Connection to the database
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		if (sConnection == null) {
			createConnection();
		}
		return sConnection;
	}
	
	/**
	 * Close the connection to the database when done. 
	 * @throws SQLException
	 */
	public static void closeConnection() throws SQLException {
		if (sConnection != null && !sConnection.isClosed()) {
			sConnection.close();
		}
	}

}