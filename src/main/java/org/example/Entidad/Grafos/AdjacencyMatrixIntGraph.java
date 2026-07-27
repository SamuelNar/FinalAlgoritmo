package org.example.Entidad.Grafos;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyMatrixIntGraph implements IntGraph{

    private final int V;
    private int E;
    private final boolean[][] adj;

    public AdjacencyMatrixIntGraph(int V){
        if (V < 0){
            throw new IllegalArgumentException();
        }
        this.V = V;
        this.E = 0;
        adj = new boolean[V][V];
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
        if (!adj[v][w]){ // la matriz no admite aristas paralelas
            E++;
        }
        adj[v][w] = true;
        adj[w][v] = true;
    }

    public void AddEdgeD(int v,int w){
        if (v<0 || v>=V){
            throw new IllegalArgumentException();
        }
        if (w<0 || w >=V){
            throw new IllegalArgumentException();
        }
        if (!adj[v][w]){
            E++;
        }
        adj[v][w] = true;  // o depende como vaya la dirrecion
    }

    @Override
    public List<Integer> adj(int v) {
        if (v<0 || v>=V){
            throw new IllegalArgumentException();
        }
        List<Integer> vecinos = new ArrayList<>();
        for (int w = 0; w < V; w++){
            if (adj[v][w]){
                vecinos.add(w);
            }
        }
        return vecinos;
    }

}
