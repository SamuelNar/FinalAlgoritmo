package org.example.Entidad.GrafoPeso;

import java.util.LinkedList;
import java.util.List;

public class Prim {
    private final EdgeWeightedIntGraph G;
    private boolean[] marked; // marked[v] = true si v está en el árbol de expansión
    private Edge[] edgeTo; // edgeTo[v] = última arista en el camino más corto de s a v
    private double[] distTo; // distTo[v] = peso de la arista más corta de s a v
    private IndexMinPQ<Double> pq; // cola de prioridad de aristas con peso mínimo

    public Prim(EdgeWeightedIntGraph G) {
        this.G = G;
    }

    public void Prim(EdgeWeightedIntGraph G, int s) {
        if (s < 0 || s >= G.V()) {
            throw new IllegalArgumentException(
                    "El vértice de origen no es válido");
        }
        marked = new boolean[G.V()];
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
            pq.insert(v, distTo[v]);
        }

        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            marked[v] = true;
            for (Edge e : G.adj(v)) {
                int w = e.other(v);
                if (marked[w])
                    continue;
                if (e.weight() < distTo[w]) {
                    distTo[w] = e.weight();
                    edgeTo[w] = e;
                    pq.decreaseKey(w, distTo[w]);
                }
            }
        }
    }

    public List<Edge> edges() {
        List<Edge> mst = new LinkedList<>();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge e = edgeTo[v];
            if (e != null) {
                mst.add(e);
            }
        }
        return mst;
    }

    public double weight() {
        double weight = 0.0;
        for (Edge e : edges())
            weight += e.weight();
        return weight;
    }

}
