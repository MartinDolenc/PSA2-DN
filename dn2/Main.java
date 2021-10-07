package dn2; // tukaj bo treba najbrz spremeniti

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {

	public static void main(String[] args) {
//		System.out.println(stringToMilliseconds("1935-12-25 00:01:01"));
//		System.out.println(stringToMilliseconds("2003-04-13 01:02:03"));
		
		for (String x : new String[] { "A", "B" }) {
			// VNESI POTI DATOTEK
			System.out.println("Naloga " + x);
			String vhodnaDatoteka = String.format(
					"naloga%s.txt", x
					);
			String izhodnaDatoteka = String.format("naloga%s.reseno", x);
			String resitve = String.format("naloga%s.sol", x);
			
			long t0 = System.currentTimeMillis();
			dn2.Resitev.resi(vhodnaDatoteka, izhodnaDatoteka);
			long t1 = System.currentTimeMillis();
			PreveriResitev.preveri(t0, t1, izhodnaDatoteka, resitve);
		}
	}
	
	public static long stringToMilliseconds(String t) {
		// spremeni 1935-12-03 00:09:58 v milisekunde od 1. 1. 1970
		try {
			return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(t).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
