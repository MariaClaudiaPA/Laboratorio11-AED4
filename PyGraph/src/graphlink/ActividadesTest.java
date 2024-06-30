package graphlink;

public class ActividadesTest {

    public static void main(String[] args) {
        GraphLink<String> grafo = new GraphLink<>();

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

        // Búsqueda de vértices
        System.out.println("\nBuscando el vértice 'A' en el grafo...");
        Vertex<String> vertexA = new Vertex<>("A");
        boolean foundA = grafo.searchVertex(vertexA);
        System.out.println("¿Se encontró el vértice 'A'? " + foundA);

        System.out.println("\nBuscando el vértice 'F' en el grafo...");
        Vertex<String> vertexF = new Vertex<>("F");
        boolean foundF = grafo.searchVertex(vertexF);
        System.out.println("¿Se encontró el vértice 'F'? " + foundF);

        // Búsqueda de aristas
        System.out.println("\nBuscando la arista entre 'A' y 'B' en el grafo...");
        Vertex<String> vertexA2 = new Vertex<>("A");
        Vertex<String> vertexB2 = new Vertex<>("B");
        boolean foundAB = grafo.searchEdge(vertexA2, vertexB2);
        System.out.println("¿Existe la arista entre 'A' y 'B'? " + foundAB);

        System.out.println("\nBuscando la arista entre 'B' y 'C' en el grafo...");
        Vertex<String> vertexB3 = new Vertex<>("B");
        Vertex<String> vertexC3 = new Vertex<>("C");
        boolean foundBC = grafo.searchEdge(vertexB3, vertexC3);
        System.out.println("¿Existe la arista entre 'B' y 'C'? " + foundBC);

        // Operaciones adicionales
        System.out.println("\nRecorrido DFS desde el vértice 'A':");
        try {
            grafo.dfs(vertexA);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nEliminando el vértice 'B' del grafo...");
        try {
            Vertex<String> vertexB = new Vertex<>("B");
            grafo.removeVertex(vertexB);
            System.out.println("\nGrafo después de eliminar el vértice 'B' y buscarlo: " + grafo.searchVertex(vertexB));
            System.out.println(grafo);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }


    }
}
