// SPREMINJAMO 3. (package) in 19. vrstico (mapo, v kateri se nahaja datoteka)

package dn4; // spremenim lahko to

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Main {
	public static final String[] NALOGE = new String[] {"primer", "nalogaA", "nalogaB" };
	public static final int[] DOVOLJENI_DOSTOPI = new int[] {100, 100000, 10000}; 
	private final static HashMap<String, Integer> DOSTOPI_ZA_NALOGO = definirajDovoljeneDostope();

	public static void main(String[] args) {

		for (String naloga : NALOGE) {  
			String mapa = "C:\\Users\\matejp\\git\\psa2\\dn\\dn4\\";    // spremenim lahko to
			String vhodnaDatoteka = String.format("%s%s.txt", mapa, naloga);
			String izhodnaDatoteka = String.format("%s%s.reseno", mapa, naloga);
			String resitve = String.format("%s%s.sol", mapa, naloga);
			
			Disk disk = new Disk();
			int[][] dimenzijeMatrik = disk.napolniDisk(vhodnaDatoteka); 
			Resitev.resi(disk, dimenzijeMatrik, izhodnaDatoteka);
			preveri(naloga, disk.getDostopi(), izhodnaDatoteka, resitve);
		}
	}
	
	private final static HashMap<String, Integer> definirajDovoljeneDostope(){
		HashMap<String, Integer> dostopi = new HashMap<>();
		for (int i = 0; i < NALOGE.length; i++) {
			dostopi.put(NALOGE[i], DOVOLJENI_DOSTOPI[i]);
		}
		return dostopi;
		
	}
	
	public static void preveri(String naloga, int dostopi, String out, String resitve) {
		System.out.println("Preverjanje za " + naloga + ":");
		int dovoljeniDostopi = DOSTOPI_ZA_NALOGO.get(naloga);
		if (dostopi > dovoljeniDostopi) {
			System.out.println("Prevec dostopov do diska: " + dostopi);
			System.out.println("Stevilo dovoljenih      : " + dovoljeniDostopi);
		} else {
			System.out.println(
					String.format("Stevilo dostopov do diska je v mejah normale (%d <= %d).", dostopi, dovoljeniDostopi)
			);
		}
		File answers = new File(out);
		File solutions = new File(resitve);
		if(!answers.isFile()) {
			System.out.println("Datoteka " + answers + " ne obstaja!\n");
			return;
		}
		if(!solutions.isFile()) {
			System.out.println("Datoteka " + solutions + " ne obstaja!\n");
			return;
		}
		// preveri pravilnost
		BufferedReader answerReader = null; 
		BufferedReader solutionReader = null;
		FileReader answersR = null;
		FileReader solutionsR = null;
		try {
			answersR = new FileReader(answers);
			answerReader = new BufferedReader(answersR);
			solutionsR = new FileReader(solutions);
			solutionReader = new BufferedReader(solutionsR);
			answerReader.readLine();
			solutionReader.readLine();
	    	String answerLine = answerReader.readLine();
	    	String solutionLine = solutionReader.readLine();
	    	String[] dim = solutionLine.trim().split(" ");
	    	int vrstice = Integer.parseInt(dim[0]);
	    	int stolpci = Integer.parseInt(dim[1]);
	    	for (int i = 0; i < vrstice * stolpci; i++) {
	    		answerLine = answerReader.readLine().trim();
	    		solutionLine = solutionReader.readLine().trim();
	    		if (!answerLine.equals(solutionLine)) {
	    			int vrsta = i / stolpci + 1;
	    			int stolpec = i % stolpci + 1;
	    			String sporocilo = "Neujemanje matrik na elementu %d (oz. polozaju [%d, %d]) (vse steto od 1).\nOdgovor: %s\nResitev: %s\n"; 
	    			System.out.println(String.format(sporocilo, i + 1, vrsta, stolpec, answerLine, solutionLine));
	    			return;
	    		}
	    	}
	        answerReader.close();
	        solutionReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {answerReader.close();} catch (IOException e) {}
			try {solutionReader.close();} catch (IOException e) {}
		}
		System.out.println("Naloga je pravilno resena.\n");
		return;
	}
	
}
