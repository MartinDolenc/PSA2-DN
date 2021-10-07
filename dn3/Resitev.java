package dn3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class Resitev {	
	public static void resi(String vhodnaDatoteka, String izhodnaDatoteka) {
		// TUKAJ RESITE NALOGO
		
		// Namesto tolerance bi lahko definirali dodaten razred Ulomek in potem primerjali ulomke.
		// Vendar mi je ta rešitev lepša in tistega se mi ne da delat.
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(vhodnaDatoteka));
			FileWriter writer = new FileWriter(izhodnaDatoteka);
			String firstLineData = reader.readLine();
	    	String[] firstLineArray = firstLineData.split(" ");
			
		    float[][] pesmi = new float[Integer.parseInt(firstLineArray[0])][Integer.parseInt(firstLineArray[2])];
		    double toleranca = 0.001;		// toleranco nastavimo tako da upoštevamo nenatančnost množenja floatu
		    
		    // si zapomnemo vse pesmi
		    for (int i = 0; i < Integer.parseInt(firstLineArray[0]); i++) {
		        String data = reader.readLine();
		        String[] dataArray = data.split(" ");
		        
		        for (int j = 0; j < Integer.parseInt(firstLineArray[2]); j++) {
		        	pesmi[i][j] = Float.parseFloat(dataArray[j]);
		        }
		    }
		    
		    // obravnavamo demoposnetke
		    for (int i = 0; i < Integer.parseInt(firstLineArray[1]); i++) {
		        String data = reader.readLine();
		        String[] dataArray = data.split(" ");
		        
		        float[] demoposnetek = new float[Integer.parseInt(firstLineArray[3])];
		        int[] odgovor = {-1, -1};
		        
		        for (int j = 0; j < Integer.parseInt(firstLineArray[3]); j++) {
		        	demoposnetek[j] = Float.parseFloat(dataArray[j]);
		        }
		        
		        uLoop:
		        for (int u = 0; u < Integer.parseInt(firstLineArray[0]); u++) {
		        	jLoop:
		        	for(int j = 0; j < Integer.parseInt(firstLineArray[2])-Integer.parseInt(firstLineArray[3]); j++) {
		        		// faktor ki ga bomo preverjali
		        		float q = pesmi[u][j]/demoposnetek[0];
		        		
		        		// preverjamo ali je pesem[j:j+l_{d}] == q*demoposnetek, z neko mejo tolerance ker deljenje ni natančno
		        		for (int k = 1; k < Integer.parseInt(firstLineArray[3]); k++) {
		        			if (Math.abs(pesmi[u][j+k]/demoposnetek[k] - q) > toleranca) {
		        				continue jLoop;
		        			}
		        		}
		        		
		        		odgovor[0] = u;
		        		odgovor[1] = j;
		        		break uLoop;
		        	}
		        }
		        
		        if (odgovor[0] != -1) {
		        	writer.write(String.valueOf(odgovor[0] + " " + odgovor[1] + "\n"));
		        }
		    }
		    reader.close();
		    writer.close();
		} catch (Exception e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
		
	}
}
