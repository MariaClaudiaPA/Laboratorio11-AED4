package graphlink;

public interface Graph<E> {

    void insertVertex(E data);

    void insertEdge(E verOri, E verDes);

    Vertex<E> searchVertex(E data);

    boolean searchEdge(E verOri, E verDes);

    void removeVertex(E data);

    void removeEdge(E verOri, E verDes);

}
