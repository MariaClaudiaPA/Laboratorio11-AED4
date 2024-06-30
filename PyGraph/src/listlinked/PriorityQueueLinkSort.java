package listlinked;
public class PriorityQueueLinkSort<E, P extends Comparable<P>> implements PriorityQueue<E, P> {

    class NodoCola {

        E data;
        P priority;

        NodoCola(E data, P priority) {
            this.data = data;
            this.priority = priority;
        }
    }

    private Node<NodoCola> first;
    private Node<NodoCola> last;

    public PriorityQueueLinkSort() {
        this.first = null;
        this.last = null;
    }

    @Override
    public void enqueue(E x, P pr) {
        NodoCola newNode = new NodoCola(x, pr);
        Node<NodoCola> newNodeNode = new Node<>(newNode);

        if (isEmpty()) {
            this.first = newNodeNode;
            this.last = newNodeNode;
        } else {
            Node<NodoCola> act = this.first;
            Node<NodoCola> ant = null;
            while (act != null && compareTo(pr, act.getValor().priority) >= 0) {
                ant = act;
                act = act.getSiguiente();
            }

            if (ant == null) {
                newNodeNode.setSiguiente(this.first);
                this.first = newNodeNode;
            } else {
                ant.setSiguiente(newNodeNode);
                newNodeNode.setSiguiente(act);
            }
        }
    }

    @Override
    public E dequeue() throws ExceptionIsEmpty {
        if (isEmpty()) {
            throw new ExceptionIsEmpty();
        }
        E aux = this.first.getValor().data;
        this.first = this.first.getSiguiente();
        if (this.first == null) {
            this.last = null;
        }
        return aux;
    }

    @Override
    public E back() throws ExceptionIsEmpty {
        if (isEmpty() || this.last == null) {
            throw new ExceptionIsEmpty();
        }

        return this.last.getValor().data;
    }

    @Override
    public E front() throws ExceptionIsEmpty {
        if (isEmpty()) {
            throw new ExceptionIsEmpty();
        }

        return this.first.getValor().data;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Prioridad Queue: ");
        Node<NodoCola> current = this.first;

        while (current != null) {
            sb.append("(").append(current.getValor().data).append(", ").append(current.getValor().priority).append(") ");
            current = current.getSiguiente();
        }

        return sb.toString();
    }

    private int compareTo(P p1, P p2) {
        return p1.compareTo(p2);
    }

    public P getPriority(E element) {
        Node<NodoCola> current = first;
        while (current != null) {
            if (current.getValor().data.equals(element)) {
                return current.getValor().priority;
            }
            current = current.getSiguiente();
        }
        throw new IllegalArgumentException("Elemento no encontrado en la cola de prioridad: " + element);
    }


    public void updatePriority(E element, P newPriority) {
        Node<NodoCola> current = this.first;
        while (current != null) {
            if (current.getValor().data.equals(element)) {
                current.getValor().priority = newPriority;
                return;
            }
            current = current.getSiguiente();
        }
        throw new IllegalArgumentException("Element not found in the priority queue");
    }

}
