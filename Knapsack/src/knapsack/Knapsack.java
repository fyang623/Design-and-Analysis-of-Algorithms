package knapsack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Knapsack {
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("/Users/Fred/Desktop/knapsack.txt"));
			String tokens[] = br.readLine().trim().split("[\\s]+");
			int capacity = Integer.parseInt(tokens[0]);
			int nItems = Integer.parseInt(tokens[1]);
			Item[] items = new Item[nItems];
			int maxWeight = 0;
			for(int i = 0; i < nItems; i++){
				tokens = br.readLine().trim().split("[\\s]+");
				int value = Integer.parseInt(tokens[0]);
				int weight = Integer.parseInt(tokens[1]);
				items[i] = new Item(value, weight);
				if(weight > maxWeight)
					maxWeight = weight;
			}
			KnapsackSolver ks = new KnapsackSolver(items, nItems, capacity);
			ks.solve();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class KnapsackSolver{
	Item[] items;
	int vmax[][], nItems, capacity; 
	
	KnapsackSolver(Item[] items, int nItems, int capacity){
		this.items = items;
		this.nItems = nItems;
		this.capacity = capacity;
		vmax = new int[nItems][capacity];
	}
	
	public void solve(){
		int n, c;
		for(c = 0; c < capacity; c++)	
			vmax[0][c] = (c+1) < items[0].weight() ? 0 : items[0].value();
		for(n = 1; n < nItems; n++)
			for(c = 0; c < capacity; c++){
				if(c + 1 < items[n].weight())
					vmax[n][c] = vmax[n-1][c];
				else if(c + 1 == items[n].weight())
					vmax[n][c] = vmax[n-1][c] > items[n].value() ? vmax[n-1][c] : items[n].value();
				else{
					int a = vmax[n-1][c];
					int b = vmax[n-1][c-items[n].weight()] + items[n].value();
					vmax[n][c] = a > b ? a : b;
				}
			}
		System.out.println(vmax[nItems-1][capacity-1]);
	}
}

class Item{	
	int value, weight;

	Item(int value, int weight){
		this.value = value;
		this.weight = weight;
	}
	
	public int weight(){
		return weight;
	}
	
	public int value(){
		return value;
	}
	
	public void setValue(int v){
		value = v;
	}
	
	public void setWeight(int w){
		weight = w;
	}
}