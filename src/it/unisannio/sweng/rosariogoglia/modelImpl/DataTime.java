package it.unisannio.sweng.rosariogoglia.modelImpl;

import java.util.StringTokenizer;

public class DataTime {
	
	public DataTime(Data data, Orario orario) {
		this.data = data;
		this.orario = orario;
	}
	
	public DataTime(String dataTime){
		StringTokenizer tokenizer = new StringTokenizer(dataTime, "--");
	
		String dataS = tokenizer.nextToken();
		this.data = new Data(dataS);
		
		String orarioS = tokenizer.nextToken();
		this.orario = new Orario(orarioS);
	
	}
	
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Orario getOrario() {
		return orario;
	}

	public void setOrario(Orario orario) {
		this.orario = orario;
	}

	public String toString(){
		return(this.data.toString() + "--" + this.orario.toString());
	}
	
	private Data data;
	private Orario orario;

}
