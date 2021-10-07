package dn3; // tukaj bo treba najbrz spremeniti

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {

	public static void main(String[] args) {

		for (String x : new String[] { "A", "B" }) {
			// VNESI POTI DATOTEK
			System.out.println("Naloga " + x);
			String vhodnaDatoteka = String.format(
					"naloga%s.txt", x
					);
			String izhodnaDatoteka = String.format("naloga%s.reseno", x);
			String resitve = String.format("naloga%s.sol", x);
			
			long t0 = System.currentTimeMillis();
			Resitev.resi(vhodnaDatoteka, izhodnaDatoteka);
			long t1 = System.currentTimeMillis();
			PreveriResitev.preveri(t0, t1, izhodnaDatoteka, resitve);
		}
	}
}
