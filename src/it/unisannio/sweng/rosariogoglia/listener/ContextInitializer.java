package it.unisannio.sweng.rosariogoglia.listener;



import java.io.File;

import javax.naming.Context;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Application Lifecycle Listener implementation class ContextInizializer
 * Listener utilizzato per la creazione del context. Utilizzato per settare la proprietà rootPath
 *
 */
@WebListener
public class ContextInitializer implements ServletContextListener {

	
	private Logger logger = Logger.getLogger(ContextInitializer.class);  	
	
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
    	/* In questo modo viene preparato il log4j ad ogni avvio di Tomcat*/
        ServletContext context = sce.getServletContext();
        
        //log4j-config-location è il nome scelto nel web.xml per indicare la risorsa log4j.properties
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        
        String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
        
        System.out.println("Nel LISTNER CONTEXTINITIALIZER ");
        
        System.out.println("Stampo il realpath: " + context.getRealPath(""));
        PropertyConfigurator.configure(fullPath);
        
        /*setto la proprietà rootPath in modo da poterla usare sempre nel sistema, e mi da il percorso in cui Tomcat deploya la mia applicazione
         * in modo tale da poterla usare nel log4j.properties per la creazione del file di log*/
       
        System.setProperty("rootPath", context.getRealPath("/"));
        System.out.println("Stampo il rootPath: " + context.getRealPath("/"));
        
        
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    	
    	if(LogManager.class.getClassLoader().equals(this.getClass().getClassLoader())){
    			logger.info("Log4j was loaded by application classloader; shutting down.");
    			LogManager.shutdown();
    	}
    	else{
    		logger.info("Log4j was loaded by some other ClassLoader; not shutting down.");
    	}
    	
    	
    }
	
}

