package it.unisannio.sweng.rosariogoglia.filters;

import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class FilterManutenzione
 * 
 * Filter che intercetta e respinge tentativi di accesso al sito quando è in manutenzione.
 */
//@WebFilter("/FilterManutenzione")
public class FilterManutenzione implements Filter {

	protected FilterConfig config;
		
    /**
     * Default constructor. 
     */
    public FilterManutenzione() {
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
		
		
		System.out.println("FILTRO CONTROLLO MANUTENZIONE");
		
		HttpServletRequest req = (HttpServletRequest) request; 
		HttpServletResponse res = (HttpServletResponse) response;
		
		ServletContext context = this.config.getServletContext();
		
		boolean inManutenzione = (Boolean) context.getAttribute("inManutenzione");
		
		HttpSession session = req.getSession();
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		
		if(utenteInSessione == null){
			System.out.println("utente null");
		}
		
		
		//se il sito è in manutenzione indirizza ad una pagina informativa per tutti gli utenti che non sono amministratori, altrimenti procede nella catena di filtri
		if(inManutenzione){
		
			System.out.println("sito in manutenzione");
			
			if(utenteInSessione != null){
				
				
				System.out.println("Abbiamo l'utente in sessione: " + utenteInSessione.getNick());
				/*La manutenzione vale solo per gli utenti NON amministratori*/
				if(!utenteInSessione.getTipologiaCliente().equals("admin")){
					//nel caso un utente sia in sessione, mentre l'amministratore modifica lo stato del sito ad OFF-LINE, dobbiamo invalidare la sessione
					session.invalidate();
					req.getRequestDispatcher("WEB-INF/jsp/manutenzione.jsp").forward(req, res);	
					System.out.println("invio a manutenzione con utente");
				}
				else{
					//passa la richiesta lungo la catena di filtri
					chain.doFilter(request, response);
				}
			}
			else{
				req.getRequestDispatcher("WEB-INF/jsp/manutenzione.jsp").forward(req, res);
				System.out.println("invio a manutenzione utente null");
			}
			
		}
		else{
			//passa la richiesta lungo la catena di filtri
			chain.doFilter(request, response);
		}
			
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.config = fConfig;
	}

}
