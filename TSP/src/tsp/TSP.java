package tsp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TSP {

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("/Users/Fred/Desktop/tsp.txt"));
            String token = br.readLine();
            String tokens[] = token.trim().split("[\\s]+");
            Vertex vertice[] = new Vertex[Integer.parseInt(tokens[0])];
            for(int i = 0; i < vertice.length; i++){
                token = br.readLine();
                tokens = token.trim().split("[\\s]+");
                double x = Double.parseDouble(tokens[0]);
                double y = Double.parseDouble(tokens[1]);
                vertice[i] = new Vertex(i+1, x, y);
            }
            br.close();
            TSPSolver solver = new TSPSolver(vertice);
            System.out.println(solver.solve());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class TSPSolver{
    Vertex[] vertice;
    HashSet<BitSet>[] sets;
    HashMap<BitSet, double[]>[] maps;

    TSPSolver(Vertex[] vertice){
        this.vertice = vertice;
        this.maps = new HashMap[2];
        this.sets = new HashSet[2];
        for(int i = 0; i < 2; i++){
            sets[i] = new HashSet<>();
            maps[i] = new HashMap<>();
        }
    }

    public double solve(){
        double min = Double.MAX_VALUE;

        for(int i = 1; i < vertice.length; i++){
            BitSet bits = new BitSet(vertice.length);
            bits.set(0);
            bits.set(i);
            double dist[] = new double[1];
            dist[0] = vertice[0].distance(vertice[i]);
            maps[1].put(bits, dist);
        }

        long start = System.currentTimeMillis();
        for(int i = 2; i < vertice.length; i++){
            HashSet<BitSet> set = getSets(i+1);
            System.out.println("subproblem number = " + sets[i%2].size()
                    + "\n\t\tcurrentTime = " + (System.currentTimeMillis() - start));
            int counter = 0;
            maps[i%2].clear();
            for(BitSet b1 : set){
                double dist[] = new double[i];
                maps[i%2].put(b1, dist);
                for(int j = 1; j < b1.length() && b1.nextSetBit(j) != -1; j++){
                    double min1 = Double.MAX_VALUE;
                    BitSet b2 = (BitSet) b1.clone();
                    j = b1.nextSetBit(j);
                    b2.clear(j);
                    for(int k = 1; k < b2.length() && b2.nextSetBit(k) != -1; k++) {
                        k = b2.nextSetBit(k);
                        double temp = maps[(i-1)%2].get(b2)[b2.get(1, k).cardinality()]
                                + vertice[k].distance(vertice[j]);
                        min1 = temp < min1 ? temp : min1;
                    }
                    maps[i%2].get(b1)[b1.get(1, j).cardinality()] = min1;
                }
                counter++;
                if(counter % 50000 == 0)
                    System.out.println("computing " + 100 * counter/set.size() + "%");
            }
            System.out.println("solved : subproblem size = " + (i+1)
                    + "\n\t\tcurrentTime = " + (System.currentTimeMillis() - start));
        }

        BitSet s = new BitSet(vertice.length);
        s.set(0,vertice.length);
        for(int k = 1; k < vertice.length; k++){
            double temp = maps[(vertice.length - 1)%2].get(s)[s.get(1, k).cardinality()]
                    + vertice[k].distance(vertice[0]);
            if(min > temp)
                min = temp;
        }
        return min;
    }

    private HashSet<BitSet> getSets(int size) {
        sets[(size-1)%2].clear();
        getSets(new BitSet(vertice.length), size);
        return sets[(size-1)%2];
    }

    private void getSets(BitSet bits, int size){

        if(bits.cardinality() == 0){
            bits.set(0);
            getSets(bits, size);
            return;
        }

        if(bits.cardinality() == size) {
            sets[(size-1)%2].add(bits);
            return;
        }

        for(int i = bits.length(); i <= vertice.length - size + bits.cardinality(); i++){
            BitSet bs = (BitSet) bits.clone();
            bs.set(i);
            getSets(bs, size);
        }
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