package it.unisannio.sweng.rosariogoglia.filters;


import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


/**
 * Servlet Filter implementation class FilterLog
 * 
 *
 */
//@WebFilter("/FilterLog")
public class FilterLog implements Filter {

	protected FilterConfig config;
	private String filterName;
	// log per gli accessi alle risorse
	//private Logger logAccessi = Logger.getLogger("asteonline.logAccessi");
	
	private Logger logAccessi = Logger.getLogger(FilterLog.class);
	
	/**
     * Default constructor. 
     */
    public FilterLog() {
 //   	DOMConfigurator.configure("WebContent/WEB-INF/log4jConfigAccessoRisorse.xml");
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {

		}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		System.out.println("FILTRO LOG PRENDE LA RICHIESTA");
		
		
		HttpServletRequest req = (HttpServletRequest) request;
		// scrive sul file log accessi ad ogni richiesta
		logAccessi.info(new Date() + " --> " + req.getRemoteHost() + " ha tentato l'accesso alla risorsa: " + req.getRequestURL() + "." + "(Reported by " + filterName + ")");

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.config = fConfig;
		this.filterName = fConfig.getFilterName();
	}

}
