package graphlink;

public interface Graph<E> {

    void insertVertex(E data);

    void insertEdge(E verOri, E verDes);

    boolean searchVertex(Vertex<E> v);

    public boolean searchEdge(Vertex<E> v, Vertex<E> z);

    void removeVertex(Vertex<E> v);

    void removeEdge(Vertex<E> v, Vertex<E> z);

}
