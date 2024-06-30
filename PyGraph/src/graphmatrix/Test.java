package graphmatrix;

import graphlink.*;

public class Test {
    public static void main(String[] args) {
        GraphMat<String> grafo = new GraphMat<>(5);

        grafo.insertVertex("A");
        grafo.insertVertex("B");
        grafo.insertVertex("C");
        grafo.insertVertex("D");
        grafo.insertVertex("E");

        grafo.insertEdge("A", "B");
        grafo.insertEdge("A", "C");
        grafo.insertEdge("B", "D");
        grafo.insertEdge("C", "D");
        grafo.insertEdge("C", "E");
        grafo.insertEdge("D", "E");

        System.out.println("Grafo después de insertar vértices y aristas:");
        System.out.println(grafo);

        System.out.println("\nBuscando el vértice 'A':");
        boolean vertexA = grafo.searchVertex(new Vertex<>("A"));
        if (!vertexA) {
            System.out.println("Vértice 'A' encontrado.");
        } else {
            System.out.println("Vértice 'A' no encontrado.");
        }

        System.out.println("\nBuscando la arista entre 'A' y 'B':");
        boolean edgeExists = grafo.searchEdge(new Vertex<>("A"), new Vertex<>("B"));
        if (edgeExists) {
            System.out.println("Arista entre 'A' y 'B' encontrada.");
        } else {
            System.out.println("Arista entre 'A' y 'B' no encontrada.");
        }

        System.out.println("\nRecorrido DFS desde el vértice 'A':");
        grafo.dfs(new Vertex<>("A"));

        System.out.println("\nEliminando el vértice 'B' del grafo...");
        grafo.removeVertex(new Vertex<>("B"));
        System.out.println("Grafo después de eliminar el vértice 'B':");
        System.out.println(grafo);

        System.out.println("\nEliminando la arista entre 'C' y 'D'...");
        grafo.removeEdge(new Vertex<>("C"), new Vertex<>("D"));
        System.out.println("Grafo después de eliminar la arista entre 'C' y 'D':");
        System.out.println(grafo);
    }
}
