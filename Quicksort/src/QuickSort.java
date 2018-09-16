import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class QuickSort {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("/Users/Fred/Desktop/QuickSort.txt"));
		int[] data = new int[10000];
		for(int i=0; i<10000; i++)
			data[i] = Integer.parseInt(br.readLine());
		br.close();
		Sorter sorter = new Sorter(data);
		System.out.println(sorter.sort());
	}
}

class Sorter {
	int[] data;
	
	Sorter(int[] data){
		this.data = data;
	}
	
	public int sort() {
		return sort(0, data.length - 1);
	}
	
	public int sort(int start, int end){
		if(end == start) return 0;
		int pos = partion(start, end);
		int x=0, y=0;
		if(pos != start)
			x = sort(start, pos-1);
		if(pos != end)
			y = sort(pos+1, end);
		return end - start + x + y;
	}
	
	public int partion(int start, int end){
		int pivot = median(start, end, (start+end)/2);
		swap(start, pivot);
		int i=start+1;
		for(int j=start+1; j<=end; j++){
			if(data[j] < data[start])
				swap(i++, j);
		}
		swap(start, i-1);
		return i-1;
	}
	
	private int median(int a, int b, int c) {
		if (data[c] >= data[a] && data[a] >= data[b]) return a;
		if (data[b] >= data[a] && data[a] >= data[c]) return a;
		if (data[c] >= data[b] && data[b] >= data[a]) return b;
		if (data[a] >= data[b] && data[b] >= data[c]) return b;
		return c;
	}
	private void swap(int a, int b) {
		int temp = data[a];
		data[a] = data[b];
		data[b] = temp;
	}
}
