package it.unisannio.sweng.rosariogoglia.filters;

import it.unisannio.sweng.rosariogoglia.dao.BannedCookiesDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.BannedCookiesDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.BannedCookies;
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
 * Filter utilizzato per impedire ad utente bannato di effettuare alcune operazioni.
 */
//@WebFilter("/FilterUtenteBanned")
public class FilterUtenteBanned implements Filter {

	protected FilterConfig config;
	private String filterName;
	
    /**
     * Default constructor. 
     */
    public FilterUtenteBanned() {
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
		
		System.out.println("FILTRO UTENTE BANNED PRENDE LA RICHIESTA");
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession(true);
		
		UtenteRegistrato utenteInSessione = new UtenteRegistratoImpl();
		utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		
		BannedCookiesDao daoC = new BannedCookiesDaoMysqlJdbc();
			
		
		//se l'utente in sessione non è bannato si prosegue lungo la categna di filtri
		if(utenteInSessione == null || !daoC.checkUtenteRegistratoBanned(utenteInSessione.getIdUtente())){
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
		else{
						
			if(utenteInSessione.getTipologiaCliente().equals("acquirente")){
				request.setAttribute("messaggio", "Non è possibile effettuare questa operazione: utente bannato dall'amministratore. Puoi solo effettuare pagamenti!!!");
			}
			else{
				request.setAttribute("messaggio", "Non è possibile effettuare questa operazione: utente bannato dall'amministratore. Puoi solo cancellare inserzioni scadute o effettuare pagamenti!!!");
			}
			
			request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(req, res);
		}
		
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.config = fConfig;
		this.filterName = fConfig.getFilterName();
	}

}
