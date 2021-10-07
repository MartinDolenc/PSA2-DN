package dn1.javno; // tukaj bo treba najbrz spremeniti

public class Main {

	public static void main(String[] args) {		
		long t0 = System.currentTimeMillis();
		
		System.out.println("wtf je tu");
		
		// VNESI POTI DATOTEK
		String vhodnaDatoteka = null;
		String izhodnaDatoteka = null;
		String zakodiraneResitve = null;
		
		// VASA RESITEV
		Resitev.resi(vhodnaDatoteka, izhodnaDatoteka);
		
		
		long t1 = System.currentTimeMillis();
		
		PreveriResitev.preveri(t0, t1, izhodnaDatoteka, zakodiraneResitve);
	}

}
