package org.example.Entidad.GrafoPeso;

public class UF {

    private final int[] parent;
    private final int[] size;

    private int componentCount;

    public UF(int vertexCount) {

        if (vertexCount < 0) {
            throw new IllegalArgumentException(
                    "La cantidad de vértices no puede ser negativa"
            );
        }

        parent = new int[vertexCount];
        size = new int[vertexCount];

        componentCount = vertexCount;

        /*
         * Al principio cada vértice forma su
         * propio conjunto.
         */
        for (int v = 0; v < vertexCount; v++) {
            parent[v] = v;
            size[v] = 1;
        }
    }

    /*
     * Busca el representante del conjunto
     * al cual pertenece el vértice.
     */
    public int find(int vertex) {

        validateVertex(vertex);

        int root = vertex;

        /*
         * Buscar la raíz.
         */
        while (root != parent[root]) {
            root = parent[root];
        }

        /*
         * Compresión de caminos.
         */
        while (vertex != root) {

            int next = parent[vertex];

            parent[vertex] = root;

            vertex = next;
        }

        return root;
    }

    /*
     * Devuelve true si ambos vértices pertenecen
     * al mismo componente.
     */
    public boolean connected(int v, int w) {
        return find(v) == find(w);
    }

    /*
     * Une los componentes de v y w.
     */
    public void union(int v, int w) {

        int rootV = find(v);
        int rootW = find(w);

        /*
         * Ya pertenecen al mismo conjunto.
         */
        if (rootV == rootW) {
            return;
        }

        /*
         * Unimos el árbol más pequeño debajo
         * del árbol más grande.
         */
        if (size[rootV] < size[rootW]) {

            parent[rootV] = rootW;
            size[rootW] += size[rootV];

        } else {

            parent[rootW] = rootV;
            size[rootV] += size[rootW];
        }

        componentCount--;
    }

    /*
     * Cantidad actual de componentes.
     */
    public int count() {
        return componentCount;
    }

    private void validateVertex(int vertex) {

        if (vertex < 0 || vertex >= parent.length) {
            throw new IllegalArgumentException(
                    "El vértice debe estar entre 0 y "
                            + (parent.length - 1)
            );
        }
    }
}