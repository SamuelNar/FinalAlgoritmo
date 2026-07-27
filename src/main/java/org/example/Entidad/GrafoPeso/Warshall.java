package org.example.Entidad.GrafoPeso;

public class Warshall {

    private final EdgeWeightedIntDigraph G;

    private double[][] distTo;  //Distancia más corta de v a w
    private DirectEdge[][] edgeTo; //Ultima arista en el camino más corto de v a w

    /*
     * Indica si el algoritmo ya fue ejecutado.
     */    

    public Warshall(EdgeWeightedIntDigraph G){
        if(G==null){
            throw new IllegalArgumentException(
                    "El grafo no puede ser null"
            );
        }
        this.G = G;        
    }

    public void FloydWharshall(){
        int V = G.V();  //Cantidad de vértices
        distTo = new double[V][V];  // representación de la distancia más corta de v a w
        edgeTo = new DirectEdge[V][V]; // representación de la última arista en el camino más corto de v a w

        for(int v =0; v < V; v++ ){
            distTo[v][v] = 0.0;  // La distancia más corta de un vértice a sí mismo es 0
            for(int w =0; w < V; w++){
                if(v!=w){
                    distTo[v][w] = Double.POSITIVE_INFINITY; // La distancia más corta de un vértice a otro es infinita , por que no conocemos el camino 
                }
            }
        }

        for (int i = 0; i < V; i++) {
            for (DirectEdge e : G.adj(i)) {
                distTo[e.from][e.to] = e.weight; // Guarda peso de la arista en la matriz de distancias 0 -> 1, peso 5 distTo[0][1] = 5
                edgeTo[e.from][e.to]=e; // Guarda la arista en la matriz de aristas 0 -> 1->3 edgeTo[0][1] = 1->3 ultima arista, 
            }
        }

        for(int k=0; k < V;k++ ){   // k es intermedio, i es el vértice de origen y j es el vértice de destino
            for(int i = 0 ; i<V;i++){
                for (int j = 0; j < distTo.length; j++) {
                    if(distTo[i][j] > distTo[i][k]+distTo[k][j]){
                        distTo[i][j] = distTo[i][k]+distTo[k][j];
                        edgeTo[i][j] = edgeTo[k][j]; // Actualiza la última arista en el camino más corto de i a j a través de k
                    }
                }
            }
        }
    }
}
