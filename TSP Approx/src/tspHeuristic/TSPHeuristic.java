package tspHeuristic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class TSPHeuristic {
	public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("/Users/Fred/Desktop/nn.txt"));
            String token = br.readLine();
            String tokens[] = token.trim().split("[\\s]+");
            LinkedList<Vertex> vertices = new LinkedList< >();
            while((token = br.readLine()) != null){
                tokens = token.trim().split("[\\s]+");
                int lable = Integer.parseInt(tokens[0]);
                double x = Double.parseDouble(tokens[1]);
                double y = Double.parseDouble(tokens[2]);
                vertices.add(new Vertex(lable, x, y));
            }
            br.close();
            TSPSolver solver = new TSPSolver(vertices);
            System.out.println(solver.solve());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class TSPSolver{
    LinkedList<Vertex> vertices;
    LinkedList<Vertex> visited = new LinkedList<>();

    TSPSolver(LinkedList<Vertex> vertices){
        this.vertices = vertices;
    }

	public double solve(){
		double min, temp, total = 0;
	    Vertex curr = vertices.remove(0), next = null;
	    visited.add(curr);
	    while(vertices.size() > 0){
	    	min = Double.MAX_VALUE;
	        for(Vertex v : vertices){
                assert curr != null;
                temp = curr.distance(v);
	        	if(temp < min){
	        		min = temp;
	        		next = v;
	        	}
	        }	        
	        visited.add(next);
	        vertices.remove(next);
	        curr = next;
	        total += min;
	        if(visited.size() % 100 == 0)
	    	    System.out.println("computing : " + visited.size());
	    }
	    return total + visited.get(0).distance(next);
	}
}

class Vertex {
    int lable;
    double x, y;

    Vertex(int lable, double x, double y){
        this.lable = lable;
        this.x = x;
        this.y = y;
    }

    public int lable(){
        return lable;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double distance(Vertex v){
        double x1 = v.getX();
        double y1 = v.getY();
        double dist = (x-x1)*(x-x1) + (y-y1)*(y-y1);
        return Math.sqrt(dist);
    }

    @Override
    public boolean equals(Object v){
        return lable == ((Vertex) v).lable();
    }

    @Override
    public int hashCode() {
        return 25 + this.lable;
    }
}