public class Main {
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();

        // Basic operations on the BST
        System.out.println("Inserting elements into BST...");
        bst.insert(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(2);
        bst.insert(5); // Duplicate insert to see no effect
        bst.insert(13);
        bst.insert(22);
        bst.insert(1);

        System.out.println("BST Size: " + bst.size());
        System.out.println("BST Maximum: " + bst.getMax());
        System.out.println("Searching for 15 in BST: " + bst.search(15));
        System.out.println("Searching for 99 in BST: " + bst.search(99));

        bst.remove(10);
        System.out.println("Removed 10 from BST. New Size: " + bst.size());

        // Testing the areSameBST function
        testAreSameBST(new int[]{8, 3, 10, 1, 6, 14, 4, 7, 13}, new int[]{8, 10, 14, 13, 3, 6, 4, 7, 1});
        testAreSameBST(new int[]{3, 2, 5, 1, 4}, new int[]{3, 5, 4, 2, 1});
        testAreSameBST(new int[]{}, new int[]{});
        testAreSameBST(new int[]{1}, new int[]{1});
        testAreSameBST(new int[]{1}, new int[]{2});
    }

    private static void testAreSameBST(int[] array1, int[] array2) {
        BinarySearchTree bst = new BinarySearchTree();
        boolean result = bst.areSameBST(array1, array2);
        System.out.println("Are the following arrays representing the same BST?");
        System.out.println("Array 1: " + arrayToString(array1));
        System.out.println("Array 2: " + arrayToString(array2));
        System.out.println("Result: " + result + "\n");
    }

    private static String arrayToString(int[] array) {
        if (array == null || array.length == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
