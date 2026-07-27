import org.example.Entidad.Grafos.AdjacencyMatrixGraph;
import org.example.Entidad.Grafos.AdjacencyMatrixIntGraph;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixGraphTest {

    // ---- Versión genérica ----

    @Test
    public void testAgregarVerticesYAristas() {
        AdjacencyMatrixGraph<String> graph = new AdjacencyMatrixGraph<>(5);

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        assertEquals(3, graph.V());
        assertEquals(0, graph.E());
        assertTrue(graph.containsVertex("A"));
        assertTrue(graph.containsVertex("B"));
        assertTrue(graph.containsVertex("C"));
        assertFalse(graph.containsVertex("D"));

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        assertEquals(2, graph.E());

        // Verifico que las aristas estén reflejadas (no dirigidas)
        String toString = graph.toString();
        assertTrue(toString.contains("A: B"));
        assertTrue(toString.contains("B: A C"));
        assertTrue(toString.contains("C: B"));
    }

    @Test
    public void testAgregarVerticeDuplicadoLanzaExcepcion() {
        AdjacencyMatrixGraph<Integer> graph = new AdjacencyMatrixGraph<>(3);
        graph.addVertex(1);
        assertThrows(RuntimeException.class, () -> graph.addVertex(1));
    }

    @Test
    public void testAgregarAristaConVerticeNoExistenteLanzaExcepcion() {
        AdjacencyMatrixGraph<String> graph = new AdjacencyMatrixGraph<>(3);
        graph.addVertex("X");
        graph.addVertex("Y");

        assertThrows(IllegalArgumentException.class, () -> graph.addEdge("X", "Z"));
    }

    @Test
    public void testCapacidadMaximaDeVertices() {
        AdjacencyMatrixGraph<String> graph = new AdjacencyMatrixGraph<>(2);
        graph.addVertex("A");
        graph.addVertex("B");

        assertThrows(RuntimeException.class, () -> graph.addVertex("C"));
    }

    @Test
    public void testAristaDuplicadaNoSumaE() {
        AdjacencyMatrixGraph<String> graph = new AdjacencyMatrixGraph<>(3);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");
        graph.addEdge("A", "B"); // la matriz no admite aristas paralelas
        assertEquals(1, graph.E());
    }

    // ---- Versión int ----

    @Test
    public void testIntAgregarAristas() {
        AdjacencyMatrixIntGraph graph = new AdjacencyMatrixIntGraph(5);

        assertEquals(5, graph.V());
        assertEquals(0, graph.E());

        graph.AddEdge(0, 1);
        graph.AddEdge(1, 2);
        graph.AddEdge(0, 3);

        assertEquals(3, graph.E());

        // No dirigido: la adyacencia es simétrica
        assertEquals(List.of(1, 3), graph.adj(0));
        assertEquals(List.of(0, 2), graph.adj(1));
        assertEquals(List.of(1), graph.adj(2));
        assertEquals(List.of(0), graph.adj(3));
        assertTrue(graph.adj(4).isEmpty());
    }

    @Test
    public void testIntAristaDirigida() {
        AdjacencyMatrixIntGraph graph = new AdjacencyMatrixIntGraph(3);
        graph.AddEdgeD(0, 1);

        assertEquals(1, graph.E());
        assertEquals(List.of(1), graph.adj(0));
        assertTrue(graph.adj(1).isEmpty());
    }

    @Test
    public void testIntVerticeInvalidoLanzaExcepcion() {
        AdjacencyMatrixIntGraph graph = new AdjacencyMatrixIntGraph(3);
        assertThrows(IllegalArgumentException.class, () -> graph.AddEdge(0, 3));
        assertThrows(IllegalArgumentException.class, () -> graph.AddEdge(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> graph.adj(5));
    }

    @Test
    public void testIntCapacidadNegativaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new AdjacencyMatrixIntGraph(-1));
    }
}
