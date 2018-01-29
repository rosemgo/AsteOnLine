package it.unisannio.sweng.rosariogoglia.manager_acquisti;

public class ValidatoreCarta {
	
	
	public ValidatoreCarta() {
        // TODO Auto-generated constructor stub
    }
   
    public boolean isCodiceCorretto (String numero) {
    	
    	boolean isValido = false; 
    	
    	if(numero.length() == 16){
    	
	    	int[] cifre = new int[16];
	    	
	    	for(int i=0; i<16; i++){
	    		cifre[i] = Integer.parseInt(String.valueOf(numero.charAt(i))); //converto ogni carattere del codice carta di credito intero
	    	}
	    	printString(numero);
           
	    	
            //applico l'algoritmo di Luhn
	    	
	    	//1- Raddoppio tutte le cifre in posizione pari partendo da destra (oppure partendo da sinistra raddoppio le cifre in posizione dispari)
	    	for(int i=0; i<16; i=i+2){
	    		cifre[i] *= 2;
	    	}
	    	  printArray(cifre);
	    	
	    	
	    	//2- In caso una delle moltiplicazioni porti a numeri di due cifre queste vanno sommate per ottenerne una sola
	    	for(int i=0; i<16; i++){
	    		if(cifre[i] > 9){
	    			String num = cifre[i] + "";
	    			int cifra1 = Integer.parseInt(String.valueOf(num.charAt(0)));
	    			int cifra2 = Integer.parseInt(String.valueOf(num.charAt(1)));
	    			
	    			int tot = cifra1 + cifra2;
	    			cifre[i] = tot;
	      		}
	    	}
	    	  printArray(cifre);
	    	  
	    	  
	    	//3- Si sommano tutte le cifre ottenute
	    	int somma = 0;
	    	for(int i=0; i<16; i++){
	    		somma = somma + cifre[i];
	    	}
	    	 
	    	System.out.println("somma: " + somma);
	    	
	    	
	    	//4- Si divide la somma ottenuta per 10: se il resto è 0 la carta di credito sarà valida, in caso contrario non sarà valida 
	    	isValido = ((somma%10)==0);
    	
    	}
    	
    	
    	return isValido;
    }
	
    public void printString(String numero){
    	
    	for(int i=0; i<16; i++){
    		System.out.print(" | " + numero.charAt(i));
    	}
    	System.out.println("");
    }
	
    public void printArray(int[] numero){
    	
    	for(int i=0; i<16; i++){
    		System.out.print(" | " + numero[i]);
    	}
    	 System.out.println("");
    }
	
    	
	
	

}
