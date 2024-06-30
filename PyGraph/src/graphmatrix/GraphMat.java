package graphmatrix;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import listlinked.*;
import graphlink.*;

public class GraphMat<E> implements Graph<E> {

    private int[][] adjacenciaMatriz;
    private ListLinked<Vertex<E>> listVertex;
    private int numVertices;
    private int verticesActuales;
    private static final int INF = Integer.MAX_VALUE / 2;

    public GraphMat(int numVertices) {
        this.numVertices = numVertices;
        this.adjacenciaMatriz = new int[numVertices][numVertices];
        this.listVertex = new ListLinked<>();
        this.verticesActuales = 0;

        // Inicializar la matriz de adyacencia con infinito
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (i == j) {
                    adjacenciaMatriz[i][j] = 0;
                } else {
                    adjacenciaMatriz[i][j] = INF;
                }
            }
        }
    }

    @Override
    public void insertVertex(E data) {
        if (!searchVertex(new Vertex<>(data))) {
            if (verticesActuales < numVertices) {
                Vertex<E> newVertex = new Vertex<>(data);
                listVertex.insert(newVertex);
                verticesActuales++;
            } else {
                System.out.println("Capacidad máxima de vértices alcanzada.");
            }
        } else {
            System.out.println("El vértice ya existe.");
        }
    }

    @Override
    public void insertEdge(E verOri, E verDes) {
        Vertex<E> originVertex = new Vertex<>(verOri);
        Vertex<E> destinationVertex = new Vertex<>(verDes);

        if (!searchEdge(originVertex, destinationVertex)) {
            if (searchVertex(originVertex) && searchVertex(destinationVertex)) {
                int originIndex = listVertex.search(originVertex);
                int destinationIndex = listVertex.search(destinationVertex);
                adjacenciaMatriz[originIndex][destinationIndex] = 1;
                adjacenciaMatriz[destinationIndex][originIndex] = 1; // Si es un grafo no dirigido
            } else {
                System.out.println("Uno o ambos vértices no encontrados.");
            }
        } else {
            System.out.println("La arista ya existe.");
        }
    }

    public void insertEdgeWeighted(E verOri, E verDes, int peso) {
        Vertex<E> originVertex = new Vertex<>(verOri);
        Vertex<E> destinationVertex = new Vertex<>(verDes);

        if (!searchEdge(originVertex, destinationVertex)) {
            if (searchVertex(originVertex) && searchVertex(destinationVertex)) {
                int originIndex = listVertex.search(originVertex);
                int destinationIndex = listVertex.search(destinationVertex);
                adjacenciaMatriz[originIndex][destinationIndex] = peso;
                adjacenciaMatriz[destinationIndex][originIndex] = peso; // Si es un grafo no dirigido
            } else {
                System.out.println("Uno o ambos vértices no encontrados.");
            }
        } else {
            System.out.println("La arista ya existe.");
        }
    }

    @Override
    public boolean searchVertex(Vertex<E> v) {
        int index = listVertex.search(v);
        return index != -1;
    }

    @Override
    public boolean searchEdge(Vertex<E> v, Vertex<E> z) {
        if (searchVertex(v) && searchVertex(z)) {
            int originIndex = listVertex.search(v);
            int destinationIndex = listVertex.search(z);
            return adjacenciaMatriz[originIndex][destinationIndex] != INF;
        }
        return false;
    }

    @Override
    public void removeVertex(Vertex<E> v) {
        if (searchVertex(v)) {
            int indexToRemove = listVertex.search(v);
            listVertex.removeNode(v);

            for (int i = indexToRemove; i < numVertices - 1; i++) {
                for (int j = 0; j < numVertices; j++) {
                    adjacenciaMatriz[i][j] = adjacenciaMatriz[i + 1][j];
                }
            }
            for (int j = indexToRemove; j < numVertices - 1; j++) {
                for (int i = 0; i < numVertices; i++) {
                    adjacenciaMatriz[i][j] = adjacenciaMatriz[i][j + 1];
                }
            }
            numVertices--;
            verticesActuales--;
        } else {
            System.out.println("El vértice no se encontró.");
        }
    }

    @Override
    public void removeEdge(Vertex<E> v, Vertex<E> z) {
        if (searchEdge(v, z)) {
            int originIndex = listVertex.search(v);
            int destinationIndex = listVertex.search(z);
            adjacenciaMatriz[originIndex][destinationIndex] = INF;
            adjacenciaMatriz[destinationIndex][originIndex] = INF;
        } else {
            System.out.println("Uno o ambos vértices no se encontraron.");
        }
    }

    public void dfs(Vertex<E> startVertex) {
        boolean[] visited = new boolean[numVertices];
        if (searchVertex(startVertex)) {
            dfsRecursive(startVertex, visited);
        } else {
            System.out.println("El vértice de inicio no se encontró.");
        }
    }

    private void dfsRecursive(Vertex<E> vertex, boolean[] visited) {
        int vertexIndex = listVertex.search(vertex);
        visited[vertexIndex] = true;
        System.out.println("Vértice visitado: " + vertex.getData());

        for (int i = 0; i < numVertices; i++) {
            if (adjacenciaMatriz[vertexIndex][i] != INF && !visited[i]) {
                dfsRecursive(listVertex.getNodeAt(i).getValor(), visited);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < verticesActuales; i++) {
            sb.append("Vértice ").append(listVertex.getNodeAt(i).getValor().getData()).append(": ");
            for (int j = 0; j < verticesActuales; j++) {
                sb.append(adjacenciaMatriz[i][j] == INF ? "INF" : adjacenciaMatriz[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public ArrayList<Vertex<E>> shortPath(Vertex<E> v, Vertex<E> z) {
        int[][] dist = new int[numVertices][numVertices];
        int[][] next = new int[numVertices][numVertices];

        // Inicializar las matrices dist y next
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                dist[i][j] = adjacenciaMatriz[i][j];
                if (adjacenciaMatriz[i][j] != INF) {
                    next[i][j] = j;
                } else {
                    next[i][j] = -1;
                }
            }
        }

        // Aplicar el algoritmo de Floyd-Warshall
        for (int k = 0; k < numVertices; k++) {
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }

        // Construir el camino más corto de v a z
        int originIndex = listVertex.search(v);
        int destinationIndex = listVertex.search(z);
        ArrayList<Vertex<E>> path = new ArrayList<>();

        if (next[originIndex][destinationIndex] == -1) {
            return path; // No hay camino
        }

        int at = originIndex;
        while (at != destinationIndex) {
            path.add(listVertex.getNodeAt(at).getValor());
            at = next[at][destinationIndex];
        }
        path.add(listVertex.getNodeAt(destinationIndex).getValor());

        return path;
    }

//    public GraphMat<E> kruskalMST() {
//        List<Edge<E>> edges = new ArrayList<>();
//        for (int i = 0; i < numVertices; i++) {
//            for (int j = i + 1; j < numVertices; j++) {
//                if (adjacenciaMatriz[i][j] != 0) {
//                    edges.add(new Edge<>(i, j, adjacenciaMatriz[i][j]));
//                }
//            }
//        }
//
//        edges.sort(Comparator.comparingInt(Edge::getWeight));
//
//        GraphMat<E> mst = new GraphMat<>(numVertices);
//        for (int i = 0; i < numVertices; i++) {
//            mst.insertVertex(listVertex.get(i).getData());
//        }
//
//        int[] parent = new int[numVertices];
//        for (int i = 0; i < numVertices; i++) {
//            parent[i] = i;
//        }
//
//        for (Edge<E> edge : edges) {
//            int root1 = find(parent, edge.getOrigin());
//            int root2 = find(parent, edge.getDestination());
//
//            if (root1 != root2) {
//                mst.insertEdge(listVertex.get(edge.getOrigin()).getData(), listVertex.get(edge.getDestination()).getData(), edge.getWeight());
//                parent[root1] = root2;
//            }
//        }
//
//        return mst;
//    }
//
//    private int find(int[] parent, int vertex) {
//        if (parent[vertex] != vertex) {
//            parent[vertex] = find(parent, parent[vertex]);
//        }
//        return parent[vertex];
//    }


}
