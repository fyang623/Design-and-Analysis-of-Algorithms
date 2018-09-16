import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class SCC {

	public static void main(String[] args) {
		int size = 875714;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("/Users/Fred/Desktop/SCC.txt"));
			List<Vertex> vertice = new ArrayList<>();
			String token;
			for(int i = 1; i <= size; i++)
				vertice.add(new Vertex(i));

			while((token = br.readLine()) != null){
				if(!token.matches("[\\s]*")){
					String tokens[] = token.split("[\\s]+");
					int v1 = Integer.parseInt(tokens[0]);
					int v2 = Integer.parseInt(tokens[1]);
					vertice.get(v1-1).addOutEdge(v2);
					vertice.get(v2-1).addInEdge(v1);
				}
			}
			br.close();
			SCCSolver ss = new SCCSolver(vertice);
			ss.compute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class SCCSolver{
	List<Vertex> vertice;
	List<Vertex> finishTime = new ArrayList<>();
	int sizes[] = new int[5];
	Vertex s;
	
	SCCSolver(List<Vertex> vertice){
		this.vertice = vertice;
		finishTime.addAll(vertice);
	}
	
	public void compute(){
		reverseGraph();
		DFSLoop();
		reverseGraph();
		DFSLoop();
	}
	
	private void reverseGraph(){
		for(Vertex v : vertice) {
			v.switchInOut();
			v.setExplored(false);
		}
	}
	
	private void DFSLoop(){
		Arrays.fill(sizes, 0);
		List<Vertex> temp = new ArrayList<Vertex>();
		for (int i = finishTime.size(); i >= 1; i--){
			if(!finishTime.get(i-1).explored()){
				s = finishTime.get(i-1);
				List<Vertex> FT_sublist = DFS(finishTime.get(i-1));
				temp.addAll(FT_sublist);
			}
		}
		finishTime = temp;
		System.out.println();
		for(int i : sizes) System.out.print(i + "\t");
		System.out.println("\n");
	}
	
	private List<Vertex> DFS(Vertex v_start){
		Stack<Vertex> stack = new Stack<>();
		List<Vertex> popped = new LinkedList<>();
		v_start.setExplored(true);		
		stack.push(v_start);
		while(!stack.empty()){
			Vertex v = stack.pop();	
			popped.add(0, v);
			for(int i : v.getOutEdges()){
				Vertex vi = vertice.get(i-1);
				if(!vi.explored){
					vi.setExplored(true);
					stack.push(vi);
				}
			}			
		}
		if(popped.size()>sizes[0]) sizes[0] = popped.size();
		Arrays.sort(sizes);
		return popped;
	}
}

class Vertex{
	int v, t;
	Vertex l;
	boolean explored;
	List<Integer> inEdges = new ArrayList<>();
	List<Integer> outEdges = new ArrayList<>();
	
	Vertex(int v){
		this.v = v;
		t = v;
		explored = false;
	}
	
	public int getV(){
		return v;
	}
	
	public void setFinishTime(int time){
		t = time;
	}
	
	public void setExplored(boolean b){
		explored = b;
	}

	public boolean explored(){
		return explored;
	}
	
	public void setLeader(Vertex leader){
		l = leader;
	}
	
	public Vertex getLeader(){
		return l;
	}
	
	public List<Integer> getInEdges(){
		return inEdges;
	}
	
	public void addInEdge(int edge){
		inEdges.add(edge);
	}
	
	List<Integer> getOutEdges(){
		return outEdges;
	}
	
	public void addOutEdge(int edge){
		outEdges.add(edge);
	}
	
	public void switchInOut(){
		List<Integer> temp = inEdges;
		inEdges = outEdges;
		outEdges = temp;
	}
}

