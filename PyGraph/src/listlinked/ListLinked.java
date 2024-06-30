package listlinked;

public class ListLinked<T> implements TDAList<T> {

    protected Node<T> primero;

    public ListLinked() {
        this.primero = null;
    }

    @Override
    public boolean isEmpty() {
        return primero == null;
    }

    @Override
    public int length() {
        int cont = 0;
        Node<T> actual = primero;
        while (actual != null) {
            cont++;
            actual = actual.getSiguiente();
        }
        return cont;
    }

    @Override
    public void destroyList() {
        primero = null;
    }

    @Override
    public int search(T searchItem) {
        int indice = 0;
        Node<T> actual = primero;
        while (actual != null) {
            T valorActual = actual.getValor();
            if ((valorActual == null && searchItem == null) || (valorActual != null && valorActual.equals(searchItem))) {
                return indice;
            }
            indice++;
            actual = actual.getSiguiente();
        }
        return -1;
    }


    public Node<T> getNodeAt(int index) {
        if (index < 0 || index >= length()) {
            throw new IndexOutOfBoundsException("Índice fuera de los límites de la lista enlazada.");
        }

        Node<T> current = primero;
        int currentIndex = 0;

        while (currentIndex < index) {
            current = current.getSiguiente();
            currentIndex++;
        }

        return current;
    }

    @Override
    public void insertFirst(T nuevoItem) {
        Node<T> nuevoNodo = new Node<>(nuevoItem);
        nuevoNodo.setSiguiente(primero);
        primero = nuevoNodo;
    }

    @Override
    public void insert(T nuevoItem) {
        Node<T> nuevoNodo = new Node<>(nuevoItem);
        if (primero == null) {
            primero = nuevoNodo;
        } else {
            Node<T> actual = primero;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoNodo);
        }
    }

    @Override
    public T removeNode(T borrarItem) {
        if (primero != null) {
            if (primero.getValor().equals(borrarItem)) {
                T valorEliminado = primero.getValor();
                primero = primero.getSiguiente();
                return valorEliminado;
            } else {
                Node<T> actual = primero;
                while (actual.getSiguiente() != null) {
                    if (actual.getSiguiente().getValor().equals(borrarItem)) {
                        T valorEliminado = actual.getSiguiente().getValor();
                        actual.setSiguiente(actual.getSiguiente().getSiguiente());
                        return valorEliminado;
                    }
                    actual = actual.getSiguiente();
                }
            }
        }
        return null;
    }

    public Node<T> getPrimero() {
        return primero;
    }

    public void setPrimero(Node<T> primero) {
        this.primero = primero;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> actual = primero;
        while (actual != null) {
            sb.append(actual.getValor()).append(" ");
            actual = actual.getSiguiente();
        }
        return sb.toString();
    }

    public T get(int searchIndex) {
        if (searchIndex < 0 || searchIndex >= length()) {
            throw new IndexOutOfBoundsException("Index: " + searchIndex + ", Size: " + length());
        }

        Node<T> current = primero;
        for (int i = 0; i < searchIndex; i++) {
            current = current.getSiguiente();
        }
        return current.getValor();
    }
}
