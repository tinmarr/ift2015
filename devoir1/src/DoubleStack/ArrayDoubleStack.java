package DoubleStack;

public class ArrayDoubleStack<E> implements DoubleStack<E> {
    private static final int MAX_ELEMENTS = 100;
    private E[] elements;
    private int top1;
    private int top2;

    @SuppressWarnings("unchecked")
    public ArrayDoubleStack() {
        elements = (E[]) new Object[MAX_ELEMENTS];
        top1 = -1;
        top2 = MAX_ELEMENTS;
    }

    @Override
    public boolean push(boolean one, E e) {
        if (isFull()) {
            return false;
        }
        if (one) {
            elements[++top1] = e;
        } else {
            elements[--top2] = e;
        }
        return true;
    }

    @Override
    public E pop(boolean one) {
        if (one && top1 >= 0) {
            E element = elements[top1];
            elements[top1--] = null;
            return element;
        } else if (!one && top2 < MAX_ELEMENTS) {
            E element = elements[top2];
            elements[top2++] = null;
            return element;
        }
        return null;
    }

    @Override
    public E top(boolean one) {
        if (one && top1 >= 0) {
            return elements[top1];
        } else if (!one && top2 < MAX_ELEMENTS) {
            return elements[top2];
        }
        return null;
    }

    @Override
    public int size(boolean one) {
        if (one) {
            return top1 + 1;
        } else {
            return MAX_ELEMENTS - top2;
        }
    }

    @Override
    public boolean isFull() {
        return top1 + 1 == top2;
    }

    @Override
    public void print() {
        System.out.print("Pile 1: [");
        for (int i = 0; i <= top1; i++) {
            System.out.print(elements[i]);
            if (i < top1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");

        System.out.print("Pile 2: [");
        for (int i = MAX_ELEMENTS - 1; i >= top2; i--) {
            System.out.print(elements[i]);
            if (i > top2) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}
