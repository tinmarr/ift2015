class AVLNode {
    String word, meaning;
    int height;
    AVLNode left, right;

    AVLNode(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
        this.height = 1;
        this.left = this.right = null;
    }
}

class UnbalancedNodeInfo {
    AVLNode node, parent;
    boolean leftOfParent;

    UnbalancedNodeInfo(AVLNode node, AVLNode parent, boolean leftOfParent) {
	this.node = node;
	this.parent = parent;
	this.leftOfParent = leftOfParent;
    }
}

public class DictionaryAVLTree {
    private AVLNode root;


    private int height(AVLNode N) {
        if (N == null) return 0;
        return N.height;
    }

    
    /** Update the heights of the tree
     * @param node The node to start at
     * @return The height of the node
     */
    private int updateHeights(AVLNode node) {
	if (node.left == null && node.right == null) {
	    node.height = 1;
	} else {
	    node.height = Math.max(node.left != null ? updateHeights(node.left) : 0, node.right != null ? updateHeights(node.right) : 0) + 1;
	}
	return node.height;
    }

    /** Insert newNode into node as if it was a regular BST 
     * @param node The node to insert into
     * @param newNode The node to insert
     */
    private void insertBST(AVLNode node, AVLNode newNode) {
	// check if new word < node word
	if (newNode.word.compareTo(node.word) < 0) {
	    if (node.left == null) {
		// There is room on the left
		node.left = newNode;
	    } else {
		// There is no room on the left, traverse to the next node
		insertBST(node.left, newNode); 
	    }
	} else {
	    if (node.right == null) {
		// There is room on the right
		node.right = newNode;
	    } else {
		// There is no room on the right, traverse to the next node
		insertBST(node.right, newNode);
	    }
	}
	return;
    }

    /** Find an unbalanced node in the tree (start at the bottom of the tree and work back up)
     * @param node The node to start at
     * @return A list of 2 AVL Nodes with the format [unbalanced node, parent of unbalanced]
     */
    private UnbalancedNodeInfo findUnbalancedNode(AVLNode node) {
	UnbalancedNodeInfo info = new UnbalancedNodeInfo(null, null, false);
	if (node.left != null) {
	    info = findUnbalancedNode(node.left);
	    info.leftOfParent = true;
	}
	if (info.node == null && node.right != null) {
	    info = findUnbalancedNode(node.right);
	    info.leftOfParent = false;
	}
	if (info.node != null) {
	    info.parent = node;
	    return info;
	}
	int balanceFactor = height(node.left) - height(node.right);
	if (Math.abs(balanceFactor) > 1) {
	    info.node = node;
	}
	return info;
    }

    /** Balances the AVL Tree with node as a root
     * @param node The node to consider a root
     * @return The new root node
     */
    private AVLNode balanceTree(AVLNode node) {
	// Get the bad node (if any)
	UnbalancedNodeInfo info = findUnbalancedNode(node);
	AVLNode z = info.node;
	if (z == null) {
	    return node; // Tree is balanced
	} 
	int zBF = height(z.left) - height(z.right); // We know here that this is not -1, 0, or 1
	AVLNode y;
	if (zBF > 0) {
	    // Right rotations
	    y = z.left;
	    int yBF = height(y.left) - height(y.right);
	    if (yBF >= 0) {
		// Rotate right
		z.left = y.right;
		y.right = z;
	    } else {
		// Rotate x and y left 
		AVLNode x = y.right;
		y.right = x.left;
		x.left = y;
		z.left = x;
		// x becomes the new y
		y = x;
		// Rotate right
		z.left = y.right;
		y.right = z;
	    }
	} else {
	    // Left rotations
	    y = z.right;
	    int yBF = height(y.left) - height(y.right);
	    if (yBF <= 0) {
		// Rotate left
		z.right = y.left;
		y.left = z;
	    } else {
		// Rotate x and y right
		AVLNode x = y.left;
		y.left = x.right;
		x.right = y;
		z.right = x;
		// x becomes the new y
		y = x;
		// Rotate left
		z.right = y.left;
		y.left = z;
	    }
	}
	// Reattach y to parent
	if (info.parent != null) {
	    if (info.leftOfParent) {
		info.parent.left = y;
	    } else {
		info.parent.right = y;
	    }
	    return node;
	}
	// Root node has changed
	return y;
    }

    /** Inserts a new node
     * @param node The root node
     * @param word The word to insert
     * @param meaning The meeting of the word
     * @return The new root node
     */
    private AVLNode insert(AVLNode node, String word, String meaning) {
	// Construct the node
	AVLNode newNode = new AVLNode(word, meaning);
	// If it's the first node, return that
	if (node == null) {
	    return newNode;
	}
	// Insert the node as in a regular BST (updating heights as we go)
	insertBST(node, newNode);
	updateHeights(node);
	// Balance
	return balanceTree(node);
    }

    /** Inserts a word and meaning into the tree
     * @param word The word to insert
     * @param meaning The meaning of that word
     */
    public void insert(String word, String meaning) {
        root = insert(root, word, meaning);
    }

