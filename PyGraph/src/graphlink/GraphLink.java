package graphlink;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GraphLink<E> implements Graph<E> {

    protected ListLinked<Vertex<E>> listVertex;
    protected HashMap<Vertex<E>, HashMap<Vertex<E>, Integer>> distances;
    protected HashMap<Vertex<E>, HashMap<Vertex<E>, Vertex<E>>> predecessors;

    public GraphLink() {
        listVertex = new ListLinked<>();
        distances = new HashMap<>();
        predecessors = new HashMap<>();
    }

    @Override
    public void insertVertex(E data) {
        Vertex<E> newVertex = new Vertex<>(data);
        listVertex.insert(newVertex);
    }

    @Override
    public void insertEdge(E verOri, E verDes) {
        Vertex<E> originVertex = searchVertex(verOri);
        Vertex<E> destinationVertex = searchVertex(verDes);

        if (originVertex == null || destinationVertex == null) {
            System.out.println("Uno o ambos vértices no fueron encontrados");
            return;
        }

        Edge<E> newEdge = new Edge<>(destinationVertex);
        originVertex.listAdj.insert(newEdge);
    }

    @Override
    public Vertex<E> searchVertex(E data) {
        int index = listVertex.search(new Vertex<>(data));
        if (index == -1) {
            return null;
        }
        Node<Vertex<E>> currentNode = listVertex.primero;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getSiguiente();
        }
        return currentNode.getValor();
    }

    @Override
    public boolean searchEdge(E verOri, E verDes) {
        Vertex<E> originVertex = searchVertex(verOri);
        Vertex<E> destinationVertex = searchVertex(verDes);

        if (originVertex == null || destinationVertex == null) {
            return false;
        }
        Edge<E> searchEdge = new Edge<>(destinationVertex);
        int index = originVertex.listAdj.search(searchEdge);
        return index != -1;
    }

    @Override
    public void removeVertex(E data) {
        Node<Vertex<E>> currentVertexNode = listVertex.primero;
        while (currentVertexNode != null) {
            Vertex<E> currentVertex = currentVertexNode.getValor();
            if (currentVertex.getData().equals(data)) {
                listVertex.removeNode(currentVertex);
                break;
            }
            currentVertexNode = currentVertexNode.getSiguiente();
        }

        currentVertexNode = listVertex.primero;
        while (currentVertexNode != null) {
            Vertex<E> currentVertex = currentVertexNode.getValor();
            Node<Edge<E>> currentEdge = currentVertex.listAdj.primero;
            while (currentEdge != null) {
                if (currentEdge.getValor().refDest.getData().equals(data)) {
                    currentVertex.listAdj.removeNode(currentEdge.getValor());
                }
                currentEdge = currentEdge.getSiguiente();
            }
            currentVertexNode = currentVertexNode.getSiguiente();
        }
    }

    @Override
    public void removeEdge(E verOri, E verDes) {
        Vertex<E> originVertex = searchVertex(verOri);
        Vertex<E> destinationVertex = searchVertex(verDes);

        if (originVertex == null || destinationVertex == null) {
            return;
        }

        Node<Edge<E>> currentEdge = originVertex.listAdj.primero;
        while (currentEdge != null) {
            if (currentEdge.getValor().refDest.equals(destinationVertex)) {
                originVertex.listAdj.removeNode(currentEdge.getValor());
                return;
            }
            currentEdge = currentEdge.getSiguiente();
        }
    }

    public void dfs(E data) throws ExceptionIsFull, ExceptionIsEmpty {
        Vertex<E> startVertex = searchVertex(data);
        if (startVertex == null) {
            System.out.println("Vertice no encontrado");
            return;
        }

        Stack<Vertex<E>> stack = new StackLink<>();
        stack.push(startVertex);

        while (!stack.isEmpty()) {
            Vertex<E> vertex = stack.pop();
            if (!vertex.isVisited()) {
                vertex.setVisited(true);
                System.out.println(vertex.getData());

                Node<Edge<E>> currentEdge = vertex.listAdj.primero;
                while (currentEdge != null) {
                    Vertex<E> neighborVertex = currentEdge.getValor().refDest;
                    if (!neighborVertex.isVisited()) {
                        stack.push(neighborVertex);
                    }
                    currentEdge = currentEdge.getSiguiente();
                }
            }
        }
    }

    public void bfs(E data) throws ExceptionIsEmpty {
        Vertex<E> startVertex = searchVertex(data);
        if (startVertex == null) {
            System.out.println("Vertice no encontrado");
            return;
        }
        System.out.println("Iniciando BFS desde: " + startVertex.getData());

        Queue<Vertex<E>> queue = new QueueLink<>();
        queue.enqueue(startVertex);

        while (!queue.isEmpty()) {
            Vertex<E> vertex = queue.dequeue();
            if (!vertex.isVisited()) {
                vertex.setVisited(true);
                System.out.println("Visitando vértice: " + vertex.getData());

                Node<Edge<E>> currentEdge = vertex.listAdj.primero;
                while (currentEdge != null) {
                    Vertex<E> neighborVertex = currentEdge.getValor().refDest;
                    if (!neighborVertex.isVisited()) {
                        queue.enqueue(neighborVertex);
                        System.out.println("Encolando vecino: " + neighborVertex.getData());
                    }
                    currentEdge = currentEdge.getSiguiente();
                }
            }
        }
    }

    public ArrayList<E> bfsPath(E verOri, E verDes) throws ExceptionIsEmpty {
        Vertex<E> startVertex = searchVertex(verOri);
        Vertex<E> endVertex = searchVertex(verDes);

        if (startVertex == null || endVertex == null) {
            System.out.println("Uno o ambos vértices no fueron encontrados");
            return null;
        }

        Queue<Vertex<E>> queue = new QueueLink<>();
        HashMap<Integer, Vertex<E>> hashTable = new HashMap<>();
        ArrayList<E> path = new ArrayList<>();

        queue.enqueue(startVertex);
        hashTable.put(startVertex.hashCode(), startVertex);

        while (!queue.isEmpty()) {
            Vertex<E> vertex = queue.dequeue();
            if (vertex.equals(endVertex)) {
                break;
            }

            Node<Edge<E>> currentEdge = vertex.listAdj.primero;
            while (currentEdge != null) {
                Vertex<E> neighborVertex = currentEdge.getValor().refDest;
                if (!hashTable.containsKey(neighborVertex.hashCode())) {
                    queue.enqueue(neighborVertex);
                    hashTable.put(neighborVertex.hashCode(), neighborVertex);
                }
                currentEdge = currentEdge.getSiguiente();
            }
        }

        Vertex<E> step = endVertex;
        while (step != null && !step.equals(startVertex)) {
            path.add(0, step.getData());
            step = encontrarPredecesor(step, hashTable);
        }
        path.add(0, startVertex.getData());

        if (path.isEmpty()) {
            return null;
        } else {
            return path;
        }

    }

    private Vertex<E> encontrarPredecesor(Vertex<E> vertex, HashMap<Integer, Vertex<E>> hashTable) {
        for (Vertex<E> v : hashTable.values()) {
            Node<Edge<E>> currentEdge = v.listAdj.primero;
            while (currentEdge != null) {
                if (currentEdge.getValor().refDest.equals(vertex)) {
                    return v;
                }
                currentEdge = currentEdge.getSiguiente();
            }
        }
        return null;
    }

    public void insertEdgeWeight(E verOri, E verDes, int weight) {
        Vertex<E> origin = searchVertex(verOri);
        Vertex<E> destination = searchVertex(verDes);

        if (origin != null && destination != null) {
            origin.listAdj.insertFirst(new Edge<>(destination, weight));
            destination.listAdj.insertFirst(new Edge<>(origin, weight));
        }
    }

    // EJ 2 Y 4A
    public void initializeDistances() {
        Node<Vertex<E>> node = listVertex.primero;
        while (node != null) {
            Vertex<E> v = node.getValor();
            distances.put(v, new HashMap<>());
            predecessors.put(v, new HashMap<>());

            Node<Vertex<E>> node2 = listVertex.primero;
            while (node2 != null) {
                Vertex<E> u = node2.getValor();
                if (v.equals(u)) {
                    distances.get(v).put(u, 0);
                } else {
                    distances.get(v).put(u, Integer.MAX_VALUE);
                }
                predecessors.get(v).put(u, null);
                node2 = node2.getSiguiente();
            }

            Node<Edge<E>> edgeNode = v.listAdj.primero;
            while (edgeNode != null) {
                Edge<E> edge = edgeNode.getValor();
                Vertex<E> neighbor = edge.refDest;
                distances.get(v).put(neighbor, edge.getWeight());
                distances.computeIfAbsent(neighbor, k -> new HashMap<>()).put(v, edge.getWeight());
                predecessors.get(v).put(neighbor, v);
                edgeNode = edgeNode.getSiguiente();
            }

            node = node.getSiguiente();
        }
    }

    public void floydWarshall() {
        initializeDistances();

        Node<Vertex<E>> nodeK = listVertex.primero;
        while (nodeK != null) {
            Vertex<E> k = nodeK.getValor();

            Node<Vertex<E>> nodeI = listVertex.primero;
            while (nodeI != null) {
                Vertex<E> i = nodeI.getValor();

                Node<Vertex<E>> nodeJ = listVertex.primero;
                while (nodeJ != null) {
                    Vertex<E> j = nodeJ.getValor();

                    if (distances.get(i).get(k) < Integer.MAX_VALUE
                            && distances.get(k).get(j) < Integer.MAX_VALUE
                            && distances.get(i).get(k) + distances.get(k).get(j) < distances.get(i).get(j)) {

                        distances.get(i).put(j, distances.get(i).get(k) + distances.get(k).get(j));
                        predecessors.get(i).put(j, predecessors.get(k).get(j));
                    }

                    nodeJ = nodeJ.getSiguiente();
                }

                nodeI = nodeI.getSiguiente();
            }

            nodeK = nodeK.getSiguiente();
        }
    }

    public ArrayList<E> shortPath(E verOri, E verDes) {
        Vertex<E> startVertex = searchVertex(verOri);
        Vertex<E> endVertex = searchVertex(verDes);

        if (startVertex == null || endVertex == null) {
            System.out.println("Uno o ambos vértices no fueron encontrados");
            return null;
        }

        if (distances.isEmpty()) {
            floydWarshall();
        }

        ArrayList<E> path = new ArrayList<>();
        Vertex<E> step = endVertex;
        while (step != null && !step.equals(startVertex)) {
            path.add(0, step.getData());
            step = predecessors.get(startVertex).get(step);
        }
        path.add(0, startVertex.getData());

        if (path.isEmpty()) {
            return null;
        } else {
            return path;
        }
    }

    //EJ4 B
    public void shortPathDijkstra(E startData) throws ExceptionIsEmpty {
        Vertex<E> startVertex = searchVertex(startData);
        if (startVertex == null) {
            System.out.println("Vértice de inicio no encontrado");
            return;
        }
        PriorityQueue<Pair<Vertex<E>, Integer>, Integer> minHeap = new PriorityQueueLinkSort<>(
                (pair1, pair2) -> Integer.compare(pair1.y, pair2.y)
        );

        HashMap<Vertex<E>, Integer> distanceMap = new HashMap<>();
        HashMap<Vertex<E>, Vertex<E>> predecessorMap = new HashMap<>();

        for (Node<Vertex<E>> node = listVertex.primero; node != null; node = node.getSiguiente()) {
            Vertex<E> vertex = node.getValor();
            distanceMap.put(vertex, Integer.MAX_VALUE);
            predecessorMap.put(vertex, null);
        }
        distanceMap.put(startVertex, 0);

        minHeap.enqueue(new Pair<>(startVertex, 0), 0);

        while (!minHeap.isEmpty()) {
            Pair<Vertex<E>, Integer> pair = minHeap.dequeue();
            Vertex<E> currentVertex = pair.x;
            int currentDistance = pair.y;

            Node<Edge<E>> edgeNode = currentVertex.listAdj.primero;
            while (edgeNode != null) {
                Edge<E> edge = edgeNode.getValor();
                Vertex<E> neighborVertex = edge.refDest;
                int weight = edge.getWeight();
                int newDistance = currentDistance + weight;

                if (newDistance < distanceMap.get(neighborVertex)) {
                    distanceMap.put(neighborVertex, newDistance);
                    predecessorMap.put(neighborVertex, currentVertex);
                    minHeap.enqueue(new Pair<>(neighborVertex, newDistance), newDistance);
                }

                edgeNode = edgeNode.getSiguiente();
            }
        }

        distances.put(startVertex, distanceMap);
        predecessors.put(startVertex, predecessorMap);
    }

    public ArrayList<E> getPathTo(Vertex<E> startVertex, Vertex<E> endVertex) {
        ArrayList<E> path = new ArrayList<>();
        if (startVertex == null || endVertex == null) {
            System.out.println("Uno o ambos vértices no fueron encontrados");
            return path;
        }

        Vertex<E> step = endVertex;
        while (step != null && !step.equals(startVertex)) {
            path.add(0, step.getData());
            step = predecessors.get(startVertex).get(step);
        }
        path.add(0, startVertex.getData());

        return path.isEmpty() ? null : path;
    }

    public void insertEdgeWeighted(E verOri, E verDes, int weight) {
        Vertex<E> originVertex = searchVertex(verOri);
        Vertex<E> destinationVertex = searchVertex(verDes);

        if (originVertex == null || destinationVertex == null) {
            System.out.println("Uno o ambos vértices no fueron encontrados");
            return;
        }

        Edge<E> newEdge = new Edge<>(destinationVertex, weight);
        originVertex.listAdj.insert(newEdge);
    }

    //EJ 5A
    public GraphLink<E> primMST() throws ExceptionIsEmpty {
        GraphLink<E> mst = new GraphLink<>();

        if (listVertex.isEmpty()) {
            System.out.println("El grafo está vacío. No se puede aplicar Prim.");
            return mst;
        }

        Set<Vertex<E>> visitedVertices = new HashSet<>();

        PriorityQueue<Pair<Vertex<E>, Edge<E>>, Integer> minHeap = new PriorityQueueLinkSort<>(
                Comparator.comparingInt(p -> p.y)
        );

        Vertex<E> startVertex = listVertex.primero.getValor();

        visitedVertices.add(startVertex);
        mst.insertVertex(startVertex.getData());

        Node<Edge<E>> edgeNode = startVertex.listAdj.primero;
        while (edgeNode != null) {
            minHeap.enqueue(new Pair<>(startVertex, edgeNode.getValor()), edgeNode.getValor().getWeight());
            edgeNode = edgeNode.getSiguiente();
        }

        while (!minHeap.isEmpty() && visitedVertices.size() < listVertex.length()) {
            Pair<Vertex<E>, Edge<E>> pair = minHeap.dequeue();
            Vertex<E> currentVertex = pair.x;
            Edge<E> currentEdge = pair.y;

            Vertex<E> nextVertex = currentEdge.refDest;

            if (!visitedVertices.contains(nextVertex)) {
                visitedVertices.add(nextVertex);
                mst.insertVertex(nextVertex.getData());
                mst.insertEdgeWeighted(currentVertex.getData(), nextVertex.getData(), currentEdge.getWeight());

                edgeNode = nextVertex.listAdj.primero;
                while (edgeNode != null) {
                    minHeap.enqueue(new Pair<>(nextVertex, edgeNode.getValor()), edgeNode.getValor().getWeight());
                    edgeNode = edgeNode.getSiguiente();
                }
            }
        }

        return mst;
    }

