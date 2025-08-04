import org.example.Entidad.GrafoPeso.Dijkstrat;
import org.example.Entidad.GrafoPeso.DirectEdge;
import org.example.Entidad.GrafoPeso.EdgeWeightedIntDigraph;
import org.example.Entidad.Grafos.AdjacencyListIntGraph;
import org.example.Entidad.Grafos.DepthFirtsSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IntTest {

    private AdjacencyListIntGraph graph;

    @Test
    public void testShortestPathsFromSource() {
        EdgeWeightedIntDigraph g = new EdgeWeightedIntDigraph(5);
        g.addEdge(new DirectEdge(0, 1, 10));
        g.addEdge(new DirectEdge(0, 2, 3));
        g.addEdge(new DirectEdge(2, 1, 1));
        g.addEdge(new DirectEdge(1, 3, 2));
        g.addEdge(new DirectEdge(2, 3, 8));
        g.addEdge(new DirectEdge(3, 4, 7));

        Dijkstrat dijkstra = new Dijkstrat(g, 0);

        assertEquals(0.0, dijkstra.distTo(0), 0.0001);
        assertEquals(4.0, dijkstra.distTo(1), 0.0001); // 0 -> 2 -> 1
        assertEquals(3.0, dijkstra.distTo(2), 0.0001); // 0 -> 2
        assertEquals(6.0, dijkstra.distTo(3), 0.0001); // 0 -> 2 -> 1 -> 3
        assertEquals(13.0, dijkstra.distTo(4), 0.0001); // 0 -> 2 -> 1 -> 3 -> 4

        // Verificamos camino a 3
        List<DirectEdge> expectedPathTo3 = List.of(
                new DirectEdge(0, 2, 3),
                new DirectEdge(2, 1, 1),
                new DirectEdge(1, 3, 2)
        );
        List<DirectEdge> actualPathTo3 = new ArrayList<>();
        dijkstra.pathTo(3).forEach(actualPathTo3::add);

        assertEquals(expectedPathTo3.size(), actualPathTo3.size());
        for (int i = 0; i < expectedPathTo3.size(); i++) {
            DirectEdge expected = expectedPathTo3.get(i);
            DirectEdge actual = actualPathTo3.get(i);
            assertEquals(expected.from, actual.from);
            assertEquals(expected.to, actual.to);
            assertEquals(expected.weight, actual.weight, 0.0001);
        }
    }

    @BeforeEach
    public void setup() {
        graph = new AdjacencyListIntGraph(5);  // grafo con 5 vértices: 0 a 4
    }

    @Test
    public void testInitialGraph() {
        assertEquals(5, graph.V());
        assertEquals(0, graph.E());
        for (int i = 0; i < graph.V(); i++) {
            List<Integer> adjList = graph.adj(i);
            assertNotNull(adjList);
            assertTrue(adjList.isEmpty());
        }
    }

    @Test
    public void testAddEdgeUndirected() {
        graph.AddEdge(1, 3);
        assertEquals(1, graph.E());
        assertTrue(graph.adj(1).contains(3));
        assertTrue(graph.adj(3).contains(1));
    }

    @Test
    public void testAddEdgeDirected() {
        graph.AddEdgeD(2, 4);
        assertEquals(1, graph.E());
        assertTrue(graph.adj(2).contains(4));
        assertFalse(graph.adj(4).contains(2));
    }

    @Test
    public void testMultipleEdges() {
        graph.AddEdge(0, 1);
        graph.AddEdge(0, 2);
        graph.AddEdgeD(3, 4);
        assertEquals(3, graph.E());

        assertTrue(graph.adj(0).contains(1));
        assertTrue(graph.adj(1).contains(0));

        assertTrue(graph.adj(0).contains(2));
        assertTrue(graph.adj(2).contains(0));

        assertTrue(graph.adj(3).contains(4));
        assertFalse(graph.adj(4).contains(3));
    }

    @Test
    public void testInvalidVertexAddEdge() {
        assertThrows(IllegalArgumentException.class, () -> graph.AddEdge(-1, 2));
        assertThrows(IllegalArgumentException.class, () -> graph.AddEdge(0, 5));
        assertThrows(IllegalArgumentException.class, () -> graph.AddEdgeD(5, 1));
        assertThrows(IllegalArgumentException.class, () -> graph.AddEdgeD(1, -2));
    }

    @Test
    public void testInvalidVertexAdj() {
        assertThrows(IllegalArgumentException.class, () -> graph.adj(-1));
        assertThrows(IllegalArgumentException.class, () -> graph.adj(5));
    }

    @Test
    public void testConectividadYCaminos() {
        // Grafo con 5 vértices
        AdjacencyListIntGraph graph = new AdjacencyListIntGraph(5);

        // Agregar aristas para que el grafo sea conexo
        graph.AddEdge(0, 1);
        graph.AddEdge(1, 2);
        graph.AddEdge(2, 3);
        graph.AddEdge(3, 4);

        // Crear objeto DFS desde el nodo 0
        DepthFirtsSearch dfs = new DepthFirtsSearch(graph, 0);

        // Verificar que el grafo es conexo
        assertTrue(dfs.isConnected(), "El grafo debería ser conexo");

        // Verificar que todos los nodos están marcados
        for (int v = 0; v < graph.V(); v++) {
            assertTrue(dfs.isMarked(v), "Nodo " + v + " debería estar marcado");
        }

        // Verificar caminos desde 0 a 4
        List<Integer> path = dfs.pathTo(4);
        assertNotNull(path, "Debe existir un camino de 0 a 4");
        assertEquals(5, path.size(), "El camino debe tener 5 nodos");
        assertEquals(0, path.get(0));
        assertEquals(4, path.get(path.size() - 1));

        // Ver camino exacto (0-1-2-3-4)
        int expected = 0;
        for (int node : path) {
            assertEquals(expected++, node);
        }
    }

    @Test
    public void testGrafoNoConexo() {
        // Grafo con 5 vértices, pero sin conectar todos
        AdjacencyListIntGraph graph = new AdjacencyListIntGraph(5);

        // Solo conectamos 0-1 y 2-3 (4 queda aislado)
        graph.AddEdge(0, 1);
        graph.AddEdge(2, 3);

        DepthFirtsSearch dfs = new DepthFirtsSearch(graph, 0);

        // Grafo no es conexo desde 0
        assertFalse(dfs.isConnected(), "El grafo NO es conexo desde el nodo 0");

        // Nodo 4 no debe estar marcado
        assertFalse(dfs.isMarked(4), "Nodo 4 no debe estar marcado");

        // Nodo 3 tampoco (no conectado a 0)
        assertFalse(dfs.isMarked(3), "Nodo 3 no debe estar marcado");

        // Nodo 1 sí
        assertTrue(dfs.isMarked(1), "Nodo 1 debe estar marcado");

        // pathTo para nodo no alcanzable devuelve null
        assertNull(dfs.pathTo(4), "No debe existir camino a nodo 4");
    }


}
