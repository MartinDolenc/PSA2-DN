package dn4;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Disk {
//	private static final int CAS_BRANJA = 0; // [ms]
//	private static final int CAS_PISANJA = 0; // [ms]
	private static final int SPOMIN_MB = 30;  // [MB]
	private int[][] bloki;
	private int velikostBloka = 19;
	private int nDostopov = 0;

	public Disk() {
		int nBlokov = izracunajNBlokov();
		bloki = new int[nBlokov][velikostBloka];
	}
	
	private int izracunajNBlokov() {
		int bajti = SPOMIN_MB * 1024 * 1024;
		int bajtovNaInt = 4;
		return bajti / bajtovNaInt / velikostBloka; 
	}

	public int[] preberiZDiska(int iBlok) {
		nDostopov++;
//		pocakaj(CAS_BRANJA);
		return Arrays.copyOf(bloki[iBlok], velikostBloka);
	}

	public void zapisiNaDisk(int[] blok, int iBlok) {
		nDostopov++;
//		pocakaj(CAS_PISANJA);
		bloki[iBlok] = Arrays.copyOf(blok, velikostBloka);
	}

	public int getVelikostBloka() {
		return velikostBloka;
	}

//	private void pocakaj(int cas) {
//		try {
//			Thread.sleep(cas);
//		} catch (InterruptedException ex) {
//			Thread.currentThread().interrupt();
//		}
//	}

	/**
	 * Zapisemo elemente po vrsticah zapisane matrike v datoteko.
	 * 
	 * @param i0      zacetek matrike na disku
	 * @param vrstice
	 * @param stolpci
	 * @return
	 */
	public void zapisiVDatoteko(String datoteka, int i0, int vrstice, int stolpci) {
		int dolzina = vrstice * stolpci;
		try {
			FileWriter writer = new FileWriter(datoteka);
			writer.write("1\n");    // 1 matrika
			writer.write(String.format("%d %d\n", vrstice, stolpci));  // dim
			for (int i = i0; i < i0 + dolzina; i++) {
				int a = bloki[i / velikostBloka][i % velikostBloka];
				writer.write(String.format("%d\n", a));
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Namenjeno testiranju. Prikaze del diska od mesta i0 naprej ...
	 * @param i0
	 * @param vrstice
	 * @param stolpci
	 */
	public void pokazi(int i0, int vrstice, int stolpci) {
		int dolzina = vrstice * stolpci;
		String[] b = new String[dolzina];
		for (int i = i0; i < i0 + dolzina; i++) {
			int a = bloki[i / velikostBloka][i % velikostBloka];
			b[i - i0] = String.format("%s%d", i % velikostBloka == 0 ? "|": " ", a);
		}
		System.out.println(String.join("", b));
		
	}

	public int[][] napolniDisk(String vhodnaDatoteka) {
		int[][] dimMatrik = null;
		try {
			Scanner reader = new Scanner(new FileInputStream(vhodnaDatoteka));
	    	// preberi nMatrik
	    	int nMatrik = Integer.parseInt(reader.nextLine());
	    	int kam = 0;
	    	dimMatrik = new int[nMatrik][2]; 
			for (int i = 0; i < nMatrik; i++) {
				// preberi dim
				String[] m_n = reader.nextLine().trim().split(" ");
		    	dimMatrik[i][0] = Integer.parseInt(m_n[0]);
		    	dimMatrik[i][1] = Integer.parseInt(m_n[1]);
		    	// preberi matriko
		    	int nElementov = dimMatrik[i][0] * dimMatrik[i][1]; 
		    	for (int j = 0; j < nElementov; j++) {
		    		int element = Integer.parseInt(reader.nextLine());
		    		bloki[kam / velikostBloka][kam % velikostBloka] = element;
		    		kam++;
		    	}
			}  
	    	reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return dimMatrik;
	}
	
	public int getDostopi() {
		return nDostopov;
	}
}
