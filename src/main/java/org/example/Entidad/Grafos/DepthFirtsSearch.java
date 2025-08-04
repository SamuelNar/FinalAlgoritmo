package org.example.Entidad.Grafos;

import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;


public class DepthFirtsSearch {
    private boolean[] marked;
    private AdjacencyListIntGraph G;
    private int[] edgeTo;// nodos visitados
    private int count;
    private int s;



    //camino mas corto el coutn es para contar los nodos
    public DepthFirtsSearch(AdjacencyListIntGraph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()]; // crea el array de visitados
        this.G = G;
        count=0;
        dfs(G, s); // comienza la búsqueda desde el nodo s
    }

    public boolean isConnected() {
        return count == G.V();
    }


    private void dfs(AdjacencyListIntGraph G, int v) {
        marked[v] = true;
        count++;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    public boolean isMarked(int v) {
        return marked[v];
    }

    private void isValidVertex(int v) {
        if (v < 0 || v >= marked.length) {
            throw new IllegalArgumentException("Vértice inválido: " + v);
        }
    }

    public boolean hasPathTo(int v){
        isValidVertex(v);
        return marked[v];
    }

    public List<Integer> pathTo(int v){
        isValidVertex(v);
        if (!hasPathTo(v)){
            return null;
        }

        LinkedList<Integer> path = new LinkedList<>();
        for (int x = v; x!= s; x = edgeTo[x]){
            path.addFirst(x);
        }
        path.addFirst(s);
        return path;
    }

    public void bfs(AdjacencyListIntGraph G, int s){
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        Queue<Integer> q = new Queue<Integer>();
        marked[s]=true;
        q.enqueue(s);
        while (!q.isEmpty()){
            int v = q.dequeue();
            for (int w : G.adj(v)){
                if (!marked[w]){
                    marked[w] = true;
                    edgeTo[w]=v;
                    q.enqueue(w);
                }
            }
        }
    }

}
