package median_maintenance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

public class MedianMaintenance {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("/Users/Fred/Desktop/Median.txt"));
			int numbers[] = new int[10000], i = 0;
			String token;
			while((token = br.readLine()) != null)
				numbers[i++] = Integer.parseInt(token);
			br.close();
			MedianMaintainer mm = new MedianMaintainer(numbers);
			System.out.println(mm.trackMedian());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class MedianMaintainer{
	Queue<Integer> highHeap = new PriorityQueue<>();
	Queue<Integer> lowHeap = new PriorityQueue<>(Collections.reverseOrder());
	int[] numbers;
	long sum;
	
	MedianMaintainer(int[] numbers){
		this.numbers = numbers;
	}
	
	public int trackMedian(){
		
		if(numbers[0] > numbers[1]){
			highHeap.add(numbers[0]);
			lowHeap.add(numbers[1]);
		}
		else{
			highHeap.add(numbers[1]);
			lowHeap.add(numbers[0]);
		}
		
		sum = numbers[0] + lowHeap.peek();
		
		for(int i = 2; i < 10000; i++){
			if (numbers[i] > highHeap.peek())
				highHeap.add(numbers[i]);
			else if(numbers[i] < lowHeap.peek())
				lowHeap.add(numbers[i]);
			else if(highHeap.size() < lowHeap.size())
				highHeap.add(numbers[i]);
			else
				lowHeap.add(numbers[i]);
			
			if(lowHeap.size() - highHeap.size() == 2)
				highHeap.add(lowHeap.poll());
			else if(highHeap.size() - lowHeap.size() == 1)
				lowHeap.add(highHeap.poll());
			sum += lowHeap.peek();
			System.out.println(sum);
		}
		return (int) (sum%10000);
	}
}