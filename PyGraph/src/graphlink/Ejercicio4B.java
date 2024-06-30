package graphlink;

import java.util.ArrayList;
import listlinked.*;

public class Ejercicio4B {

    public static void main(String[] args) {
        GraphLink<String> graph = new GraphLink<>();

        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");

        graph.insertEdgeWeight("A", "B", 2);
        graph.insertEdgeWeight("A", "C", 4);
        graph.insertEdgeWeight("B", "C", 1);
        graph.insertEdgeWeight("B", "D", 3);
        graph.insertEdgeWeight("C", "E", 3);
        graph.insertEdgeWeight("D", "E", 1);
        System.out.println("Grafo:");
        System.out.println(graph);
        try {
            System.out.println("Camino más corto:");
            ArrayList<Vertex<String>> shortestPath = graph.shortPath(new Vertex<>("A"), new Vertex<>("E"));
            for (Vertex<String> vertex : shortestPath) {
                System.out.print(vertex.getData() + " ");
            }
            System.out.println();
        } catch (ExceptionIsEmpty e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
