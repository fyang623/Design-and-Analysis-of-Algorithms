package bellmanford;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BellmanFord {

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("/Users/Fred/Desktop/g1.txt"));
            String token = br.readLine();
            String tokens[] = token.trim().split("[\\s]+");
            Vertex vertice[] = new Vertex[Integer.parseInt(tokens[0])];
            for(int i = 0; i < vertice.length; i++)
                vertice[i] = new Vertex(i+1);
            int tail = 0, head = 0, length = 0;
            while((token = br.readLine()) != null){			
                tokens = token.trim().split("[\\s]+");
                tail = Integer.parseInt(tokens[0]);
                head = Integer.parseInt(tokens[1]);
                length = Integer.parseInt(tokens[2]);
                vertice[tail-1].addOutNeighbor(head, length);	
                vertice[head-1].addInNeighbor(tail, length);
            }
            br.close();
            BFSolver solver = new BFSolver(vertice);
            System.out.println(solver.ASAP());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class BFSolver{
    int dist[][];
    Vertex vertice[];

    BFSolver(Vertex vertice[]){
        this.vertice = vertice;
        dist = new int[2][vertice.length];
    }

    public int ASAP(){
        int temp = 0, ssl = 0;
        ssl = SPSP(0);
        if(negativeCycles() == true){
            System.out.println("negative cycles!");
            System.exit(0);
        }
        for(int s = 1; s < vertice.length; s++){
            temp = SPSP(s);
            if(temp < ssl)
                ssl = temp;
            System.out.println(s + "\t" + temp + "\t" + ssl);
        }
        return ssl;
    }

    public int SPSP(int s){
        Map<Integer, Integer> inNeighbors;
        int temp = 0, ssl = Integer.MAX_VALUE;
        boolean finished;

        Arrays.fill(dist[0], Integer.MAX_VALUE);	
        dist[0][s] = 0;
        for(int i = 1; i < vertice.length; i++){
            finished = true;
            for(int v = 0; v < vertice.length; v++){
                inNeighbors = vertice[v].inNeighbors();
                dist[i%2][v] = dist[(i-1)%2][v];
                for(int n : inNeighbors.keySet()){
                    if(dist[(i-1)%2][n-1] == Integer.MAX_VALUE)
                        temp = Integer.MAX_VALUE;
                    else
                        temp = dist[(i-1)%2][n-1] + inNeighbors.get(n);

                    if(dist[i%2][v] > temp) 
                        dist[i%2][v] = temp;   
                }

                if(dist[i%2][v] < dist[(i-1)%2][v]){
                    finished = false;
                    if(dist[i%2][v] < ssl)
                        ssl = dist[i%2][v];
                }
            }
            if(finished == true)
                    break;
        }
        return ssl;
    }
	
    public boolean negativeCycles(){
        Map<Integer, Integer> inNeighbors;
        int i = vertice.length, temp = 0;
        boolean finished = true;

        for(int v = 0; v < i; v++){
            inNeighbors = vertice[v].inNeighbors();
            dist[i%2][v] = dist[(i-1)%2][v];
            for(int n : inNeighbors.keySet()){
                if(dist[(i-1)%2][n-1] == Integer.MAX_VALUE)
                    temp = Integer.MAX_VALUE;
                else
                    temp = dist[(i-1)%2][n-1] + inNeighbors.get(n);

                if(temp < dist[i%2][v]){
                    dist[i%2][v] = temp;
                    finished = false;
                }
            }
        }
        return finished;
    }
}

class Vertex {
    int lable;
    Map<Integer, Integer> inNeighbors = new HashMap<Integer, Integer>();
    Map<Integer, Integer> outNeighbors = new HashMap<Integer, Integer>();

    Vertex(int lable){
        this.lable = lable;
    }

    public int lable(){
        return lable;
    }

    public Map<Integer, Integer> inNeighbors(){
        return inNeighbors;
    }

    public Map<Integer, Integer> outNeighbors(){
        return outNeighbors;
    }

    public void addInNeighbor(int lable, int length){
        inNeighbors.put(lable, length);
    }

    public void addOutNeighbor(int lable, int length){
        outNeighbors.put(lable, length);
    }

    @Override
    public boolean equals(Object v){
        if(lable == ((Vertex) v).lable())
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.lable;
        hash = 71 * hash + Objects.hashCode(this.inNeighbors);
        hash = 71 * hash + Objects.hashCode(this.outNeighbors);
        return hash;
    }	
}
