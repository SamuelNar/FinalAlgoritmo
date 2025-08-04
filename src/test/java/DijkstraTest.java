import org.example.Entidad.GrafoPeso.Dijkstrat;
import org.example.Entidad.GrafoPeso.DirectEdge;
import org.example.Entidad.GrafoPeso.EdgeWeightedIntDigraph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;

public class DijkstraTest {

    /*
        1.testShortestPathsFromSource()
        2.testDirectPath() - Camino simple directo
        3.testUnreachableVertex() - Vértices inalcanzables
        4.testSelfLoop() - Distancia a sí mismo
        5.testComplexGraph() - Grafo con múltiples caminos
        6.testSingleVertex() - Grafo de un solo vértice
        7.testDecimalWeights() - Pesos con decimales
        8.testPathIntegrity() - Verificación de integridad del camino
        9.testEmptyGraph() - Grafo sin aristas
        10.testMultipleQueriesConsistency() - Consistencia en múltiples consultas
        11.testAlternativePaths() - Elección del camino más corto entre alternativas
     */
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

    @Test
    public void testDirectPath() {
        EdgeWeightedIntDigraph g = new EdgeWeightedIntDigraph(3);
        g.addEdge(new DirectEdge(0, 1, 5));
        g.addEdge(new DirectEdge(1, 2, 3));

        Dijkstrat dijkstra = new Dijkstrat(g, 0);

        assertEquals(0.0, dijkstra.distTo(0), 0.0001);
        assertEquals(5.0, dijkstra.distTo(1), 0.0001);
        assertEquals(8.0, dijkstra.distTo(2), 0.0001);

        // Verificar camino directo a 1
        List<DirectEdge> expectedPathTo1 = List.of(new DirectEdge(0, 1, 5));
        List<DirectEdge> actualPathTo1 = new ArrayList<>();
        dijkstra.pathTo(1).forEach(actualPathTo1::add);
        assertEquals(expectedPathTo1.size(), actualPathTo1.size());
        assertEquals(expectedPathTo1.get(0).from, actualPathTo1.get(0).from);
        assertEquals(expectedPathTo1.get(0).to, actualPathTo1.get(0).to);
        assertEquals(expectedPathTo1.get(0).weight, actualPathTo1.get(0).weight, 0.0001);
    }

    @Test
    public void testUnreachableVertex() {
        EdgeWeightedIntDigraph g = new EdgeWeightedIntDigraph(4);
        g.addEdge(new DirectEdge(0, 1, 5));
        g.addEdge(new DirectEdge(1, 2, 3));
        // Vértice 3 queda aislado

        Dijkstrat dijkstra = new Dijkstrat(g, 0);

        assertEquals(0.0, dijkstra.distTo(0), 0.0001);
        assertEquals(5.0, dijkstra.distTo(1), 0.0001);
        assertEquals(8.0, dijkstra.distTo(2), 0.0001);
        assertEquals(Double.POSITIVE_INFINITY, dijkstra.distTo(3));

        assertFalse(dijkstra.hasPathTo(3));
        assertNull(dijkstra.pathTo(3));
    }

    @Test
    public void testSelfLoop() {
        EdgeWeightedIntDigraph g = new EdgeWeightedIntDigraph(2);
        g.addEdge(new DirectEdge(0, 1, 10));

        Dijkstrat dijkstra = new Dijkstrat(g, 0);

        assertEquals(0.0, dijkstra.distTo(0), 0.0001);
        assertTrue(dijkstra.hasPathTo(0));

        // El camino a sí mismo debe estar vacío
        List<DirectEdge> pathToSelf = new ArrayList<>();
        dijkstra.pathTo(0).forEach(pathToSelf::add);
        assertEquals(0, pathToSelf.size());
    }

