package it.unisannio.sweng.rosariogoglia.controllerAmministrazione;

import it.unisannio.sweng.rosariogoglia.dao.CategoriaDao;
import it.unisannio.sweng.rosariogoglia.daoImpl.CategoriaDaoMysqlJdbc;
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
 * Servlet che permette all'amministratore di eliminare una categoria presente nel database
 */
//@WebServlet("/ServletEliminaCategoria")
public class ServletEliminaCategoria extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext context = this.getServletConfig().getServletContext();
		
		Integer idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
		
		System.out.println("idcategoria: " + idCategoria);
		
		String messaggio = "";
		
		CategoriaDao daoC = new CategoriaDaoMysqlJdbc();
		
		/*Se esiste una categoria con l'id indicato, effettuo l'eliminazione */
		if(idCategoria != 0 && idCategoria != null){
		
			Categoria categoria = daoC.getCategoriaById(idCategoria);
			
			//controllare se la categoria può essere eliminata. Non deve avere ne prodotti e ne inserzioni
			if(daoC.checkDeleteCategoria(idCategoria)){
			
				int deletedRows = daoC.deleteCategoria(idCategoria);
				
				if(deletedRows != -1){
					messaggio = "Categoria " + categoria.getNome() + " eliminata correttamente!!!";
				}
				else{
					messaggio = "Categoria " + categoria.getNome() + " non eliminata correttamente!!!";
				}
				
				/* Aggiorno il parametro listaCategorie in seguito all'aggiunta della categoria appena inserita */ 
				List<Categoria> listaCategorie = daoC.getCategorie(); 
				context.setAttribute("listaCategorie", listaCategorie);
				System.out.println("CATEGORIE CARICATE E AGGIUNTE ALLO SCOPE");
				
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
				messaggio = "Non è possibile eliminare una categoria con prodotti ed inserzioni associati!!!";
			}
			
			
		}
		else {
			messaggio = "Nessuna categoria selezionata!!! Seleziona la categoria da eliminare!!!";
		}	
						
		request.setAttribute("messaggio", messaggio);
		request.getRequestDispatcher("/categorie.jsp").forward(request, response);
			
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
