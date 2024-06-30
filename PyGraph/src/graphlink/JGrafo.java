package graphlink;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class JGrafo {
    public static void main(String[] args) {
        // Crear un grafo no dirigido con v�rtices de tipo String y aristas predeterminadas
        Graph<String, DefaultEdge> grafo = new SimpleGraph<>(DefaultEdge.class);

        // Agregar v�rtices al grafo
        grafo.addVertex("A");
        grafo.addVertex("B");
        grafo.addVertex("C");
        grafo.addVertex("D");
        grafo.addVertex("E");

        // Agregar aristas entre los v�rtices
        grafo.addEdge("A", "B");
        grafo.addEdge("B", "C");
        grafo.addEdge("C", "D");
        grafo.addEdge("D", "E");
        grafo.addEdge("E", "A");

        // Obtener el conjunto de v�rtices
        System.out.println("V�rtices del grafo: " + grafo.vertexSet());

        // Obtener el conjunto de aristas
        System.out.println("Aristas del grafo: " + grafo.edgeSet());

        // Encontrar la ruta m�s corta entre dos v�rtices usando el algoritmo de Dijkstra
        DijkstraShortestPath<String, DefaultEdge> dijkstra = new DijkstraShortestPath<>(grafo);
        GraphPath<String, DefaultEdge> path = dijkstra.getPath("A", "D");

        // Imprimir la ruta m�s corta
        System.out.println("Ruta m�s corta de A a D: " + path.getVertexList());

        // Imprimir el grafo
        System.out.println("Grafo: " + grafo);
    }
}
