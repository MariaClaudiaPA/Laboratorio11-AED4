package graphlink;

import listlinked.ExceptionIsEmpty;

import java.util.ArrayList;

public class Ejercicio5A {
    public static void main(String[] args) {
        GraphLink<String> graph = new GraphLink<>();
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");

        graph.insertEdgeWeight("A", "B", 2);
        graph.insertEdgeWeight("A", "C", 3);
        graph.insertEdgeWeight("B", "C", 1);
        graph.insertEdgeWeight("B", "D", 4);
        graph.insertEdgeWeight("C", "D", 2);
        graph.insertEdgeWeight("D", "E", 4);

        try {
            ArrayList<Edge<String>> mst = graph.primMST();
            System.out.println("Árbol de Expansión Mínima usando Prim:");
            for (Edge<String> edge : mst) {
                System.out.println(edge.refDest.getData() + " : " + edge.getWeight());
            }
        } catch (ExceptionIsEmpty e) {
            e.printStackTrace();
        }
    }
}
