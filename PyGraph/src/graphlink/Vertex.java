package graphlink;
import listlinked.*;

public class Vertex<E> {

    private E data;
    protected ListLinked<Edge<E>> listAdj;
    private boolean visited;

    public Vertex(E data) {
        this.data = data;
        this.listAdj = new ListLinked<>();
        this.visited = false;
    }

    public E getData() {
        return data;
    }
    public ListLinked<Edge<E>> getListAdj() {
        return listAdj;
    }
    public boolean isVisited() {
        return visited;
    }
    public void visit() {
        visited = true;
    }
    public void unvisit() {
        visited = false;
    }

    @Override
    public String toString() {
        return data.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vertex<?> vertex = (Vertex<?>) obj;
        return data.equals(vertex.data);
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }


}
