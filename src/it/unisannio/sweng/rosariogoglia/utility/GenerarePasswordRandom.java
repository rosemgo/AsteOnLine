package it.unisannio.sweng.rosariogoglia.utility;

import java.util.Random;

public class GenerarePasswordRandom {

	static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static Random rnd = new Random(System.currentTimeMillis());
    static private final int LENGHT = 8;
	 
    public static String generate() {
        StringBuilder sb = new StringBuilder(LENGHT);
        //char[] prova = new char[LENGHT];
        for (int i = 0; i < LENGHT; i++) {
            sb.append(ALPHABET.charAt(rnd.nextInt(ALPHABET.length())));
            //prova[i] = ALPHABET.charAt(rnd.nextInt(ALPHABET.length()));
        }
        //String result = new String(prova);
        return sb.toString();
    }

	
}
