import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author Rand Ismaael
 * @version 1.0
 * @userid rismaael3
 * @GTID 903885377
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 * <p>
 * By typing 'I agree' below, you are agreeing that this is your
 * own work and that you are responsible for all the contents of
 * this file. If this is left blank, this homework will receive a zero.
 * <p>
 * Agree Here: I agree
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize an empty BST.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     * <p>
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Data is invalid");
        }
        size = 0;
        for (T i : data) {
            add(i);
        }

    }

    /**
     * Adds the data to the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * The data becomes a leaf in the tree.
     * <p>
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is invalid");
        }
        root = addHelper(data, root);
    }

    /**
     * Helper to add to BST.
     *
     * @param data data to add
     * @param curr node to traverse tree recursively
     * @return returns node in the lower stack for pointer reinforcement
     */
    private BSTNode<T> addHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            size++;
            return new BSTNode<>(data);
        } else if (curr.getData().compareTo(data) == 0) {
            return curr;
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(addHelper(data, curr.getLeft()));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(addHelper(data, curr.getRight()));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     * <p>
     * This must be done recursively.
     * <p>
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data. You MUST use recursion to find and remove the
     * predecessor (you will likely need an additional helper method to
     * handle this case efficiently).
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is invalid");
        }
        BSTNode<T> removedData = new BSTNode<>(null);
        root = removeHelper(data, root, removedData);
        size--;
        return removedData.getData();
    }

    /**
     * Helper to remove from BST
     *
     * @param data        data to remove
     * @param curr        node to traverse the tree recursively
     * @param removedData node being removed
     * @return parent node of node being removed
     */
    private BSTNode<T> removeHelper(T data, BSTNode<T> curr, BSTNode<T> removedData) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("Data is not in the tree");
        }
        //search for data
        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeHelper(data, curr.getLeft(), removedData));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeHelper(data, curr.getRight(), removedData));
        } else { //found
            //no child
            removedData.setData(curr.getData()); //store data
            if (curr.getLeft() == null) {
                return curr.getRight(); //replace w right
            } else if (curr.getRight() == null) {
                return curr.getLeft(); //replace w left
            }
            //two children
            BSTNode<T> successorNode = predecessor(curr.getLeft()); //find max of left child
            curr.setData(successorNode.getData()); //switch data between successor and current node

            //remove
            curr.setLeft(removeHelper(successorNode.getData(), curr.getLeft(), new BSTNode<>(null))); //Setting left child as the tree returned the node data that replaced
        }
        return curr;
    }

    /**
     * Predecessor helper.
     *
     * @param node node to traverse the tree
     * @return successor node
     */

    private BSTNode<T> predecessor(BSTNode<T> node) {
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     * <p>
     * This must be done recursively.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is invalid");
        }
        return getHelper(data, root);
    }

    /**
     * Helper method to find data.
     *
     * @param data data to look for
     * @param curr node to traverse the tree
     * @return data found
     */

    private T getHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("Data is not in the BST");
        }
        if (data.compareTo(curr.getData()) == 0) {
            return curr.getData();
        } else if (data.compareTo(curr.getData()) < 0) {
            return getHelper(data, curr.getLeft());
        } else {
            return getHelper(data, curr.getRight());
        }
    }


    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        return containsHelper(data, root);
    }

    /**
     * Helper method that finds if data is in the tree.
     *
     * @param data data being searched for
     * @param curr node to traverse the tree
     * @return data found
     */

    private boolean containsHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            return false;
        } else if (data.compareTo(curr.getData()) == 0) {
            return true;
        } else if (data.compareTo(curr.getData()) < 0) {
            return containsHelper(data, curr.getLeft());
        } else if (data.compareTo(curr.getData()) > 0) {
            return containsHelper(data, curr.getRight());
        }
        return false;
    }

    /**
     * Generate a pre-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> output = new ArrayList<T>();
        preorderHelper(root, output);
        return output;
    }

    /**
     * Helper method to do preorder traversal.
     *
     * @param curr   node to traverse the tree
     * @param output list of preorder traversal
     */
    private void preorderHelper(BSTNode<T> curr, List<T> output) {
        if (curr == null) {
            return;
        }
        output.add(curr.getData());
        preorderHelper(curr.getLeft(), output);
        preorderHelper(curr.getRight(), output);
    }

    /**
     * Generate an in-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> output = new ArrayList<T>();
        inorderHelper(root, output);
        return output;
    }

    /**
     * Helper method to do inorder traversal.
     *
     * @param curr   node to traverse the tree
     * @param output list of inorder traversal
     */

    private void inorderHelper(BSTNode<T> curr, List<T> output) {
        if (curr == null) {
            return;
        }
        inorderHelper(curr.getLeft(), output);
        output.add(curr.getData());
        inorderHelper(curr.getRight(), output);
    }

    /**
     * Generate a post-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> output = new ArrayList<T>();
        postorderHelper(root, output);
        return output;
    }

    /**
     * Helper method that creates postorder traversal.
     *
     * @param curr   node to traverse the tree
     * @param output list of postorder traversal
     */
    private void postorderHelper(BSTNode<T> curr, List<T> output) {
        if (curr == null) {
            return;
        }
        postorderHelper(curr.getLeft(), output);
        postorderHelper(curr.getRight(), output);
        output.add(curr.getData());
    }

    /**
     * Generate a level-order traversal of the tree.
     * <p>
     * This does not need to be done recursively.
     * <p>
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     * <p>
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> output = new LinkedList<>();
        if (root == null) {
            return output;
        }
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> curr = queue.remove();
            output.add(curr.getData());
            if (curr.getLeft() != null) {
                queue.add(curr.getLeft());
            }
            if (curr.getRight() != null) {
                queue.add(curr.getRight());
            }
        }
        return output;
    }

    /**
     * Returns the height of the root of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     * <p>
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return heightFinder(root);
    }

    /**
     * Helper method to find tree height
     *
     * @param curr node to traverse the tree
     * @return returns height
     */

    private int heightFinder(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        }
        if (curr.getRight() == null && curr.getLeft() == null) {
            return 0;
        } else {
            return (Math.max(heightFinder(curr.getLeft()), heightFinder(curr.getRight())) + 1);
        }
    }

    /**
     * Clears the tree.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree. (Another way to think about this is to get the right most
     * data per level from top to bottom.)
     * <p>
     * This must be done recursively.
     * <p>
     * This list should contain the last node of each level.
     * <p>
     * If the tree is empty, an empty list should be returned.
     * <p>
     * Ex:
     * Given the following BST composed of Integers
     * 2
     * /   \
     * 1     4
     * /     / \
     * 0     3   5
     * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
     * data of level 0, 4 is the max data of level 1, and 5 is the max data of
     * level 2
     * <p>
     * Ex:
     * Given the following BST composed of Integers
     * 50
     * /        \
     * 25         75
     * /    \
     * 12    37
     * /  \    \
     * 11   15   40
     * /
     * 10
     * getMaxDataPerLevel() should return the list [50, 75, 37, 40, 10] - 50 is
     * the max data of level 0, 75 is the max data of level 1, 37 is the
     * max data of level 2, etc.
     * <p>
     * Must be O(n).
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        List<T> output = new ArrayList<>();
        getMaxDataPerLevelHelper(root, 0, output);
        return output;
    }

    /**
     * Recursive helper for getMaxDataPerLevel().
     *
     * @param curr   node to traverse the three
     * @param depth  current depth
     * @param output list that stores the first node in each level
     */
    private void getMaxDataPerLevelHelper(BSTNode<T> curr, int depth, List<T> output) {
        if (curr == null) {
            return;
        }
        if (depth == output.size()) {
            output.add(curr.getData());
        }
        getMaxDataPerLevelHelper(curr.getRight(), depth + 1, output);
        getMaxDataPerLevelHelper(curr.getLeft(), depth + 1, output);
    }

    /**
     * Returns the root of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
