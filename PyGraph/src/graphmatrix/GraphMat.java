package graphmatrix;

import graphlink.*;

public class GraphMat<E> implements Graph<E> {

    private int[][] adjacencyMatrix;
    private ListLinked<Vertex<E>> listVertex;
    private int numVertices;

    public GraphMat(int numVertices) {
        this.numVertices = numVertices;
        this.adjacencyMatrix = new int[numVertices][numVertices];
        this.listVertex = new ListLinked<>();
    }

    @Override
    public void insertVertex(E data) {
        if (searchVertex(data) == null) {
            Vertex<E> newVertex = new Vertex<>(data);
            listVertex.insert(newVertex);
        } else {
            System.out.println("Vertex already exists.");
        }
    }

    @Override
    public void insertEdge(E verOri, E verDes) {
        if (!searchEdge(verOri, verDes)) {
            Vertex<E> originVertex = searchVertex(verOri);
            Vertex<E> destinationVertex = searchVertex(verDes);

            if (originVertex != null && destinationVertex != null) {
                int originIndex = listVertex.search(originVertex);
                int destinationIndex = listVertex.search(destinationVertex);
                adjacencyMatrix[originIndex][destinationIndex] = 1; // or set weight as needed
                adjacencyMatrix[destinationIndex][originIndex] = 1; // for undirected graph
            } else {
                System.out.println("One or both vertices not found.");
            }
        } else {
            System.out.println("Edge already exists.");
        }
    }

    @Override
    public Vertex<E> searchVertex(E data) {
        Node<Vertex<E>> current = listVertex.getPrimero();
        while (current != null) {
            Vertex<E> vertex = current.getValor();
            if (vertex.getData().equals(data)) {
                return vertex;
            }
            current = current.getSiguiente();
        }
        return null;
    }

    @Override
    public boolean searchEdge(E verOri, E verDes) {
        Vertex<E> originVertex = searchVertex(verOri);
        Vertex<E> destinationVertex = searchVertex(verDes);

        if (originVertex != null && destinationVertex != null) {
            int originIndex = listVertex.search(originVertex);
            int destinationIndex = listVertex.search(destinationVertex);
            return adjacencyMatrix[originIndex][destinationIndex] != 0; // Check edge presence
        }
        return false;
    }

    @Override
    public void removeVertex(E data) {
        Vertex<E> vertexToRemove = searchVertex(data);
        if (vertexToRemove != null) {
            int indexToRemove = listVertex.search(vertexToRemove);
            listVertex.removeNode(vertexToRemove);

            // Shift adjacency matrix rows and columns to remove vertex
            for (int i = indexToRemove; i < numVertices - 1; i++) {
                for (int j = 0; j < numVertices; j++) {
                    adjacencyMatrix[i][j] = adjacencyMatrix[i + 1][j];
                }
            }
            for (int j = indexToRemove; j < numVertices - 1; j++) {
                for (int i = 0; i < numVertices; i++) {
                    adjacencyMatrix[i][j] = adjacencyMatrix[i][j + 1];
                }
            }
            numVertices--;
        } else {
            System.out.println("Vertex not found.");
        }
    }

    @Override
    public void removeEdge(E verOri, E verDes) {
        Vertex<E> originVertex = searchVertex(verOri);
        Vertex<E> destinationVertex = searchVertex(verDes);

        if (originVertex != null && destinationVertex != null) {
            int originIndex = listVertex.search(originVertex);
            int destinationIndex = listVertex.search(destinationVertex);
            adjacencyMatrix[originIndex][destinationIndex] = 0;
            adjacencyMatrix[destinationIndex][originIndex] = 0; // for undirected graph
        } else {
            System.out.println("One or both vertices not found.");
        }
    }

    public void dfsRecursive(Vertex<E> startVertex) {
        boolean[] visited = new boolean[numVertices];
        Vertex<E> foundVertex = searchVertex(startVertex.getData());

        if (foundVertex != null) {
            dfsRecursive(foundVertex, visited);
        } else {
            System.out.println("Start vertex not found.");
        }
    }

    private void dfsRecursive(Vertex<E> vertex, boolean[] visited) {
        visited[listVertex.search((Vertex<E>) vertex.getData())] = true;
        System.out.println("Visited vertex: " + vertex.getData());

        Node<Edge<E>> currentEdge = vertex.getListAdj().getPrimero();
        while (currentEdge != null) {
            Vertex<E> neighborVertex = currentEdge.getValor().getRefDest();
            int neighborIndex = listVertex.search((Vertex<E>) neighborVertex.getData());
            if (!visited[neighborIndex]) {
                dfsRecursive(neighborVertex, visited);
            }
            currentEdge = currentEdge.getSiguiente();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numVertices; i++) {
            sb.append("Vertex ").append(i).append(": ");
            for (int j = 0; j < numVertices; j++) {
                sb.append(adjacencyMatrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
