package it.unisannio.sweng.rosariogoglia.controller;

import it.unisannio.sweng.rosariogoglia.dao.BannedCookiesDao;
import it.unisannio.sweng.rosariogoglia.dao.InserzioneDao;
import it.unisannio.sweng.rosariogoglia.dao.UtenteRegistratoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.BannedCookiesDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.InserzioneDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.UtenteRegistratoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.manager_acquisti.SimulatoreAcquisto;
import it.unisannio.sweng.rosariogoglia.manager_acquisti.ValidatoreCarta;
import it.unisannio.sweng.rosariogoglia.model.BannedCookies;
import it.unisannio.sweng.rosariogoglia.model.Inserzione;
import it.unisannio.sweng.rosariogoglia.model.UtenteRegistrato;
import it.unisannio.sweng.rosariogoglia.modelImpl.BannedCookiesImpl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletPagamento
 */
//@WebServlet("/ServletPagamento")
public class ServletPagamento extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		
		UtenteRegistrato utenteInSessione = (UtenteRegistrato) session.getAttribute("utente");
		//Inserzione inserzione = (Inserzione) session.getAttribute("inserzioneDaPagare");
		
		String idInserzioneDaPagare = request.getParameter("idInserzioneDaPagare");
		
		InserzioneDao daoI = new InserzioneDaoMysqlJdbc();
		Inserzione inserzione = daoI.getInserzioneById(Integer.parseInt(idInserzioneDaPagare));
			
		
		String modPagamento = request.getParameter("modPagamento");
		
		if(modPagamento != null){
		
			String numCartaCredito;
			
			if(modPagamento.equals("nuovaCarta")){
				
				numCartaCredito = request.getParameter("cartaCredito");
				System.out.println("carta credito: " + numCartaCredito);		
			}
			else{
				
				numCartaCredito = utenteInSessione.getNumContoCorrente();
				
			}
			
			
			ValidatoreCarta vd = new ValidatoreCarta();
			/*effettuo un controllo per stabilire la validità della carta di credito*/
			if(vd.isCodiceCorretto(numCartaCredito)){
				
				SimulatoreAcquisto sa = new SimulatoreAcquisto();
				//simulo un controllo sul credito (se il credito è maggiore del costo la transazione avverrà correttamente)
				if(sa.pagamento()){
									
					
					daoI.updateStatoInserzione("pagata", inserzione.getIdInserzione());			
					System.out.println("inserzione pagata!!!");
					
					String messaggio = "Complimenti!!! Transazione avvenuta con successo!!! <br /> Per visualizzare tutti gli acquisti effettuati, seleziona 'I Miei Acquisti' dal menù in alto!!!";
					
					session.removeAttribute("codiceErrato"); //in seguito ad un pagamento corretto rimuova dalla sessione l'attributo che manifesta un primo tentativo di pagamento errato. Quindi l'utente ha di nuovo 2 possibilità di pagamento.
					
					request.setAttribute("inserzioneOfferta", inserzione);
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/complimenti.jsp").forward(request, response);
									
				}
				else{
					String messaggio = "Credito insufficiente per effettuare la transazione!!!";
					request.setAttribute("messaggio", messaggio);
					request.setAttribute("inserzioneDaPagare", inserzione);
					request.getRequestDispatcher("/WEB-INF/jsp/pagamento.jsp").forward(request, response);
				}
							
			}
			else{	
				//se ho in sessione l'attributo "codiceErrato"(se c'è, può essere solo true), significa che la tessera inserita per la seconda volta risulta errata, quindi banno e disabilito l'utente
				Boolean isValido = (Boolean) session.getAttribute("codiceErrato");
				
				if(isValido != null){
					
					System.out.println("codice errato!!! true");
					
					UtenteRegistratoDao daoU = new UtenteRegistratoDaoMysqlJdbc();
					daoU.updateAbilitazioneUtente(utenteInSessione.getNick(), false);
	
					
				/*  HO DECISO DI NON BANNARE L'UTENTE IN SEGUITO AD UN PAGAMENTO ERRATO! L'UTENTE VIENE BANNATO SOLO IN CASO DI REGISTRAZIONE O DI MODIFICA DATI CON CARTA ERRATA! */
					
//					//BANNO L'UTENTE IN SEGUITO AL SECONDO TENTATIVO ERRATO DI INSERIMENTO CODICE CARTA
//					BannedCookiesDao daoBC = new BannedCookiesDaoMysqlJdbc();
//					BannedCookies cookie = new BannedCookiesImpl();
//					cookie.setIdUtenteBannato(utenteInSessione.getIdUtente());
//					/*Come cookie utilizzo la mail dell'utente, in modo tale da essere sempre un valore univoco!*/
//					cookie.setCookie(utenteInSessione.geteMail());
//					
//					daoBC.insertBannedCookies(cookie);
				
					//DISABILITO L'UTENTE
					daoU.updateAbilitazioneUtente(utenteInSessione.getNick(), false);
					
					//dopo aver verificato che l'utente ha inserito un codice carta errato per 2 volte, rimuovo dalla sessione l'attributo codiceErrato
					session.removeAttribute("codiceErrato");
					
					String messaggio = "CARTA DI CREDITO NON VALIDA!!! UTENTE DISABILITATO DALL'AMMINISTRATORE!!! CONTATTARE L'AMMINISTRATORE: rosariogoglia@gmail.com / marco.belfiore88@gmail.com";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/errore.jsp").forward(request, response);
													
				}
				else{
					
					String messaggio = "CARTA DI CREDITO NON VALIDA!!! HAI SOLO UN ALTRO TENTATIVO DI INSERIMENTO!!!";
					
					//la prima volta che il codice carta risulta non valido setto la variabile codiceErrato (true) in sessione, da poterla utilizzare per capire se il codice è stato sbagliato per 2 volte
					session.setAttribute("codiceErrato", true);
					request.setAttribute("inserzioneDaPagare", inserzione);
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/WEB-INF/jsp/pagamento.jsp").forward(request, response);
					
				}			
			}
		}
		else{
			String messaggio = "Non hai selezionato nessun metodo di pagamento!!!";
									
			request.setAttribute("messaggio", messaggio);
			request.setAttribute("inserzioneDaPagare", inserzione);
			request.getRequestDispatcher("/WEB-INF/jsp/pagamento.jsp").forward(request, response);
		}
		
	}

}
