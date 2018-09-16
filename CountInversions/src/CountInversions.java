import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Fred
 *
 */
public class CountInversions {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("/Users/Fred/Desktop/IntegerArray.txt"));
		int[] data = new int[100000];
		for(int i=0; i<100000; i++)
			data[i] = Integer.parseInt(br.readLine());
		br.close();
		InversionCounter ic = new InversionCounter(data);
		System.out.println(ic.sortCount());
	}
}

class InversionCounter {
	int[] data;
	InversionCounter(int[] data) {
		this.data = data;
	}
	
	long sortCount() {
		return sortCount(0, data.length - 1);
	}
	
	long sortCount(int start, int end) {
		if(end == start) return 0;
		long x = sortCount(start, (start + end)/2);
		long y = sortCount((start + end)/2 + 1, end);
		long z = countSplit(start, end);
		return x+y+z;
	}
	
	long countSplit(int start, int end) {
		int mid = (start + end)/2;
		int[] temp = new int[end - start + 1];
		int i = start, j = mid + 1, k = 0;
		long counter = 0;
		while (i <= mid && j <= end){
			if(data[i] < data[j])
				temp[k++] = data[i++];
			else {
				temp[k++] = data[j++];
				counter += (mid - i + 1);
			}
		}
		
		while (i <= mid)
			temp[k++] = data[i++];
		
		while (j <= end)
			temp[k++] = data[j++];
		
		for(i = start; i <= end; i++)
			data[i] = temp[i - start];
		
		return counter;
	}
}