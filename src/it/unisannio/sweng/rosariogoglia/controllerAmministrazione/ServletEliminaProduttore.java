package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;


import it.unisannio.sweng.rosariogoglia.dao.CategoriaDao;
import it.unisannio.sweng.rosariogoglia.dao.ProduttoreDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.CategoriaDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.daoImpl.ProduttoreDaoMysqlJdbc;
import it.unisannio.sweng.rosariogoglia.model.Categoria;
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
 * Servlet che permette all'amministratore di eliminare un produttore presente nel database
 */
//@WebServlet("/ServletEliminaProduttore")
public class ServletEliminaProduttore extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		ServletContext context = this.getServletConfig().getServletContext();
		
		Integer idProduttore = Integer.parseInt(request.getParameter("idProduttore"));
		
		System.out.println("idproduttore: " + idProduttore);
		
		String messaggio = "";
		
		ProduttoreDao daoP = new ProduttoreDaoMysqlJdbc();
		
		/*Se esiste un produttore con l'id indicato, effettuo l'eliminazione */
		if(idProduttore != 0 && idProduttore != null){
		
			Produttore produttore = daoP.getProduttoreById(idProduttore);
			
			//controllare se il produttore può essere eliminato. Non deve avere ne prodotti e ne inserzioni
			if(daoP.checkDeleteProduttore(idProduttore)){
			
				int deletedRows = daoP.deleteProduttore(idProduttore);
				
				if(deletedRows == 1){
					messaggio = "Produttore " + produttore.getNome() + " eliminato correttamente!!!";
				}
				else{
					messaggio = "Produttore " + produttore.getNome() + " non eliminato correttamente!!!";
				}
				
				/* Aggiorno il parametro listaCategorie in seguito all'aggiunta della produttore appena inserita */ 
				List<Produttore> listaProduttori = daoP.getProduttori();
				context.setAttribute("listaProduttori", listaProduttori);
				System.out.println("PRODUTTORI CARICATI E AGGIUNTI ALLO SCOPE");
				
				
				Map<String, String> produttoriMancantiMap = new HashMap<String,String>();
				//il metodo effettua la modifica sulla tabella anche se non ha un return, perchè la tabella è un tipi NON primitivo, quindi il passaggio avviene per riferimento e non per valore!!!
				this.aggiornaTabellaProduttoriMancanti(produttoriMancantiMap);
				context.setAttribute("produttoriMancantiMap", produttoriMancantiMap);
				
				/* modifico anche la tabella dei produttori relativi ad ogni categoria presente nel contesto!!! Tale modifica è necessaria per avere la tabella aggiornata*/
				Map<String, String> produttoriMap = new HashMap<String,String>();
				this.aggiornaTabellaProduttori(produttoriMap);
				context.setAttribute("produttoriMap", produttoriMap);
					
				
				
				
				
				
				
			}
			else{
				messaggio = "Non è possibile eliminare un produttore a cui sono associati prodotti presenti in inserzioni!!!";
			}
			
			
		}
		else {
			messaggio = "Nessuna produttore selezionato!!! Seleziona il produttore da eliminare!!!";
		}	
						
		request.setAttribute("messaggio", messaggio);
		request.getRequestDispatcher("/produttori.jsp").forward(request, response);
	  	
	
		
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

	
	

}
