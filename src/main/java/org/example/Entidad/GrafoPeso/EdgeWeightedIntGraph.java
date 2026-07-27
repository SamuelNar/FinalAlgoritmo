package org.example.Entidad.GrafoPeso;

import java.util.LinkedList;
import java.util.List;

public class EdgeWeightedIntGraph {
      private final int V;
    private int E;

    private final List<Edge>[] adj;

    @SuppressWarnings("unchecked")
    public EdgeWeightedIntGraph(int V) {

        if (V < 0) {
            throw new IllegalArgumentException(
                    "La cantidad de vértices no puede ser negativa"
            );
        }

        this.V = V;
        this.E = 0;

        adj = new LinkedList[V];

        for (int v = 0; v < V; v++) {
            adj[v] = new LinkedList<>();
        }
    }

    /*
     * Cantidad de vértices.
     */
    public int V() {
        return V;
    }

    /*
     * Cantidad de aristas.
     */
    public int E() {
        return E;
    }

    /*
     * Agrega una arista al grafo.
     */
    public void addEdge(Edge e) {

        if (e == null) {
            throw new IllegalArgumentException(
                    "La arista no puede ser null"
            );
        }

        int v = e.either();
        int w = e.other(v);
        validateVertex(v);
        validateVertex(w);

        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    /*
     * Devuelve las aristas que salen del vértice v.
     */
    public List<Edge> adj(int v) {

        validateVertex(v);

        return adj[v];
    }

      public List<Edge> edges() {

        List<Edge> allEdges = new LinkedList<>();

        for (int v = 0; v < V; v++) {

            for (Edge e : adj[v]) {

                /*
                 * Agregamos la arista solamente desde
                 * uno de sus extremos para no repetirla.
                 */
                if (e.other(v) > v) {
                    allEdges.add(e);
                }
            }
        }

        return allEdges;
    }

    private void validateVertex(int v) {

        if (v < 0 || v >= V) {

            throw new IllegalArgumentException(
                    "El vértice debe estar entre 0 y "
                            + (V - 1)
            );
        }
    }
}
