import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Karger {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("/Users/Fred/Desktop/kargerMinCut.txt"));
		Map<Integer, List<Integer>> data = new HashMap<>();
		for(int i = 0; i < 200; i++){
			String token = br.readLine();
			String tokens[] = token.split("\\t");
			Integer vertex = Integer.parseInt(tokens[0]);
			List<Integer> neighbors = new LinkedList<>();
			for(int j = 1; j < tokens.length; j++)
				neighbors.add(Integer.parseInt(tokens[j]));
			data.put(vertex, neighbors);
		}
		br.close();
		Graph graph = new Graph(data);
		System.out.println("\nMinCut = " + graph.calMinCut());
	}
}

class Graph {
	
	Map<Integer, List<Integer>> data = new HashMap<>();
	Map<Integer, List<Integer>> data_copy = new HashMap<>();
	List<Integer> keyList = new LinkedList<>();
	int num_edges = 0;
	
	Graph(Map<Integer, List<Integer>> data){
		this.data = data;
	}
	
	public int calMinCut() {
		int k, min = 200, counter = 0;
		for(int i=1; i<=100000; i++){
			k = calCut();
			if(min > k) min = k;
			if(k == 17)
				System.out.println(i + "th loop:\tcounter = " + (++counter));
		}
		return min;
	}
	
	public int calCut(){		
		this.prepData();
		while(keyList.size() > 2){
			int[] edge = this.selectRandomEdge();
			Integer v1 = edge[0], v2 = edge[1];
			List<Integer> v1_neighbors = data_copy.get(v1);
			List<Integer> v2_neighbors = data_copy.get(v2);
			
			for(Integer neighbor : v2_neighbors){
				List<Integer> far_neighbors = data_copy.get(neighbor);	
				for(int i=0; i<far_neighbors.size(); i++) 
					if (far_neighbors.get(i).equals(v2))
						far_neighbors.set(i, v1);
			}
			v1_neighbors.addAll(v2_neighbors);
			while(v1_neighbors.remove(v1))
				num_edges--;
			data_copy.put(v1, v1_neighbors);
			data_copy.remove(v2);	
			keyList.remove(v2);
		}
		return data_copy.get(keyList.get(0)).size();
	}
		
	private void prepData() {
		num_edges = 0;
		keyList.clear();
		data_copy.clear();
		for(Integer vertex : data.keySet()){
			List<Integer> neighbors = new LinkedList<>();
			neighbors.addAll(data.get(vertex));
			keyList.add(vertex);
			data_copy.put(vertex, neighbors);
			num_edges += neighbors.size();
		}
	}
	
	private int[] selectRandomEdge(){
		Random r = new Random();
		int count = 0, temp = r.nextInt(num_edges);
		int v1 = 0, v2 = 0;
		for(Integer v : keyList){
			count += data_copy.get(v).size();
			if(count > temp) {
				v1 = v;
				v2 = data_copy.get(v).get(temp - count + data_copy.get(v).size());
				break;
			}
		}
		return new int[]{v1, v2};
	}
}