    /** Delete a node from a BST. DOES NOT touch the given node. Assumes that it was checked before
     * @param node The node to start from. If this node is the given word, it will not be deleted
     * @param word The word to delete
     */
    private void deleteBST(AVLNode node, String word) {
	AVLNode holding = null;
	AVLNode toDelete = null;
	if (word.compareTo(node.word) < 0) {
	    // word < node.word
	    if (node.left != null) {
		if (word.equals(node.left.word)) {
		    // Delete node.left
		    toDelete = node.left;
		    if (toDelete.right != null) {
			holding = toDelete.right.left;
			toDelete.right.left = toDelete.left;
			node.left = toDelete.right;
		    } else if (toDelete.left != null) {
			holding = toDelete.left.right;
			toDelete.left.right = toDelete.right;
			node.left = toDelete.left;
		    } 
		    if (holding != null) {
			insertBST(node, holding);
		    }
		    return;
		}
		// Word not equal, continue through tree
		deleteBST(node.left, word);
		return;
	    }
	    return;
	} 
	// Check right side
	if (node.right != null) {
	    if ( word.equals(node.right.word)) {
		// Delete node.right
		toDelete = node.right;
		if (toDelete.right != null) {
		    holding = toDelete.right.left;
		    toDelete.right.left = toDelete.left;
		    node.right = toDelete.right;
		} else if (toDelete.left != null) {
		    holding = toDelete.left.right;
		    toDelete.left.right = toDelete.right;
		    node.right = toDelete.left;
		}
		if (holding != null) {
		    insertBST(node, holding);
		}
		return;
	    }
	    // Word not equal, continue through tree
	    deleteBST(node.right, word);
	    return;
	}
	return;
    }

    /** Deletes a word
     * @param node The root node
     * @param word The word to delete
     * @return The new root node
     */
    private AVLNode delete(AVLNode root, String word) {
	// Check if root is word
	if (root.word.equals(word)) {
	    // Root is word, delete + update root
	    AVLNode holding = null;
	    if (root.right != null) {
		// Replace root with right
		holding = root.right.left;
		root.right.left = root.left;
		root = root.right;
	    } else if (root.left != null) {
		// Replace root with left
		holding = root.left.right;
		root.left.right = root.right;
		root = root.left;
	    } else {
		// Root is a leaf, remove it
		return null;
	    }
	    if (holding != null) {
		insertBST(root, holding);
	    }
	} else {
	    // Root is not word, we can check the tree
	    deleteBST(root, word);
	}
	// Rebalance tree
	updateHeights(root);
	return balanceTree(root);
    }
    
    /** Deletes a word from the tree
     * @param word The word to delete
     */
    public void delete(String word) {
        root = delete(root, word);
    }
    
    /** Searchs for the meaning of a word in the AVL Tree
     * @param root The root node of the tree
     * @param word The word to look for
     * @return The meaning of the word
     */
    public String search(AVLNode root, String word) {
	if (root.word.equals(word)) {
	    return root.meaning;
	}
	if (root.left == null && root.left == null) {
	    return "";
	}
	// Check if word < root.word
	if (word.compareTo(root.word) < 0) {
	    return search(root.left, word);
	}
	return search(root.right, word);
    }
    
    /** Looks up the meaning of a word
     * @param word The word to look up
     * @return The meaning of the word
     */
    public String search(String word) {
        return search(root, word);
    }

    /** Prints out the contents of the dictionary in alphabetical order
     * @param node The node to start at (the root)
     */
    private void inOrderTraversal(AVLNode node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.println("Word: " + node.word + ", Meaning: " + node.meaning);
            inOrderTraversal(node.right);
        }
    }

    /** Display the full contents of the dictionary in alphabetical order
     */
    public void displayDictionary() {
        inOrderTraversal(root);
    }

    public static void main(String[] args) {
        DictionaryAVLTree dictionary = new DictionaryAVLTree();
    
        // Example insertions
        String[][] wordsAndMeanings = {
                {"Harmony", "The combination of simultaneously sounded musical notes to produce chords and chord progressions having a pleasing effect."},
                {"Ephemeral", "Lasting for a very short time."},
                {"Serendipity", "The occurrence and development of events by chance in a happy or beneficial way."},
                {"Quintessential", "Representing the most perfect or typical example of a quality or class."},
                {"Eloquence", "Fluent or persuasive speaking or writing."},
                {"Melancholy", "A feeling of pensive sadness, typically with no obvious cause."},
                {"Labyrinth", "A complicated irregular network of passages or paths in which it is difficult to find one's way; a maze."},
                {"Solitude", "The state or situation of being alone."}
            };
    
        // Insert each word and meaning into the dictionary
        for (String[] entry : wordsAndMeanings) {
            dictionary.insert(entry[0], entry[1]);
        }

        // Display the dictionary before deletion
        System.out.println("Dictionary content in alphabetical order before deletion:");
        dictionary.displayDictionary();
    
        // Deleting a word from the dictionary
        String wordToDelete = "Ephemeral";
        System.out.println("\nDeleting word: " + wordToDelete);
        dictionary.delete(wordToDelete);
    
        // Display the dictionary after deletion
        System.out.println("Dictionary content in alphabetical order after deletion:");
        dictionary.displayDictionary();
    
        // Searching for meanings
        String[] wordsToSearch = {"Harmony", "Eloquence", "Ephemeral"};
        System.out.println("\nSearching for words:");
        for (String word : wordsToSearch) {
            System.out.println("Meaning of '" + word + "': " + dictionary.search(word));
        }
    }
}

