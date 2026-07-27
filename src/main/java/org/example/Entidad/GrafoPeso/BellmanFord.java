package org.example.Entidad.GrafoPeso;

import java.util.Arrays;

public class BellmanFord {

    public static boolean bellmanFord(
            int vertexCount, // cantidad de vértices en el grafo
            Iterable<DirectEdge> edges, // aristas del grafo
            int source, // vértice de origen
            double[] distance // arreglo para almacenar las distancias más cortas desde el vértice de origen
    ) {
        Arrays.fill(distance, Double.POSITIVE_INFINITY); // Inicializa todas las distancias como infinitas
        distance[source] = 0; // La distancia desde el vértice de origen a sí mismo es 0

        // for i = 1 to |G.V| - 1
        for (int i = 1; i <= vertexCount - 1; i++) {

            // for each edge (u,v) perteneciente a G.E
            for (DirectEdge edge : edges) { // recorrer todas las aristas del grafo
                relax(edge, distance); // Relaja la arista (u,v) y actualiza la distancia si es necesario
            }
        }

        // Comprobar ciclos negativos
        for (DirectEdge edge : edges) {
            // Si se puede relajar una arista después de |G.V| - 1 iteraciones, entonces hay un ciclo negativo
            int u = edge.from;
            int v = edge.to;
            if (distance[u] != Double.POSITIVE_INFINITY
                    && distance[v] > distance[u] + edge.weight) {

                return false;
            }
        }

        return true;
    }

    private static void relax(
            DirectEdge edge,
            double[] distance
    ) {
        int u = edge.from; // vértice de origen de la arista
        int v = edge.to; // vértice de destino de la arista

        if (distance[u] != Double.POSITIVE_INFINITY // si existe un camino desde el vértice de origen hasta u
                && distance[v] > distance[u] + edge.weight) { // si la distancia actual a v es mayor que la distancia a u más el peso de la arista (u,v) {

            distance[v] = distance[u] + edge.weight; // Actualiza la distancia a v con la nueva distancia más corta
        }
    }
}