package org.example.Entidad.GrafoPeso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.example.Entidad.Grafos.Graph;

public class EdgeWeightedDigraph<T extends Comparable<? super T>>{

    private  int V; // cantidad de vértices     
    private int E;
    private List<List<DirectEdgeGenerico<T>>> adj; // arreglo de listas de adyacencia
    private Map<T, Integer> indice;
    private List<T> vertices;       

    public EdgeWeightedDigraph(){
        V=0;
        E=0;
        indice = new HashMap<>();
        vertices = new ArrayList<>(); 
        adj = new ArrayList<>();
    }

    public int V() {
        return V;
    }       

    public int E() {
        return E;
    }

    private int obtenerIndice(T v){
        Integer idx = indice.get(v);
        if(idx !=null){
            return idx; 
        }

        idx = V;
        indice.put(v, idx);
        vertices.add(v);
        adj.add(new LinkedList<>());
        V++;
        return idx;
    }

     public void addEdge(DirectEdgeGenerico<T> e) {

        int from = obtenerIndice(e.from);
        obtenerIndice(e.to);

        adj.get(from).add(e);

        E++;
    }

    /**
     * Devuelve las aristas adyacentes al vértice.
     */
    public List<DirectEdgeGenerico<T>> adj(T vertice) {

        Integer pos = indice.get(vertice);

        if (pos == null)
            throw new IllegalArgumentException("El vértice no existe.");

        return adj.get(pos);
    }

    /**
     * Devuelve el índice asociado al vértice.
     */
    public int indexOf(T vertice) {

        Integer pos = indice.get(vertice);

        if (pos == null)
            throw new IllegalArgumentException("El vértice no existe.");

        return pos;
    }

    /**
     * Devuelve el vértice asociado al índice.
     */
    public T vertexOf(int indice) {

        if (indice < 0 || indice >= V)
            throw new IllegalArgumentException();

        return vertices.get(indice);
    }

    /**
     * Indica si el vértice pertenece al grafo.
     */
    public boolean contains(T vertice) {
        return indice.containsKey(vertice);
    }

}