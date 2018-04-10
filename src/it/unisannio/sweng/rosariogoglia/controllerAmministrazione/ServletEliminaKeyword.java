package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;

import it.unisannio.sweng.rosariogoglia.dao.KeywordDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.KeywordDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Keyword;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet che permette all'amministratore di eliminare una keyword presente nel database
 */
//@WebServlet("/ServletEliminaKeyword")
public class ServletEliminaKeyword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext context = getServletContext();
		
		String word = request.getParameter("keyword");
		String messaggio = "";
		
		if(word != null && !word.equals("")){
			
			
			KeywordDao daoK = new KeywordDaoMysqlJdbc();
			Keyword keyword = daoK.getKeywordByWord(word);
			
			if(keyword != null){
				
				int deleteRows = daoK.deleteKeyword(keyword.getIdKeyword());
				
				if(deleteRows == 1){
					messaggio = "Parole chiave eliminata correttamente!!!";
					
					/* Aggiorno il parametro listaCategorie in seguito all'aggiunta della categoria appena inserita */ 
					List<Keyword> listaKeyword = daoK.getKeywords();
					context.setAttribute("listaKeyword", listaKeyword);
					System.out.println("CATEGORIE CARICATE E AGGIUNTE ALLO SCOPE");
					
					/*NEL CASO DELL'ELIMINAZIONE DI UNA KEYWORD E' NECESSARIO AGGIORNARE, SE GIA' PRESENTI IN CONTESTO, SIA LA TABELLA MOSTRA KEYWORD MANCANTI, SIA LA TABELLA MOSTRA KEYWORD */
					/*Modifico la tabella keyword(contente tutte le parole chiave associate ad ogni prodotto). Per fare ciò, ho utilizzato una procedura diversa dalle precedenti modifiche. 
					 * In pratica, non definisco il metodo aggiornaKeywordMao in questa classe ma, lo definisco nella classe ServletMostraKeyword e lo richiamo su un oggetto che istanzia la classe ServletMostraKeyword*/
					ServletMostraKeywordMancanti servlet = (ServletMostraKeywordMancanti) context.getAttribute("ServletMostraKeywordMancanti");
					if(servlet != null){
						HashMap<String, String> keywordMancantiMap = new HashMap<String, String>();
						servlet.aggiornaKeywordMancantiMap(keywordMancantiMap);
						context.setAttribute("keywordMancantiMap", keywordMancantiMap);
					}
					
					
					ServletMostraKeyword servlet2 = (ServletMostraKeyword) context.getAttribute("ServletMostraKeyword");
					/*Se ServletMostraKeyword non è stato mai utilizzata e quindi non stato invocato il metodo init, non l'avrò ancora nel contesto*/
					if(servlet2 != null){
						HashMap<String, String> keywordMap = new HashMap<String, String>();
						servlet2.aggiornaKeywordMap(keywordMap);
						context.setAttribute("keywordMap", keywordMap);
					}			
					
				}
				else{
					messaggio = "Parole chiave non eliminata!!!";
				}
			}
			else{
				messaggio = "Parole chiave non presente, non puoi eliminare una parola che non esiste!!!";
			}
			
			
		}						
		else{
			messaggio = "Inserire almeno una parole chiave!!!";
		}
	
		request.setAttribute("messaggio", messaggio);
		request.getRequestDispatcher("/prodotti.jsp").forward(request, response);

		
		
	}

	

}
