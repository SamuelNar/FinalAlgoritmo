package org.example.Entidad.Grafos;

import java.util.List;

public interface IntGraph {
    public int V();
    public int E();
    public void AddEdge(int v,int w);
    public List<Integer> adj(int v);
}
