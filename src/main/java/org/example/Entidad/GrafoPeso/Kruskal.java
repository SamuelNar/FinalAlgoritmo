package org.example.Entidad.GrafoPeso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Kruskal {

    /*
     * Aristas que forman el árbol de expansión mínima.
     */
    private final List<Edge> mst;

    /*
     * Peso total del árbol.
     */
    private double weight;

    public Kruskal(EdgeWeightedIntGraph G) {

        if (G == null) {
            throw new IllegalArgumentException(
                    "El grafo no puede ser null"
            );
        }

        mst = new ArrayList<>();
        weight = 0.0;

        kruskal(G);
    }

    private void kruskal(EdgeWeightedIntGraph G) {

        /*
         * Copiar todas las aristas del grafo
         * dentro de un arreglo.
         */
        Edge[] edges = new Edge[G.E()];

        int t = 0;

        for (Edge edge : G.edges()) {
            edges[t] = edge;
            t++;
        }

        /*
         * Ordenar las aristas de menor a mayor peso.
         *
         * Esto funciona porque Edge implementa
         * Comparable<Edge>.
         */
        Arrays.sort(edges);

        /*
         * Inicialmente cada vértice está en su
         * propio conjunto.
         */
        UF uf = new UF(G.V());

        /*
         * Recorrer las aristas ordenadas.
         *
         * El MST está completo cuando tiene V - 1 aristas.
         */
        for (
                int i = 0;
                i < edges.length && mst.size() < G.V() - 1;
                i++
        ) {

            Edge edge = edges[i];

            /*
             * Como la arista es no dirigida, tomamos
             * uno de los extremos y después el otro.
             */
            int v = edge.either();
            int w = edge.other(v);

            /*
             * Si están en componentes diferentes,
             * agregar esta arista no forma un ciclo.
             */
            if (uf.find(v) != uf.find(w)) {

                /*
                 * Unir ambos componentes.
                 */
                uf.union(v, w);

                /*
                 * Agregar la arista al MST.
                 */
                mst.add(edge);

                /*
                 * Sumar su peso al peso total.
                 */
                weight += edge.weight();
            }
        }
    }

    /*
     * Devuelve las aristas del MST.
     */
    public List<Edge> edges() {
        return Collections.unmodifiableList(mst);
    }

    /*
     * Devuelve el peso total del MST.
     */
    public double weight() {
        return weight;
    }

    /*
     * Indica si pudo construirse un árbol que conecte
     * todos los vértices.
     */
    public boolean hasSpanningTree(int vertexCount) {

        if (vertexCount == 0) {
            return true;
        }

        return mst.size() == vertexCount - 1;
    }
}