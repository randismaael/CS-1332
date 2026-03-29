import java.util.PriorityQueue;

/**
 * Solve a challenge using Java's built-in PriorityQueue.
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
public class MinHeapChallenge {

    /**
     * In a Heap or PriorityQueue, the "peek" operation typically returns the topmost
     * element without removing it, allowing users to see it without changing the heap contents.
     * <p>
     * Here, we want to do the same, but for an arbitrary k-th smallest value. You
     * should return this data while also maintaining the contents of the heap such
     * that the heap before and after this operation appears unchanged to the user.
     * <p>
     * You should use Java's PriorityQueue for this method. No credit will be given for
     * solutions that do not use a PriorityQueue. Try to make it as efficient as possible.
     * <p>
     * Example: given the MinHeap
     * 1
     * /   \
     * 4     5
     * / \   / \
     * 7  9  8   6
     * kthSmallestPeek(4) should return the 4-th smallest data, which is 6.
     * The heap should still have all elements present in it at the end of the method.
     * <p>
     * See the Java PriorityQueue documentation:
     * https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/PriorityQueue.html
     * <p>
     * Expected effiiency is O(k log n)
     *
     * @param <T>  the type of object stored in heap
     * @param heap the heap that we want to find the k-th smallest in
     * @param k    of k-th smallest, i.e. k=3 is the third-smallest value
     * @return T the k-th smallest data value in the heap
     * @throws IllegalArgumentException if k < 1 or k > size, or if heap is null
     */
    public static <T> T kthSmallestPeek(PriorityQueue<T> heap, int k) {
        // Use java.util.PriorityQueue, NOT your own MinHeap.
        if (heap == null || k < 1 || k > heap.size()) {
            throw new IllegalArgumentException("Heap is inavlid");
        }
        PriorityQueue<T> tmp = new PriorityQueue<>(heap);
        for (int i = 1; i < k; i++) {
            tmp.poll(); //Retrieves and removes the head of this queue, or returns null if this queue is empty.
        }
        return tmp.peek();
    }
}
