package listlinked;

public class StackLink<T> implements Stack<T> {

    private Node<T> tope;

    public StackLink() {
        this.tope = null;
    }

    @Override
    public void push(T x) {
        Node<T> nuevoNodo = new Node<>(x);
        nuevoNodo.setSiguiente(tope);
        tope = nuevoNodo;
    }

    @Override
    public T pop() throws ExceptionIsEmpty {
        if (isEmpty()) {
            throw new ExceptionIsEmpty();
        }
        T data = tope.getValor();
        tope = tope.getSiguiente();
        return data;
    }

    @Override
    public T top() throws ExceptionIsEmpty {
        if (isEmpty()) {
            throw new ExceptionIsEmpty();
        }
        return tope.getValor();
    }

    @Override
    public boolean isEmpty() {
        return tope == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<T> actual = tope;
        while (actual != null) {
            sb.append(actual.getValor());
            if (actual.getSiguiente() != null) {
                sb.append(", ");
            }
            actual = actual.getSiguiente();
        }
        sb.append("]");
        return sb.toString();
    }
}
