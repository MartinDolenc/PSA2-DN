package psa;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Arrays;
import java.util.*;
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class Resitev {
	
	public static void resi(String vhodnaDatoteka, String izhodnaDatoteka) {
		try {
			File inputFile = new File(vhodnaDatoteka);
		    Scanner readerFirstTime = new Scanner(inputFile);
		    String firstLineDataFirstTime = readerFirstTime.nextLine();
		    String[] firstLineArrayFirstTime = firstLineDataFirstTime.split(" ");
		    
		    int[] houses = new int[Integer.parseInt(firstLineArrayFirstTime[1]) + 2];
		    HashMap<Integer, Integer> housesMap = new HashMap<Integer, Integer>();
		    int[] petsInHouses = new int[Integer.parseInt(firstLineArrayFirstTime[1]) + 2];
		    int insertionCounter = 1;
		    
		    houses[0] = -1;
		    houses[Integer.parseInt(firstLineArrayFirstTime[1]) + 1] = Integer.parseInt(firstLineArrayFirstTime[0]) + 1;
		    
		    housesMap.put(-1, 0);
		    housesMap.put(Integer.parseInt(firstLineArrayFirstTime[0]) + 1, Integer.parseInt(firstLineArrayFirstTime[1]) + 1);
		    
		    petsInHouses[0] = 0;
		    petsInHouses[petsInHouses.length-1] = 0;
		    
		    while (readerFirstTime.hasNextLine()) {
		        String data = readerFirstTime.nextLine();
		        String[] dataArray = data.split(" ");
		        
		        if (dataArray[0].equals("V")) {
		        	houses[insertionCounter] = Integer.parseInt(dataArray[1]);
		        	insertionCounter++;
		        }
		    }
		    readerFirstTime.close();
		    Arrays.sort(houses);		// sortiramo hiše da je potem lažje računati
		    
		    for (int i = 1; i < houses.length-1; i++) {
		    	housesMap.put(houses[i], i);
		    }

			FileWriter outputFile = new FileWriter(izhodnaDatoteka);
		    Scanner reader = new Scanner(inputFile);		// resetiramo scanner
		    reader.nextLine();								// preskočimo prvo vrstico ker je ne rabimo več
		    
		    while (reader.hasNextLine()) {
		        String data = reader.nextLine();
		        String[] dataArray = data.split(" ");
		        
		        if (dataArray[0].equals("V")) {
		        	petsInHouses[housesMap.get(Integer.parseInt(dataArray[1]))] = Integer.parseInt(dataArray[2]);
		        } else {
		        	int x = insertionPoint(houses, Integer.parseInt(dataArray[1]));
		        	int y = insertionPoint(houses, Integer.parseInt(dataArray[2]));
		        	
		        	if ((houses[y] != Integer.parseInt(dataArray[2]))) {
		        		y--;
		        	}
		        	
		        	int sum = 0;
		        	
		        	for (int i = x; i < y + 1; i++) {
		        		sum += petsInHouses[i];
		        	}
		        	
		        	outputFile.write(String.valueOf(sum + "\n"));
		        }
		    }
		  reader.close();
		  outputFile.close(); 
		} catch (Exception e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
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