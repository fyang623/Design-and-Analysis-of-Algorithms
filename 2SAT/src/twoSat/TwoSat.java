package twoSat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class TwoSat{

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("/Users/Fred/Desktop/2sat4.txt"));
            String token = br.readLine();
            String tokens[] = token.split("[\\s]+");
            int size = Integer.parseInt(tokens[0]);
            List<Vertex> vertices = new ArrayList<>();
            for(int i = 0; i <= 2*size; i++)
                vertices.add(new Vertex(i - size));
            while((token = br.readLine()) != null){
                tokens = token.trim().split("[\\s]+");
                int v1 = Integer.parseInt(tokens[0]);
                int v2 = Integer.parseInt(tokens[1]);
                vertices.get(-v1 + size).addOutEdge(v2);
                vertices.get(-v2 + size).addOutEdge(v1);
                vertices.get(v2 + size).addInEdge(-v1);
                vertices.get(v1 + size).addInEdge(-v2);
            }
            br.close();
            SCCSolver ss = new SCCSolver(vertices);
            ss.compute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class SCCSolver{
    List<Vertex> vertices;
    List<Vertex> finishTime = new ArrayList<>();
    Vertex leader;
    int size;

    SCCSolver(List<Vertex> vertices){
        this.vertices = vertices;
        finishTime.addAll(vertices);
        size = (vertices.size() - 1)/2;
    }

    public void compute(){
        reverseGraph();
        DFSLoop(0);
        reverseGraph();
        DFSLoop(1);
    }

    private void reverseGraph(){
        for(Vertex v : vertices) {
            v.switchInOut();
            v.setExplored(false);
        }
    }

    private void DFSLoop(int t){
        List<Vertex> temp = new ArrayList<>();
        for (int i = finishTime.size(); i > 0; i--){
            if(!finishTime.get(i - 1).explored()){
                leader = finishTime.get(i-1);
                List<Vertex> FT_sublist = DFS(leader, t);
                temp.addAll(FT_sublist);
            }
            if(i%100000 == 0)
                System.out.print(i + "\t");
        }
        System.out.println();
        finishTime = temp;
    }

    private List<Vertex> DFS(Vertex v_start, int t){
        Stack<Vertex> stack = new Stack<>();
        List<Vertex> popped = new LinkedList<>();
        v_start.setExplored(true);
        stack.push(v_start);
        while(!stack.empty()){
            Vertex v = stack.pop();
            popped.add(0, v);
            for(int i : v.outEdges()){
                Vertex vi = vertices.get(size + i);
                if(!vi.explored){
                    vi.setExplored(true);
                    if(t == 1){
                        if(leader.equals(vertices.get(size - i))) {
                            System.out.println("\nunsatisfiable");
                            System.exit(0);
                        }
                        else
                            vi.setLeader(leader);
                    }
                    stack.push(vi);
                }
            }
        }
        return popped;
    }
}

class Vertex{
    int lable;
    Vertex l;
    boolean explored;
    List<Integer> inEdges = new ArrayList<>();
    List<Integer> outEdges = new ArrayList<>();

    Vertex(int v){
        this.lable = v;
        explored = false;
    }

    public int lable(){
        return lable;
    }

    public void setExplored(boolean b){
        explored = b;
    }

    public boolean explored(){
        return explored;
    }

    public void setLeader(Vertex leader){
        l = leader;
    }

    public Vertex leader(){
        return l;
    }

    public List<Integer> inEdges(){
        return inEdges;
    }

    public void addInEdge(int edge){
        inEdges.add(edge);
    }

    List<Integer> outEdges(){
        return outEdges;
    }

    public void addOutEdge(int edge){
        outEdges.add(edge);
    }

    public void switchInOut(){
        List<Integer> temp = inEdges;
        inEdges = outEdges;
        outEdges = temp;
    }

    @Override
    public boolean equals(Object v){
        return lable == ((Vertex) v).lable();
    }

    @Override
    public int hashCode() {
        return lable;
    }
}

