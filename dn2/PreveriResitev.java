package dn2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PreveriResitev {
	private final static long TIME_LIMIT = 10 * 1000;  // 10 seconds
		
	public static boolean preveri(long t0, long t1, String out, String resitve) {
		long deltaT = t1 - t0; 
		if (deltaT > TIME_LIMIT) {
			System.out.println("Naloga se je izvajala predolgo!");
			System.out.println("Dovoljen cas [ms]: " + TIME_LIMIT);
			//return false;
		} else {
			System.out.println("Naloga je bila dovolj hitro resena.");
		}
		System.out.println("Porabljen cas [ms]: " + deltaT);
		File answers = new File(out);
		File solutions = new File(resitve);
		if(!answers.isFile()) {
			System.out.println("Datoteka " + answers + " ne obstaja!");
			return false;
		}
		if(!solutions.isFile()) {
			System.out.println("Datoteka " + solutions + " ne obstaja!");
			return false;
		}
		// compare answers
		int iVrsta = 0;
		try {
			BufferedReader answerReader = new BufferedReader(new FileReader(answers));
			BufferedReader solutionReader = new BufferedReader(new FileReader(solutions));
	    	
	    	String answerLine = answerReader.readLine();
	    	String solutionLine = solutionReader.readLine();
	    	while (solutionLine != null) {
	    		iVrsta++;
	    		answerLine = answerLine.trim();
	    		solutionLine = solutionLine.trim();
				int solutionHash = Integer.parseInt(solutionLine);
	    		int trueAnswer = myHash(Integer.parseInt(answerLine));
	    		if (solutionHash != trueAnswer) {
	    			System.out.println(
	    					String.format(
	    							"Odgovor na poizvedbo stevilka %d (steto od 1) je napacen.",
	    							iVrsta
	    							)
	    					);
	    			return false;
	    		}
	    		answerLine = answerReader.readLine();
	    		solutionLine = solutionReader.readLine();
	    		
	    	}
	        answerReader.close();
	        solutionReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Naloga je pravilno resena.");
		return true;
	}
	
	private static int myHash(int a) {
//		a ^= (a << 13);
//		a ^= (a >>> 17);        
//		a ^= (a << 5);
		return a;   
	}
}
