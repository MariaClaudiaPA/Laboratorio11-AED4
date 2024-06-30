package graphlink;

import hash.*;

import java.util.ArrayList;

import listlinked.*;

public class GraphLink<E> implements Graph<E> {

    protected ListLinked<Vertex<E>> listVertex;

    public GraphLink() {
        listVertex = new ListLinked<>();
    }

    @Override
    public void insertVertex(E data) {
        Vertex<E> newVertex = new Vertex<>(data);
        if (listVertex.search(newVertex) == -1) {
            listVertex.insert(newVertex);
        }
    }

    @Override
    public void insertEdge(E verOri, E verDes) {
        Vertex<E> vOri = null;
        Vertex<E> vDes = null;

        Node<Vertex<E>> nodeOri = listVertex.getPrimero();
        Node<Vertex<E>> nodeDes = listVertex.getPrimero();

        while (nodeOri != null && vOri == null) {
            if (nodeOri.getValor().getData().equals(verOri)) {
                vOri = nodeOri.getValor();
            }
            nodeOri = nodeOri.getSiguiente();
        }

        while (nodeDes != null && vDes == null) {
            if (nodeDes.getValor().getData().equals(verDes)) {
                vDes = nodeDes.getValor();
            }
            nodeDes = nodeDes.getSiguiente();
        }

        if (vOri != null && vDes != null) {
            Edge<E> edgeOriToDes = new Edge<>(vDes);
            Edge<E> edgeDesToOri = new Edge<>(vOri);

            if (vOri.listAdj.search(edgeOriToDes) == -1) {
                vOri.listAdj.insert(edgeOriToDes);
            }
            if (vDes.listAdj.search(edgeDesToOri) == -1) {
                vDes.listAdj.insert(edgeDesToOri);
            }
        } else {
            throw new IllegalArgumentException("Uno o ambos vértices no existen.");
        }
    }


    @Override
    public String toString() {
        return this.listVertex.toString();
    }

    @Override
    public boolean searchVertex(Vertex<E> v) {
        return listVertex.search(v) != -1;
    }

    @Override
    public boolean searchEdge(Vertex<E> v, Vertex<E> z) {
        Edge<E> edgeVToZ = new Edge<>(z);
        Edge<E> edgeZToV = new Edge<>(v);

        int indexV = listVertex.search(v);
        int indexZ = listVertex.search(z);

        if (indexV != -1 && indexZ != -1) {
            Vertex<E> vertexV = listVertex.getNodeAt(indexV).getValor();
            Vertex<E> vertexZ = listVertex.getNodeAt(indexZ).getValor();

            int indexVToZ = vertexV.listAdj.search(edgeVToZ);
            int indexZToV = vertexZ.listAdj.search(edgeZToV);

            return indexVToZ != -1 && indexZToV != -1;
        }
        return false;
    }


    @Override
    public void removeVertex(Vertex<E> v) {
        if (!searchVertex(v)) {
            throw new IllegalArgumentException("El vértice no existe en el grafo.");
        }

        listVertex.removeNode(v);

        Node<Vertex<E>> currentVertex = listVertex.getPrimero();
        while (currentVertex != null) {
            ListLinked<Edge<E>> adjList = currentVertex.getValor().listAdj;

            Edge<E> edgeToRemove = new Edge<>(v);
            adjList.removeNode(edgeToRemove);
            currentVertex = currentVertex.getSiguiente();
        }
    }

    @Override
    public void removeEdge(Vertex<E> v, Vertex<E> z) {
        if (!searchEdge(v, z)) {
            throw new IllegalArgumentException("La arista entre los vértices no existe en el grafo.");
        }

        Edge<E> edgeVToZ = new Edge<>(z);
        Edge<E> edgeZToV = new Edge<>(v);

        v.listAdj.removeNode(edgeVToZ);
        z.listAdj.removeNode(edgeZToV);
    }

    public void dfs(Vertex<E> v) {
        if (!searchVertex(v)) {
            throw new IllegalArgumentException("El vértice no existe en el grafo.");
        }
        resetVisited();
        dfsRecursive(v);
    }

    private void dfsRecursive(Vertex<E> v) {
        v.visit();
        System.out.println(v.getData());

        ListLinked<Edge<E>> adjList = v.listAdj;
        Node<Edge<E>> current = adjList.getPrimero();

        while (current != null) {
            Vertex<E> neighbor = current.getValor().refDest;
            if (!neighbor.isVisited()) {
                dfsRecursive(neighbor);
            }
            current = current.getSiguiente();
        }
    }

