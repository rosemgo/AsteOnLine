package it.unisannio.sweng.rosariogoglia.listener;

import java.io.File;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.apache.log4j.PropertyConfigurator;

/**
 * Application Lifecycle Listener implementation class ContextInizializer
 *
 */
@WebListener
public class ContextInitializer implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ContextInitializer() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {

    	Context initContext;
    	
   /*     DataSource ds = null;
        try {
	    
        	initContext = new InitialContext();
	    	Context envContext = (Context) initContext.lookup("java:/comp/env");
		   	ds = (DataSource) envContext.lookup("jdbc/AsteOnLine"); //la risorsa viene caricata grazie al file context.xml che si trova in META-INF
    	  	
		   	ServletContext context = sce.getServletContext();
		   	context.setAttribute("dataSource", ds);
		   
		} catch (NamingException e) {
			e.printStackTrace();
		}
    	
    */	
    	/* In questo modo viene preparato il log4j 
    	 */
        ServletContext context = sce.getServletContext();
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
         
        System.out.println("Stampo il realpath: " + context.getRealPath(""));
        PropertyConfigurator.configure(fullPath);
        
         
        
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
