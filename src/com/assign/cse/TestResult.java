package com.assign.cse;

import java.util.ArrayList;

public class TestResult {
	private String test_file;
	private BinaryTree trained_tree;

	public TestResult(String test_file, BinaryTree trained_tree) {

		this.test_file = test_file;
		this.trained_tree= trained_tree;
		run();
	}
	private void run(){
		TestData test = new TestData (test_file);
		ArrayList<ArrayList<Double>> rowArray = test.getRowArray();
		int numberOfRows= test.getNumberOfRows();
		int classIndex= rowArray.get(0).size()-1;
		ArrayList <Integer> listOfPredictedClass= new ArrayList<Integer>();
		ArrayList <Integer> listOfTrueClass= new ArrayList<Integer>();
		for (int i=0; i< numberOfRows;i++){
			int trueClass = rowArray.get(i).get(classIndex).intValue();
			listOfTrueClass.add(trueClass);
			BinaryTree bt = trained_tree;
			BinaryTree tempTree= bt;

	        
	        while(true){
	        	int attribute = tempTree.getAttributeNumber();
	        	double threshold= tempTree.getThreshold();
	        	if(attribute>-1){
	        		//there is an arrtribute towork with
	        		Double attributeValue = rowArray.get(i).get(attribute);
	        		if (attributeValue<threshold){
	        			tempTree= tempTree.getLeft();
	        		}
	        		else{
	        			tempTree= tempTree.getRight();
	        		}
	        		
	        	}
	        	else if (attribute==-1){
	        		int predictedClass = tempTree.getThreshold().intValue();
	        		listOfPredictedClass.add(predictedClass);
	        		
	        		break;
	        	}
	        	
	        }
			
		}
		
		int totalAccurate=0;
		for (int i=0; i<listOfPredictedClass.size();i++){
			int predictedClass = listOfPredictedClass.get(i);
			int trueClass= listOfTrueClass.get(i);
			Integer accuracy=0; 
			if (trueClass == predictedClass){
				accuracy=1;
				totalAccurate++;
			}
			System.out.printf("ID=%5d, predicted=%3d, true=%3d, accuracy=%4.2f\n", i, predictedClass, trueClass, accuracy.doubleValue());
			//System.out.println("ID ="+i+" Predicited = "+ predictedClass + " TrueClasss = " +trueClass+ " Accuracy = "+ accuracy);
			
		}
		int totalInaccurate = listOfPredictedClass.size()-totalAccurate;
		Double classification_accuracy = (double) ((totalAccurate*100)/(double)(rowArray.size()));
		System.out.println(" Total = "+rowArray.size());
		System.out.println(" Total Acuurate = "+totalAccurate+ " Total Inacuurate = "+totalInaccurate );
		System.out.printf("classification accuracy=%6.4f\n", classification_accuracy);

	}

}