    @Test
    public void testComplexGraph() {
        EdgeWeightedIntDigraph g = new EdgeWeightedIntDigraph(6);
        g.addEdge(new DirectEdge(0, 1, 4));
        g.addEdge(new DirectEdge(0, 2, 2));
        g.addEdge(new DirectEdge(1, 2, 1));
        g.addEdge(new DirectEdge(1, 3, 5));
        g.addEdge(new DirectEdge(2, 3, 8));
        g.addEdge(new DirectEdge(2, 4, 10));
        g.addEdge(new DirectEdge(3, 4, 2));
        g.addEdge(new DirectEdge(3, 5, 6));
        g.addEdge(new DirectEdge(4, 5, 3));

        Dijkstrat dijkstra = new Dijkstrat(g, 0);

        assertEquals(0.0, dijkstra.distTo(0), 0.0001);
        assertEquals(4.0, dijkstra.distTo(1), 0.0001);
        assertEquals(2.0, dijkstra.distTo(2), 0.0001);
        assertEquals(9.0, dijkstra.distTo(3), 0.0001); // 0->1->3
        assertEquals(11.0, dijkstra.distTo(4), 0.0001); // 0->1->3->4
        assertEquals(14.0, dijkstra.distTo(5), 0.0001); // 0->1->3->4->5

        // Verificar todos los vértices son alcanzables
        for (int v = 0; v < 6; v++) {
            assertTrue(dijkstra.hasPathTo(v));
        }

        // Verificar camino específico a 4
        List<DirectEdge> expectedPathTo4 = List.of(
                new DirectEdge(0, 1, 4),
                new DirectEdge(1, 3, 5),
                new DirectEdge(3, 4, 2)
        );
        List<DirectEdge> actualPathTo4 = new ArrayList<>();
        dijkstra.pathTo(4).forEach(actualPathTo4::add);
        assertEquals(expectedPathTo4.size(), actualPathTo4.size());
        for (int i = 0; i < expectedPathTo4.size(); i++) {
            DirectEdge expected = expectedPathTo4.get(i);
            DirectEdge actual = actualPathTo4.get(i);
            assertEquals(expected.from, actual.from);
            assertEquals(expected.to, actual.to);
            assertEquals(expected.weight, actual.weight, 0.0001);
        }
    }

    @Test
    public void testSingleVertex() {
        EdgeWeightedIntDigraph g = new EdgeWeightedIntDigraph(1);

        Dijkstrat dijkstra = new Dijkstrat(g, 0);

        assertEquals(0.0, dijkstra.distTo(0), 0.0001);
        assertTrue(dijkstra.hasPathTo(0));

        List<DirectEdge> pathToSelf = new ArrayList<>();
        dijkstra.pathTo(0).forEach(pathToSelf::add);
        assertEquals(0, pathToSelf.size());
    }

    @Test
    public void testDecimalWeights() {
        EdgeWeightedIntDigraph g = new EdgeWeightedIntDigraph(3);
        g.addEdge(new DirectEdge(0, 1, 1.5));
        g.addEdge(new DirectEdge(1, 2, 2.7));
        g.addEdge(new DirectEdge(0, 2, 4.5));

        Dijkstrat dijkstra = new Dijkstrat(g, 0);

        assertEquals(0.0, dijkstra.distTo(0), 0.0001);
        assertEquals(1.5, dijkstra.distTo(1), 0.0001);
        assertEquals(4.2, dijkstra.distTo(2), 0.0001); // 1.5 + 2.7 = 4.2 < 4.5

        // Verificar que toma la ruta más corta
        List<DirectEdge> expectedPathTo2 = List.of(
                new DirectEdge(0, 1, 1.5),
                new DirectEdge(1, 2, 2.7)
        );
        List<DirectEdge> actualPathTo2 = new ArrayList<>();
        dijkstra.pathTo(2).forEach(actualPathTo2::add);
        assertEquals(expectedPathTo2.size(), actualPathTo2.size());
        for (int i = 0; i < expectedPathTo2.size(); i++) {
            DirectEdge expected = expectedPathTo2.get(i);
            DirectEdge actual = actualPathTo2.get(i);
            assertEquals(expected.from, actual.from);
            assertEquals(expected.to, actual.to);
            assertEquals(expected.weight, actual.weight, 0.0001);
        }
    }

