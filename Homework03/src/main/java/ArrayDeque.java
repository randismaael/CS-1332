import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayDeque.
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
public class ArrayDeque<T> {

    /**
     * The initial capacity of the ArrayDeque.
     * <p>
     * DO NOT MODIFY THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 11;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * Constructs a new ArrayDeque.
     */
    public ArrayDeque() {
        backingArray = (T[]) (new Object[INITIAL_CAPACITY]);
        size = 0;
        front = 0;

    }

    /**
     * Adds the element to the front of the deque.
     * <p>
     * If sufficient space is not available in the backing array, resize it to
     * double the current capacity. When resizing, copy elements to the
     * beginning of the new array and reset front to 0. After the resize and add
     * operation, the new data should be at index 0 of the array. Consider how
     * to do this efficiently.
     * <p>
     * Must be amortized O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is invalid.");
        }

        if (size == backingArray.length) {
            T[] newArray = (T[]) (new Object[backingArray.length * 2]);
            for (int i = 0; i < size; i++) {
                int index = mod(front + i, backingArray.length);
                newArray[i + 1] = backingArray[index];
            }
            front = 0;
            backingArray = newArray;
        } else {
            front = mod(front - 1, backingArray.length);
        }
        backingArray[front] = data;
        size++;
    }


    public void addFirst1(T data) {
        if (size == backingArray.length){
            T[] tmpArray = (T[]) (new Object[backingArray.length *2]);
            for (int i = 0; i< size; i++){
                int index = mod(front+1, backingArray.length);
                tmpArray[i+1]= backingArray[index];
            }
            front = 0;
            backingArray = tmpArray;
        } else {
            front = mod (front-1, backingArray.length);
        }
        backingArray[front] = data;
        size++;
    }

    /**
     * Adds the element to the back of the deque.
     * <p>
     * If sufficient space is not available in the backing array, resize it to
     * double the current capacity. When resizing, copy elements to the
     * beginning of the new array and reset front to 0.
     * <p>
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is invalid");
        }
        if (size == backingArray.length) {
            T[] newArray = (T[]) (new Object[backingArray.length * 2]);
            for (int i = 0; i < size; i++) {
                int index = mod(front + i, backingArray.length);
                newArray[i] = backingArray[index];
            }
            front = 0;
            backingArray = newArray;
        }
        int last = mod(front + size, backingArray.length);
        backingArray[last] = data;
        size++;
    }

    /**
     * Removes and returns the first element of the deque.
     * <p>
     * Do not grow or shrink the backing array.
     * <p>
     * If the deque becomes empty as a result of this call, do not reset
     * front to 0. Rather, modify the front index as if the deque did not become
     * empty as a result of this call.
     * <p>
     * Replace any spots that you remove from with null. Failure to do so can
     * result in loss of points.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        T temp = backingArray[front];
        backingArray[front] = null;
        front = mod(front + 1, backingArray.length);
        size--;
        return temp;
    }

    public T removeFirs1t() {
        T tmp = backingArray[front]; //to return later
        backingArray[front] = null;
        front = mod(front + 1, backingArray.length);
        size--;
        return tmp;
    }

    /**
     * Removes and returns the last element of the deque.
     * <p>
     * Do not grow or shrink the backing array.
     * <p>
     * If the deque becomes empty as a result of this call, do not reset
     * front to 0.
     * <p>
     * Replace any spots that you remove from with null. Failure to do so can
     * result in loss of points.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        int last = mod(front + (size - 1), backingArray.length);
        T temp = backingArray[last];
        backingArray[last] = null;
        size--;
        return temp;
    }

    /**
     * Returns the first data of the deque without removing it.
     * <p>
     * Must be O(1).
     *
     * @return the first data
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        return backingArray[front];
    }

    /**
     * Returns the last data of the deque without removing it.
     * <p>
     * Must be O(1).
     *
     * @return the last data
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        int last = mod(front + (size - 1), backingArray.length);
        return backingArray[last];
    }

    /**
     * Returns the backing array of the deque.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the deque
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the deque.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the deque
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the smallest non-negative remainder when dividing index by
     * modulo. So, for example, if modulo is 5, then this method will return
     * either 0, 1, 2, 3, or 4, depending on what the remainder is.
     * <p>
     * This differs from using the % operator in that the % operator returns
     * the smallest answer with the same sign as the dividend. So, for example,
     * (-5) % 6 => -5, but with this method, mod(-5, 6) = 1.
     * <p>
     * Examples:
     * mod(-3, 5) => 2
     * mod(11, 6) => 5
     * mod(-7, 7) => 0
     * <p>
     * This helper method is here to make the math part of the circular
     * behavior easier to work with.
     *
     * @param index  the number to take the remainder of
     * @param modulo the divisor to divide by
     * @return the remainder in its smallest non-negative form
     * @throws java.lang.IllegalArgumentException if the modulo is non-positive
     */
    private static int mod(int index, int modulo) {
        // DO NOT MODIFY THIS METHOD!
        if (modulo <= 0) {
            throw new IllegalArgumentException("The modulo must be positive");
        }
        int newIndex = index % modulo;
        return newIndex >= 0 ? newIndex : newIndex + modulo;
    }
}
