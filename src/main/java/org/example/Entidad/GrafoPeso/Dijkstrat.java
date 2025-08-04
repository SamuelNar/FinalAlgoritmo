package org.example.Entidad.GrafoPeso;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class Dijkstrat {
    private double[] distTo;        // distancias más cortas desde el origen
    private DirectEdge[] edgeTo;
    private final IndexMinPQ<Double> pq;

    public Dijkstrat(EdgeWeightedIntDigraph G, int s) {
            if (s<0 || s>=G.V()){
                throw new IllegalArgumentException();
            }
            distTo = new double[G.V()];
            edgeTo = new DirectEdge[G.V()];
            pq = new IndexMinPQ<Double>(G.V());
            for (int v = 0; v < G.V(); v++){
                if (v!=s){
                    distTo[v] = Double.POSITIVE_INFINITY;
                    pq.insert(v,distTo[v]);
                }
            }
            distTo[s] = 0.0;
            pq.insert(s,distTo[s]);
            while (!pq.isEmpty()){
                int v = pq.delMin();
                for (DirectEdge e : G.adj(v)){
                    relax(e);
                }
            }

    }

    private void relax(DirectEdge e) {
        int v = e.from;
        int w = e.to;
        if (distTo[w] > distTo[v] + e.weight) {
            distTo[w] = distTo[v] + e.weight;
            edgeTo[w] = e;
            pq.decreaseKey(w,distTo[w]);
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectEdge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        LinkedList<DirectEdge> path = new LinkedList<>();
        for (DirectEdge e = edgeTo[v]; e != null; e = edgeTo[e.from]) {
            path.addFirst(e);
        }
        return path;
    }
}
