package prim_mst;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class PrimsMST {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("/Users/Fred/Desktop/edges.txt"));
			List<Vertex> vertice = new ArrayList<>();
			List<Edge> edges = new ArrayList<>();
			String token = br.readLine();
			String sizes[] = token.trim().split("[\\s]+");
			int vSize = Integer.parseInt(sizes[0]);
			int eSize = Integer.parseInt(sizes[1]);
			
			for(int i = 1; i <= vSize; i++)	
				vertice.add(new Vertex(i));	
			
			while((token = br.readLine()) != null){			
				String tokens[] = token.trim().split("[\\s]+");
				Vertex v1 = vertice.get(Integer.parseInt(tokens[0]) - 1);
				Vertex v2 = vertice.get(Integer.parseInt(tokens[1]) - 1);
				int cost = Integer.parseInt(tokens[2]);
				Edge edge = new Edge(v1, v2, cost);
				v1.addNeighbor(v2, edge);
				v2.addNeighbor(v1, edge);
				edges.add(edge);
			}
			System.out.println(edges.size());
			br.close();
			PrimSolver pSolver = new PrimSolver(vertice, edges);
			pSolver.solve();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class PrimSolver {
	List<Vertex> vertice;
	List<Edge> edges;
	PrimSolver(List<Vertex> vertice, List<Edge> edges){
		this.vertice = vertice;
		this.edges = edges;
	}
	
	public void solve(){
		int cost = 0;
		Edge currE ;
		Vertex currV = vertice.get(0);
		Map<Vertex, Edge> neighbors = currV.getNeighbors();
		PriorityQueue<Edge> border = new PriorityQueue<>(neighbors.values());
		currV.setExplored(true);

		while(!border.isEmpty()){
			currE = border.poll();
			cost += currE.getCost();
			currV =  currE.unexploredVertex();
			currV.setExplored(true);
			neighbors = currV.getNeighbors();
			for(Vertex v : currV.getNeighbors().keySet()){
				if(!v.explored())
					border.add(neighbors.get(v));
				else
					border.remove(neighbors.get(v));
			}
		}
		System.out.println(cost);
	}
}

class Vertex{
	int lable;
	boolean explored;
	Vertex leader;
	Map<Vertex, Edge> neighbors = new HashMap<>();
	
	Vertex(int lable){
		this.lable = lable;
		explored = false;
		leader = this;
	}
	
	public Vertex getLeader(){
		return leader;
	}
	
	public void setLeader(Vertex v){
		leader = v;
	}
	
	public int getLable(){
		return lable;
	}
	
	public void setExplored(boolean b){
		explored = b;
	}

	public boolean explored(){
		return explored;
	}
	
	public Map<Vertex, Edge> getNeighbors(){
		return neighbors;
	}
	
	public void addNeighbor(Vertex v, Edge e){
		neighbors.put(v, e);
	}
}

class Edge implements Comparable<Edge>{
	
	Vertex v1, v2;
	int cost;
	boolean explored;
	
	Edge(Vertex v1, Vertex v2, int cost){
		this.v1 = v1;
		this.v2 = v2;
		this.cost = cost;
		explored = false;
	}
	
	public Vertex[] getVertice(){
		return new Vertex[]{v1, v2};
	}
	
	public Vertex unexploredVertex(){
		if (!v1.explored()) return v1;
		if (!v2.explored()) return v2;
		return null;
	}
	
	public int getCost(){
		return cost;
	}

	public boolean explored(){
		return explored;
	}
	
	public void setExplored(boolean b){
		explored = b;
	}
	
	@Override
	public int compareTo(Edge e) {
		return Integer.compare(cost, e.getCost());
	}
}

