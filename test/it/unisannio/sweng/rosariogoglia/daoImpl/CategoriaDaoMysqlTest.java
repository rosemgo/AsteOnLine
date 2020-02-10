package it.unisannio.sweng.rosariogoglia.daoImpl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.unisannio.sweng.rosariogoglia.dao.CategoriaDao;
import it.unisannio.sweng.rosariogoglia.dao.ProduttoreDao;
import it.unisannio.sweng.rosariogoglia.model.Categoria;
import it.unisannio.sweng.rosariogoglia.model.Produttore;
import it.unisannio.sweng.rosariogoglia.modelImpl.CategoriaImpl;

public class CategoriaDaoMysqlTest {

	@Test
	public void testInserimentoRimozioneCategoria() {
		
		
		CategoriaDao categoriaDao = new CategoriaDaoMysqlJdbc();
		Categoria categoria = new CategoriaImpl();
		Integer idCategoria = -1;
		Integer updateRows = -1;
		
		categoria.setNome("Test Categoria nuova");
		idCategoria = categoriaDao.insertCategoria(categoria);
		categoria.setIdCategoria(idCategoria);
		
		System.out.println("Categoria Inserita");
		System.out.println("La categoria inserita è: " + categoriaDao.getCategoriaById(idCategoria));
		
		Categoria readingCategoria = categoriaDao.getCategoriaById(idCategoria);
		
		System.out.println("readingCategoria : " + readingCategoria.getIdCategoria() + " " + readingCategoria.getNome() );
		
		assertEquals(readingCategoria.getIdCategoria(), categoria.getIdCategoria()); //verifico che la categoria letta con il metodo getCategoriaById è realmente quella inserita nel database
		
		//aggiorno il nome della categoria
		categoria.setNome("update Categoria");
		updateRows = categoriaDao.updateCategoria(categoria);
		assertEquals((Integer)1, updateRows);
		 
		
		//Leggo tutte le categorie presenti nel DB
		List<Categoria> listaCategoria = categoriaDao.getCategorie();
		
		for(int i=0;i<listaCategoria.size();i++) {
			
			System.out.println("Categoria: " + i + " " + listaCategoria.get(i).getNome());
			
		}
		
		List<Produttore> listaProdMancanti = categoriaDao.getProduttoriMancantiByIdCategoria(idCategoria);
		System.out.println("Stampa i produttori non ancora associati alla categoria appena inserita. Quindi devono essere tutti.");
		
		for(int i=0;i<listaProdMancanti.size();i++) {
			
			System.out.println("Produttore:  " + i + " " + listaProdMancanti.get(i).getNome());
			
		}
		
		//confronto tra tutti i produttori presenti nel db e quelli non ancora associati alla categoria appena inseriti (cioè tutti i produttori)
		ProduttoreDao produttoreDao = new ProduttoreDaoMysqlJdbc();
		List<Produttore> listaTuttiProduttori = produttoreDao.getProduttori();
		
		assertEquals(listaTuttiProduttori.size(), listaProdMancanti.size());
		
		//cancellazione della categoria inserita
		if(categoriaDao.checkDeleteCategoria(idCategoria)) {
			
			Integer del = categoriaDao.deleteCategoria(idCategoria);
			
			assertEquals(del, (Integer)1);
			
			System.out.println("Catgoria eliminata");
			
		}
		
	}
	
	@Test
	public void testAssociaDisassociaProduttore() {
		
		CategoriaDao categoriaDao = new CategoriaDaoMysqlJdbc(); 
		Integer result;
		boolean result2 = false;
		
		//associa un produttore ad una categoria
		result = categoriaDao.insertCategoriaHasProduttore(3 , 9);
		assertEquals(result, (Integer) 1) ;
		
		//controllo che il produttore è associato correttamente alla categoria
		result2 = categoriaDao.checkAssociazioneCategoriaProduttore(3, 9);
		assertEquals(true, result2);
		
		//disassocia un produttore ad una categoria
		categoriaDao.deleteCategoriaHasProduttore(3 , 9);
		assertEquals(result, (Integer) 1) ;
		
	}
	
	
	
}
