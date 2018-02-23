package com.assign.cse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DecisionForest {
	private int forestSize;
	private ArrayList<ArrayList<Double>> colArray = new ArrayList<ArrayList<Double>> ();
	private  ArrayList<Integer>  attributes;
	private Double defaultValue;
	private BinaryTree bt; 
	private int bestAttribute;
	private double bestThreshold;
	private String test_file;
	private int pruningValue;
	private HashMap<Double, ArrayList<Double>> listOfDistribution;
	HashMap <Double, Double> totalDistribution =new HashMap<>();
	private double bestProbability;
	private ArrayList <BinaryTree> treeList= new ArrayList <>();
	
	public DecisionForest(int size, String test_file, ArrayList<ArrayList<Double>> colArray,  ArrayList<Integer>  attributes, Double defaultValue,int pruningValue) {
		// TODO Auto-generated constructor stub
		this.forestSize=size;
		this.colArray=colArray;
		this.attributes=attributes;
		this.defaultValue=defaultValue;
		this.pruningValue=pruningValue;
		this.test_file=test_file;
		forest (size);
		
	}
	
	void forest (int size){


		listOfDistribution = new HashMap<Double, ArrayList<Double>> ();
		for (int i=0;i<size;i++){
			RandomTree rt = new RandomTree(colArray,attributes,-5.0,pruningValue);
			BinaryTree trained_tree = rt.getTree();
			printTrainingData(trained_tree,i);
			treeList.add(trained_tree);
			HashMap <Double, Double> distribution = new HashMap<>();
			distribution.clear();
			distribution = rt.getDistribution();
			totalDistribution(distribution);

			//new TestResult (test_file,trained_tree);
			
			double maxKey = maxKeyValue();
			System.out.println("Predicted Class= "+maxKey+" with probability = "+ bestProbability/size);
			
		}
		new TestResultForest (test_file,treeList);

		
		
	}
	public void BinaryTreeArrayToClass(){
		
	}
	
	public void totalDistribution (HashMap <Double, Double>  distribution){

		
		Set set = distribution.entrySet();
		  
		// Get an iterator
		Iterator it =  set.iterator();

		// Display elements
		while(it.hasNext()) {
			 Map.Entry me = (Map.Entry)it.next();
			 Double key = (Double) me.getKey();
			 Double value = (Double) me.getValue();
			 
			 if (totalDistribution.containsKey(key)){
				 value= value + totalDistribution.get(key);
				 totalDistribution.replace(key, value);

			 }
			 else {
				 totalDistribution.put(key, value);
			 }

		}

		
	}
	
	public Double maxKeyValue( ){
		Set set = totalDistribution.entrySet();
		  
		// Get an iterator
		Iterator it =  set.iterator();

		Double  maxValue=0.0,maxKey=0.0;
		ArrayList <Double> maxKeyArray= new ArrayList<>();
		//find max element
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
		 bestProbability = maxValue;
		
		 return maxKey;
		
	}
 
	
	public void printTrainingData(BinaryTree root,int treeId ){
		ArrayList <BinaryTree> queue= new ArrayList();
		int count = queue.size();
		ArrayList<Integer> idList = new ArrayList<>();
		//BinaryTree root = ((RandomTree) dt).getTree();
        queue.add(root);
      	int id=1;
      	idList.add(id);
        while (!queue.isEmpty()){
        	BinaryTree a = queue.get(0);
        	id = idList.get(0);
        	
        	System.out.printf("tree=%2d, node=%3d, feature=%2d, thr=%6.2f, gain=%f\n", treeId,id ,a.getAttributeNumber(), a.getThreshold(), a.getInfoGain());
        	//System.out.println("Dist = "+a.getNodeDistribution());
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
        System.out.println(" ================================================================");
	} 

}
