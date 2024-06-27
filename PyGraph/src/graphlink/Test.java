package graphlink;

public class Test {

    public static void main(String[] args) {
        GraphLink<Integer> graph = new GraphLink<>();

        graph.insertVertex(1);
        graph.insertVertex(3);
        graph.insertVertex(2);
        graph.insertVertex(7);
        graph.insertVertex(8);
        graph.insertVertex(6);

        graph.insertEdge(1, 3);
        graph.insertEdge(1, 2);
        graph.insertEdge(2, 6);
        graph.insertEdge(3, 7);
        graph.insertEdge(2, 7);
        graph.insertEdge(7, 8);

        System.out.println("Recorrido por profundidad empezando por el vertice 1:");
        try {
            graph.dfs(1);
        } catch (ExceptionIsFull | ExceptionIsEmpty ex) {
            System.out.println(ex);
        }

    }

}
