package com.assign.cse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class TrainingData {

	private ArrayList<ArrayList<Double>> rowArray = new ArrayList<>();

	private ArrayList<ArrayList<Double>> colArray = new ArrayList<>();
	private int numberOfAttributes;
	
	public ArrayList<Integer>  getAttributes() {
		ArrayList<Integer> attributes = new ArrayList <>();
		for (int  i = 0; i < numberOfAttributes; i++)
			attributes.add(i);
		return attributes;
	}


	public TrainingData(String fileName) {
		numberOfAttributes=0;
		processData(fileName);
	}
    
	
	public ArrayList<ArrayList<Double>> getRowArray() {
		return rowArray;
	}

	public ArrayList<ArrayList<Double>> getColArray() {
		return colArray;
	}
	public void processData(String fileName) {
		String line="";
	    FileReader fr;
	    int i=0, j=0;

		try {
			fr = new FileReader(fileName);
	        Scanner scan = new Scanner (fr);
	        while(scan.hasNext()){
	        	line=scan.nextLine();
	        	Scanner sc = new Scanner (line);
	        	ArrayList<Double> row = new ArrayList<>();
		        while(sc.hasNext()){
		        	row.add(sc.nextDouble());
		        }
		        rowArray.add(row);
	        	sc.close();
	        }
	        scan.close();
	        //rowArray list is done. using it to create column arraylist
	        
	        int row= 0;
	        int col=0;
	        int rowSize = rowArray.size();
	        if (rowSize>0){
		        row= rowArray.size();
		        col=rowArray.get(0).size();
		        numberOfAttributes=col-1;
				//System.out.println("column = " + col+ "row = "+row);
				for ( i=0;i<col;i++){
				
				    ArrayList<Double> column = new ArrayList<>();
				    
				    for(j=0;j<row;j++) {
				    	column.add(rowArray.get(j).get(i));	
			    	}
				    colArray.add(column);
				}
	        }
        
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
	
	

	
}
/*
 	        System.out.println("Array Size= "+colArray.size());
	        for (int w=0;w<colArray.size();w++){
		        ArrayList<Double> a= colArray.get(w);
		        System.out.println(" Column "+(w+1) + " size= "+ a.size());
		        for(int x=0; x<a.size();x++){
		        	System.out.print(a.get(x)+" ");
		        }
		        System.out.println();
	        }
 */
