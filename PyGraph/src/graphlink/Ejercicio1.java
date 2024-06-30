package graphlink;

import listlinked.ExceptionIsEmpty;

import java.util.ArrayList;

public class Ejercicio1 {

    public static void main(String[] args) {
        GraphLink<String> grafo1 = new GraphLink<>();

        // Insertar vértices
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        Vertex<String> vertexC = new Vertex<>("C");
        Vertex<String> vertexD = new Vertex<>("D");
        Vertex<String> vertexE = new Vertex<>("E");

        grafo1.insertVertex(String.valueOf(vertexA));
        grafo1.insertVertex(String.valueOf(vertexB));
        grafo1.insertVertex(String.valueOf(vertexC));
        grafo1.insertVertex(String.valueOf(vertexD));
        grafo1.insertVertex(String.valueOf(vertexE));

        // Insertar aristas
        grafo1.insertEdge("A", "B");
        grafo1.insertEdge("B", "D");
        grafo1.insertEdge("A", "D");
        grafo1.insertEdge("C", "E");

        System.out.println("Grafo después de insertar vértices y aristas:");
        System.out.println(grafo1);
        System.out.println("Cantidad de vértices en el grafo1: " + grafo1.countVertices());

        try {
            // Recorrido BFS desde el vértice 'E'
            System.out.println("\nRecorrido BFS desde el vértice 'E':");
            grafo1.bfs(vertexE);
            System.out.println();

            // Camino BFS desde 'A' a 'C'
            System.out.println("\nCamino BFS desde 'A' a 'E':");
            ArrayList<Vertex<String>> pathAC = grafo1.bfsPath(vertexA, vertexE);
            if (pathAC != null) {
                for (Vertex<String> vertex : pathAC) {
                    System.out.print(vertex.getData() + " ");
                }
                System.out.println();
            } else {
                System.out.println("No se encontró un camino.");
            }
        } catch (ExceptionIsEmpty | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
