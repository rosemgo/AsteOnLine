package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;

import it.unisannio.sweng.rosariogoglia.chiaveTabellaProdotti.KeyTabellaProdotti;
import it.unisannio.sweng.rosariogoglia.dao.CategoriaDao;
import it.unisannio.sweng.rosariogoglia.dao.ProdottoDao;
import it.unisannio.sweng.rosariogoglia.dao.ProduttoreDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.CategoriaDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProdottoDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProduttoreDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Categoria;
import it.unisannio.sweng.rosariogoglia.model.Prodotto;
import it.unisannio.sweng.rosariogoglia.model.Produttore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.helpers.AjaxXmlBuilder;

/**
 * Servlet che permette all'amministratore di eliminare un prodotto presente nel database
 */
//@WebServlet("/ServletEliminaProdotto")
public class ServletEliminaProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = this.getServletConfig().getServletContext();
		
		String idCategoriaSring = request.getParameter("categoria");
		String idProduttoreString = request.getParameter("produttore");
		String idProdottoString = request.getParameter("prodotto");
		
		System.out.println("idcategoria: " + idCategoriaSring);
		
		System.out.println("idproduttore: " + idProduttoreString);
		
		System.out.println("idprodotto: " + idProdottoString);
		
		String messaggio = "";
		
		ProdottoDao daoP = new ProdottoDaoMysqlJdbc();
		
		/*Se esiste una categoria con l'id indicato, effettuo l'eliminazione */
		if(!idCategoriaSring.equals("0") && idCategoriaSring != null && !idProduttoreString.equals("0") && idProduttoreString != null && !idProdottoString.equals("0") && idProdottoString != null){
		
			Integer idCategoria = Integer.parseInt(idCategoriaSring);
			Integer idProduttore = Integer.parseInt(idProduttoreString);
			Integer idProdotto= Integer.parseInt(idProdottoString);
			
			Prodotto prodotto = daoP.getProdottoById(idProdotto);
			
			//controllare se il prodotto può essere eliminata. Non devono esserci inserzioni con quel prodotto
			if(daoP.checkDeleteProdotto(idProdotto)){
			
				int deletedRows = daoP.deleteProdotto(prodotto);
				
				if(deletedRows == 1){
					messaggio = "Prodotto " + prodotto.getNome() + " eliminato correttamente!!!";
				}
				else{
					messaggio = "Prodotto " + prodotto.getNome() + " non eliminato correttamente!!!";
				}
				
				/* Aggiorno il parametro listaProdotti in seguito all'eliminazione del prodotto appena inserito */ 
				List<Prodotto> listaProdotti = daoP.getProdotti(); 
				context.setAttribute("listaProdotti", listaProdotti);
				System.out.println("PRODOTTI CARICATI E AGGIUNTI ALLO SCOPE");
								
				  
			    /*Aggiorno nel contesto la tabella prodottoMap che associa la lista di prodotti alla coppia categoria-produttore*/
				Map<String, String> prodottiMap = new HashMap<String,String>();
				//il metodo effettua la modifica sulla tabella anche se non ha un return, perchè la tabella è un tipi NON primitivo, quindi il passaggio avviene per riferimento e non per valore!!!
				this.aggiornaTabellaProdotti(prodottiMap);
				context.setAttribute("prodottiMap", prodottiMap);
				
				/*Aggiorno nel contesto la tabella mostraProdottiMap che associa la lista di prodotti alla coppia categoria-produttore*/
				Map<String, String> mostraProdottiMap = new HashMap<String,String>();
				//il metodo effettua la modifica sulla tabella anche se non ha un return, perchè la tabella è un tipi NON primitivo, quindi il passaggio avviene per riferimento e non per valore!!!
				this.aggiornaTabellaMostraProdotti(mostraProdottiMap);
				context.setAttribute("mostraProdottiMap", mostraProdottiMap);
									
				
				
			}
			else{
				messaggio = "Non è possibile eliminare un prodotto con inserzioni associate!!!";
			}
			
		}
		else {
			messaggio = "Nessun prodotto selezionato!!! Seleziona il prodotto da eliminare!!!";
		}	
						
		request.setAttribute("messaggio", messaggio);
		request.getRequestDispatcher("/prodotti.jsp").forward(request, response);
	  	
	}
	
	
	
	
	
	

	/*Aggiorno la tabella dei prodotti utilizzati dall'utente nell'inserimento inserzione*/
	private void aggiornaTabellaProdotti(Map<String, String> prodottiMap) {
		 	
			CategoriaDao categoriaDao = new CategoriaDaoMysqlJdbc();
		    ProduttoreDao produttoreDao = new ProduttoreDaoMysqlJdbc();
		    ProdottoDao prodottoDao = new ProdottoDaoMysqlJdbc();
		    
		    KeyTabellaProdotti keyMap;
		    List<Prodotto> listaProdotti;
		    
		    /* Scorro tutte le categorie, e per ogni categoria scorro tutti i produttori, in modo tale da poter inizializzare la tabella secondo la coppia categoria-prodotto --> lista produtti.
		     * in pratica la tabella, usata per la scelta dei prodotti, conterrà come "valore" la lista di prodotti associtai alla coppia "chiave" categoria-produttore.
		     * Questo perchè, ad ogni categoria appartengono dei produttori, e ad ogni coppia categoria-produttore appartengono solo una lista di prodotti relativi a quella categoria e a quel produttore (quindi nn consideriamo tutti i prodotti di quel produttore!!!)
		     */
		    for(int i=0; i < categoriaDao.getCategorie().size(); i++){
			    
		    	Categoria categoria = categoriaDao.getCategorie().get(i);
		    			
		    	for(int z=0; z < produttoreDao.getProduttoriByIdCategoria(categoria.getIdCategoria()).size(); z++){
			    	
		    		Produttore produttore = produttoreDao.getProduttoriByIdCategoria(categoria.getIdCategoria()).get(z);
		    		
			    	keyMap = new KeyTabellaProdotti(categoria.getIdCategoria(), produttore.getIdProduttore());
			    	
			    	listaProdotti = prodottoDao.getProdottiByIdCategoriaByIdProduttore(categoria.getIdCategoria(), produttore.getIdProduttore());
			    	
			    	System.out.println("chiave: " + keyMap.toString());
			    	System.out.println("valore: " + makeProdottiList( (ArrayList<Prodotto>) listaProdotti ));
			    	
			    	prodottiMap.put(keyMap.toString(), makeProdottiList( (ArrayList<Prodotto>) listaProdotti ));
			    	
			    	System.out.println("elemento "+z+":" + prodottiMap.get(String.valueOf(keyMap)));
				    
			    }
		    	
		    }
		    keyMap = new KeyTabellaProdotti(0, 0);
		    AjaxXmlBuilder builder = new AjaxXmlBuilder();
		    prodottiMap.put(keyMap.toString(), builder.addItem("Seleziona prodotto", String.valueOf(0)).toString() );
	    	
	}


	
	
	 /*Aggiorno la tabella dei prodotti da mostrare nella sezione "prodotti" dell'amministrazione!!!*/
	 private void aggiornaTabellaMostraProdotti(Map<String, String> mostraProdottiMap) {


		  	CategoriaDao categoriaDao = new CategoriaDaoMysqlJdbc();
		    ProduttoreDao produttoreDao = new ProduttoreDaoMysqlJdbc();
		    ProdottoDao prodottoDao = new ProdottoDaoMysqlJdbc();
		    
		    KeyTabellaProdotti keyMap;
		    List<Prodotto> listaProdotti;
		    
		    /* Scorro tutte le categorie, e per ogni categoria scorro tutti i produttori, in modo tale da poter inizializzare la tabella secondo la coppia categoria-prodotto --> lista produtti.
		     * in pratica la tabella, usata per la scelta dei prodotti, conterrà come "valore" la lista di prodotti associtai alla coppia "chiave" categoria-produttore.
		     * Questo perchè, ad ogni categoria appartengono dei produttori, e ad ogni coppia categoria-produttore appartengono solo una lista di prodotti relativi a quella categoria e a quel produttore (quindi nn consideriamo tutti i prodotti di quel produttore!!!)
		     */
		    for(int i=0; i < categoriaDao.getCategorie().size(); i++){
			    
		    	Categoria categoria = categoriaDao.getCategorie().get(i);
		    			
		    	for(int z=0; z < produttoreDao.getProduttoriByIdCategoria(categoria.getIdCategoria()).size(); z++){
			    	
		    		Produttore produttore = produttoreDao.getProduttoriByIdCategoria(categoria.getIdCategoria()).get(z);
		    		
			    	keyMap = new KeyTabellaProdotti(categoria.getIdCategoria(), produttore.getIdProduttore());
			    	
			    	listaProdotti = prodottoDao.getProdottiByIdCategoriaByIdProduttore(categoria.getIdCategoria(), produttore.getIdProduttore());
			    	
			    	System.out.println("chiave: " + keyMap.toString());
			    	System.out.println("valore: " + makeMostraProdottiList( (ArrayList<Prodotto>) listaProdotti ));
			    	
			    	mostraProdottiMap.put(keyMap.toString(), makeMostraProdottiList( (ArrayList<Prodotto>) listaProdotti ));
			    	
			    	System.out.println("elemento "+z+":" + mostraProdottiMap.get(String.valueOf(keyMap)));
				    
			    }
		    	
		    }
		    
		    keyMap = new KeyTabellaProdotti(0, 0);
		    mostraProdottiMap.put(keyMap.toString(), "Seleziona categoria e produttore");
	    			
	}
	  
	  
	  /*Creo la lista dei prodotti da visualizzare*/
	  private String makeMostraProdottiList(ArrayList<Prodotto> prodotti) {
		    String listaProdotti = "";
		    for(Prodotto prodotto: prodotti) {
		    	listaProdotti = listaProdotti + prodotto.getNome() + "<br />";
		    }
		    return(listaProdotti);
	  }

	  private String makeProdottiList(ArrayList<Prodotto> prodotti) {
		    AjaxXmlBuilder builder = new AjaxXmlBuilder();
		    for(Prodotto prodotto: prodotti) {
		      builder.addItem(prodotto.getNome(), String.valueOf(prodotto.getIdProdotto()));
		    }
		    return(builder.toString());
	 }
	
	
	
	
	
	
	
	
	
	
	
	
	


}
