package dn4;

public class Resitev {	
	public static void resi(Disk disk, int[][] dimenzijeMatrik, String izhodnaDatoteka) {
		if (dimenzijeMatrik.length == 2) {
			resiA(disk, dimenzijeMatrik, izhodnaDatoteka);
		} else {
			resiB(disk, dimenzijeMatrik, izhodnaDatoteka);
		}
	}
	
	public static void resiA(Disk disk, int[][] dimenzijeMatrik, String izhodnaDatoteka) {
		
		ResiZaDveMatrike(disk, 0, dimenzijeMatrik[0][0]*dimenzijeMatrik[0][1], dimenzijeMatrik, izhodnaDatoteka);
	}
	
	public static void resiB(Disk disk, int[][] dimenzijeMatrik, String izhodnaDatoteka) {
		
		int zacetekDrugeMat = dimenzijeMatrik[0][0]*dimenzijeMatrik[0][1];
		
		int[] buffer = new int[disk.getVelikostBloka()];
		int bufferCounter = 0;
		int blokZaZapis = (dimenzijeMatrik[0][0]*dimenzijeMatrik[0][1]+dimenzijeMatrik[1][0]*dimenzijeMatrik[1][1]+dimenzijeMatrik[2][0]*dimenzijeMatrik[2][1])/disk.getVelikostBloka() + 1;
		
		int[][] drugaMatrika = new int[dimenzijeMatrik[1][1]][dimenzijeMatrik[1][0]];
		
		// Opomba: dimenzija druge matrike je 51x63
		// 51 < 3*19
		// 63 < 4*19
		// => 51x63 < 12*19^2
		
		int steviloBlokov = zacetekDrugeMat/disk.getVelikostBloka();
		int zacetnaTocka = zacetekDrugeMat%disk.getVelikostBloka();
		
		int trenutniBlokCounterMatrike = steviloBlokov;
		int[] trenutniBlokMatrike = disk.preberiZDiska(trenutniBlokCounterMatrike);
		int blokCounterMatrike = zacetnaTocka;
		
		// napolnimo drugo matriko z podatki
		
		for (int i = 0; i < dimenzijeMatrik[1][0]; i++) {
			
			int elementCounterMatrike = 0;	
			
			while (dimenzijeMatrik[1][1] - elementCounterMatrike > 0) {
				if (dimenzijeMatrik[1][1] - elementCounterMatrike < disk.getVelikostBloka() - blokCounterMatrike) {
					for (int j = 0; j < dimenzijeMatrik[1][1]-elementCounterMatrike; j++) {
						drugaMatrika[elementCounterMatrike+j][i] = trenutniBlokMatrike[blokCounterMatrike+j];
					}
					
					blokCounterMatrike += dimenzijeMatrik[1][1]-elementCounterMatrike;
					elementCounterMatrike = dimenzijeMatrik[1][1];
				}
				else {
					for (int j = 0; j < disk.getVelikostBloka()-blokCounterMatrike; j++) {
						drugaMatrika[elementCounterMatrike+j][i] = trenutniBlokMatrike[blokCounterMatrike+j];
					}
					
					elementCounterMatrike += disk.getVelikostBloka() - blokCounterMatrike;
					blokCounterMatrike = 0;
					trenutniBlokCounterMatrike++;
					trenutniBlokMatrike = disk.preberiZDiska(trenutniBlokCounterMatrike);
				}
			}
		}
		
		int trenutniBlokCounter = 0;
		int[] trenutniBlok = disk.preberiZDiska(trenutniBlokCounter);
		int blokCounter = 0;
		
		// Opomba: shranjujemo vrstice in jih množimo z shranjeno matriko
		// dolžina vrstice: 51 < 3*19
		
		for(int i = 0; i < dimenzijeMatrik[0][0]; i++) {
			
			int elementCounter = 0;
			
			int[] arrayVrstice = new int[dimenzijeMatrik[0][1]];
			
			while (dimenzijeMatrik[0][1] - elementCounter > 0) {
				if (dimenzijeMatrik[0][1] - elementCounter < disk.getVelikostBloka() - blokCounter) {
					for (int j = 0; j < dimenzijeMatrik[0][1]-elementCounter; j++) {
						arrayVrstice[elementCounter+j] = trenutniBlok[blokCounter+j];
					}
					
					blokCounter += dimenzijeMatrik[0][1]-elementCounter;
					elementCounter = dimenzijeMatrik[0][1];
				}
				else {
					for (int j = 0; j < disk.getVelikostBloka()-blokCounter; j++) {
						arrayVrstice[elementCounter+j] = trenutniBlok[blokCounter+j];
					}
					
					elementCounter += disk.getVelikostBloka() - blokCounter;
					blokCounter = 0;
					trenutniBlokCounter++;
					trenutniBlok = disk.preberiZDiska(trenutniBlokCounter);
				}
				
			}
			
			// množimo vrstico z matriko
			
			for (int j = 0; j < drugaMatrika.length; j++) {
				buffer[bufferCounter] = PomnoziInSestej(arrayVrstice, drugaMatrika[j]);
				bufferCounter++;
				
				if (bufferCounter == disk.getVelikostBloka()) {
					
					disk.zapisiNaDisk(buffer, blokZaZapis);
					
					blokZaZapis++;
					bufferCounter = 0;
					buffer = new int[disk.getVelikostBloka()];
				}
			}
		}
		
		disk.zapisiNaDisk(buffer, blokZaZapis);
		
		int[][] noveDimenzije = new int[2][2];
		
		noveDimenzije[0][0] = dimenzijeMatrik[0][0];
		noveDimenzije[0][1] = dimenzijeMatrik[1][1];
		noveDimenzije[1][0] = dimenzijeMatrik[2][0];
		noveDimenzije[1][1] = dimenzijeMatrik[2][1];
		
		ResiZaDveMatrike(disk, ((dimenzijeMatrik[0][0]*dimenzijeMatrik[0][1]+dimenzijeMatrik[1][0]*dimenzijeMatrik[1][1]+dimenzijeMatrik[2][0]*dimenzijeMatrik[2][1])/disk.getVelikostBloka() + 1)*disk.getVelikostBloka(), dimenzijeMatrik[0][0]*dimenzijeMatrik[0][1]+dimenzijeMatrik[1][0]*dimenzijeMatrik[1][1], noveDimenzije, izhodnaDatoteka);
	}
	
