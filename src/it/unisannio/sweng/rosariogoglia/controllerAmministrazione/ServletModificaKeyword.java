package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;

import it.unisannio.sweng.rosariogoglia.dao.KeywordDao;
import it.unisannio.sweng.rosariogoglia.dao.ProdottoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.KeywordDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProdottoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Keyword;
import it.unisannio.sweng.rosariogoglia.model.Prodotto;
import it.unisannio.sweng.rosariogoglia.utility.Utility;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet che permette ad un utente amministratore di modificare una keyword
 */
//@WebServlet("/ServletModificaKeyword")
public class ServletModificaKeyword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String word = request.getParameter("keyword");
		String keywordModificata = request.getParameter("keywordModificata");
		
		ProdottoDao daoP = new ProdottoDaoMysqlJdbc();
		KeywordDao daoK = new KeywordDaoMysqlJdbc();
		
		int updateRow = -1;
		String messaggio = "";
		
		if((!word.equals("") && word != null) && (keywordModificata != null && !keywordModificata.equals(""))){
			
			if(Utility.hasSpecialChars(word)){
				messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
				request.setAttribute("messaggio", messaggio);
				request.getRequestDispatcher("/prodotti.jsp").forward(request, response);
				return;
			}
			
			Keyword keyword = daoK.getKeywordByWord(word);
			
			if(keyword != null){
				//aggiorno la parola chiave
				keyword.setKeyword(keywordModificata);
				updateRow = daoK.updateKeyword(keyword);
							
				if(updateRow != -1){
					
					messaggio = "Parola chiave '" + word + "' aggiornata correttamente in " + keyword.getKeyword() + " !!!";
					
					ServletContext context = getServletContext();
					
					/* Aggiorno il parametro listaCategorie in seguito all'aggiunta della categoria appena inserita */ 
					List<Keyword> listaKeyword = daoK.getKeywords();
					context.setAttribute("listaKeyword", listaKeyword);
					System.out.println("CATEGORIE CARICATE E AGGIUNTE ALLO SCOPE");					
					
					
					/*Modifico la tabella keyword(contente tutte le parole chiave associate ad ogni prodotto). Per fare ciò, ho utilizzato una procedura diversa dalle precedenti modifiche. 
					 * In pratica, non definisco il metodo aggiornaKeywordMap in questa classe ma, lo definisco nella classe ServletMostraKeyword e lo richiamo su un oggetto che istanzia la classe ServletMostraKeyword*/
					ServletMostraKeywordMancanti servlet = (ServletMostraKeywordMancanti) context.getAttribute("ServletMostraKeywordMancanti");
					if(servlet != null){
						HashMap<String, String> keywordMancantiMap = new HashMap<String, String>();
						servlet.aggiornaKeywordMancantiMap(keywordMancantiMap);
						context.setAttribute("keywordMancantiMap", keywordMancantiMap);
					}			
					
					ServletMostraKeyword servlet2 = (ServletMostraKeyword) context.getAttribute("ServletMostraKeyword");
					/*Se ServletMostraKeyword non è stato mai utilizzata e quindi non è stato invocato il metodo init, non l'avrò ancora nel contesto*/
					if(servlet2 != null){
						HashMap<String, String> keywordMap = new HashMap<String, String>();
						servlet2.aggiornaKeywordMap(keywordMap);
						context.setAttribute("keywordMap", keywordMap);
					}
					
				}
				else{
					messaggio = "Parola chiave '" + word + "' non aggiornata correttamente in " + keyword.getKeyword() + " !!!";
				}	
					
			}
			else{
				messaggio = "La parola chiave inserita non è presente!!! Inserire una parola chiave presente nell'elenco!!!";
			}
			
		}
		else {
			messaggio = "Inserisci dei valori validi!!!";
		}
				
		request.setAttribute("messaggio", messaggio);
		request.getRequestDispatcher("/prodotti.jsp").forward(request, response);
		
		
	}


}
