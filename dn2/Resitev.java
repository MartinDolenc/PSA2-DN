package dn2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Resitev {	
	public static void resi(String vhodnaDatoteka, String izhodnaDatoteka) {
		// TUKAJ RESITE NALOGO
		// OK
		
		// Opomba: Uporabniških imen nisem nikjer uporabil ker sem izkoristil dejstvo da so časi rojstva unikatni.
		// Ideja: V prihodnje bi lahko imeli uporabnike z istim časom rojstva, za sosede, ki so enako odaljeni od poizvedbe
		// pa rečemo da vzamemo tistega ki je po abecedi prej. Potem smo tudi prisiljeni uporabljati uporabniška imena.
		
		try {
			BufferedReader readerFirstTime = new BufferedReader(new FileReader(vhodnaDatoteka));
			String firstLineDataFirstTime = readerFirstTime.readLine();
	    	String[] firstLineArrayFirstTime = firstLineDataFirstTime.split(" ");
			
		    Long[] seznamUporabnikov = new Long[Integer.parseInt(firstLineArrayFirstTime[0])];
		    HashMap<Long, Integer> uporabnikiMap = new HashMap<Long, Integer>();
		    int uporabnikiCounter = 0;
		    int vseVrstice = Integer.parseInt(firstLineArrayFirstTime[0]) + Integer.parseInt(firstLineArrayFirstTime[1]) + Integer.parseInt(firstLineArrayFirstTime[2]);
		    
		    for (int i = 0; i < vseVrstice; i++) {
		        String data = readerFirstTime.readLine();
		        String[] dataArray = data.split(" ");
		        
		        if (dataArray[0].equals("dodaj")) {
		        	seznamUporabnikov[uporabnikiCounter] = Long.parseLong(dataArray[2]);
		        	uporabnikiCounter++;
		        	
		        	// preskočimo intervale ogledov
		        	for (int u = 0; u < Integer.parseInt(dataArray[3]); u++) {
		        		readerFirstTime.readLine();
		        	}
		        }
		    }
		    readerFirstTime.close();
		    Arrays.sort(seznamUporabnikov);		// sortiramo za kasnejšo uporabo
		    
		    for (int i = 0; i < seznamUporabnikov.length; i++) {
		    	uporabnikiMap.put(seznamUporabnikov[i], i);
		    }
		    
		    boolean[] aktivniUporabniki = new boolean[Integer.parseInt(firstLineArrayFirstTime[0])];	// sledimo kateri uporabniki so aktivni
		    
		    BufferedReader reader = new BufferedReader(new FileReader(vhodnaDatoteka));
		    reader.readLine();
	        
	        int[][] intervaliUporabnikov = new int[Integer.parseInt(firstLineArrayFirstTime[0])][];
	        int[] odgovori = new int[Integer.parseInt(firstLineArrayFirstTime[1])];
	        
	        int odgovoriCounter = 0;
	        
	        for (int u = 0; u < vseVrstice; u++) {
		        String data = reader.readLine();
		        String[] dataArray = data.split(" ");
		        
		        if (dataArray[0].equals("dodaj")) {
		        	int[] intervali = new int[Integer.parseInt(dataArray[3])*2];
		        	
		        	for (int i = 0; i < Integer.parseInt(dataArray[3]); i++) {
		        		String dataIzsek = reader.readLine();
				        String[] dataArrayIzsek = dataIzsek.split(" ");
				        intervali[i*2] = Integer.parseInt(dataArrayIzsek[0]);
				        intervali[i*2+1] = Integer.parseInt(dataArrayIzsek[1]);
		        	}
		        	
		        	intervaliUporabnikov[uporabnikiMap.get(Long.parseLong(dataArray[2]))] = intervali;
		        	aktivniUporabniki[uporabnikiMap.get(Long.parseLong(dataArray[2]))] = true;
		        } else if (dataArray[0].equals("poizvedba")) {
		        	Long poizvedbaCas = Long.parseLong(dataArray[1]);
		        	int vstavitvenaTočka = insertionPoint(seznamUporabnikov, poizvedbaCas);
		        	
		        	int low = vstavitvenaTočka-1;
		        	int hi = vstavitvenaTočka;
		        	int[] sosedje = new int[5];
		        	int steviloSosedov = 0;
		        	
		        	while (steviloSosedov < 5) {
		        		if (low == -1) {
		        			if (aktivniUporabniki[hi]) {
		        				sosedje[steviloSosedov] = hi;
		        				steviloSosedov++;
		        			}
		        			hi++;
		        			continue;
		        		}
		        		
		        		if (hi == seznamUporabnikov.length) {
		        			if (aktivniUporabniki[low]) {
		        				sosedje[steviloSosedov] = low;
		        				steviloSosedov++;
		        			}
		        			low--;
		        			continue;
		        		}
		        		
		        		if (poizvedbaCas - seznamUporabnikov[low] > seznamUporabnikov[hi] - poizvedbaCas) {
		        			// hi je bližje
		        			
		        			if (aktivniUporabniki[hi]) {
		        				sosedje[steviloSosedov] = hi;
		        				steviloSosedov++;
		        			}
		        			hi++;
		        		}
		        		else {
		        			// low je bližje
		        			
		        			if (aktivniUporabniki[low]) {
		        				sosedje[steviloSosedov] = low;
		        				steviloSosedov++;
		        			}
		        			low--;
		        		}
		        	}
		        	int maximumPoizvedbe = -1;
		        	int zacetek = -1;
		        	int trenutnaPoivedba = 1;
		        	
		        	for (int s1 : sosedje) {
		        		for (int i = 0; i < intervaliUporabnikov[s1].length/2; i++) {
		        			for (int s2 : sosedje) {
		        				if (s1 == s2) continue;
		        				
		        				int iP = insertionPoint(intervaliUporabnikov[s2], intervaliUporabnikov[s1][i*2]);
		        				if (iP % 2 == 1 || (iP < intervaliUporabnikov[s2].length && intervaliUporabnikov[s2][iP] == intervaliUporabnikov[s1][i*2])) {
		        					trenutnaPoivedba++;
		        				}
		        			}
		        			if (trenutnaPoivedba > maximumPoizvedbe) {
		        				maximumPoizvedbe = trenutnaPoivedba;
		        				zacetek = intervaliUporabnikov[s1][i*2];
		        			} else if (trenutnaPoivedba == maximumPoizvedbe && zacetek > intervaliUporabnikov[s1][i*2]) {
		        				zacetek = intervaliUporabnikov[s1][i*2];
		        			}
		        			trenutnaPoivedba = 1;
		        		}
		        	}
		        	odgovori[odgovoriCounter] = zacetek;
		        	odgovoriCounter++;
		        } else {
		        	aktivniUporabniki[uporabnikiMap.get(Long.parseLong(dataArray[2]))] = false;
		        }
		    }
		    reader.close();
		    FileWriter writer = new FileWriter(izhodnaDatoteka);
	        for (int o : odgovori) {
	        	writer.write(String.valueOf(o + "\n"));
	        }
	        writer.close();
		} catch (Exception e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
				
	}
	
	/**
	Z uporabo bisekcijo vrne index vstavitve ključa v seznam.
	@return index
	*/
	public static int insertionPoint(Long[] arr, Long key)
	{
		int min=0;
		int max=arr.length-1;
		int mid=(min+max)/2;
		while(min<=max)
		{
			if(arr[mid]>key)
				max=mid-1;
			else if(arr[mid]<key)
				min=mid+1;
			else
				return mid;
			mid=(min+max)/2;
		}
		return min;
	}
	
	/**
	Z uporabo bisekcijo vrne index vstavitve ključa v seznam.
	@return index
	*/
	public static int insertionPoint(int[] arr, int key)
	{
		int min=0;
		int max=arr.length-1;
		int mid=(min+max)/2;
		while(min<=max)
		{
			if(arr[mid]>key)
				max=mid-1;
			else if(arr[mid]<key)
				min=mid+1;
			else
				return mid;
			mid=(min+max)/2;
		}
		return min;
	}
}
