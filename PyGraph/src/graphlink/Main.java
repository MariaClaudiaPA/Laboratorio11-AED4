package graphlink;

public class Main {
    public static void main(String[] args) throws ExceptionIsEmpty {
        GraphLink<String> graph = new GraphLink<>();

        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");

        graph.insertEdge("A", "B");
        graph.insertEdge("A", "C");
        graph.insertEdge("B", "D");
        graph.insertEdge("C", "D");
        graph.insertEdge("D", "E");

        graph.insertEdgeWeight("A", "D", 50);
        graph.insertEdgeWeight("B", "E", 30);

        System.out.println("Grafo:");
        System.out.println(graph);

//        System.out.println("DFS desde A:");
//        try {
//            graph.dfs("A");
//        } catch (ExceptionIsFull | ExceptionIsEmpty e) {
//        System.out.println(e);
//        }

        System.out.println("BFS desde el vértice 'A':");
                graph.bfs("A");
        
        System.out.println("\nBuscar vértice 'C': " + (graph.searchVertex("C") != null));
        System.out.println("Buscar arista de A a D: " + graph.searchEdge("A", "D"));
        System.out.println("Buscar arista de A a E: " + graph.searchEdge("A", "E"));

        graph.removeEdge("A", "B");
        graph.removeVertex("C");

        System.out.println("Grafo después de eliminar arista A-B y vértice C:");
        System.out.println(graph);
    }
}
