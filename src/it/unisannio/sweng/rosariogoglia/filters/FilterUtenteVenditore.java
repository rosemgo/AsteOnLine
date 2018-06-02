package it.unisannio.sweng.rosariogoglia.filters;

import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;
import it.unisannio.sweng.rosariogoglia.modelImpl.UtenteRegistratoImpl;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class FilterUtenteVenditore
 * 
 * Filtro utilizzato per respingere i tentativi di accesso tramite url, da parte di utenti acquirenti, a pagine riservate all'utilizzo degli utenti venditori
 */
//@WebFilter("/FilterUtenteVenditore")
public class FilterUtenteVenditore implements Filter {

    /**
     * Default constructor. 
     */
    public FilterUtenteVenditore() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession(true);
		
		UtenteRegistrato utenteInSessione = new UtenteRegistratoImpl();
		utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		
		if(utenteInSessione == null || utenteInSessione.getTipologiaCliente().equals("venditore")){
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
		else{
			req.setAttribute("messaggio", "Errore!!! Operazione consentita solo agli utenti venditori!!!");
			request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(req, res);
		}
		
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
