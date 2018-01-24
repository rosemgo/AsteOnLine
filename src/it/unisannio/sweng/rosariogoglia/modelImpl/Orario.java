package it.unisannio.sweng.rosariogoglia.modelImpl;

import java.util.StringTokenizer;

public class Orario {

	public Orario(int ora, int minuti, int secondi) {
		this.ora = ora;
		this.minuti = minuti;
		this.secondi = secondi;
	}
	
	public Orario(String orario){
		StringTokenizer tokenizer = new StringTokenizer(orario,":");
		this.ora = Integer.parseInt(tokenizer.nextToken());
		this.minuti = Integer.parseInt(tokenizer.nextToken());
		this.secondi = Integer.parseInt(tokenizer.nextToken());
	}
	
	public int getOra() {
		return ora;
	}
	public void setOra(int ora) {
		this.ora = ora;
	}
	public int getMinuti() {
		return minuti;
	}
	public void setMinuti(int minuti) {
		this.minuti = minuti;
	}
	public int getSecondi() {
		return secondi;
	}
	public void setSecondi(int secondi) {
		this.secondi = secondi;
	}

	public String toString(){
		return(this.ora + ":" + this.minuti + ":" + this.secondi);
	}


	private int ora;
	private int minuti;
	private int secondi;
	
	
}
