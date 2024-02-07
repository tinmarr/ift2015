package SpecialStack;

public interface SpecialStack<E extends Comparable<E>> {
    void push(E e);

    E pop();

    E top();

    int size();

    boolean isEmpty();

    String toString();

    E getMax();
}
