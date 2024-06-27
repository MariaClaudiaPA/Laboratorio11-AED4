package graphmatrix;

public class GraphMat implements Grafo {

    private int[][] adjMatrix;
    private int vertices;

    public GraphMat(int vertices) {
        this.vertices = vertices;
        this.adjMatrix = new int[vertices][vertices];
    }

    @Override
    public void insertVertex(int v) {
        if (v >= vertices) {
            int[][] nuevaMat = new int[v + 1][v + 1];
            for (int i = 0; i < vertices; i++) {
                System.arraycopy(adjMatrix[i], 0, nuevaMat[i], 0, vertices);
            }
            adjMatrix = nuevaMat;
            vertices = v + 1;
        } else if (v < 0) {
            throw new IllegalArgumentException("No debe ser negativo");
        }
    }

    @Override
    public void insertEdge(int v, int z) {
        if (v < vertices && z < vertices) {
            adjMatrix[v][z] = 1;
            adjMatrix[z][v] = 1;
        } else {
            throw new IndexOutOfBoundsException("Vertex indice fuera de rango");
        }
    }

    @Override
    public boolean searchVertex(int v) {
        return v < vertices;
    }

    @Override
    public boolean searchEdge(int v, int z) {
        if (v < vertices && z < vertices) {
            return adjMatrix[v][z] == 1;
        } else {
            throw new IndexOutOfBoundsException("Vertex indice fuera de rango");
        }
    }

    @Override
    public void dfs(int v) {
        boolean[] visited = new boolean[vertices];
        dfsRecursive(v, visited);
    }

    private void dfsRecursive(int v, boolean[] visited) {
        visited[v] = true;
        System.out.print(v + " ");

        for (int i = 0; i < vertices; i++) {
            if (adjMatrix[v][i] == 1 && !visited[i]) {
                dfsRecursive(i, visited);
            }
        }
    }

    public static void main(String[] args) {
        GraphMat graph = new GraphMat(4);

        graph.insertVertex(0);
        graph.insertVertex(1);
        graph.insertVertex(2);
        graph.insertVertex(3);

        graph.insertEdge(0, 1);
        graph.insertEdge(0, 2);
        graph.insertEdge(1, 3);

        System.out.println(graph.searchVertex(2));
        System.out.println(graph.searchEdge(1, 2));

        System.out.print("DFS: ");
        graph.dfs(0);
    }
}
