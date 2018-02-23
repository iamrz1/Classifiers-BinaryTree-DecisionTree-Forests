package com.assign.cse;

import java.util.HashMap;

public class BinaryTree
{


	private BinaryTree left;
    private BinaryTree right;
    private int attributeNumber;
    private Double threshold;
    private Double infoGain;
    private int numberOfexamples;
    private HashMap <Double, Double> nodeDistribution = new HashMap<>();
    public HashMap<Double, Double> getNodeDistribution() {
		return nodeDistribution;
	}
	public void setNodeDistribution(HashMap<Double, Double> nodeDistribution) {
		this.nodeDistribution = nodeDistribution;
	}
	public int getNumberOfexamples() {
		return numberOfexamples;
	}

	private boolean hasright, hasleft;
    private int size;
	public BinaryTree(int attribute,Double threshold,Double infoGain,int numberOfexamples)
    {
        this.attributeNumber = attribute;
        this.threshold = threshold;
        this.numberOfexamples = numberOfexamples;
        this.infoGain=infoGain;
        this.hasleft=false;
        this.hasright=false;
        this.size=1;
    }
	public BinaryTree()
    {
		
    }

    
    public int getAttributeNumber() {
    	
		return attributeNumber;
	}



	public Double getThreshold() {
		return threshold;
	}

	public Double getInfoGain() {
		return infoGain;
	}

    public BinaryTree getLeft() {
		return left;
	}



	public void setLeft(BinaryTree left) {
		this.left = left;
		hasleft=true;
		size=size+left.getSize();
		
	}


	public BinaryTree getRight() {
		return right;
	}



	public void setRight(BinaryTree right) {
		this.right = right;
		hasright = true;
		size = size + right.getSize();
	}
	
	public int getSize(){
		return size;
	}

	public boolean hasLeft() {
		return hasleft;
	}
	public boolean hasRight() {
		return hasright;
	}
	
    public void printNode()
    {
        System.out.println("Attribute = "+ attributeNumber +" col= "+ threshold);
    }


}
/*
     	BinaryTree root = new BinaryTree(3);
    	BinaryTree n1 = new BinaryTree(1);
    	BinaryTree n2 = new BinaryTree(4);
    	BinaryTree n3 = new BinaryTree(2);
    	BinaryTree n4 = new BinaryTree(5);

        root.left = n1;
        root.right = n2;
        root.right.left = n3;
        root.right.right = n4;
 */