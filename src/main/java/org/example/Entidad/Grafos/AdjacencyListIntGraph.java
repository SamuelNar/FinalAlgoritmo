package org.example.Entidad.Grafos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AdjacencyListIntGraph implements IntGraph{

    private final int V;
    private int E;
    private final List<Integer>[] adj;
    boolean[] marked;

    public AdjacencyListIntGraph(int V){
        if (V < 0){
            throw  new IllegalArgumentException();
        }
        this.V = V;
        this.E = 0;
        marked = new boolean[V];
        adj = new LinkedList[V];
        for (int a = 0; a <V ;a++){
            adj[a] = new LinkedList<Integer>();
        }
    }


    @Override
    public int V() {
        return V;
    }

    @Override
    public int E() {
        return E;
    }

    //Grafo no dirigido
    @Override
    public void AddEdge(int v, int w) {
        if (v<0 || v>=V){
            throw new IllegalArgumentException();
        }
        if (w <0 || w>=V){
            throw new IllegalArgumentException();
        }
        E++;
        adj[v].add(w);
        adj[w].add(v);
    }

    public void AddEdgeD(int v,int w){
        if (v<0 || v>=V){
            throw new IllegalArgumentException();
        }
        if (w<0 || w >=V){
            throw new IllegalArgumentException();
        }
        E++;
        adj[v].add(w);  // o depende como vaya la dirrecion
    }
    @Override
    public List<Integer> adj(int v) {
        if (v<0 || v>=V){
            throw new IllegalArgumentException();
        }
        return adj[v];
    }

}
