package DoubleStack;

public interface DoubleStack<E> {
    boolean push(boolean one, E e);

    E pop(boolean one);

    E top(boolean one);

    int size(boolean one);

    boolean isFull();

    void print();
}
