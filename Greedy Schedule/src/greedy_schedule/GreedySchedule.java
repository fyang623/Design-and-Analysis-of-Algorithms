package greedy_schedule;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class GreedySchedule {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("/Users/Fred/Desktop/jobs.txt"));
			Job jobs[] = new Job[Integer.parseInt(br.readLine())];
			int i = 0;
			long timeSum = 0, weightedSum = 0;
			String token;
			while((token = br.readLine()) != null){
				String tokens[] = token.trim().split("[\\s]+");
				int weight = Integer.parseInt(tokens[0]);
				int length = Integer.parseInt(tokens[1]);
				jobs[i++] = new Job(weight, length);
			}
			
			System.out.println(jobs[9999].getWeight() + "\t" + jobs[9999].getLength());
			Arrays.sort(jobs);
			System.out.println(jobs[9999].getWeight() + "\t" + jobs[9999].getLength());
			for(Job job : jobs){
				timeSum += job.getLength();
				weightedSum += timeSum*job.getWeight();
			}
			
			br.close();
			System.out.println(weightedSum);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

class Job implements Comparable<Job>{
	private int weight, length;
	
	Job(int weight, int length){
		this.weight = weight;
		this.length = length;
	}
	
	public int getWeight(){
		return weight;
	}
	
	public int getLength(){
		return length;
	}
	
	@Override
	public int compareTo(Job job) {
		int f1 = weight - length;
		int f2 = job.getWeight() - job.getLength();
		if(f1 > f2) return -1;
		if(f1 == f2 && weight > job.getWeight()) return -1;
		if(f1 == f2 && weight == job.getWeight()) return 0;
		return 1;
	}
	
}
