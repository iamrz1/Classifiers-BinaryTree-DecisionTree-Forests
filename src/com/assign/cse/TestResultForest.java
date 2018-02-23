package com.assign.cse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TestResultForest {

	private String test_file;
	private ArrayList<BinaryTree> treeList;
	ArrayList<ArrayList <HashMap<Double,Double>>> listOflistOfDistribution = new ArrayList<>();
	ArrayList< ArrayList <Integer> > listOfListOfPredictedClass= new ArrayList<>();
	Double totalAccurary=0.0;

	
	public TestResultForest(String test_file, ArrayList<BinaryTree> treeList) {
		this.test_file = test_file;
		this.treeList= treeList;
		run();
	}
	
	private void run(){
		TestData test = new TestData (test_file);
		ArrayList<ArrayList<Double>> rowArray = test.getRowArray();
		int numberOfRows= test.getNumberOfRows();
		int classIndex= rowArray.get(0).size()-1;
		ArrayList <Integer> listOfPredictedClass= new ArrayList<Integer>();
		ArrayList <Integer> listOfTrueClass= new ArrayList<Integer>();
		int numberOfTree = treeList.size();
		
		for (int treeNo =0;  treeNo< numberOfTree;treeNo++){
			listOfPredictedClass.clear();
			listOfTrueClass.clear();
			
			ArrayList <HashMap<Double,Double>> listOfDistribution = new ArrayList<>();
			
			BinaryTree trained_tree = treeList.get(treeNo);
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
		        		listOfDistribution.add(tempTree.getNodeDistribution());
		        		// got a result (test result here)
		        		listOfPredictedClass.add(predictedClass);
		        		
		        		break;
		        	}
		        	
		        }
				
			}
			listOflistOfDistribution.add(listOfDistribution);
			listOfListOfPredictedClass.add(listOfPredictedClass);
			
			
		}
		
		int totalAccurate=0;
		for (int i=0; i<listOfTrueClass.size();i++){
			HashMap<Double, Double> tempMergedDist=new HashMap<>();
			
			for (int j =0; j< treeList.size();j++){
				Set set = listOflistOfDistribution.get(j).get(i).entrySet();

				Iterator it =  set.iterator();

				while(it.hasNext()) {
					 Map.Entry me = (Map.Entry)it.next();
					 Double key = (Double) me.getKey();
					 Double value = (Double) me.getValue();
					 if (!tempMergedDist.containsKey(key)){
						 tempMergedDist.put(key, value);
					 }
					 else {
						 Double tempValue = tempMergedDist.get(key);
						 value = value+tempValue;
						 tempMergedDist.replace(key, value);
					 }
		
				}

				
				
				
				//System.out.println(listOflistOfDistribution.get(j).get(i));
				
				
				
			}
			//System.out.println("merged = "+tempMergedDist);
			Double maxValue = Collections.max(tempMergedDist.values());
			//System.out.println("maxValue = "+maxValue);
			Set set = tempMergedDist.entrySet();
			ArrayList<Integer> tempPredicted = new ArrayList<>();
			Iterator it =  set.iterator();

			while(it.hasNext()) {
				 Map.Entry me = (Map.Entry)it.next();
				 Double key = (Double) me.getKey();
				 Double value = (Double) me.getValue();
				 if (value.equals(maxValue)){
					 tempPredicted.add(key.intValue());
				 }		
			}
			Collections.shuffle(tempPredicted);
			int predictedClass = tempPredicted.get(0);
			
			//int predictedClass = listOfPredictedClass.get(i);
			int trueClass= listOfTrueClass.get(i);
			Double accuracy=0.0; 
			int divisor = tempPredicted.size();
			if (trueClass == predictedClass){
				accuracy=1.0/(double)divisor;
				totalAccurate++;
				totalAccurary= totalAccurary+accuracy;
			}
			System.out.printf("ID=%5d, predicted=%3d, true=%3d, accuracy=%4.2f\n", i, predictedClass, trueClass, accuracy.doubleValue());
			//System.out.println("ID ="+i+" Predicited = "+ predictedClass + " TrueClasss = " +trueClass+ " Accuracy = "+ accuracy);
			
		}
		int totalInaccurate = listOfPredictedClass.size()-totalAccurate;
		Double classification_accuracy = (double) ((totalAccurate*100)/(double)(rowArray.size()));
		System.out.println(" Total = "+rowArray.size());
		System.out.println(" Total Acuurate = "+totalAccurate+ " Total Inacuurate = "+totalInaccurate +" total tied Accurary = " + totalAccurary);
		System.out.printf("(classification accuracy without ties =%6.4f )\n", totalAccurary/rowArray.size()*100);		
		System.out.printf(" classification accuracy=%6.4f \n", classification_accuracy);

	}
	
		
	
	

}
