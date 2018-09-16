package maximumWeightIndependentSet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class mwis {
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("/Users/Fred/Desktop/mwis.txt"));
			String token = br.readLine();
			int nNodes = Integer.parseInt(token);
			int nodes[] = new int[nNodes];
			int i = 0;
			while((token = br. readLine()) != null)
				nodes[i++] = Integer.parseInt(token);
			WIS_Solver solver = new WIS_Solver(nodes);
			solver.solve();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class WIS_Solver{
	int[] nodes, maxW, set;
	WIS_Solver(int[] nodes){
		this.nodes = nodes;
		maxW = new int[nodes.length];
		set = new int[nodes.length];
	}
	
	public void solve(){
		fillMaxW();
		reconstruct();
		int[] indice = {1, 2, 3, 4, 17, 117, 517, 997};
		for(int i : indice)
			System.out.print(set[i-1]);
	}
	
	private void fillMaxW(){
		maxW[0] = nodes[0];
		maxW[1] = nodes[0] > nodes[1] ? nodes[0] : nodes[1];
		for(int i = 2; i < nodes.length; i++){
			int a = maxW[i-1];
			int b = maxW[i-2] + nodes[i];
			maxW[i] = a > b ? a : b;
		}
	}
	
	private void reconstruct(){
		int i = nodes.length - 1;
		while(i > 1){
			if(maxW[i] == maxW[i-2] + nodes[i]){
				set[i] = 1;
				i -= 2;
			} else
				i--;
		}
		if(maxW[i] == nodes[0])
			set[0] = 1;
		else
			set[1] = 1;	
	}
}