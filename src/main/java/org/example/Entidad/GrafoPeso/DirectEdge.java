package org.example.Entidad.GrafoPeso;

public class DirectEdge {
   public final int from;
    public final int to;
    public final double weight;

    public DirectEdge(int from,int to, double weight){
        this.from = from;
        this.to=to;
        this.weight=weight;
    }
}
