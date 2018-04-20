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
import it.unisannio.sweng.rosariogoglia.modelImpl.CategoriaImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.ProdottoImpl;
import it.unisannio.sweng.rosariogoglia.modelImpl.ProduttoreImpl;
import it.unisannio.sweng.rosariogoglia.utility.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.helpers.AjaxXmlBuilder;

/**
 * Servlet implementation class ServletModificaProdotto
 */
//@WebServlet("/ServletModificaProdotto")
public class ServletModificaProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//prodotto da modificare
		String idProdottoString = request.getParameter("prodotto");
		
		System.out.println("idprodotto: " + idProdottoString);
		
		//parametri da modificare
		String nomeProdotto = request.getParameter("nomeProdotto");
		String idCategoria = request.getParameter("categoria");
		String idProduttore = request.getParameter("produttore");
		
		System.out.println("idCategoria: " + idCategoria);
		System.out.println("idProduttore: " + idProduttore);
		System.out.println("nomeProdotto: " + nomeProdotto);
					
		
		String messaggio = "";
		int updatedRows = -1;
		
		if(idProdottoString != null && !idProdottoString.equals("0")){
		
			Integer idProdotto = Integer.parseInt(idProdottoString);
			
			ProdottoDao daoP = new ProdottoDaoMysqlJdbc();
			Prodotto prodotto = daoP.getProdottoById(idProdotto);
								
			if(nomeProdotto != null && nomeProdotto != ""){
				
				if(Utility.hasSpecialChars(nomeProdotto)){
					messaggio="Errore !!! Non sono permessi caratteri speciali !!!";
					request.setAttribute("messaggio", messaggio);
					request.getRequestDispatcher("/prodotti.jsp").forward(request, response);
					return;
				}
				
				prodotto.setNome(nomeProdotto);
			}
			
			if(idCategoria != null && idCategoria != "" && !idCategoria.equals("0") && idProduttore != null && idProduttore != "" && !idProduttore.equals("0")){
				System.out.println("caso in cui sia categoria che produttore vengono modificati");
						
				
				Categoria categoria = new CategoriaImpl();
				CategoriaDao daoC = new CategoriaDaoMysqlJdbc();
				categoria = daoC.getCategoriaById(Integer.parseInt(idCategoria));
				System.out.println("categoria: " + categoria.getNome());
				
				prodotto.setIdCategoria(Integer.parseInt(idCategoria));
				prodotto.setCategoria(categoria);
				System.out.println("categoria del prodotto: " + prodotto.getCategoria().getNome());
				
				
				Produttore produttore = new ProduttoreImpl();
				ProduttoreDao daoProd = new ProduttoreDaoMysqlJdbc();
				produttore = daoProd.getProduttoreById(Integer.parseInt(idProduttore));
				
				prodotto.setIdProduttore(Integer.parseInt(idProduttore));
				prodotto.setProduttore(produttore);
				System.out.println("produttore del prodotto: " + prodotto.getProduttore().getNome());
				
				
				updatedRows = daoP.updateProdotto(prodotto);
				System.out.println("Prodotto aggiornato con tutti i campi");
				
				ServletContext context = getServletContext();
				
				if(!daoC.checkAssociazioneCategoriaProduttore(Integer.parseInt(idCategoria), Integer.parseInt(idProduttore))){
					
					System.out.println("entro per associare produttore e categoria");
					
					int insertRow = daoC.insertCategoriaHasProduttore(Integer.parseInt(idCategoria), Integer.parseInt(idProduttore));
										
					if(insertRow != -1){
						
						/*modifico la tabella dei produttori mancanti relativi ad ogni categoria presente nel contesto!!! Tale modifica è necessaria per avere la tabella aggiornata in caso di futura associazione*/
						
						Map<String, String> produttoriMancantiMap = new HashMap<String,String>();
						//il metodo effettua la modifica sulla tabella anche se non ha un return, perchè la tabella è un tipi NON primitivo, quindi il passaggio avviene per riferimento e non per valore!!!
						this.aggiornaTabellaProduttoriMancanti(produttoriMancantiMap);
						context.setAttribute("produttoriMancantiMap", produttoriMancantiMap);
						
						/* modifico anche la tabella dei produttori relativi ad ogni categoria presente nel contesto!!! Tale modifica è necessaria per avere la tabella aggiornata*/
						Map<String, String> produttoriMap = new HashMap<String,String>();
						this.aggiornaTabellaProduttori(produttoriMap);
						context.setAttribute("produttoriMap", produttoriMap);
						
					}
					
				}
				
				Map<String, String> prodottiMap = new HashMap<String,String>();
				//il metodo effettua la modifica sulla tabella anche se non ha un return, perchè la tabella è un tipi NON primitivo, quindi il passaggio avviene per riferimento e non per valore!!!
				this.aggiornaTabellaProdotti(prodottiMap);
				context.setAttribute("prodottiMap", prodottiMap);
				
				Map<String, String> mostraProdottiMap = new HashMap<String,String>();
				//il metodo effettua la modifica sulla tabella anche se non ha un return, perchè la tabella è un tipi NON primitivo, quindi il passaggio avviene per riferimento e non per valore!!!
				this.aggiornaTabellaMostraProdotti(mostraProdottiMap);
				context.setAttribute("mostraProdottiMap", mostraProdottiMap);
				
			
			}
			else if(idCategoria != null && idCategoria != "" && !idCategoria.equals("0")){
				System.out.println("caso in cui solo categoria viene modificata");
			
				Categoria categoria = new CategoriaImpl();
				CategoriaDao daoC = new CategoriaDaoMysqlJdbc();
				categoria = daoC.getCategoriaById(Integer.parseInt(idCategoria));
				System.out.println("categoria: " + categoria.getNome());
				
				prodotto.setIdCategoria(Integer.parseInt(idCategoria));
				prodotto.setCategoria(categoria);
				System.out.println("categoria del prodotto: " + prodotto.getCategoria().getNome());
				
				updatedRows = daoP.updateProdotto(prodotto);
				System.out.println("Prodotto aggiornato con tutti i campi");
				
				ServletContext context = getServletContext();
				
				if(!daoC.checkAssociazioneCategoriaProduttore(Integer.parseInt(idCategoria), prodotto.getIdProduttore())){
					
					System.out.println("entro per associare produttore e categoria");
					
					int insertRow = daoC.insertCategoriaHasProduttore(Integer.parseInt(idCategoria), prodotto.getIdProduttore());
										
					if(insertRow != -1){
						
						/*modifico la tabella dei produttori mancanti relativi ad ogni categoria presente nel contesto!!! Tale modifica è necessaria per avere la tabella aggiornata in caso di futura associazione*/
						
						Map<String, String> produttoriMancantiMap = new HashMap<String,String>();
						//il metodo effettua la modifica sulla tabella anche se non ha un return, perchè la tabella è un tipi NON primitivo, quindi il passaggio avviene per riferimento e non per valore!!!
						this.aggiornaTabellaProduttoriMancanti(produttoriMancantiMap);
						context.setAttribute("produttoriMancantiMap", produttoriMancantiMap);
						
						/* modifico anche la tabella dei produttori relativi ad ogni categoria presente nel contesto!!! Tale modifica è necessaria per avere la tabella aggiornata*/
						Map<String, String> produttoriMap = new HashMap<String,String>();
						this.aggiornaTabellaProduttori(produttoriMap);
						context.setAttribute("produttoriMap", produttoriMap);
						
					}
					
				}
				
				Map<String, String> prodottiMap = new HashMap<String,String>();
				//il metodo effettua la modifica sulla tabella anche se non ha un return, perchè la tabella è un tipi NON primitivo, quindi il passaggio avviene per riferimento e non per valore!!!
				this.aggiornaTabellaProdotti(prodottiMap);
				context.setAttribute("prodottiMap", prodottiMap);
				
				Map<String, String> mostraProdottiMap = new HashMap<String,String>();
				//il metodo effettua la modifica sulla tabella anche se non ha un return, perchè la tabella è un tipi NON primitivo, quindi il passaggio avviene per riferimento e non per valore!!!
				this.aggiornaTabellaMostraProdotti(mostraProdottiMap);
				context.setAttribute("mostraProdottiMap", mostraProdottiMap);
				
				
				
			
			}
			else if(idProduttore != null && idProduttore != "" && !idProduttore.equals("0")){
				System.out.println("caso in cui solo produttore viene modificato");
			
				CategoriaDao daoC = new CategoriaDaoMysqlJdbc();//utilizzato solo per invocare il metodo checkAssociazioneCategoriaProduttore
				
				Produttore produttore = new ProduttoreImpl();
				ProduttoreDao daoProd = new ProduttoreDaoMysqlJdbc();
				produttore = daoProd.getProduttoreById(Integer.parseInt(idProduttore));
				
				prodotto.setIdProduttore(Integer.parseInt(idProduttore));
				prodotto.setProduttore(produttore);
				System.out.println("produttore del prodotto: " + prodotto.getProduttore().getNome());
				
				
				updatedRows = daoP.updateProdotto(prodotto);
				System.out.println("Prodotto aggiornato con tutti i campi");
				
				ServletContext context = getServletContext();
				
				if(!daoC.checkAssociazioneCategoriaProduttore(prodotto.getIdCategoria(), Integer.parseInt(idProduttore))){
					
					System.out.println("entro per associare produttore e categoria");
					
					int insertRow = daoC.insertCategoriaHasProduttore(prodotto.getIdCategoria(), Integer.parseInt(idProduttore));
										
					if(insertRow != -1){
						
						/*modifico la tabella dei produttori mancanti relativi ad ogni categoria presente nel contesto!!! Tale modifica è necessaria per avere la tabella aggiornata in caso di futura associazione*/
						
						Map<String, String> produttoriMancantiMap = new HashMap<String,String>();
						//il metodo effettua la modifica sulla tabella anche se non ha un return, perchè la tabella è un tipi NON primitivo, quindi il passaggio avviene per riferimento e non per valore!!!
						this.aggiornaTabellaProduttoriMancanti(produttoriMancantiMap);
						context.setAttribute("produttoriMancantiMap", produttoriMancantiMap);
						
						/* modifico anche la tabella dei produttori relativi ad ogni categoria presente nel contesto!!! Tale modifica è necessaria per avere la tabella aggiornata*/
						Map<String, String> produttoriMap = new HashMap<String,String>();
						this.aggiornaTabellaProduttori(produttoriMap);
						context.setAttribute("produttoriMap", produttoriMap);
						
					}
					
				}
				
				Map<String, String> prodottiMap = new HashMap<String,String>();
				//il metodo effettua la modifica sulla tabella anche se non ha un return, perchè la tabella è un tipi NON primitivo, quindi il passaggio avviene per riferimento e non per valore!!!
				this.aggiornaTabellaProdotti(prodottiMap);
				context.setAttribute("prodottiMap", prodottiMap);
				
				Map<String, String> mostraProdottiMap = new HashMap<String,String>();
				//il metodo effettua la modifica sulla tabella anche se non ha un return, perchè la tabella è un tipi NON primitivo, quindi il passaggio avviene per riferimento e non per valore!!!
				this.aggiornaTabellaMostraProdotti(mostraProdottiMap);
				context.setAttribute("mostraProdottiMap", mostraProdottiMap);
				
			
			}
			else{
				System.out.println("caso in cui solo il nome viene modificato");
				
				updatedRows = daoP.updateNomeProdotto(prodotto);

				ServletContext context = getServletContext();
				
				Map<String, String> prodottiMap = new HashMap<String,String>();
				//il metodo effettua la modifica sulla tabella anche se non ha un return, perchè la tabella è un tipi NON primitivo, quindi il passaggio avviene per riferimento e non per valore!!!
				this.aggiornaTabellaProdotti(prodottiMap);
				context.setAttribute("prodottiMap", prodottiMap);
				
				Map<String, String> mostraProdottiMap = new HashMap<String,String>();
				//il metodo effettua la modifica sulla tabella anche se non ha un return, perchè la tabella è un tipi NON primitivo, quindi il passaggio avviene per riferimento e non per valore!!!
				this.aggiornaTabellaMostraProdotti(mostraProdottiMap);
				context.setAttribute("mostraProdottiMap", mostraProdottiMap);			
			}
			
			
			if(updatedRows != -1){
				messaggio = "Prodotto " + prodotto.getNome() + " aggiornata correttamente!!!";
			}
			else{
				messaggio = "Prodotto " + prodotto.getNome() + " non aggiornata!!!";
			}
		
		}
		
		else {
			messaggio = "Nessuna prodotto selezionato!!! Seleziona il prodotto da modificare!!!";
		}	
		
		
		request.setAttribute("messaggio", messaggio);
		request.getRequestDispatcher("/prodotti.jsp").forward(request, response);
 	
	}

	
	
	
	
	
	
	public String makeProduttoriList(ArrayList<Produttore> produttori) {
	    AjaxXmlBuilder builder = new AjaxXmlBuilder();
	    for(Produttore produttore: produttori) {
	      builder.addItem(produttore.getNome(), String.valueOf(produttore.getIdProduttore()));
	    }
	    return(builder.toString());
	}
	
	
	
	public void aggiornaTabellaProduttoriMancanti(Map<String, String> produttoriMancantiMap ){
		CategoriaDao categoriaDao = new CategoriaDaoMysqlJdbc();
	    for(Categoria categoria: categoriaDao.getCategorie()){
	      produttoriMancantiMap.put(String.valueOf(categoria.getIdCategoria()), makeProduttoriList((ArrayList<Produttore>)categoriaDao.getProduttoriMancantiByIdCategoria(categoria.getIdCategoria())));
	    }
	    AjaxXmlBuilder builder = new AjaxXmlBuilder();
	    produttoriMancantiMap.put(String.valueOf(0), (builder.addItem("Seleziona produttore", String.valueOf(0))).toString());
	   
	}
	
	
	
	public void aggiornaTabellaProduttori(Map<String, String> produttoriMap ){
		CategoriaDao categoriaDao = new CategoriaDaoMysqlJdbc();
	    	for(Categoria categoria: categoriaDao.getCategorie()) {
	    		produttoriMap.put(String.valueOf(categoria.getIdCategoria()), makeProduttoriList((ArrayList<Produttore>)categoria.getListaProduttori()));
	    	}
	    	AjaxXmlBuilder builder = new AjaxXmlBuilder();
	        produttoriMap.put(String.valueOf(0), (builder.addItem("Seleziona produttore", String.valueOf(0))).toString());
	        
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


	 private String makeProdottiList(ArrayList<Prodotto> prodotti) {
		    AjaxXmlBuilder builder = new AjaxXmlBuilder();
		    for(Prodotto prodotto: prodotti) {
		      builder.addItem(prodotto.getNome(), String.valueOf(prodotto.getIdProdotto()));
		    }
		    return(builder.toString());
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

