package knapsack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Knapsack2 {
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("/Users/Fred/Desktop/knapsack.txt"));
			String tokens[] = br.readLine().trim().split("[\\s]+");
			int capacity = Integer.parseInt(tokens[0]);
			int nItems = Integer.parseInt(tokens[1]);
			Item[] items = new Item[nItems];
			for(int i = 0; i < nItems; i++){
				tokens = br.readLine().trim().split("[\\s]+");
				int value = Integer.parseInt(tokens[0]);
				int weight = Integer.parseInt(tokens[1]);
				items[i] = new Item(value, weight);
			}
			Solver ks = new Solver(items, nItems, capacity);
			ks.solve();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class Solver{
	Item[] items;
	int nItems, capacity;
	Map<Combo, Integer> computed = new HashMap<Combo, Integer>();
	
	Solver(Item[] items, int nItems, int capacity){
		this.items = items;
		this.nItems = nItems;
		this.capacity = capacity;
	}
	
	public void solve(){
		System.out.println(solve(nItems, capacity));
	}
	
	private int solve(int n, int c){
		Combo combo = new Combo(n, c);
		if(computed.containsKey(combo)){
			return computed.get(combo);
		}
		if(n == 0){
			computed.put(combo, 0);
			return 0;
		}
		if(c < items[n-1].weight()){
			int temp = solve(n-1, c);
			computed.put(combo, temp);
			return temp;
		}
		
		int temp = Math.max(solve(n-1, c), solve(n-1, c - items[n-1].weight()) + items[n-1].value());
		computed.put(combo, temp);
		return temp;	
	}
}

class Combo{
	int nItems, capacity;
	
	Combo(int n, int c){
		nItems = n;
		capacity = c;
	}
	
	public int numItems(){
		return nItems;
	}
	
	public int capacity(){
		return capacity;
	}
	
	@Override
	public boolean equals(Object combo){
		return nItems == ((Combo)combo).numItems() && capacity == ((Combo)combo).capacity();
	}
	
	@Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.nItems;
        hash = 97 * hash + this.capacity;
        return hash;
    }
}