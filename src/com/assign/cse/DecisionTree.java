package com.assign.cse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import java.util.Iterator;

public class DecisionTree {
	private ArrayList<ArrayList<Double>> examples = new ArrayList<ArrayList<Double>> ();
	private  ArrayList<Integer>  attributes;
	private Double defaultValue;
	private BinaryTree bt; 
	private int bestAttribute;
	private double bestThreshold;
	private Double bestInfoGain;
	private int pruningValue;
	HashMap <Double, Double> distributionArray;
	

	public BinaryTree getTree() {
		return bt;
	}
	
	public HashMap <Double, Double> getDistribution(){
		
		return distributionArray;
	}
	public DecisionTree(ArrayList<ArrayList<Double>> examples, ArrayList<Integer>  attributes,Double defaultValue,int pruningValue){
		this.examples=examples;
		this.attributes=attributes;
		this.defaultValue=defaultValue;
		this.pruningValue=pruningValue;
		bt = DTL(examples,attributes,defaultValue);
		bestAttribute=-1;
		bestThreshold=-1.0;

		distributionArray = distribution(examples);
	
	}
	
	private BinaryTree DTL(ArrayList<ArrayList<Double>> examples,  ArrayList<Integer>  attributes, Double defaultValue) {
		this.examples=examples;
		this.attributes=attributes;
		this.defaultValue=defaultValue;
		bestInfoGain=0.0;
		int numberOfExamples = examples.get(0).size();
		int numberOfAttributes = examples.size();
		
		     
		//System.out.println("");
		//System.out.println("numberOfExamples = "+ numberOfExamples);
		//for (int i=0; i< numberOfExamples;i++)
		//System.out.println(examples.get(numberOfAttributes-1).get(i));
		//System.out.println("");
		if (numberOfExamples<pruningValue) {
			//examples empty
			//System.out.println("Less Than PrununingValue");
			return new BinaryTree(-1, -1.0,-1.0,numberOfExamples);
			
		}
		else {

			ArrayList<Double> classValues = examples.get(numberOfAttributes-1);
			boolean equal = true;
			
			if (numberOfExamples==1) {
				//System.out.println("Just one class.");
				return new BinaryTree(-1, classValues.get(0),-1.0,numberOfExamples);
			}
			else {
				for (int i=1; i < numberOfExamples; i++){
					int difference = (int) ((classValues.get(i-1) - classValues.get(i)));
					if (difference!=0) {
						equal=false;
						break;
					}
				}
				
				if (equal){
					//System.out.println("All class labels are equal.");
					return new BinaryTree(-1, classValues.get(0),-1.0,numberOfExamples);
					
				}
				else{ 
					//More than one example and All class values are not equal
					//System.out.println("Third part yet to be implemented");
					//To find best attribute and Threshold
					chooseBestAttribute();
					//best attribute(index) and its threshold (double) is now stored as global variable.
					
					BinaryTree tree = new BinaryTree (bestAttribute,bestThreshold,bestInfoGain,numberOfExamples);
					
					// now lets use these to create bestLeftsubset and  bestrightsubset of examples;
					ArrayList<ArrayList<Double>> bestLeftExamples = new ArrayList<>();
					ArrayList<ArrayList<Double>> bestRightExamples = new ArrayList<>();
					bestRightExamples.clear();

					ArrayList<Integer> leftIndexArray = new ArrayList<>();
					ArrayList<Integer> rightIndexArray = new ArrayList<>();
					
					for (int i=0;i < numberOfExamples ;i++){
						double attributeValue = examples.get(bestAttribute).get(i);
						if (attributeValue<bestThreshold){
							leftIndexArray.add(i);
						}
						else {
							rightIndexArray.add(i);
						}
						
					}
					//System.out.println("Left Index Size="+leftIndexArray.size());
					if (leftIndexArray.isEmpty()){
						//System.out.println("====================> leftIndexArray = 0");

					}
					for (int i=0;i<numberOfAttributes;i++){
						ArrayList<Double> tempArray = new ArrayList<>();
						for(int j=0;j<leftIndexArray.size();j++){
							double temp = examples.get(i).get(leftIndexArray.get(j));
							tempArray.add(temp);
							
						}
						
						bestLeftExamples.add(tempArray);
					}
					
					if (rightIndexArray.isEmpty()){

						//System.out.println("====================> rightIndexArray = 0");
						
					}
					for (int i=0;i<numberOfAttributes;i++){
						ArrayList<Double> tempArray = new ArrayList<>();
						for(int j=0;j<rightIndexArray.size();j++){
							double temp = examples.get(i).get(rightIndexArray.get(j));
							tempArray.add(temp);
							
						}
						
						bestRightExamples.add(tempArray);
					}

					
					//System.out.println("LeftExamples col Size = "+bestLeftExamples.size()+" LeftExample row Size = "+bestLeftExamples.get(0).size());
					//System.out.println("RightExamples col Size = "+bestRightExamples.size()+" Right Example row Size = "+bestRightExamples.get(0).size());
				
					
					
					//
					HashMap <Double, Double> probability = distribution(examples);
					Double maxProbability = Collections.max(probability.values());
					HashMap <Double,Double> classDistribution = new HashMap <>();
					classDistribution.clear();
					
					// Get a set of the entries
					Set set = probability.entrySet();
			  
					// Get an iterator
					Iterator it =  set.iterator();
					ArrayList<Double> keyList = new ArrayList<>();
					// Display elements
					while(it.hasNext()) {
						 Map.Entry me = (Map.Entry)it.next();
					    // System.out.print(me.getKey() + ": ");
						 //System.out.println(me.getValue());
						 Double key = (Double) me.getKey();
						 Double value = (Double) me.getValue();
						 if (value.equals(maxProbability))
							 keyList.add(key);
					}
					
					//One randon Best Attribute		
					 Collections.shuffle(keyList);

				     Double newDefaultValue = keyList.get(0);
				     //System.out.println("newDefaultValue =  "+ newDefaultValue);
				     //
				
				     //System.out.println("===========");
					
					
					
					tree.setLeft(DTL(bestLeftExamples,attributes,newDefaultValue));
					tree.setRight(DTL(bestRightExamples,attributes,newDefaultValue));
					//System.out.println("========================================");
					//System.out.println("one node done ");
					return tree;
				}
			}
		}
		
	}
	
