package it.unisannio.sweng.rosariogoglia.filters;

import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * Utilizzo questo filtro per controllare che l'utente che intende osservare o fare offerte per un'inserzione non ne sia il venditore.
 * Il venditore di un inserzione non può osservare né fare offerte per la stessa.
 */
//@WebFilter("/FilterControlloNotVenditore")
public class FilterControlloNotVenditore implements Filter {

    /**
     * Default constructor. 
     */
    public FilterControlloNotVenditore() {
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
		
		System.out.println("FilterControlloNotVenditore");
		
		HttpServletRequest req = (HttpServletRequest) request; 
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		
		
		//prelevo l'utente dalla sessione corrente
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		//prelevo l'inserzione dalla sessione corrente
		Inserzione inserzione = null; //(Inserzione) session.getAttribute("inserzione");
		
		
		String idInserzioneString;
		
		idInserzioneString = req.getParameter("idInserzioneForm"); //preleviamo l'id dal form della paginaOfferta.jsp
		
		if(idInserzioneString == null){
			idInserzioneString = (String) req.getParameter("idInserzione"); //
		}
		
		if(idInserzioneString != null){
			InserzioneDao daoI = new InserzioneDaoMysqlJdbc();
			inserzione = daoI.getInserzioneById(Integer.parseInt(idInserzioneString));
		}
		
		
		if(utenteInSessione == null || inserzione == null || (utenteInSessione.getIdUtente() != inserzione.getVenditore().getIdUtente())){
			// pass the request along the filter chain
			chain.doFilter(req, res);
		}
		else{
						
			String messaggio="Non è possibile osservare o effettuare offerte per una propria inserzione!!!";
			request.setAttribute("messaggio", messaggio);
			request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(req, res);
			
		}
		
			
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
