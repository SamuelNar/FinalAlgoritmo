package org.example.Entidad.GrafoPeso;

import java.util.LinkedList;
import java.util.List;

public class EdgeWeightIntMatrixDigraph {

    private int V;
    private int E;
    private DirectEdge[][] m;

    public EdgeWeightIntMatrixDigraph(int V){
        if(V < 0){
            throw new IllegalArgumentException();
        }   
        this.V = V;
        this.E = 0;
        m = new DirectEdge[V][V];
    }

    public int V(){
        return V;
    }   

    public int E(){
        return E;
    }

    public void addEdge(DirectEdge e){
        if(e.from < 0 || e.from >= V){
            throw new IllegalArgumentException();
        }
        if(e.to < 0 || e.to >= V){
            throw new IllegalArgumentException();
        }   
        if(m[e.from][e.to] == null){
            E++;
        }
        m[e.from][e.to] = e;
    }

    public List<DirectEdge> adj(int v){
        if(v < 0 || v >= V){
            throw new IllegalArgumentException();
        }
        
        List<DirectEdge> edges = new LinkedList<>();
        for(int i = 0; i < V; i++){
            if(m[v][i] != null){
                edges.add(m[v][i]);
            }
        }
        return edges;       
    }

    public boolean containsEdge(int from, int to){
        if(from < 0 || from >= V){
            throw new IllegalArgumentException();
        }
        if(to < 0 || to >= V){
            throw new IllegalArgumentException();
        }
        return m[from][to] != null;
    }

     public DirectEdge getEdge(int from, int to) {

        if (from < 0 || from >= V || to < 0 || to >= V)
            throw new IllegalArgumentException();

        return m[from][to];
    }
}