	private void chooseBestAttribute (){
		Double maxGain=-1.0;
		bestAttribute=-1;
		bestThreshold=-1;

		int colSize= attributes.size();
		double rootEntropy = classifier(examples.get(examples.size()-1));
		//System.out.println("rootEntropy = " +rootEntropy);
		for (int i=0; i<colSize;i++){
			ArrayList<Double> attributeValues = examples.get(i);
			Double L = Collections.min(attributeValues);
			Double M = Collections.max(attributeValues);

			//System.out.println("\n====>for Atrtribute index = "+ i);
			if (L==M) continue;		
			for (int K = 1; K <= 50; K++){
				
				Double threshold = L + K*(M-L)/51;
				//System.out.println("\n==> Threshold = "+threshold);
				Double gain =-1000.0;
				double LRentropy = informationGain(threshold,i);
				gain = rootEntropy-LRentropy;
				if (gain> maxGain){
					maxGain=gain;
					bestThreshold=threshold;
					bestAttribute=i;
					
					//System.out.println("Gain = "+ gain);
					//System.out.println(" ");
									
				}
			}
			//System.out.println("best bestThreshold as of now  = "+bestThreshold);
			//System.out.println(" ");
			//System.out.println(" ");
			
		}
		//System.out.println("bestAttribute = "+bestAttribute+" Threshold= "+bestThreshold+"Max Max Gain = "+ maxGain);

		//System.out.println(" ");
		//System.out.println(" ");
		bestInfoGain = maxGain;
	}
	
	private double informationGain( double threshold,int attributeIndex){
		//System.out.println("// ");
		int classLabelIndex = examples.size()-1; //yeast = 7
		int totalClass = examples.get(classLabelIndex-1).size();// 1000
		
		ArrayList<Double> attributeValues = examples.get(attributeIndex);
		ArrayList<Double> classValues = examples.get(classLabelIndex);
		//gather indices for possible left subtree and right subtree
		ArrayList<Integer> leftIndexes = new ArrayList<>();
		ArrayList<Integer> rightIndexes = new ArrayList<>();

		double sum=0.0;
		for (int i=0; i<totalClass;i++){
			if(attributeValues.get(i)<threshold){ 
				//System.out.println("Attribute Value = "+attributeValues.get(i)+" < Threshold = "+threshold+" :=>Added to Left Index");
				leftIndexes.add(i);
			}
			else {
				rightIndexes.add(i);
			}
		}
		//indices for possible left subtree and right subtree is now gathered
		//System.out.println("left- index 0   = "+ rightIndex.get(0));
		ArrayList<Double> leftClass = arrayListGenerator(leftIndexes); 
		ArrayList<Double> rightClass = arrayListGenerator(rightIndexes); 
		double leftEntropy = classifier(leftClass);
		double rightEntropy = classifier(rightClass);
		//System.out.println("left- right entropy  = "+ (leftEntropy-rightEntropy));
		Double LRentropy=leftEntropy+rightEntropy;
		
		return LRentropy;
	}
	
	private Double classifier( ArrayList<Double> classArray){
		
		ArrayList<Double> classValues = new ArrayList<>();
		classValues.addAll(classArray);
		
		//System.out.println(" Recieved A node with size = "+classArray.size());
		int totalIndex = classValues.size();
		
		int K = examples.get(0).size();
		int k = totalIndex;
		double multiplier = (double)k/(double)K;



		double entropyTotal=0.0;
		
  		while (!classValues.isEmpty()){
  			
			Double rootValue = classValues.get(0);
			int rootOccurance =0;
			while(classValues.contains(rootValue)){
				classValues.remove(rootValue);
				rootOccurance++;
			}
			double entropy = entropyCalc(rootOccurance,totalIndex);
			entropyTotal = entropyTotal +  ( multiplier * entropy);
			/*
			System.out.println("");
			System.out.println("rootValue = "+ rootValue+ " rootOccurance "+ rootOccurance+" infoGain = "+entropyTotal);
			System.out.println("");
			*/
			
		}
  		//System.out.println("entropy= "+ entropyTotal);

		return entropyTotal;
	}
	
