package kruskal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Stack;

public class ClusterAlgo {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("/Users/Fred/Desktop/clustering_big.txt"));
			String token = br.readLine();
			String tokens[] = token.trim().split("[\\s]+");
			int nNodes = Integer.parseInt(tokens[0]);
			int nBits = Integer.parseInt(tokens[1]);
			HashSet<BitSet> nodes = new HashSet<>();
			while((token = br. readLine()) != null){
				BitSet node = new BitSet(nBits);					
				for(int j = 0; j < nBits; j++)
					if(token.charAt(2*j) == '1')
						node.set(j);
				nodes.add(node);
			}
			ClusterSolver cs = new ClusterSolver(nodes, 2, nBits);
			cs.solve();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class ClusterSolver{
	HashSet<BitSet> nodes, nodes_copy = new HashSet<>();
	int spacing, nBits;
	
	ClusterSolver(HashSet<BitSet> nodes, int spacing, int nBits){
		this.nodes = nodes;
		this.spacing = spacing;
		this.nBits = nBits;
		nodes_copy.addAll(nodes);
	}
	
	public void solve(){
		int counter = 0;
	
		for(BitSet node : nodes){
			if(!nodes_copy.contains(node))
				continue;
			Stack<BitSet> stack = new Stack<BitSet>();
			BitSet curr, next;
			stack.push(node);
			while(!stack.isEmpty()){
				curr = stack.pop();
				for(int i = 0; i < nBits; i++)
					for(int j = i; j < nBits; j++){
						next = (BitSet) curr.clone();
						next.flip(i);
						if(i != j) 
							next.flip(j);
						if(nodes_copy.contains(next)){
							stack.push(next);
							nodes_copy.remove(next);
						}
					}
			}
			counter++;
			System.out.println(counter);
		}
	}
}
