package graphlink;

import java.util.ArrayList;

public class Ejercicio4B {

    public static void main(String[] args) throws ExceptionIsEmpty {
        // Crear un grafo
        GraphLink<Integer> graph = new GraphLink<>();

        graph.insertVertex(1);
        graph.insertVertex(2);
        graph.insertVertex(3);
        graph.insertVertex(4);

        graph.insertEdgeWeight(1, 2, 1);  // V1 <-> V2
        graph.insertEdgeWeight(1, 3, 4);  // V1 <-> V3
        graph.insertEdgeWeight(2, 3, 2);  // V2 <-> V3
        graph.insertEdgeWeight(3, 4, 5);  // V3 <-> V4

        int startVertexData = 1;  
        graph.shortPathDijkstra(startVertexData);

        for (int i = 1; i <= 4; i++) {
            if (i != startVertexData) {
                Vertex<Integer> startVertex = graph.searchVertex(startVertexData);
                Vertex<Integer> endVertex = graph.searchVertex(i);
                ArrayList<Integer> path = graph.getPathTo(startVertex, endVertex);
                int distance = graph.distances.get(startVertex).get(endVertex);

                System.out.print("Camino más corto desde " + startVertexData + " hasta " + i + ": ");
                if (path != null) {
                    System.out.print(path + ", Distancia: " + distance);
                } else {
                    System.out.print("No se encontró camino");
                }
                System.out.println();
            }
        }
    }
}
