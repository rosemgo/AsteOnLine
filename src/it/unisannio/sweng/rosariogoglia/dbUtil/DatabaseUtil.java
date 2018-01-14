package it.unisannio.sweng.rosariogoglia.dbUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.lang.Class;

public class DatabaseUtil {

	private static Connection connection;
	
    private static String fileProperties = "WebContent/WEB-INF/db.properties";
	

	public static Connection getConnection() throws SQLException, ClassNotFoundException, IOException {
	
		Properties properties = new Properties();
		properties.load(new FileInputStream(fileProperties));

        String jdbcDriver = properties.getProperty("driver");
        String hostname = properties.getProperty("hostname");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");     
        String database = properties.getProperty("database");     
        
		
		//Carico il driver JDBC per la connessione con il database MySQL
		Class.forName(jdbcDriver);
		//String URL = jdbc:mysql://localhost:3306/ecommerce
		connection = DriverManager.getConnection(hostname + database, username, password); //Definiamo l'URL per la connessione

		return connection;
		
	}
		
	
/*
	// Chiude la connessione con il Database
		public static void closeConnection() {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		public static void rollback (){
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public static void beginTransaction(){
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public static void endTransaction(){
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		*/
}
