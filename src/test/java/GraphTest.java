import org.example.Entidad.Grafos.AdjacencyListGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {

    @Test
    public void testAgregarVerticesYAristas() {
        AdjacencyListGraph<String> graph = new AdjacencyListGraph<>(5);

        // Agregar vértices
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        assertEquals(3, graph.V());
        assertEquals(0, graph.E());
        assertTrue(graph.containsVertex("A"));
        assertTrue(graph.containsVertex("B"));
        assertTrue(graph.containsVertex("C"));
        assertFalse(graph.containsVertex("D"));

        // Agregar aristas
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
        AdjacencyListGraph<Integer> graph = new AdjacencyListGraph<>(3);
        graph.addVertex(1);
        assertThrows(RuntimeException.class, () -> graph.addVertex(1));
    }

    @Test
    public void testAgregarAristaConVerticeNoExistenteLanzaExcepcion() {
        AdjacencyListGraph<String> graph = new AdjacencyListGraph<>(3);
        graph.addVertex("X");
        graph.addVertex("Y");

        // El vértice "Z" no existe
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge("X", "Z"));
    }

    @Test
    public void testCapacidadMaximaDeVertices() {
        AdjacencyListGraph<String> graph = new AdjacencyListGraph<>(2);
        graph.addVertex("A");
        graph.addVertex("B");

        // Ya se alcanzó la capacidad
        assertThrows(RuntimeException.class, () -> graph.addVertex("C"));
    }

    @Test
    public void test0_agregarVerticesYAristas() {
        AdjacencyListGraph<String> grafo = new AdjacencyListGraph<>(10);

        grafo.addVertex("A");
        grafo.addVertex("B");
        grafo.addVertex("C");
        grafo.addVertex("D");

        grafo.addEdge("A", "B");
        grafo.addEdge("A", "C");
        grafo.addEdge("B", "D");

        String resultado = grafo.toString().trim();

        // Aseguramos que la salida contiene las conexiones esperadas
        assertTrue(resultado.contains("A: B C") || resultado.contains("A: C B"));
        assertTrue(resultado.contains("B: A D") || resultado.contains("B: D A"));
        assertTrue(resultado.contains("C: A"));
        assertTrue(resultado.contains("D: B"));
    }

    @Test
    public void test1_verticeDuplicado() {
        AdjacencyListGraph<String> grafo = new AdjacencyListGraph<>(5);
        grafo.addVertex("X");

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            grafo.addVertex("X");
        });

        assertNotNull(thrown);
    }
}
