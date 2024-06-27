package graphlink;

public class Vertex<E> {

    private E data;
    protected ListLinked<Edge<E>> listAdj;
    private boolean visited;

    public Vertex(E data) {
        this.data = data;
        listAdj = new ListLinked<>();
        this.visited = false;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vertex<?>) {
            Vertex<E> v = (Vertex<E>) obj;
            return this.data.equals(v.data);
        }
        return false;
    }

    @Override
    public String toString() {
        return this.data + " ---> " + this.listAdj.toString() + "\n";
    }

}
