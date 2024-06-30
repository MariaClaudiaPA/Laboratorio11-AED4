package graphlink;

import graphmatrix.GraphMat;

import java.util.ArrayList;

public class Ejercicio2Y4A {
    public static void main(String[] args) {
        GraphMat<String> graph = new GraphMat<>(5);

        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");

        graph.insertEdgeWeighted("A", "B", 2);
        graph.insertEdgeWeighted("A", "C", 4);
        graph.insertEdgeWeighted("B", "C", 1);
        graph.insertEdgeWeighted("B", "D", 3);
        graph.insertEdgeWeighted("C", "E", 3);
        graph.insertEdgeWeighted("D", "E", 1);
        graph.insertEdgeWeighted("A", "E", 1);
        System.out.println("Matriz de Adyacencia:");
        System.out.println(graph);
        Vertex<String> v = new Vertex<>("A");
        Vertex<String> z = new Vertex<>("E");
        ArrayList<Vertex<String>> path = graph.shortPath(v, z);

        System.out.println("Camino más corto de " + v.getData() + " a " + z.getData() + ":");
        for (Vertex<String> vertex : path) {
            System.out.print(vertex.getData() + " ");
        }
    }
}

