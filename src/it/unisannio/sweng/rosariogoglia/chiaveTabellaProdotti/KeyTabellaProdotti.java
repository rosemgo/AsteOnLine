package it.unisannio.sweng.rosariogoglia.chiaveTabellaProdotti;


/**
 * Questa classe è usata per creare una chiave per la HashMap "prodottoMap" definita in ServletCercaProdotti, ed usata per associare tutti i prodotti appartentendi ad un produttore in una particolare categoria.
 * In pratica, il produttore Samsung nella categoria Elettronica, avrà dei prodotti; invece in un'altra categoria avrà altri prodotti.
 * @author Rosario
 *
 */

public class KeyTabellaProdotti {
	
	public KeyTabellaProdotti(Integer idCategoria, Integer idProduttore) {
		super();
		this.idCategoria = idCategoria;
		this.idProduttore = idProduttore;
	}
	
	public Integer getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}
	public Integer getIdProduttore() {
		return idProduttore;
	}
	public void setIdProduttore(Integer idProduttore) {
		this.idProduttore = idProduttore;
	}

	public String toString(){
		return(this.idCategoria +"-"+this.idProduttore);
	}
	
	private Integer idCategoria;
	private Integer idProduttore;

}
