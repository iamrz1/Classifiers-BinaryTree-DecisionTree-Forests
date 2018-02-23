package com.assign.cse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class TestData {
	
	private ArrayList<ArrayList<Double>> rowArray = new ArrayList<>();

	public TestData(String fileName) {
		// TODO Auto-generated constructor stub
		processData(fileName);
	}
	
	public int getNumberOfRows(){
		return rowArray.size();
	}
	
    public ArrayList<ArrayList<Double>> getRowArray() {
		return rowArray;
	}

	private void processData(String fileName) {
		String line="";
	    FileReader fr;

		int i=0;
		try {
			fr = new FileReader(fileName);
	        Scanner scan = new Scanner (fr);
	        while(scan.hasNext()){
	        	line=scan.nextLine();
	        	//System.out.println("line = "+lineArray[i]);
	        	i=i+1;
	        	Scanner sc = new Scanner (line);
	        	ArrayList<Double> row = new ArrayList<>();
		        while(sc.hasNext()){
		        	row.add(sc.nextDouble());
		        }
		        rowArray.add(row);
	        	sc.close();
	        }
	        scan.close();


	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}

	

}
/*
 * Call it like this
 *  		
		TestData test=new TestData(file);
		ArrayList<ArrayList<Double>> rowArray = new ArrayList<>();
		rowArray=test.getRowArray();
        System.out.println("Examples= "+rowArray.size());
        for (int w=0;w<rowArray.size();w++){
	        ArrayList<Double> a= rowArray.get(w);
	        System.out.println(" row "+(w+1) + "= ");
	        for(int x=0; x<a.size();x++){
	        	System.out.print(a.get(x)+ " " );
	        }
	        System.out.println("");
	        
        } 
 */

