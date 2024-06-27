package graphlink;

public class Edge<E> {

    public Vertex<E> refDest;
    private int weight;

    public Edge(Vertex<E> refDest) {
        this(refDest, -1);
    }

    public Edge(Vertex<E> refDest, int weight) {
        this.refDest = refDest;
        this.weight = weight;
    }

    public Vertex<E> getRefDest() {
        return refDest;
    }

    public void setRefDest(Vertex<E> refDest) {
        this.refDest = refDest;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Edge<?>) {
            Edge<E> e = (Edge<E>) obj;
            return this.refDest.equals(e.refDest);
        }
        return false;
    }

    @Override
    public String toString() {
        if (this.weight > -1) {
            return refDest.getData() + "[" + this.weight + "],";
        } else {
            return refDest.getData() + ", ";
        }
    }

}
