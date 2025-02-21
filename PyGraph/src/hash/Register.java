package hash;

public class Register<E> implements Comparable<Register<E>> {

    protected int key;
    protected E value;

    public Register(int key, E value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int compareTo(Register<E> r) {
        return this.key - r.key;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Register) {
            Register<E> r = (Register<E>) o;
            return r.key == this.key;
        }
        return false;
    }

    public int getKey() {
        return this.key;
    }

    public E getValue() {
        return value;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setValue(E value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "[" + this.key + ":" + this.value.toString() + "]";
    }
}