	private Double entropyCalc(int K1, int K){
		Double X = (double)(K1)/(double)K;
		Double ans = 0-(X*log2(X));
		//System.out.println("k1 = "+K1+" K = " +K+" X= "+ X+" - entropy = " +ans);
		return ans;
		
		
	}
	private ArrayList <Double> arrayListGenerator (ArrayList<Integer> indexArray){
		int arraySize = indexArray.size(); 
		int classLabelIndex = examples.size()-1;
		int totalClass = examples.get(classLabelIndex).size();
		ArrayList<Double> list = new ArrayList<> ();
		for (int i=0;i<arraySize;i++){
			//getting the index of classlabel column from examples, that has a new member of 'list' 
			int index = indexArray.get(i); 
			//adding the element of that index to 'list'
			list.add(examples.get(classLabelIndex).get(index));
			
		}
		return list ;
	} 
	
	
	private double log2 (Double x2){
		return Math.log10(x2)/Math.log10(2);
	}
	
	private HashMap <Double,Double> distribution(ArrayList<ArrayList<Double>> examples){
		HashMap <Double,Integer> classCount = new HashMap <>();

		int numberOfExamples = examples.get(0).size();
		int indexOfClass = examples.size() -1;
		
		for (int i=0; i< numberOfExamples; i++){
			Double z = examples.get(indexOfClass).get(i);
			if (classCount.containsKey(z)){
				//key already there
				int count = classCount.get(z)+1;
				classCount.replace(z, count);
			}
			else {
				classCount.put(z, 1);
			}
		}
		
		HashMap <Double,Double> classDistribution = new HashMap <>();
		classDistribution.clear();
		
		// Get a set of the entries
		Set set = classCount.entrySet();
  
		// Get an iterator
		Iterator it =  set.iterator();
  
		// Display elements
		while(it.hasNext()) {
			 Map.Entry me = (Map.Entry)it.next();
			 Double key = (Double) me.getKey();
			 int value = (int) me.getValue();
			 double probability=  (double)value/(double)numberOfExamples;
			 //System.out.println("key = " +key+ "probability = "+probability);
		     
			 classDistribution.put(key, probability);
		}
		classCount.clear();
		
		return classDistribution;
	}


}

/*
  		while (!classValues.isEmpty()){
			Double rootValue = classValues.get(0);
			int rootOccurance =0;
			while(classValues.contains(rootValue)){
				classValues.remove(rootValue);
				rootOccurance++;
			}
			double ratio = rootOccurance/totalClass;
			sum= sum- ratio * log2(ratio);
			
			System.out.println("Class = "+ rootValue+ " rootOccurance "+ rootOccurance+" Sum = "+sum);
		}
		
	private Double informationGain( double threshold,int attributeIndex){
		System.out.println("// informaton gain: threshold= "+threshold+" attributeIndex = "+ attributeIndex);
		int classLabelIndex = examples.size()-1; //yeast = 7
		double rootEntropy = classifier(examples.get(classLabelIndex));
		int totalClass= examples.get(classLabelIndex).size();// 1000
		int K1=0,K2=0;
		ArrayList<Double> attributeValues = examples.get(attributeIndex);
		ArrayList<Double> classValues = examples.get(classLabelIndex);
		ArrayList<Integer> leftIndex = new ArrayList<>();
		ArrayList<Integer> rightIndex = new ArrayList<>();
		Double sum=0.0;
		for (int i=0; i<totalClass;i++){
			if(attributeValues.get(i)<threshold){ 
				leftIndex.add(i);
				K1++;
			}
			else {
				rightIndex.add(i);
				K2++;
			}
		}
		ArrayList<Double> leftClass = arrayListGenerator(leftIndex); 
		ArrayList<Double> rightClass = arrayListGenerator(rightIndex); 
		double leftEntropy = classifier(leftClass);
		double rightEntropy = classifier(rightClass);
		System.out.println("Return entropy sum = "+ (rootEntropy-leftEntropy-rightEntropy));
		return rootEntropy-leftEntropy-rightEntropy;
	}
		
					for (int i=0;i < classSize;i++){
						double attributeValue = examples.get(bestAttribute).get(i);
						if (attributeValue<bestAttribute){
							ArrayList<Double> temp = new ArrayList<>();
							for (int j=0;j < examplesSize;j++){
								ArrayList<Double> tempArray = new ArrayList<>();
								double tempAttribute= examples.get(i).get(j);
								tempArray.add(tempAttribute);
								bestLeftExamples.add(j, tempArray);
							}
							
						}
						
					}
		
*/
