package it.unisannio.sweng.rosariogoglia.manager_acquisti;

import java.util.Random;

public class SimulatoreAcquisto {

	
	/*Il metodo simula il controllo pagamento. Restituisce un numero casuale compreso da 0 a 10. In caso di numero minore di 8 il pagamento andrà a buon fine, in caso di numero maggiore di 10 il pagamento non sarà convalidato*/
    public boolean pagamento () {
        boolean result = false;
        //int numCasuale = (int) Math.random() * 10;
        Random r = new Random();
        int numCasuale =  r.nextInt(10);
        if (numCasuale < 8){
            result = true;
        }
        return result;
    }
   
}