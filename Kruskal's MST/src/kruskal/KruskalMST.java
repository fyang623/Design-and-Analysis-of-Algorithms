package kruskal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.lang.Integer;

public class KruskalMST {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("/Users/Fred/Desktop/clustering.txt"));
			List<Vertex> vertice = new ArrayList<Vertex>();
			List<Edge> edges = new ArrayList<Edge>();
			String tokens[], token = br.readLine();
			int size = Integer.parseInt(token);
			
			for(int i = 1; i <= size; i++)	
				vertice.add(new Vertex(i));	
			
			while((token = br.readLine()) != null){
				tokens = token.trim().split("[\\s]+");
				Vertex v1 = vertice.get(Integer.parseInt(tokens[0]) - 1);
				Vertex v2 = vertice.get(Integer.parseInt(tokens[1]) - 1);
				int cost = Integer.parseInt(tokens[2]);
				Edge edge = new Edge(v1, v2, cost);
				v1.addNeighbor(v2, edge);
				v2.addNeighbor(v1, edge);			
				edges.add(edge);			
			}
			br.close();
			
			KruskalSolver ks = new KruskalSolver(vertice, edges);
			int spacing = ks.solve();
			System.out.println(spacing);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

class KruskalSolver {
	List<Vertex> vertice;
	List<Edge> edges;
	KruskalSolver(List<Vertex> vertice, List<Edge> edges){
		this.vertice = vertice;
		this.edges = edges;
	}
	
	public int solve(){
		PriorityQueue<Edge> unexplored = new PriorityQueue<Edge>();	
		Vertex nodes[], v1, v2;
		Edge curr = null;
		int counter = vertice.size();
		
		for(Edge e : edges)	unexplored.add(e);
		
		while(counter >= 4){
			curr = unexplored.poll();
			nodes = curr.getVertice();
			v1 = nodes[0].getLeader();
			v2 = nodes[1].getLeader();
			if(v1 == v2)	continue;	

			if(v1.getRank() == v2.getRank()){
				v1.setLeader(v2);
				v2.incrementRank();
			}
			else if(v1.getRank() > v2.getRank())
				v2.setLeader(v1);
			else
				v1.setLeader(v2);
			counter--;
		}	
		
		return curr.getCost();
	}
}


class Vertex{
	
	int lable, rank;
	Vertex leader;
	Map<Vertex, Edge> neighbors = new HashMap<Vertex, Edge>();
	
	Vertex(int i){
		lable = i;
		rank = 0;
		leader = this;
	}
	
	public int getRank(){
		return rank;
	}
	
	public void incrementRank(){
		rank++;
	}
	
	public Vertex getLeader(){
		if(leader == this) 
			return leader;
		leader = leader.getLeader();
		return leader;
		
	}
	
	public Vertex directLeader(){
		return leader;	
	}
	
	public void setLeader(Vertex v){
		leader = v;
	}
	
	public int getLable(){
		return lable;
	}
	
	public Map<Vertex, Edge> getNeighbors(){
		return neighbors;
	}
	
	public void addNeighbor(Vertex v, Edge e){
		neighbors.put(v, e);
	}
	
	public boolean equals(Vertex v){
		if(lable == v.getLable())
			return true;
		else
			return false;
	}
}

class Edge implements Comparable<Edge>{
	
	Vertex v1, v2;
	int cost;
	
	Edge(Vertex v1, Vertex v2, int cost){
		this.v1 = v1;
		this.v2 = v2;
		this.cost = cost;
	}
	
	public Vertex[] getVertice(){
		return new Vertex[]{v1, v2};
	}
	
	public int getCost(){
		return cost;
	}
	
	@Override
	public int compareTo(Edge e) {
		if(cost < e.getCost())	return -1;
		if(cost > e.getCost())	return 1;
		return 0;
	}
}