    private void resetVisited() {
        Node<Vertex<E>> current = listVertex.getPrimero();
        while (current != null) {
            current.getValor().unvisit();
            current = current.getSiguiente();
        }
    }

    public int countVertices() {
        return listVertex.length();
    }

    public void bfs(Vertex<E> v) throws ExceptionIsEmpty {
        if (!searchVertex(v)) {
            throw new IllegalArgumentException("El vértice no existe en el grafo.");
        }

        resetVisited();
        QueueLink<Vertex<E>> queue = new QueueLink<>();
        v.visit();
        queue.enqueue(v);

        while (!queue.isEmpty()) {
            Vertex<E> currentVertex = queue.dequeue();
            System.out.println(currentVertex.getData());

            ListLinked<Edge<E>> adjList = currentVertex.listAdj;
            Node<Edge<E>> current = adjList.getPrimero();

            while (current != null) {
                Vertex<E> neighbor = current.getValor().refDest;
                if (!neighbor.isVisited()) {
                    neighbor.visit();
                    queue.enqueue(neighbor);
                }
                current = current.getSiguiente();
            }
        }
    }

    public ArrayList<Vertex<E>> bfsPath(Vertex<E> v, Vertex<E> z) throws ExceptionIsEmpty {
        if (!searchVertex(v) || !searchVertex(z)) {
            throw new IllegalArgumentException("Uno o ambos vértices no existen en el grafo.");
        }

        resetVisited();
        QueueLink<Vertex<E>> queue = new QueueLink<>();
        HashA<Vertex<E>> parentHash = new HashA<>(countVertices());

        v.visit();
        queue.enqueue(v);
        parentHash.insert(v.hashCode(), null);

        while (!queue.isEmpty()) {
            Vertex<E> currentVertex = queue.dequeue();

            if (currentVertex.equals(z)) {
                ArrayList<Vertex<E>> path = new ArrayList<>();
                path.add(z);

                Vertex<E> traceVertex = z;
                while (!traceVertex.equals(v)) {
                    Register<Vertex<E>> parentRegister = parentHash.search(traceVertex.hashCode());
                    if (parentRegister != null) {
                        traceVertex = parentRegister.getValue();
                        path.add(traceVertex);
                    } else {
                        break;
                    }
                }

                ArrayList<Vertex<E>> reversedPath = new ArrayList<>();
                for (int i = path.size() - 1; i >= 0; i--) {
                    reversedPath.add(path.get(i));
                }
                return reversedPath;
            }

            ListLinked<Edge<E>> adjList = currentVertex.listAdj;
            Node<Edge<E>> current = adjList.getPrimero();

            while (current != null) {
                Vertex<E> neighbor = current.getValor().refDest;
                if (!neighbor.isVisited()) {
                    neighbor.visit();
                    queue.enqueue(neighbor);
                    parentHash.insert(neighbor.hashCode(), new Register<>(neighbor.hashCode(), currentVertex));
                }
                current = current.getSiguiente();
            }
        }

        return null;
    }

    public void insertEdgeWeight(E verOri, E verDes, int weight) {
        Vertex<E> vOri = new Vertex<>(verOri);
        Vertex<E> vDes = new Vertex<>(verDes);

        int indexOri = listVertex.search(vOri);
        int indexDes = listVertex.search(vDes);

        if (indexOri != -1 && indexDes != -1) {
            Vertex<E> vertexOri = listVertex.getNodeAt(indexOri).getValor();
            Vertex<E> vertexDes = listVertex.getNodeAt(indexDes).getValor();

            Edge<E> edgeOriToDes = new Edge<>(vertexDes, weight);
            Edge<E> edgeDesToOri = new Edge<>(vertexOri, weight);

            if (vertexOri.listAdj.search(edgeOriToDes) == -1) {
                vertexOri.listAdj.insert(edgeOriToDes);
            }
            if (vertexDes.listAdj.search(edgeDesToOri) == -1) {
                vertexDes.listAdj.insert(edgeDesToOri);
            }
        } else {
            throw new IllegalArgumentException("Uno o ambos vértices no existen.");
        }
    }

