package it.unisannio.sweng.rosariogoglia.filters;

import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
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
 * Filter utilizzato per impedire ad utente disabilitato di effettuare alcune operazioni. 
 */
//@WebFilter("/UtenteDisabilitato")
public class FilterUtenteDisabilitato implements Filter {

    /**
     * Default constructor. 
     */
    public FilterUtenteDisabilitato() {
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
		
		System.out.println("FILTRO UTENTE DISABILITATO PRENDE LA RICHIESTA");
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession(true);
		
		UtenteRegistrato utenteInSessione = new UtenteRegistratoImpl();
		utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		
		UtenteRegistratoDao daoC = new UtenteRegistratoDaoMysqlJdbc();
		
		//se l'utente in sessione non è disabilitato si prosegue lungo la categna di filtri
		if(utenteInSessione == null || daoC.isUtenteAbilitato(utenteInSessione.getNick())){
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
		else{
			if(utenteInSessione.getTipologiaCliente().equals("acquirente")){
				request.setAttribute("messaggio", "Non è possibile effettuare questa operazione: utente disabilitato dall'amministratore!!! Contattare l'amministratore: rosariogoglia@gmail.com oppure marco.belfiore88@gmail.com!!! </br></br> Al momento puoi soltanto pagare inserzioni aggiudicate!!!");
			}
			else{
				request.setAttribute("messaggio", "Non è possibile effettuare questa operazione: utente disabilitato dall'amministratore!!! Contattare l'amministratore: rosariogoglia@gmail.com oppure marco.belfiore88@gmail.com!!! </br></br> Al momento puoi soltanto eliminare le tue inserzioni scadute e pagare inserzioni aggiudicate!!!");
			}
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
