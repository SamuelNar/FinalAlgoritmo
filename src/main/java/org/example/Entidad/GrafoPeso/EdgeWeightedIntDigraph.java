package org.example.Entidad.GrafoPeso;

import java.util.LinkedList;
import java.util.List;

public class EdgeWeightedIntDigraph {
    private final int V;
    private int E;
    private List<DirectEdge>[] adj;


    public int V() {
        return V;
    }
    public EdgeWeightedIntDigraph(int V){
        if (V <0){
            throw new IllegalArgumentException();
        }
        this.V=V;
        this.E = 0;
        adj = new LinkedList[V];
        for(int v = 0; v < V;v++){
            adj[v] = new LinkedList<DirectEdge>();
        }
    }

    public void addEdge(DirectEdge e){
        if (e.from < 0 || e.from>= V){
            throw new IllegalArgumentException();
        }
        if (e.to<0 || e.to>=V){
            throw new IllegalArgumentException();
        }
        E++;
        adj[e.from].add(e);
    }

    public List<DirectEdge> adj (int v){
        if (v<0 || v>=V){
            throw new IllegalArgumentException();
        }
        return adj[v];
    }

}
