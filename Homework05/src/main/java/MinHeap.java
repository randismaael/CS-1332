import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
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
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * To initialize the backing array, create a Comparable array and then cast
     * it to a T array.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     * <p>
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     * <p>
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     * <p>
     * The backingArray should have capacity 2n + 1 where n is the
     * size of the passed in ArrayList (not INITIAL_CAPACITY). Index 0 should
     * remain empty, indices 1 to n should contain the data in proper order, and
     * the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is invalid.");
        }
        backingArray = (T[]) new Comparable[(data.size() * 2) + 1]; //odd so its closest to being a prime
        size = data.size();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("Data is invalid");
            }
            backingArray[i + 1] = data.get(i);
        }
        for (int i = (size / 2); i >= 1; i--) {
            minHeapHelper(i);
        }

    }

    /**
     * Turns data inputted into a min heap.
     *
     * @param i index
     */

    private void minHeapHelper(int i) {
        while ((2 * i) <= size) {
            int left = 2 * i;
            int right = (2 * i) + 1;
            int smallest = left;
            if ((right <= size) && backingArray[right].compareTo(backingArray[left]) < 0) {
                smallest = right; //if right child < left child
            }
            if (backingArray[i].compareTo(backingArray[smallest]) <= 0) {
                break; //base case, if u have found the smallest child
            }
            T tmp = backingArray[i];
            backingArray[i] = backingArray[smallest];
            backingArray[smallest] = tmp; //swap value with smallest
            i = smallest; //continue
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding. You can
     * assume that no duplicate data will be passed in.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is invald");
        }
        if ((size + 1) >= backingArray.length) {
            T[] tmpArray = (T[]) new Comparable[backingArray.length * 2 + 1]; //keep it odd
            for (int i = 1; i <= size; i++) {
                tmpArray[i] = backingArray[i];
            }
            backingArray = tmpArray;
        }
        size++;
        backingArray[size] = data;
        //fix the heap after adding
        int i = size;
        while (i > 1 && backingArray[i].compareTo(backingArray[i / 2]) < 0) {
            T tmp = backingArray[i];
            backingArray[i] = backingArray[i / 2];
            backingArray[i / 2] = tmp;
            i = i / 2;
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after removing.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty!");
        }
        T tmp = backingArray[1]; //min heap at the root
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        minHeapHelper(1);
        return tmp;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty!");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;

    }

    /**
     * Clears the heap.
     * <p>
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
