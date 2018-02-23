package com.assign.cse;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(args.length);
		String training_file = args[0];
		String test_file= args[1];
		String option = args[2];
		int pruning_thr=1;
		if (args.length>3){
			
			String x= args[3];
			Double y = Double.parseDouble(x);
			pruning_thr=y.intValue();
		}
		//String file="C:\\input.txt";
		//ArrayList<ArrayList<Double>> rowArray = new ArrayList<>();
		ArrayList<ArrayList<Double>> colArray = new ArrayList<>();
		TrainingData train = new TrainingData(training_file);
		
		ArrayList<Integer> attributes = train.getAttributes();
		colArray= train.getColArray();
			
	
		//printRow(rowArray);
		//printCol(colArray);
		//test();
		String options = option.toLowerCase();
		if (options.contains("optimize")){
			DecisionTree dt= new DecisionTree(colArray,attributes,-5.0,pruning_thr);
			BinaryTree root = dt.getTree();
			printTrainingData(root);
			
			//Find predicted class
			HashMap <Double, Double> distribution = dt.getDistribution();
			
			Set set = distribution.entrySet();
			  
			// Get an iterator
			Iterator it =  set.iterator();
			Double  maxValue=0.0,maxKey=0.0;
			ArrayList <Double> maxKeyArray= new ArrayList<>();
			// Display elements
			while(it.hasNext()) {
				 Map.Entry me = (Map.Entry)it.next();
				 Double key = (Double) me.getKey();
				 Double value = (Double) me.getValue();
				 if (value>maxValue){
					 maxKeyArray.clear();
					 maxKeyArray.add(key);
					 maxValue=value;
				 }
				 else if (value==maxValue){
					 maxKeyArray.add(key);
				 }
	
			}
			Collections.shuffle(maxKeyArray);
			
			maxKey = maxKeyArray.get(0);
			 
			System.out.println("Predicted class = "+maxKey+" with probability = "+maxValue);
			distribution.clear();
			
			
			BinaryTree trained_tree = dt.getTree();
			TestResult test_result = new TestResult (test_file,trained_tree);
		}
		else if (options.contains("random")){
			RandomTree rt = new RandomTree(colArray,attributes,-5.0,pruning_thr);
			BinaryTree root = rt.getTree();
			printTrainingData(root);
			
			//Find predicted class
			HashMap <Double, Double> distribution = rt.getDistribution();
			
			Set set = distribution.entrySet();
			  
			// Get an iterator
			Iterator it =  set.iterator();
			Double  maxValue=0.0,maxKey=0.0;
			ArrayList <Double> maxKeyArray= new ArrayList<>();
			// Display elements
			while(it.hasNext()) {
				 Map.Entry me = (Map.Entry)it.next();
				 Double key = (Double) me.getKey();
				 Double value = (Double) me.getValue();
				 if (value>maxValue){
					 maxKeyArray.clear();
					 maxKeyArray.add(key);
					 maxValue=value;
				 }
				 else if (value==maxValue){
					 maxKeyArray.add(key);
				 }
	
			}
			Collections.shuffle(maxKeyArray);

			maxKey = maxKeyArray.get(0);
			
			
			BinaryTree trained_tree = rt.getTree();
			new TestResult (test_file,trained_tree);
			
		}
		else if (options.contains("forest")){
			if (option.contains("3"))
			{
				DecisionForest df3= new DecisionForest(3,test_file,colArray,attributes,-5.0,pruning_thr);
			}
			else
			{
				DecisionForest df15 = new DecisionForest(15,test_file,colArray,attributes,-5.0,pruning_thr);
			}
		}
		
				
			

		
		
	}

	
	public static void printRow (ArrayList<ArrayList<Double>> rowArray ){
        System.out.println("Examples= "+rowArray.size());
        for (int w=0;w<rowArray.size();w++){
	        ArrayList<Double> a= rowArray.get(w);
	        System.out.println(" row "+(w+1) + "= ");
	        for(int x=0; x<a.size();x++){
	        	System.out.print(a.get(x)+ " " );
	        }
	        System.out.println("");
	        
        } 
		
	}
	public static void printCol( ArrayList<ArrayList<Double>> colArray){
	        System.out.println("Array Size= "+colArray.size());
	        for (int w=0;w<colArray.size();w++){
	        ArrayList<Double> a= colArray.get(w);
	        System.out.println(" Column "+(w+1) + " size= "+ a.size());
	        for(int x=0; x<a.size();x++){
	        	System.out.print(a.get(x)+" ");
	        }
	        System.out.println();
        }
		
	}
	public static void test (){
     	BinaryTree root = new BinaryTree(3,1.5,0.0,1);

    	ArrayList <BinaryTree> queue= new ArrayList();
    	


        queue.add(root);
      	
        while (!queue.isEmpty()){
        	BinaryTree a=queue.get(0);
        	System.out.println(a.getAttributeNumber()+" "+a.getThreshold());
        	if (a.hasLeft()) {
        		queue.add(a.getLeft());
        		
        		}
        	if (a.hasRight()){
        		queue.add(a.getRight());

        	}
        	queue.remove(0);
        }
	}
	
	public static void printTrainingData(BinaryTree root ){
		ArrayList <BinaryTree> queue= new ArrayList();
		ArrayList<Integer> idList = new ArrayList<>();
		//BinaryTree root = ((RandomTree) dt).getTree();
        queue.add(root);
      	int id=1;
      	idList.add(id);
        while (!queue.isEmpty()){
        	BinaryTree a = queue.get(0);
        	id = idList.get(0);
        	System.out.printf("tree=%2d, node=%3d, feature=%2d, thr=%6.2f, gain=%f\n", 0,id ,a.getAttributeNumber(), a.getThreshold(), a.getInfoGain());
        	id++;
        	if (a.hasLeft()) {
        		queue.add(a.getLeft());
        		id = idList.get(0)*2;
        		idList.add(id);
        		
        		}
        	if (a.hasRight()){
        		queue.add(a.getRight());
        		id = idList.get(0)*2+1;
        		idList.add(id);

        	}
        	queue.remove(0);
        	idList.remove(0);
        }
		
	} 


}

/*
 *       		BinaryTree root = dt.getTree();
		@SuppressWarnings({ "rawtypes", "unchecked" })
		
		
		ArrayList <BinaryTree> queue= new ArrayList();
        queue.add(root);
      	
        while (!queue.isEmpty()){
        	BinaryTree a = queue.get(0);
        	System.out.println(a.getAttributeNumber()+" "+a.getThreshold()+" "+a.getNumberOfexamples());
        	if (a.hasLeft()) {
        		queue.add(a.getLeft());
        		
        		}
        	if (a.hasRight()){
        		queue.add(a.getRight());

        	}
        	queue.remove(0);
        }

 * */
