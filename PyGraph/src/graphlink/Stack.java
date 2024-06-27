package graphlink;

public interface Stack<E> {

    void push(E x) throws ExceptionIsFull;

    E pop() throws ExceptionIsEmpty;

    E top() throws ExceptionIsEmpty;

    boolean isEmpty();

}
