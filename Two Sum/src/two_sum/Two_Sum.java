package two_sum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Two_Sum {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("/Users/Fred/Desktop/2sum.txt"));
			Set<Long> numbers = new HashSet<>();
			List<Integer> sums = new LinkedList<>();
			String token;
			while((token = br.readLine()) != null)
				numbers.add(Long.parseLong(token));
			br.close();
			
			for(int i = -10000; i <= 10000; i++)
				sums.add(i);
			SumSolver ss = new SumSolver(numbers, sums);
			System.out.println(ss.countT());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


class SumSolver{
	Set<Long> numbers = new HashSet<>();
	List<Integer> sums = new LinkedList<>();
	
	SumSolver(Set<Long> numbers, List<Integer> sums){
		this.numbers = numbers;
		this.sums = sums;		
	}
	
	public int countT(){
		int counter = 0;
		for(int sum : sums){
			for(long l : numbers){
				if(2*l >= sum) continue;
				if(numbers.contains(sum-l) ){			
					counter++;
					System.out.println("counter : " + counter);
					break;
				}
			}
		}
		return counter;
	}
}
