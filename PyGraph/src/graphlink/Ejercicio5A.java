package graphlink;

public class Ejercicio5A {

    public static void main(String[] args) {
        GraphLink<Integer> graph = new GraphLink<>();

        graph.insertVertex(1);
        graph.insertVertex(2);
        graph.insertVertex(3);
        graph.insertVertex(4);
        graph.insertVertex(5);

        graph.insertEdgeWeighted(1, 2, 2); // Vértices 1 y 2, peso 2
        graph.insertEdgeWeighted(1, 3, 4); // Vértices 1 y 3, peso 4
        graph.insertEdgeWeighted(2, 3, 1); // Vértices 2 y 3, peso 1
        graph.insertEdgeWeighted(2, 4, 3); // Vértices 2 y 4, peso 3
        graph.insertEdgeWeighted(3, 4, 5); // Vértices 3 y 4, peso 5
        graph.insertEdgeWeighted(3, 5, 7); // Vértices 3 y 5, peso 7
        graph.insertEdgeWeighted(4, 5, 6); // Vértices 4 y 5, peso 6

        try {
            GraphLink<Integer> mst = graph.primMST();
            System.out.println("Árbol de Expansión Mínima (MST):");
            System.out.println(mst);
        } catch (ExceptionIsEmpty e) {
            System.out.println("Error: La cola de prioridad está vacía.");
        }
    }
}
