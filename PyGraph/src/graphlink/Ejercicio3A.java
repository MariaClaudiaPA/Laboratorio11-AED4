package graphlink;

import listlinked.*;

public class Ejercicio3A {
    public static void main(String[] args) {
        GraphLink<String> graph = new GraphLink<>();

        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");

        graph.insertEdgeWeight("A", "B", 2);
        graph.insertEdgeWeight("A", "C", 1);
        graph.insertEdgeWeight("B", "C", 4);
        graph.insertEdgeWeight("B", "D", 3);
        graph.insertEdgeWeight("C", "D", 5);
        graph.insertEdgeWeight("C", "E", 7);
        graph.insertEdgeWeight("D", "E", 6);

    }
}