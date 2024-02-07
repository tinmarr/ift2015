package DuplicateEater;

import Stack.ArrayStack;

public class DuplicateEater {
    public static void pairDestroyer(String[] words) {
        ArrayStack<String> stack = new ArrayStack<>();
        for (String word : words) {
            if (!stack.isEmpty() && stack.top().equals(word)) {
                stack.pop();
            } else {
                stack.push(word);
            }
        }
        System.out.println(stack.size());
    }
}
