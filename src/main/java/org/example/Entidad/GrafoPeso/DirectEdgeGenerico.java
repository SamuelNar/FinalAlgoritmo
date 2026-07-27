package org.example.Entidad.GrafoPeso;

public class DirectEdgeGenerico<T extends Comparable<? super T>> {
    public final T from;
    public final T to;
    public final double weight;

    public DirectEdgeGenerico(T from, T to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
    
}
