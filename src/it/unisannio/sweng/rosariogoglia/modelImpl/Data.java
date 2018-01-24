package it.unisannio.sweng.rosariogoglia.modelImpl;

import java.util.StringTokenizer;

public class Data {
	
	public Data(int giorno, int mese, int anno) {
		this.giorno = giorno;
		this.mese = mese;
		this.anno = anno;
	}
	
	public Data(String data){
		StringTokenizer tokenizer = new StringTokenizer(data,"/");
		this.giorno = Integer.parseInt(tokenizer.nextToken());
		this.mese = Integer.parseInt(tokenizer.nextToken());
		this.anno = Integer.parseInt(tokenizer.nextToken());
	
	}
	
	public int getGiorno() {
		return giorno;
	}
	public void setGiorno(int giorno) {
		this.giorno = giorno;
	}
	public int getMese() {
		return mese;
	}
	public void setMese(int mese) {
		this.mese = mese;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}

	public boolean equals(Data data){
		return( this.giorno == data.getGiorno() &&
				this.mese == data.getMese() &&
				this.anno == data.getAnno());
	}
	
	public String toString(){
		return (this.giorno + "/" + this.mese + "/" + this.anno);
	}

	private int giorno;
	private int mese;
	private int anno;
	
	
}