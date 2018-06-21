package it.unisannio.sweng.rosariogoglia.dbUtil;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

public class DatabaseUtilTest {

	@Test
	public void testGetConnection() throws ClassNotFoundException, SQLException, IOException{

				
		Connection connection = null;
		connection = DatabaseUtil.getConnection();

		assertEquals(connection.getCatalog(), "ecommerce");
		
		System.out.println("conn: " + connection.toString());
		
		System.out.println(connection.toString());
		
		
	}

}
