package graphlink;

import java.util.ArrayList;

public class Ejercicio4A {

 public static void main(String[] args) {
        GraphLink<Integer> graph = new GraphLink<>();

        graph.insertVertex(1);
        graph.insertVertex(2);
        graph.insertVertex(3);
        graph.insertVertex(4);

        graph.insertEdgeWeight(1, 2, 1);  // V1 <-> V2
        graph.insertEdgeWeight(1, 3, 4);  // V1 <-> V3
        graph.insertEdgeWeight(2, 3, 2);  // V2 <-> V3
        graph.insertEdgeWeight(3, 4, 5);  // V3 <-> V4

        System.out.println("Calculando caminos más cortos usando Floyd-Warshall...");
        graph.floydWarshall();

        Integer origen = 1;
        Integer destino = 4;

        System.out.println("Camino más corto desde " + origen + " a " + destino + ":");
        ArrayList<Integer> shortestPath = graph.shortPath(origen, destino);

        if (shortestPath != null) {
            for (Integer vertex : shortestPath) {
                System.out.print(vertex + " ");
            }
            System.out.println();
        } else {
            System.out.println("No hay camino disponible.");
        }
    }
}
