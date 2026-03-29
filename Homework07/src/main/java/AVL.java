import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Your implementation of an AVL.
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
 * this file. If this is left blank, you will lose points.
 * <p>
 * Agree Here: I agree
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     * <p>
     * This constructor should initialize an empty AVL.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     * <p>
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is invalid.");
        }
        for (T x : data) {
            add(x);
        }
    }

    /**
     * Adds the element to the tree.
     * <p>
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     * <p>
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     * <p>
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     * <p>
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is invalid");
        }
        root = addHelper(root, data);
    }

    /**
     * helper for recursively adding
     *
     * @param data data to add
     * @param curr node for traversing
     * @return new node with data
     */

    private AVLNode<T> addHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new AVLNode<>(data);
        }
        int tempNode = data.compareTo(curr.getData()); //compare keys
        if (tempNode < 0) {
            curr.setLeft(addHelper(curr.getLeft(), data));
        } else if (tempNode > 0) {
            curr.setRight(addHelper(curr.getRight(), data));
        }
        updateBalance(curr); //for balancing
        if (Math.abs(curr.getBalanceFactor()) > 1) {
            curr = balance(curr); //if BF is 2 or -2 or higher, need to balance
        }
        return curr;
    }

    /**
     * helper method to balance the tree
     *
     * @param curr the root of a subtree to balance
     * @return the new root of the subtree
     */

    private AVLNode<T> balance(AVLNode<T> curr) {
        if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() < 0) {
                curr.setLeft(rotateLeft(curr.getLeft())); //double rotation, L (then right after)
            }
            return rotateRight(curr); //right
        } else if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() > 0) {
                curr.setRight(rotateRight(curr.getRight())); //RL
            }
            return rotateLeft(curr);
        }
        return curr;
    }

    /**
     * helper to do a rotation
     *
     * @param curr root to rotate
     * @return root of updated subtree
     */
    private AVLNode<T> rotateRight(AVLNode<T> curr) {
        AVLNode<T> leftNode = curr.getLeft();
        curr.setLeft(leftNode.getRight());
        leftNode.setRight(curr); //pointer
        updateBalance(curr);
        updateBalance(leftNode);
        return leftNode;
    }

    /**
     * helper to do a left rotation
     *
     * @param curr root to rotate
     * @return root of updated subtree
     */
    private AVLNode<T> rotateLeft(AVLNode<T> curr) {
        AVLNode<T> rightNode = curr.getRight();
        curr.setRight(rightNode.getLeft());
        rightNode.setLeft(curr);
        updateBalance(curr);
        updateBalance(rightNode);
        return rightNode;
    }

    /**
     * helper method to update balance and height
     *
     * @param curr whose BF and height must be updated
     */
    private void updateBalance(AVLNode<T> curr) {
        int left = (curr.getLeft() == null) ? -1 : curr.getLeft().getHeight();
        int right = (curr.getRight() == null) ? -1 : curr.getRight().getHeight();
        curr.setBalanceFactor(left - right);
        curr.setHeight(1 + Math.max(left, right));
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     * <p>
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data, NOT predecessor. As a reminder, rotations can occur
     * after removing the successor node.
     * <p>
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is invalid");
        }
        AVLNode<T> removed = new AVLNode<>((T) null);
        root = removeHelper(root, data, removed);
        size--;
        return removed.getData();
    }

    /**
     * recursive helper to remove
     *
     * @param data    data to remove
     * @param curr    node to check for data
     * @param removed removed data node
     * @return node with data
     */
    private AVLNode<T> removeHelper(AVLNode<T> curr, T data,
                                    AVLNode<T> removed) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("Data is not in tree");
        }
        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeHelper(curr.getLeft(), data, removed));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeHelper(curr.getRight(), data, removed));
        } else { //found node to delete
            if (removed.getData() == null) {
                removed.setData(curr.getData());
            }
            if (curr.getLeft() == null) { //0 or 1 child
                return curr.getRight();
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            }
            //2 children
            curr.setData(successor(curr));
            curr.setRight(removeHelper(curr.getRight(), curr.getData(),
                    removed));
        }
        updateBalance(curr);
        //need rotation
        if (Math.abs(curr.getBalanceFactor()) > 1) {
            curr = balance(curr);
        }
        return curr;
    }

    /**
     * helper method to return the successor
     *
     * @param curr node being removed
     * @return data of node's predecessor
     */
    private T successor(AVLNode<T> curr) {
        return successorHelper(curr.getRight());
    }

    /**
     * recursive iterator to find successor
     *
     * @param curr current curr in traversal
     * @return data of the leftmost curr
     */
    private T successorHelper(AVLNode<T> curr) {
        if (curr.getLeft() == null) { //no left child, this is successor
            return curr.getData();
        }
        return successorHelper(curr.getLeft()); //go left
    }

    /**
     * Returns the element from the tree matching the given parameter.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is invalid");
        }
        return getHelper(root, data);
    }

    /**
     * helper for get
     *
     * @param curr curr to check
     * @param data data to search for
     * @return data if it was found in curr
     */
    private T getHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("Data not found");
        }
        if (curr.getData().equals(data)) {
            return curr.getData();
        } else {
            if (data.compareTo(curr.getData()) > 0) {
                return getHelper(curr.getRight(), data);
            } else {
                return getHelper(curr.getLeft(), data);
            }
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is invalid");
        }
        return containHelper(root, data);
    }

    /**
     * recursive helper method for contains
     *
     * @param curr node to examine
     * @param data data to search for
     * @return boolean stating if data is in BST
     */
    private boolean containHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            return false;
        } else if (data.compareTo(curr.getData()) == 0) {
            return true;
        } else if (data.compareTo(curr.getData()) < 0) {
            return containHelper(curr.getLeft(), data);
        } else {
            return containHelper(curr.getRight(), data);
        }
    }

    /**
     * Returns the height of the root of the tree.
     * <p>
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Clears the tree.
     * <p>
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Find all elements within a certain distance from the given data.
     * "Distance" means the number of edges between two nodes in the tree.
     * <p>
     * To do this, first find the data in the tree. Keep track of the distance
     * of the current node on the path to the data (you can use the return
     * value of a helper method to denote the current distance to the target
     * data - but note that you must find the data first before you can
     * calculate this information). After you have found the data, you should
     * know the distance of each node on the path to the data. With that
     * information, you can determine how much farther away you can traverse
     * from the main path while remaining within distance of the target data.
     * <p>
     * Use a HashSet as the Set you return. Keep in mind that since it is a
     * Set, you do not have to worry about any specific order in the Set.
     * <p>
     * Ex:
     * Given the following AVL composed of Integers
     * 50
     * /    \
     * 25      75
     * /  \     / \
     * 13   37  70  80
     * /  \    \      \
     * 12  15    40    85
     * /
     * 10
     * elementsWithinDistance(37, 3) should return the set {12, 13, 15, 25,
     * 37, 40, 50, 75}
     * elementsWithinDistance(85, 2) should return the set {75, 80, 85}
     * elementsWithinDistance(13, 1) should return the set {12, 13, 15, 25}
     *
     * @param data     the data to begin calculating distance from
     * @param distance the maximum distance allowed
     * @return the set of all data within a certain distance from the given data
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   is the data is not in the tree
     * @throws java.lang.IllegalArgumentException if distance is negative
     */
    public Set<T> elementsWithinDistance(T data, int distance) {
        if (data == null) {
            throw new IllegalArgumentException("Data is invalid");
        }
        if (distance < 0) {
            throw new IllegalArgumentException("Distance cannot be negative");
        }

        Set<T> result = new HashSet<>();
        int distFound = find(root, data, distance, result);

        if (distFound == -1) {
            throw new java.util.NoSuchElementException("Data is not in tree");
        }

        return result;
    }

    /**
     * Finds target and collects elements within distance.
     *
     * @param curr     current node
     * @param data     target data
     * @param distance max distance allowed
     * @param result   set to collect elements
     * @return distance from curr to target, or -1 if not found
     */
    private int find(AVLNode<T> curr, T data, int distance, Set<T> result) {
        if (curr == null) {
            return -1;
        }
        int compare = data.compareTo(curr.getData());
        if (compare == 0) {
            // found
            result.add(curr.getData());
            collect(curr.getLeft(), distance - 1, result); //get all left
            collect(curr.getRight(), distance - 1, result); //get all right
            return 0;
        }
        int distToTarget;

        //search
        if (compare < 0) {
            distToTarget = find(curr.getLeft(), data, distance, result);
        } else {
            distToTarget = find(curr.getRight(), data, distance, result);
        }

        if (distToTarget == -1) {
            return -1; //not here
        }

        // (distToTarget + 1) away from target
        int currentDist = distToTarget + 1;

        if (currentDist <= distance) {
            result.add(curr.getData());

            int remaining = distance - currentDist - 1;
            if (compare < 0) {
                collect(curr.getRight(), remaining, result); //recurs
            } else {
                collect(curr.getLeft(), remaining, result);
            }
        }
        return currentDist;
    }

    /**
     * Finds nodes within the distance (recursive)
     *
     * @param curr     current node
     * @param distance remaining distance
     * @param result   set to collect elements
     */
    private void collect(AVLNode<T> curr, int distance, Set<T> result) {
        if (curr == null || distance < 0) {
            return;
        }

        result.add(curr.getData());
        collect(curr.getLeft(), distance - 1, result);
        collect(curr.getRight(), distance - 1, result);
    }

    /**
     * Returns the root of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
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
