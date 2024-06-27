package graphlink;

public class QueueLink<E> implements Queue<E> {

    private Node<E> first;
    private Node<E> last;

    public QueueLink() {
        this.first = null;
        this.last = null;
    }

    @Override
    public void enqueue(E x) {
        Node<E> aux = new Node<>(x);
        if (isEmpty()) {
            this.first = aux;
        } else {
            this.last.setSiguiente(aux);
        }
        this.last = aux;
    }

    @Override
    public E dequeue() throws ExceptionIsEmpty {
        if (isEmpty()) {
            throw new ExceptionIsEmpty();
        } else {
            E ele = first.getValor();
            first = first.getSiguiente();
            return ele;
        }

    }

    @Override
    public E back() throws ExceptionIsEmpty {
        if (isEmpty()) {
            throw new ExceptionIsEmpty();
        } else {
            return last.getValor();
        }
    }

    @Override
    public E front() throws ExceptionIsEmpty {
        if (isEmpty()) {
            throw new ExceptionIsEmpty();
        } else {
            return first.getValor();
        }
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cola:  ");
        Node<E> current = this.first;
        while (current != null) {
            sb.append(current.getValor()).append(" ");
            current = current.getSiguiente();
        }
        return sb.toString();
    }
}