//    public GraphLink<E> kruskalMST() throws ExceptionIsEmpty {
//        GraphLink<E> mst = new GraphLink<>();
//
//        if (listVertex.isEmpty()) {
//            System.out.println("El grafo está vacío. No se puede aplicar Kruskal.");
//            return mst;
//        }
//
//        List<Edge<E>> allEdges = new ArrayList<>();
//
//        Node<Vertex<E>> vertexNode = listVertex.primero;
//        while (vertexNode != null) {
//            Vertex<E> vertex = vertexNode.getValor();
//            Node<Edge<E>> edgeNode = vertex.listAdj.primero;
//            while (edgeNode != null) {
//                Edge<E> edge = edgeNode.getValor();
//                allEdges.add(edge);
//                edgeNode = edgeNode.getSiguiente();
//            }
//            vertexNode = vertexNode.getSiguiente();
//        }
//
//        Collections.sort(allEdges, Comparator.comparingInt(Edge::getWeight));
//
//        Set<Vertex<E>> addedVertices = new HashSet<>();
//
//        for (Edge<E> edge : allEdges) {
//            Vertex<E> u = edge.getRefDest(); // Primer vértice de la arista
//            Vertex<E> v = edge.getRefOrigin(); // Segundo vértice de la arista
//
//            if (!addedVertices.contains(u) || !addedVertices.contains(v)) {
//                if (!addedVertices.contains(u)) {
//                    mst.insertVertex(u.getData());
//                    addedVertices.add(u);
//                }
//                if (!addedVertices.contains(v)) {
//                    mst.insertVertex(v.getData());
//                    addedVertices.add(v);
//                }
//                mst.insertEdgeWeighted(u.getData(), v.getData(), edge.getWeight());
//            }
//        }
//
//        return mst;
//    }

    @Override
    public String toString() {
        return this.listVertex.toString();
    }
}
