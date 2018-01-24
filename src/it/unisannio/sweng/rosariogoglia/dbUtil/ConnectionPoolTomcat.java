package it.unisannio.sweng.rosariogoglia.dbUtil;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.servlet.http.HttpServlet;


@SuppressWarnings("serial")
public class ConnectionPoolTomcat extends HttpServlet{
	
		
	public static Connection getConnection(){

         Context initContext;
         Connection conn = null;
         DataSource ds = null;
         try {
                
			initContext = new InitialContext(); // creiamo il context per accedere al servizio dei nomi
			
			//Context envContext = (Context) initContext.lookup("java:/comp/env");
			//ds = (DataSource) envContext.lookup("jdbc/AsteOnLine");

			ds = (DataSource)initContext.lookup("java:comp/env/jdbc/AsteOnLine");
			
			conn = ds.getConnection();

			// System.out.println("connection: " + conn);	
        	        	
        	 	
         } catch (SQLException e) {
                 e.printStackTrace();
         } catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         return conn;
         
	 }

	
}
