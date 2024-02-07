package Stack;

public class ArrayStack<E> implements Stack<E> {
    private static final int MAX_ELEMENTS = 100;
    private E[] elements;
    private int top = -1;

    @SuppressWarnings("unchecked")
    public ArrayStack() {
        elements = (E[]) new Object[MAX_ELEMENTS];
    }

    @Override
    public void push(E e) {
        if (size() == MAX_ELEMENTS) {
            throw new IllegalStateException("Stack is full");
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
}
