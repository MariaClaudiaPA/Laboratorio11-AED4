package graphlink;

public class GraphLink<E> {

    protected ListLinked<Vertex<E>> listVertex;

    public GraphLink() {
        listVertex = new ListLinked<>();
    }

    public void insertVertex(E data) {
        Vertex<E> newVertex = new Vertex<>(data);
        listVertex.insert(newVertex);
    }

    public void insertEdge(E verOri, E verDes) {
        Vertex<E> originVertex = searchVertex(verOri);
        Vertex<E> destinationVertex = searchVertex(verDes);

        if (originVertex == null || destinationVertex == null) {
            System.out.println("Uno o ambos vertices no fueron encontrados");
            return;
        }

        Edge<E> newEdge = new Edge<>(destinationVertex);
        originVertex.listAdj.insert(newEdge);
    }

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

    public void bfs(E data) {
        Vertex<E> startVertex = searchVertex(data);
        if (startVertex == null) {
            System.out.println("Vertice no encontrado");
            return;
        }
        System.out.println("Iniciando BFS desde: " + startVertex.getData());

        Queue<Vertex<E>> queue = new QueueLink<>();
        queue.enqueue(startVertex);

        while (!queue.isEmpty()) {
            try {
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
            } catch (ExceptionIsEmpty ex) {
                System.out.println(ex);
            }
        }
    }

    public void insertEdgeWeight(E verOri, E verDes, double weight) {
        Vertex<E> originVertex = searchVertex(verOri);
        Vertex<E> destinationVertex = searchVertex(verDes);

        if (originVertex == null || destinationVertex == null) {
            System.out.println("Uno o ambos vértices no fueron encontrados");
            return;
        }

        Edge<E> nuevoEdge = new Edge<>(destinationVertex, (int) weight);
        originVertex.listAdj.insert(nuevoEdge);

        nuevoEdge = new Edge<>(originVertex, (int) weight);
        destinationVertex.listAdj.insert(nuevoEdge);
    }

    @Override
    public String toString() {
        return this.listVertex.toString();
    }
}