    public ArrayList<Vertex<E>> shortPath(Vertex<E> v, Vertex<E> z) throws ExceptionIsEmpty {
        if (!searchVertex(v) || !searchVertex(z)) {
            throw new IllegalArgumentException("Uno o ambos vértices no existen en el grafo.");
        }

        HashA<Integer> distanceHash = new HashA<>(countVertices());
        HashA<Vertex<E>> parentHash = new HashA<>(countVertices());

        PriorityQueue<Vertex<E>, Integer> pq = new PriorityQueueLinkSort<>();

        Node<Vertex<E>> currentVertexNode = listVertex.getPrimero();
        while (currentVertexNode != null) {
            Vertex<E> vertex = currentVertexNode.getValor();
            if (vertex.equals(v)) {
                distanceHash.insert(vertex.hashCode(), new Register<>(vertex.hashCode(), 0));
                pq.enqueue(vertex, 0);
            } else {
                distanceHash.insert(vertex.hashCode(), new Register<>(vertex.hashCode(), Integer.MAX_VALUE));
            }
            parentHash.insert(vertex.hashCode(), null);
            currentVertexNode = currentVertexNode.getSiguiente();
        }

        while (!pq.isEmpty()) {
            Vertex<E> currentVertex = pq.dequeue();
            Register<Integer> currentDistanceReg = distanceHash.search(currentVertex.hashCode());
            if (currentDistanceReg == null) {
                System.err.println("Distancia no encontrada para el vértice: " + currentVertex);
                continue;
            }
            int currentDistance = currentDistanceReg.getValue();

            ListLinked<Edge<E>> adjList = currentVertex.listAdj;
            Node<Edge<E>> currentEdgeNode = adjList.getPrimero();
            while (currentEdgeNode != null) {
                Vertex<E> neighbor = currentEdgeNode.getValor().refDest;
                int edgeWeight = currentEdgeNode.getValor().getWeight();
                int newDist = currentDistance + edgeWeight;

                Register<Integer> neighborDistReg = distanceHash.search(neighbor.hashCode());
                if (neighborDistReg != null) {
                    int neighborDist = neighborDistReg.getValue();
                    if (newDist < neighborDist) {
                        pq.enqueue(neighbor, newDist);
                        neighborDistReg.setValue(newDist);
                        parentHash.insert(neighbor.hashCode(), new Register<>(neighbor.hashCode(), currentVertex));
                    }
                }

                currentEdgeNode = currentEdgeNode.getSiguiente();
            }
        }

        ArrayList<Vertex<E>> path = new ArrayList<>();
        Vertex<E> traceVertex = z;

        while (traceVertex != null) {
            path.add(traceVertex);
            if (traceVertex.equals(v)) {
                break;
            }
            Register<Vertex<E>> parentRegister = parentHash.search(traceVertex.hashCode());
            if (parentRegister == null) {
                System.err.println("No se encontró el padre para el vértice: " + traceVertex);
                break;
            }
            traceVertex = parentRegister.getValue();
        }

        int left = 0;
        int right = path.size() - 1;
        while (left < right) {
            Vertex<E> temp = path.get(left);
            path.set(left, path.get(right));
            path.set(right, temp);
            left++;
            right--;
        }

        return path;
    }

    public ArrayList<Edge<E>> primMST() throws ExceptionIsEmpty {
        if (listVertex.isEmpty()) {
            throw new IllegalArgumentException("El grafo está vacío.");
        }

        resetVisited();
        PriorityQueue<Edge<E>, Integer> pq = new PriorityQueueLinkSort<>();
        ArrayList<Edge<E>> mstEdges = new ArrayList<>();
        Vertex<E> startVertex = listVertex.getPrimero().getValor();

        startVertex.visit();
        Node<Edge<E>> currentEdgeNode = startVertex.listAdj.getPrimero();
        while (currentEdgeNode != null) {
            pq.enqueue(currentEdgeNode.getValor(), currentEdgeNode.getValor().getWeight());
            currentEdgeNode = currentEdgeNode.getSiguiente();
        }

        while (!pq.isEmpty()) {
            Edge<E> minEdge = pq.dequeue();
            Vertex<E> destVertex = minEdge.refDest;

            if (!destVertex.isVisited()) {
                mstEdges.add(minEdge);
                destVertex.visit();

                currentEdgeNode = destVertex.listAdj.getPrimero();
                while (currentEdgeNode != null) {
                    Edge<E> edge = currentEdgeNode.getValor();
                    if (!edge.refDest.isVisited()) {
                        pq.enqueue(edge, edge.getWeight());
                    }
                    currentEdgeNode = currentEdgeNode.getSiguiente();
                }
            }
        }
        return mstEdges;
    }
}