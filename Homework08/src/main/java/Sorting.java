import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.List;

/**
 * Your implementation of various sorting algorithms.
 * <p>
 * Your implementations must match what was taught in lecture and
 * recitation to receive credit. Implementing a different sort or
 * a different implementation for a sort will receive no credit even
 * if it passes comparison checks.
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
public class Sorting {

    /**
     * Implement insertion sort. You should start sorting
     * from the beginning of the array.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Invalid input.");
        }
        for (int n = 1; n < arr.length; n++) {
            int i = n; //go through each value
            while (i > 0 && comparator.compare(arr[i - 1], arr[i]) > 0) { //if data before i is bigger, swap
                T tmp = arr[i];
                arr[i] = arr[i - 1]; //swap
                arr[i - 1] = tmp;
                i--; //keep going until u reach arr[0]
            }
        }
    }

    /**
     * Implement cocktail sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Invalid input.");
        }

        int start = 0;
        int end = arr.length - 1;
        int swapped; //last swapped optimization

        //forward
        while (start < end) {
            swapped = start;
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T tmp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = tmp;
                    swapped = i + 1; //move to next pair
                }
            }
            if (swapped == start) {
                break; // if we reach end we stop
            }

            end = swapped - 1; // end points to lastSwapped

            //backward
            for (int i = end; i > start; i--) {
                if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                    T tmp = arr[i - 1];
                    arr[i - 1] = arr[i];
                    arr[i] = tmp;
                    swapped = i - 1;
                }
            }
            if (swapped == end) {
                break; // if we reach end we stop
            }
            start = swapped + 1;
        }
    }

    /**
     * Implement merge sort.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     * <p>
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     * <p>
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Invalid input.");
        }
        if (arr.length <= 1) {
            return;
        }
        T[] left = (T[]) new Object[arr.length / 2];
        T[] right;

        if (arr.length % 2 == 0) {
            right = (T[]) new Object[arr.length / 2];
            for (int i = 0; i < arr.length / 2; i++) {
                right[i] = arr[i + (arr.length / 2)];
            }
        } else {
            right = (T[]) new Object[arr.length / 2 + 1];
            for (int i = 0; i < arr.length / 2 + 1; i++) {
                right[i] = arr[i + (arr.length / 2)];
            }
        }
        for (int i = 0; i < (arr.length / 2); i++) {
            left[i] = arr[i];
        }

        mergeSort(left, comparator);
        mergeSort(right, comparator);
        mergeHelper(left, right, arr, comparator);
    }

    /**
     * Recursive mergeSort Helper
     *
     * @param arr        array to sort
     * @param comparator comparator to compare values
     * @param leftSide   left array
     * @param rightSide  right array
     * @param <T>        data type to sort
     */
    private static <T> void mergeHelper(T[] leftSide, T[] rightSide, T[] arr, Comparator<T> comparator) {
        boolean swapped;
        int x = 0;
        int y = 0;
        for (int i = 0; i < arr.length; i++) {
            if (x == leftSide.length) {
                arr[i] = rightSide[y++];
            } else if (y == rightSide.length) {
                arr[i] = leftSide[x++];
            } else if (comparator.compare(leftSide[x], rightSide[y]) <= 0) {
                arr[i] = leftSide[x++];
            } else {
                arr[i] = rightSide[y++];
            }
        }
    }

    /**
     * Implement quick sort.
     * <p>
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     * <p>
     * int pivotIndex = rand.nextInt(b - a) + a;
     * <p>
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     * <p>
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     * <p>
     * It should be:
     * in-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Make sure you code the algorithm as you have been taught it in class
     * (see Canvas Modules for reference).
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) { //me !!
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Invalid input.");
        }
        quickSortHelper(arr, comparator, rand, 0, arr.length);
    }


    /**
     * Recursive quicksort helper.
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @param left       inclusive lower bound of the region to sort
     * @param right      exclusive upper bound of the region to sort
     */
    private static <T> void quickSortHelper(T[] arr, Comparator<T> comparator,
                                            Random rand, int left, int right) {
        if (right - left <= 1) {
            return; // alr sorted if 0 or 1
        }

        // random pivot
        int pivotIdx = rand.nextInt(right - left) + left;
        T pivot = arr[pivotIdx]; //pivot value
        T tmp = arr[left]; //put it left for easy access
        arr[left] = arr[pivotIdx];
        arr[pivotIdx] = tmp;

        int i = left + 1;
        int j = right - 1;

        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivot) <= 0) {
                i++; // moving i when until it reaches a number greater than pivot val
            }
            while (i <= j && comparator.compare(arr[j], pivot) > 0) {
                j--;  // moving j until it reaches a number less than pivot val
            }
            if (i <= j) { //if not overlap, swap
                T tmp1 = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp1;
                i++;
                j--;
            }
        }


        // move pivot to final pos
        T tmp2 = arr[left];
        arr[left] = arr[j];
        arr[j] = tmp2;

        // recurse on [left, j) and [j+1, right)
        quickSortHelper(arr, comparator, rand, left, j);
        quickSortHelper(arr, comparator, rand, j + 1, right);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class
     * (see Canvas Modules for reference).
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     * <p>
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(kn)
     * <p>
     * And a best case running time of:
     * O(kn)
     * <p>
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     * <p>
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     * <p>
     * Refer to the PDF for more information on LSD Radix Sort.
     * <p>
     * Note: Be very careful about how Integer.MIN_VALUE is handled, especially
     * when using Math.abs().
     * <p>
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     * <p>
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Invalid input.");
        }
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }
        int modNum = 10;
        int divNum = 1;
        boolean continued = true;

        while (continued) {
            continued = false;
            for (int num : arr) {
                int bucket = num / divNum;
                if (bucket / 10 != 0) {
                    continued = true;
                }
                if (buckets[bucket % modNum + 9] == null) {
                    buckets[bucket % modNum + 9] = new LinkedList<>();
                }
                buckets[bucket % modNum + 9].add(num);
            }
            int arrIdx = 0;
            for (LinkedList<Integer> bucket : buckets) {
                if (bucket != null) {
                    for (int num : bucket) {
                        arr[arrIdx++] = num;
                    }
                    bucket.clear();
                }
            }
            divNum *= 10;
        }
    }

    /**
     * Implement heap sort.
     * <p>
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     * <p>
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     * <p>
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Invalid input.");
        }
        PriorityQueue<Integer> q = new PriorityQueue<>();
        q.addAll(data); //suggested by IDE
        int[] arr = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            arr[i] = q.remove();
        }
        return arr;
    }
}
