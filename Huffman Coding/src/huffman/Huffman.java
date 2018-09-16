package huffman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Queue;

public class Huffman {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("/Users/Fred/Desktop/huffman.txt"));
			String token = br.readLine();
			int nNodes = Integer.parseInt(token);
			Queue<Node> nodes = new PriorityQueue<>();
			while((token = br. readLine()) != null)
				nodes.add(new Node(Integer.parseInt(token)));	
			HuffmanSolver hs = new HuffmanSolver(nodes);
			hs.solve();
			hs.print();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class HuffmanSolver{
	Queue<Node> nodes;
	int max, min;
	
	HuffmanSolver(Queue<Node> nodes){
		this.nodes = nodes;
		max = 0;
		min = 1000;
	}
	
	public void solve(){
		if(nodes.size() == 1)  return;
		Node n1 = nodes.poll();
		Node n2 = nodes.poll();
		Node n = new Node(n1.weight() + n2.weight());
		n1.setParent(n);
		n2.setParent(n);
		n.setLeftChild(n1);
		n.setRightChild(n2);
		nodes.add(n);
		solve();
		int l = n.length() + 1;
		n1.setLength(l);
		n2.setLength(l);
		if(max < l && n1.isLeaf() && n2.isLeaf())
			max = l;
		if(min > l && (n1.isLeaf() || n2.isLeaf()))
			min = l;
	}
	
	public void print(){
		System.out.println("max = " + max + "\nmin = " + min);
	}
}

class Node implements Comparable<Node> {	
	int weight, length;
	Node parent, lChild, rChild;

	Node(int i){
		weight = i;
		length = 0;
		parent = null;
		lChild = null;
		rChild = null;
	}
	
	public int weight(){
		return weight;
	}
	
	public int length(){
		return length;
	}
	
	public void setLength(int l){
		length = l;
	}

	public void setParent(Node n){
		parent = n;
	}
	
	public void setLeftChild(Node n){
		lChild = n;
	}
	
	public void setRightChild(Node n){
		rChild = n;
	}

	public Node parent(){
		return parent;
	}

	public Node leftChild(){
		return lChild;
	}
	
	public Node rightChild(){
		return rChild;
	}
	
	public boolean isLeaf(){
		return lChild == null && rChild == null;
	}

	@Override
	public int compareTo(Node n) {
		return Integer.compare(weight, n.weight());
	}
}