	/**
	Pomnozimo dve matriki z diska.
	*/
	private static void ResiZaDveMatrike(Disk disk, int zacetekPrve, int zacetekDruge, int[][] dimenzijeMatrik, String izhodnaDatoteka) {
		
		int[] buffer = new int[disk.getVelikostBloka()];
		int bufferCounter = 0;
		int blokZaZapis = Math.max(zacetekPrve+dimenzijeMatrik[0][0]*dimenzijeMatrik[0][1], zacetekDruge+dimenzijeMatrik[1][0]*dimenzijeMatrik[1][1])/disk.getVelikostBloka() + 1;
		
		int[][] drugaMatrika = new int[dimenzijeMatrik[1][1]][dimenzijeMatrik[1][0]];
		
		// Opomba: dimenzija druge matrike je 51x63
		// 51 < 3*19
		// 63 < 4*19
		// => 51x63 < 12*19^2
		
		int steviloBlokov = zacetekDruge/disk.getVelikostBloka();
		int zacetnaTocka = zacetekDruge%disk.getVelikostBloka();
		
		int trenutniBlokCounterMatrike = steviloBlokov;
		int[] trenutniBlokMatrike = disk.preberiZDiska(trenutniBlokCounterMatrike);
		int blokCounterMatrike = zacetnaTocka;
		
		// napolnimo drugo matriko z podatki
		
		for (int i = 0; i < dimenzijeMatrik[1][0]; i++) {
			
			int elementCounterMatrike = 0;
			
			while (dimenzijeMatrik[1][1] - elementCounterMatrike > 0) {
				if (dimenzijeMatrik[1][1] - elementCounterMatrike < disk.getVelikostBloka() - blokCounterMatrike) {
					for (int j = 0; j < dimenzijeMatrik[1][1]-elementCounterMatrike; j++) {
						drugaMatrika[elementCounterMatrike+j][i] = trenutniBlokMatrike[blokCounterMatrike+j];
					}
					
					blokCounterMatrike += dimenzijeMatrik[1][1]-elementCounterMatrike;
					elementCounterMatrike = dimenzijeMatrik[1][1];
				}
				else {
					for (int j = 0; j < disk.getVelikostBloka()-blokCounterMatrike; j++) {
						drugaMatrika[elementCounterMatrike+j][i] = trenutniBlokMatrike[blokCounterMatrike+j];
					}
					
					elementCounterMatrike += disk.getVelikostBloka() - blokCounterMatrike;
					blokCounterMatrike = 0;
					trenutniBlokCounterMatrike++;
					trenutniBlokMatrike = disk.preberiZDiska(trenutniBlokCounterMatrike);
				}
			}
		}

		int trenutniBlokCounter = zacetekPrve/disk.getVelikostBloka();;
		int[] trenutniBlok = disk.preberiZDiska(trenutniBlokCounter);
		int blokCounter = zacetekPrve%disk.getVelikostBloka();
		
		// Opomba: shranjujemo vrstice in jih množimo z shranjeno matriko
		// dolžina vrstice: 51 < 3*19
		
		for(int i = 0; i < dimenzijeMatrik[0][0]; i++) {
			
			int elementCounter = 0;
			
			int[] arrayVrstice = new int[dimenzijeMatrik[0][1]];
			
			while (dimenzijeMatrik[0][1] - elementCounter > 0) {
				if (dimenzijeMatrik[0][1] - elementCounter < disk.getVelikostBloka() - blokCounter) {
					for (int j = 0; j < dimenzijeMatrik[0][1]-elementCounter; j++) {
						arrayVrstice[elementCounter+j] = trenutniBlok[blokCounter+j];
					}
					
					blokCounter += dimenzijeMatrik[0][1]-elementCounter;
					elementCounter = dimenzijeMatrik[0][1];
				}
				else {
					for (int j = 0; j < disk.getVelikostBloka()-blokCounter; j++) {
						arrayVrstice[elementCounter+j] = trenutniBlok[blokCounter+j];
					}
					
					elementCounter += disk.getVelikostBloka() - blokCounter;
					blokCounter = 0;
					trenutniBlokCounter++;
					trenutniBlok = disk.preberiZDiska(trenutniBlokCounter);
				}
				
			}
			
			// množimo vrstico z matriko
			
			for (int j = 0; j < drugaMatrika.length; j++) {
				buffer[bufferCounter] = PomnoziInSestej(arrayVrstice, drugaMatrika[j]);
				bufferCounter++;
				
				if (bufferCounter == disk.getVelikostBloka()) {
					
					disk.zapisiNaDisk(buffer, blokZaZapis);
					
					blokZaZapis++;
					bufferCounter = 0;
					buffer = new int[disk.getVelikostBloka()];
				}
			}
		}
		
		disk.zapisiNaDisk(buffer, blokZaZapis);
		disk.zapisiVDatoteko(izhodnaDatoteka, (Math.max(zacetekPrve+dimenzijeMatrik[0][0]*dimenzijeMatrik[0][1], zacetekDruge+dimenzijeMatrik[1][0]*dimenzijeMatrik[1][1])/disk.getVelikostBloka() + 1)*disk.getVelikostBloka(), dimenzijeMatrik[0][0], dimenzijeMatrik[dimenzijeMatrik.length-1][1]);
	}
	
	/**
	Pomnozimo dva arraya po elementih in seštejemo.
	@return sum
	*/
	private static int PomnoziInSestej(int[] prviSeznam, int[] drugiSeznam) {
		
		int sum = 0;
		
		for (int i = 0; i < prviSeznam.length; i++) {
			sum += prviSeznam[i]*drugiSeznam[i];
		}
		
		return sum;
	}
}