    @Test
    public void testPathIntegrity() {
        EdgeWeightedIntDigraph g = new EdgeWeightedIntDigraph(4);
        g.addEdge(new DirectEdge(0, 1, 3));
        g.addEdge(new DirectEdge(1, 2, 2));
        g.addEdge(new DirectEdge(2, 3, 1));

        Dijkstrat dijkstra = new Dijkstrat(g, 0);

        // Verificar integridad del camino completo
        List<DirectEdge> pathTo3 = new ArrayList<>();
        dijkstra.pathTo(3).forEach(pathTo3::add);

        double totalWeight = 0.0;
        int currentVertex = 0;

        for (DirectEdge edge : pathTo3) {
            assertEquals(currentVertex, edge.from);
            currentVertex = edge.to;
            totalWeight += edge.weight;
        }

        assertEquals(3, currentVertex); // Termina en vértice correcto
        assertEquals(6.0, totalWeight, 0.0001); // Peso total correcto
        assertEquals(dijkstra.distTo(3), totalWeight, 0.0001); // Consistencia
    }

    @Test
    public void testEmptyGraph() {
        EdgeWeightedIntDigraph g = new EdgeWeightedIntDigraph(5);
        // Grafo sin aristas

        Dijkstrat dijkstra = new Dijkstrat(g, 0);

        assertEquals(0.0, dijkstra.distTo(0), 0.0001);
        assertTrue(dijkstra.hasPathTo(0));

        // Los demás vértices no son alcanzables
        for (int v = 1; v < 5; v++) {
            assertFalse(dijkstra.hasPathTo(v));
            assertEquals(Double.POSITIVE_INFINITY, dijkstra.distTo(v));
            assertNull(dijkstra.pathTo(v));
        }
    }

    @Test
    public void testMultipleQueriesConsistency() {
        EdgeWeightedIntDigraph g = new EdgeWeightedIntDigraph(3);
        g.addEdge(new DirectEdge(0, 1, 5));
        g.addEdge(new DirectEdge(1, 2, 3));

        Dijkstrat dijkstra = new Dijkstrat(g, 0);

        // Múltiples consultas deben dar resultados consistentes
        for (int i = 0; i < 5; i++) {
            assertEquals(5.0, dijkstra.distTo(1), 0.0001);
            assertEquals(8.0, dijkstra.distTo(2), 0.0001);
            assertTrue(dijkstra.hasPathTo(1));
            assertTrue(dijkstra.hasPathTo(2));
        }
    }

    @Test
    public void testAlternativePaths() {
        EdgeWeightedIntDigraph g = new EdgeWeightedIntDigraph(4);
        g.addEdge(new DirectEdge(0, 1, 10));  // Camino directo caro
        g.addEdge(new DirectEdge(0, 2, 3));   // Camino indirecto
        g.addEdge(new DirectEdge(2, 3, 2));
        g.addEdge(new DirectEdge(3, 1, 1));   // Camino más barato: 0->2->3->1

        Dijkstrat dijkstra = new Dijkstrat(g, 0);

        assertEquals(0.0, dijkstra.distTo(0), 0.0001);
        assertEquals(6.0, dijkstra.distTo(1), 0.0001); // 0->2->3->1 = 3+2+1 = 6 < 10
        assertEquals(3.0, dijkstra.distTo(2), 0.0001);
        assertEquals(5.0, dijkstra.distTo(3), 0.0001);

        // Verificar que eligió el camino más corto a 1
        List<DirectEdge> expectedPathTo1 = List.of(
                new DirectEdge(0, 2, 3),
                new DirectEdge(2, 3, 2),
                new DirectEdge(3, 1, 1)
        );
        List<DirectEdge> actualPathTo1 = new ArrayList<>();
        dijkstra.pathTo(1).forEach(actualPathTo1::add);
        assertEquals(expectedPathTo1.size(), actualPathTo1.size());
        for (int i = 0; i < expectedPathTo1.size(); i++) {
            DirectEdge expected = expectedPathTo1.get(i);
            DirectEdge actual = actualPathTo1.get(i);
            assertEquals(expected.from, actual.from);
            assertEquals(expected.to, actual.to);
            assertEquals(expected.weight, actual.weight, 0.0001);
        }
    }
}