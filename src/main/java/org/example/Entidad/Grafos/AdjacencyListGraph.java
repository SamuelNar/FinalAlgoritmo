package org.example.Entidad.Grafos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class AdjacencyListGraph<T extends Comparable<? super T>> implements Graph<T> {

    private int V; // capacidad máxima de vértices
    private int E; // cantidad de aristas
    private int count; // cantidad actual de vértices agregados
    private TreeMap<T, Integer> map; // mapea valor a índice
    private T[] keys; // arreglo de vértices (por índice)
    private List<Integer>[] adj; // arreglo de listas de adyacencia

    @SuppressWarnings("unchecked")
    public AdjacencyListGraph(int V) {
        if (V < 0) {
            throw new RuntimeException("Capacidad negativa");
        }
        this.V = V;
        this.E = 0;
        this.count = 0;
        map = new TreeMap<>();
        keys = (T[]) new Comparable[V];
        adj = new LinkedList[V]; // inicializo arreglo de listas pero no las listas aún
    }

    T nameOf(int v) {
        return keys[v];
    }

    int indexOf(T v) {
        Integer idx = map.get(v);
        if (idx == null) {
            throw new IllegalArgumentException("El vértice no existe");
        }
        return idx;
    }

    @Override
    public int V() {
        return count; // cantidad actual de vértices
    }

    @Override
    public int E() {
        return E;
    }

    @Override
    public void addVertex(T v) {
        if (containsVertex(v)) {
            throw new RuntimeException("Vértice ya existe");
        }
        if (count >= V) {
            throw new RuntimeException("Se alcanzó la capacidad máxima");
        }
        map.put(v, count);
        keys[count] = v;
        adj[count] = new LinkedList<>(); // inicializo la lista para el nuevo vértice
        count++;
    }

    @Override
    public boolean containsVertex(T v) {
        return map.containsKey(v);
    }

    @Override
    public void addEdge(T v, T w) {
        if (!containsVertex(v) || !containsVertex(w)) {
            throw new IllegalArgumentException("Uno o ambos vértices no existen");
        }
        int vid = indexOf(v);
        int wid = indexOf(w);
        adj[vid].add(wid);
        adj[wid].add(vid);
        E++;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int v = 0; v < count; v++) {
            s.append(nameOf(v)).append(": ");
            if (adj[v] != null) {
                for (int w : adj[v]) {
                    s.append(nameOf(w)).append(" ");
                }
            }
            s.append("\n");
        }
        return s.toString();
    }
}
