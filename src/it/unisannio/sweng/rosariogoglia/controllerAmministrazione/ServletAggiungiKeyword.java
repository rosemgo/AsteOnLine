package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;

import it.unisannio.sweng.rosariogoglia.dao.KeywordDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.KeywordDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Keyword;
import it.unisannio.sweng.rosariogoglia.modelImpl.KeywordImpl;
import it.unisannio.sweng.rosariogoglia.utility.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * La Servlet permette all'amministratore di inserire una nuova Keyword.
 */
//@WebServlet("/ServletAggiungiKeyword")
public class ServletAggiungiKeyword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext context = getServletContext();
	
		String keywords = request.getParameter("listaKeywords");
		String messaggio = "";
		
		if(keywords != null && !keywords.equals("")){
			Keyword keyword;
			List<Keyword> listaKeywords = new ArrayList<Keyword>();
			
			StringTokenizer tokenizer = new StringTokenizer(keywords, ",");
			while(tokenizer.hasMoreTokens()){
				String word = tokenizer.nextToken();
				
				if(Utility.hasSpecialChars(word)){
					messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/prodotti.jsp").forward(request, response);
					return;
				}
				
				keyword = new KeywordImpl();
				keyword.setKeyword(word);
				
				//non effettuo il controlla sulla presenza della parola nel db, perchè lo gestisco nel metodo insertListaKeyword, in quanto ogni parola ha l'attributo unique, di conseguenza in caso di parola uguale, non verrà fatto l'inserimento
				listaKeywords.add(keyword);
				System.out.println("keyword aggiunta alla lista");
			}
			
			
			KeywordDao daoK = new KeywordDaoMysqlJdbc();
						
			int insertRows = daoK.insertListaKeyword(listaKeywords);
			
			if(insertRows != -1){
				messaggio = "Parole chiave inserite correttamente!!!";
				
				/* Aggiorno il parametro listaKeyword in seguito all'aggiunta della categoria appena inserita */ 
				List<Keyword> listaKeyword = daoK.getKeywords();
				context.setAttribute("listaKeyword", listaKeyword);
				System.out.println("CATEGORIE CARICATE E AGGIUNTE ALLO SCOPE");
				
				
				/*NEL CASO DELL'AGGIUNTA DI UNA KEYWORD NUOVA, AGGIORNIAMO SOLO LA TABELLA DELLE PAROLE CHIAVE MANCANTI PERCHE' UNA KEYWORD APPENA AGGIUNTA SICURAMENTE NON PUO' ESSERE GIA' ASSOCIATA AD UN PRODOTTO PRESENTE(NON AVREBBE SENSO AGGIORNARE LA TABELLA MOSTRAKEYWORD)*/
				/*Modifico la tabella keyword(contente tutte le parole chiave associate ad ogni prodotto). Per fare ciò, ho utilizzato una procedura diversa dalle precedenti modifiche. 
				 * In pratica, non definisco il metodo aggiornaKeywordMap in questa classe ma, lo definisco nella classe ServletMostraKeyword e lo richiamo su un oggetto che istanzia la classe ServletMostraKeyword*/
				ServletMostraKeywordMancanti servlet = (ServletMostraKeywordMancanti) context.getAttribute("ServletMostraKeywordMancanti");
				if(servlet != null){
					HashMap<String, String> keywordMancantiMap = new HashMap<String, String>();
					servlet.aggiornaKeywordMancantiMap(keywordMancantiMap);
					context.setAttribute("keywordMancantiMap", keywordMancantiMap);
				}
				
			}
			else{
				messaggio = "Parola chiave non inserite!!! Controllare che non sia già presente!!!";
			}
									
		}
		else{
			messaggio = "Inserire almeno una parole chiave!!!";
		}
		
		request.setAttribute("messaggio", messaggio);
		request.getRequestDispatcher("/prodotti.jsp").forward(request, response);
		
	}

	
}
