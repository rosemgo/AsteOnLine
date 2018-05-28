package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;

import it.unisannio.sweng.rosariogoglia.dao.KeywordDao;
import it.unisannio.sweng.rosariogoglia.dao.ProdottoDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.KeywordDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProdottoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Keyword;
import it.unisannio.sweng.rosariogoglia.model.Prodotto;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletDisassociaProdottoKeyword
 */
//@WebServlet("/ServletDisassociaProdottoKeyword")
public class ServletDisassociaProdottoKeyword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		String idProdottoString = request.getParameter("prodotto");
		String word = request.getParameter("keyword");
		
		
		ProdottoDao daoP = new ProdottoDaoMysqlJdbc();
		KeywordDao daoK = new KeywordDaoMysqlJdbc();
		
		int deletedRow = -1;
		String messaggio = "";
		
		if((idProdottoString != null && !idProdottoString.equals("0")) && (word != null && !word.equals(""))){
			
			Integer idProdotto = Integer.parseInt(idProdottoString);
			
			Keyword keyword = daoK.getKeywordByWord(word);
			
			if(keyword != null){
				Integer idKeyword = keyword.getIdKeyword();
				
				Prodotto prodotto = daoP.getProdottoById(idProdotto);
				
				//Verifico che la parola chiave inserite sia effettivamente associata al prodotto
				if(daoP.checkProdottoHasKeyword(idProdotto, idKeyword)){
				
					deletedRow = daoP.deleteProdottoHasKeyword(idProdotto, idKeyword);
							
					
					if(deletedRow == 1){
						
						messaggio = "Prodotto '" + prodotto.getNome() + "' disassociato correttamente dalla keyword '" + keyword.getKeyword() + "'!!!";
						
						ServletContext context = getServletContext();
						
						/*Modifico la tabella keyword(contente tutte le parole chiave associate ad ogni prodotto). Per fare ciò, ho utilizzato una procedura diversa dalle precedenti modifiche. 
						 * In pratica, non definisco il metodo aggiornaKeywordMao in questa classe ma, lo definisco nella classe ServletMostraKeyword e lo richiamo su un oggetto che istanzia la classe ServletMostraKeyword*/
						ServletMostraKeyword servlet2 = (ServletMostraKeyword) context.getAttribute("ServletMostraKeyword");
						/*Se ServletMostraKeyword non è stato mai utilizzata e quindi non stato invocato il metodo init, non l'avrò ancora nel contesto*/
						HashMap<String, String> keywordMap = new HashMap<String, String>();
						servlet2.aggiornaKeywordMap(keywordMap);
						context.setAttribute("keywordMap", keywordMap);
						
						
						ServletMostraKeywordMancanti servlet = (ServletMostraKeywordMancanti) context.getAttribute("ServletMostraKeywordMancanti");
						/*ServletMostraKeyword non è stato mai utilizzata e quindi non stato invocato il metodo init, non l'avrò ancora nel contesto*/
						if(servlet != null){
							HashMap<String, String> keywordMancantiMap = new HashMap<String, String>();
							servlet.aggiornaKeywordMancantiMap(keywordMancantiMap);
							context.setAttribute("keywordMancantiMap", keywordMancantiMap);
						}
						
					}
					else{
						messaggio = "Prodotto '" + prodotto.getNome() + "' non disassociato correttamente alla parola chiave '" + keyword.getKeyword() + "'!!!";
					}
				
				}
				else{
					messaggio = "La disassociazione non è possibile, in quanto il prodotto '" + prodotto.getNome() + "' non è associato alla parola chiave '" + keyword.getKeyword() + "'!!!";
				}
				
				
			}
			else{
				messaggio = "La parola chiave inserita non è presente!!! Inserire una parola chiave presente nell'elenco!!!";
			}
		}
		else {
			messaggio = "Inserisci un prodotto ed una parola chiave validi!!!";
		}
					
		request.setAttribute("messaggio", messaggio);
		request.getRequestDispatcher("/prodotti.jsp").forward(request, response);
			
			
		}
}
