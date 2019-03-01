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
	public void test() {
		
		
		CategoriaDao categoriaDao = new CategoriaDaoMysqlJdbc();
		Categoria categoria = new CategoriaImpl();
		Integer idCategoria = -1;
		
		categoria.setNome("Test Categoria nuova");
		idCategoria = categoriaDao.insertCategoria(categoria);
		categoria.setIdCategoria(idCategoria);
		
		System.out.println("Categoria Inserita");
		
		System.out.println("La categoria inserita è" + categoriaDao.getCategoriaById(idCategoria));
		
		Categoria readingCategoria = categoriaDao.getCategoriaById(idCategoria);
		
		System.out.println("readingCategoria : " + readingCategoria.getIdCategoria() + " " + readingCategoria.getNome() );
		
		assertEquals(readingCategoria.getIdCategoria(), categoria.getIdCategoria()); //verifico che la categoria letta con il metodo getCategoriaById è realmente quella inserita nel database
		
		
		List<Categoria> listaCategoria = categoriaDao.getCategorie();
		
		for(int i=0;i<listaCategoria.size();i++) {
			
			System.out.println("Categoria " + i + " " + listaCategoria.get(i).getNome());
			
		}
		
		List<Produttore> listaProdMancanti = categoriaDao.getProduttoriMancantiByIdCategoria(idCategoria);
		
		for(int i=0;i<listaProdMancanti.size();i++) {
			
			System.out.println("Categoria " + i + " " + listaProdMancanti.get(i).getNome());
			
		}
		
		ProduttoreDao produttoreDao = new ProduttoreDaoMysqlJdbc();
		List<Produttore> listaTuttiProduttori = produttoreDao.getProduttori();
		
		assertEquals(listaTuttiProduttori.size(), listaProdMancanti.size());
		
		
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
		
		//associa un produttore ad una categoria
		result = categoriaDao.insertCategoriaHasProduttore(3 , 9);
		assertEquals(result, (Integer) 1) ;
		
		//disassocia un produttore ad una categoria
		categoriaDao.deleteCategoriaHasProduttore(3 , 9);
		assertEquals(result, (Integer) 1) ;


		
	}
	
	
	
}
