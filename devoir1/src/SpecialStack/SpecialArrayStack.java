package SpecialStack;

public class SpecialArrayStack<E extends Comparable<E>> implements SpecialStack<E> {
    private static final int MAX_ELEMENTS = 100;
    private E[] elements;
    private E maxElement;
    private int top;

    @SuppressWarnings("unchecked")
    public SpecialArrayStack() {
        elements = (E[]) new Comparable[MAX_ELEMENTS];
        top = -1;
    }

    @Override
    public void push(E e) {
        if (size() == MAX_ELEMENTS) {
            throw new IllegalStateException("Stack is full");
        }
        if (isEmpty() || e.compareTo(maxElement) > 0) {
            maxElement = e;
        }
        elements[++top] = e;
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        E element = elements[top];
        elements[top--] = null;
        if (element.equals(maxElement)) {
            maxElement = elements[0];
            for (int i = 1; i <= top; i++) {
                if (elements[i].compareTo(maxElement) > 0) {
                    maxElement = elements[i];
                }
            }
        }
        return element;
    }

    @Override
    public E top() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return elements[top];
    }

    @Override
    public int size() {
        return top + 1;
    }

    @Override
    public boolean isEmpty() {
        return top == -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = top; i >= 0; i--) {
            sb.append(elements[i]);
            if (i > 0) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public E getMax() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return maxElement;
    }
}
