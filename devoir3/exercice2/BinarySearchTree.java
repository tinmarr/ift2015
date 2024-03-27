public class BinarySearchTree {
    class Node {
        int key;
        Node left, right;
	
        /**
         * Node constructor initializes a BST node with a given key.
         * @param item The integer key of the new node.
         */
        public Node(int item) {
            key = item;
            left = right = null;
        }
    }

    Node root;

    public BinarySearchTree() {
        root = null;
    }

    /**
     * Inserts a new key into the BST.
     * Complexity: O(n)
     * @param key The key to insert.
     */
    void insert(int key) {
        root = insertRec(root, key);
    }

    /**
     * Recursive helper function to insert a new key into the BST.
     * @param root The root of the tree (or subtree) into which the key should be inserted.
     * @param key The key to insert.
     * @return The new root of the tree (or subtree).
     */
    private Node insertRec(Node root, int key) {
        if (root == null) {
            root = new Node(key);
            return root;
        }
        if (key < root.key)
            root.left = insertRec(root.left, key);
        else if (key > root.key)
            root.right = insertRec(root.right, key);

        return root;
    }

    /**
     * Checks if the BST is empty.
     * Complexity: O(1)
     * @return true if the BST is empty, false otherwise.
     */
    boolean isEmpty() {
        return root == null;
    }

    /**
     * Calculates the size of the BST.
     * Complexity: O(n)
     * @return The number of nodes in the BST.
     */
    int size() {
        return sizeRec(root);
    }

    /**
     * Recursive helper function to calculate the size of the BST.
     * @param root The root of the tree (or subtree) for which to calculate the size.
     * @return The size of the tree (or subtree).
     */
    private int sizeRec(Node root) {
        if (root == null) return 0;
        return sizeRec(root.left) + 1 + sizeRec(root.right);
    }

    /**
     * Finds the maximum key in the BST.
     * Complexity: O(n)
     * @return The maximum key.
     */
    int getMax() {
        return getMaxRec(root);
    }

    /**
     * Recursive helper function to find the maximum key in the BST.
     * @param root The root of the tree (or subtree) from which to find the maximum key.
     * @return The maximum key in the tree (or subtree).
     */
    private int getMaxRec(Node root) {
        if (root.right == null) return root.key;
        return getMaxRec(root.right);
    }

    /**
     * Searches for a key in the BST.
     * Complexity: O(n)
     * @param key The key to search for.
     * @return true if the key is found, false otherwise.
     */
    boolean search(int key) {
        return searchRec(root, key);
    }

    /**
     * Recursive helper function to search for a key in the BST.
     * @param root The root of the tree (or subtree) in which to search for the key.
     * @param key The key to search for.
     * @return true if the key is found in the tree (or subtree), false otherwise.
     */
    private boolean searchRec(Node root, int key) {
        if (root == null) return false;
        if (root.key == key) return true;
        return key < root.key ? searchRec(root.left, key) : searchRec(root.right, key);
    }

    /**
     * Removes a key from the BST.
     * Complexity: O(n)
     * @param key The key to remove.
     */
    void remove(int key) {
        root = removeRec(root, key);
    }

    /**
     * Recursive helper function to remove a key from the BST.
     * @param root The root of the tree (or subtree) from which to remove the key.
     * @param key The key to remove.
     * @return The new root of the tree (or subtree).
     */
    private Node removeRec(Node root, int key) {
        if (root == null) return root;
        if (key < root.key)
            root.left = removeRec(root.left, key);
        else if (key > root.key)
            root.right = removeRec(root.right, key);
        else {
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;
            
            root.key = minValue(root.right);
            root.right = removeRec(root.right, root.key);
        }
        return root;
    }

    /**
     * Finds the minimum value in the subtree rooted at the given node.
     * @param root The root of the subtree.
     * @return The minimum value in the subtree.
     */
    private int minValue(Node root) {
        int minv = root.key;
        while (root.left != null) {
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }

    /**
     * Updates the BST by modifying each node's key to be the sum of all keys greater than itself.
     * Complexity: O(n)
     * @param root The root of the BST to update.
     */
    void updateBST(Node root) {
        updateBSTRec(root, new int[]{0});
    }

    /**
     * Recursive helper function to update the BST according to the specified rule.
     * @param node The current node being processed.
     * @param sum An array of one integer, acting as a reference to hold the running total of keys.
     */
    private void updateBSTRec(Node node, int[] sum) {
        if (node == null) return;
        updateBSTRec(node.right, sum);
        sum[0] += node.key;
        node.key = sum[0];
        updateBSTRec(node.left, sum);
    }

    /**
     * Determines if two arrays of integers represent the same BST without constructing the BSTs.
     * Assumes the insertion of keys into the BST follows the order presented in the arrays.
     * Complexity: O(n^2)
     * @param array1 The first array of integers.
     * @param array2 The second array of integers.
     * @return true if the arrays represent the same BST, false otherwise.
     */
    boolean areSameBST(int[] array1, int[] array2) {
        if (array1.length != array2.length) return false;
        return areSameBSTUtil(array1, array2, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Recursive helper function to determine if two arrays represent the same BST.
     * @param a The first array.
     * @param b The second array.
     * @param i1 The current index in the first array.
     * @param i2 The current index in the second array.
     * @param min The minimum value that the current key can have (exclusive).
     * @param max The maximum value that the current key can have (exclusive).
     * @return true if the subarrays from the current indices onwards represent the same BST, false otherwise.
     */
    private boolean areSameBSTUtil(int[] a, int[] b, int i1, int i2, int min, int max) {
        int j, k;
        for (j = i1; j < a.length; j++) {
            if (a[j] > min && a[j] < max)
                break;
        }
        for (k = i2; k < b.length; k++) {
            if (b[k] > min && b[k] < max)
                break;
        }
        if (j == a.length && k == b.length)
            return true;
        if ((j == a.length) ^ (k == b.length) || a[j] != b[k])
            return false;
        return areSameBSTUtil(a, b, j + 1, k + 1, min, a[j]) && areSameBSTUtil(a, b, j + 1, k + 1, a[j], max);
    }
}

