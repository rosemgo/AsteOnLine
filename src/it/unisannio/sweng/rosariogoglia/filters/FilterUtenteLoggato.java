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
 * Filtro utilizzato per respingere i tentativi di accesso alle pagine tramite url se l'utente non è loggato
 */
//@WebFilter("/FilterUtenteLoggato")
public class FilterUtenteLoggato implements Filter {

    /**
     * Default constructor. 
     */
    public FilterUtenteLoggato() {
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

		System.out.println("FILTRO UTENTE LOGGATO PRENDE LA RICHIESTA");
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession(true);
		
		UtenteRegistrato utenteInSessione = new UtenteRegistratoImpl();
		utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		
		//se l'utente esiste ed è acquirente o venditore  procede lungo la catena di filtri altrimenti segnala un errore (in teoria possiamo aggiungere anche l'utente admin, tanto la divisione delle pagine che non possono essere visualizzate dagli admin ma solo dagli utenti acquirenti o venditori, viene gestita dal filtro NotAdmin e Admin)
		if(utenteInSessione != null && (utenteInSessione.getTipologiaCliente().equals("acquirente") || utenteInSessione.getTipologiaCliente().equals("venditore") || utenteInSessione.getTipologiaCliente().equals("admin"))){
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
		else{
			req.setAttribute("messaggio", "Errore!!! Per effettuare l'operazione è necessario essere loggati come acquirenti o venditori!!!");
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
