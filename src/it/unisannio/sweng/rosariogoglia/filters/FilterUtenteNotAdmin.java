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
 * 
 * Filtro che intercetta e respinge i tentativi di accesso da parte dell'amministratore alle pagine riservate agli utenti acquirenti o venditori.
 */
//@WebFilter("/FilterUtenteNotAdmin")
public class FilterUtenteNotAdmin implements Filter {

    /**
     * Default constructor. 
     */
    public FilterUtenteNotAdmin() {
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
	@SuppressWarnings("null")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub

		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse res=(HttpServletResponse)response;
		
		HttpSession session = req.getSession(true);
		
		
		UtenteRegistrato utenteInSessione = new UtenteRegistratoImpl();
		utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		
		if(utenteInSessione == null || (utenteInSessione.getTipologiaCliente().equals("acquirente") || utenteInSessione.getTipologiaCliente().equals("venditore")) ){
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}		
		else{
			req.setAttribute("messaggio", "Non puoi eseguire questa operazione. E' necessario essere loggato come venditore o acquirente!!!");
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
