package dijkstra;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class dijkastra {

	public static void main(String[] args) {

		try {
			BufferedReader br = new BufferedReader(new FileReader("/Users/Fred/Desktop/dijkstraData.txt"));
			List<Vertex> vertice = new ArrayList<>();
			String token;
			int size = 200;
			
			for(int i = 1; i <= size; i++)	
				vertice.add(new Vertex(i));	
			
			while((token = br.readLine()) != null){			
				String tokens[] = token.trim().split("[\\s]+");
				Vertex vertex = vertice.get(Integer.parseInt(tokens[0]) - 1);
				for(int i = 1; i < tokens.length; i++){
					String[] neighbor = tokens[i].split(",");
					int neighbor_lable = Integer.parseInt(neighbor[0]);
					int neighbor_distance = Integer.parseInt(neighbor[1]);
					vertex.addNeighbor(vertice.get(neighbor_lable - 1), neighbor_distance);				
				}
				vertice.add(vertex);
			}
			
			br.close();
			dijkastraSolver ds = new dijkastraSolver(vertice);
			ds.solve();
			ds.printResult();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class dijkastraSolver {
	private List<Vertex> vertice;
	
	dijkastraSolver(List<Vertex> vertice){
		this.vertice = vertice;
	}
	
	public void solve(){
		PriorityQueue<Vertex> border = new PriorityQueue<Vertex>();
		border.add(vertice.get(0));
		vertice.get(0).setDistance(0);
		while(!border.isEmpty()){
			Vertex curr = border.poll();
			curr.setExplored(true);
			for(Vertex neighbor : curr.getNeighbors().keySet()){
				if(!neighbor.explored()){
					int distance = curr.getDistance() + curr.distance(neighbor);
					if(distance < neighbor.getDistance()){
						border.remove(neighbor);
						neighbor.setDistance(distance);
						neighbor.setPath(curr);
						border.add(neighbor);
					}
				}
			}
		}
	}
	
void printResult(){
		int[] indice = {7,37,59,82,99,115,133,165,188,197};
		for(int i : indice)
			System.out.print(vertice.get(i-1).getDistance() + " ");
	}
}

class Vertex implements Comparable<Vertex>{
	int lable, distance;
	boolean explored;
	List<Integer> path = new LinkedList<>();
	Map<Vertex, Integer> neighbors = new HashMap<>();
	
	Vertex(int lable){
		this.lable = lable;
		explored = false;
		distance = 1000000;
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
	
	public int getDistance(){
		return distance;
	}
	
	public int distance(Vertex neighbor){
		return neighbors.get(neighbor);
	}
	
	public void setDistance(int dist){
		distance = dist;
	}
	
	public Map<Vertex, Integer> getNeighbors(){
		return neighbors;
	}
	
	public void addNeighbor(Vertex neighbor_lable, int neighbor_distance){
		neighbors.put(neighbor_lable, neighbor_distance);
	}
	
	public List<Integer> getPath(){
		return path;
	}
	
	public void setPath(Vertex v){
		path.clear();
		path.addAll(v.getPath());
		path.add(lable);
	}

	@Override
	public int compareTo(Vertex v) {
		return Integer.compare(distance, v.getDistance());
	}
}
