import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Your implementation of a QuadraticProbingHashMap.
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
public class QuadraticProbingHashMap<K, V> {

    /**
     * The initial capacity of the QuadraticProbingHashMap when created with the
     * default constructor.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * The max load factor of the QuadraticProbingHashMap
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    private static final double MAX_LOAD_FACTOR = 0.67;

    // Do not add new instance variables or modify existing ones.
    private QuadraticProbingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new QuadraticProbingHashMap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * <p>
     * Use constructor chaining.
     */
    public QuadraticProbingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new QuadraticProbingHashMap.
     * <p>
     * The backing array should have an initial capacity of initialCapacity.
     * <p>
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public QuadraticProbingHashMap(int initialCapacity) {
        table = (QuadraticProbingMapEntry<K, V>[]) new QuadraticProbingMapEntry[initialCapacity];
        size = 0;
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     * <p>
     * In the case of a collision, use quadratic probing as your resolution
     * strategy.
     * <p>
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. For example, let's say the array is of length 5 and the current
     * size is 3 (LF = 0.6). For this example, assume that no elements are
     * removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. Be
     * careful to consider the differences between integer and double
     * division when calculating load factor.
     * <p>
     * You must also resize when there are not any valid spots to add a
     * (key, value) pair in the HashMap after checking (table.length + 1)/2 spots.
     * There is more information regarding this case in the assignment PDF.
     * <p>
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     * <p>
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Invalid input.");
        }
        if (((double) (size + 1) / table.length) > MAX_LOAD_FACTOR) {
            resizeBackingTable((2 * table.length) + 1);
        }
        int ogIndex = Math.abs(key.hashCode() % table.length);
        int index = ogIndex;
        int h = 0;
        int removed = -1;

        while (table[index] != null && h < ((table.length + 1) / 2)) {
            if (table[index].isRemoved()) {
                //check removed spots
                if (removed == -1) {
                    removed = index;
                }
            } else if (table[index].getKey().equals(key)) {
                V old = table[index].getValue();
                table[index].setValue(value);
                return old; //alr in map
            }
            h++; //increase probe count
            index = ((h * h) + ogIndex) % table.length;

        }
        if (h >= (table.length + 1) / 2) {
            resizeBackingTable((2 * table.length) + 1);
            return put(key, value); //try again? check this logic idk if recursion is ideal
        }
        //found a spot, but check removed first
        if (removed != -1) {
            table[removed] = new QuadraticProbingMapEntry<>(key, value);
        } else {
            table[index] = new QuadraticProbingMapEntry<>(key, value);
        }
        size++;
        return null;
    }

    /**
     * Removes the entry with a matching key from map by marking the entry as
     * removed.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null");
        }
        int ogIndex = Math.abs(key.hashCode() % table.length);
        int index = ogIndex;
        int h = 0;
        while (table[index] != null && h < (table.length + 1) / 2) {
            if (!table[index].isRemoved() && table[index].getKey().equals(key)) {
                V oldValue = table[index].getValue();
                table[index].setRemoved(true);
                size--;
                return oldValue;
            }
            h++;
            index = ((h * h) + ogIndex) % table.length;
        }
        //key not in map
        throw new NoSuchElementException("Key is not in map");
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null");
        }
        int ogIndex = Math.abs(key.hashCode() % table.length);
        int index = ogIndex;
        int h = 0;

        while (table[index] != null && h < (table.length + 1) / 2) {
            if (!table[index].isRemoved() && table[index].getKey().equals(key)) {
                return table[index].getValue();
            }
            h++;
            index = ((h * h) + ogIndex) % table.length;
        }
        throw new NoSuchElementException("Key is not in the map");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null");
        }
        int ogIndex = Math.abs(key.hashCode() % table.length);
        int index = ogIndex;
        int h = 0;

        while (table[index] != null && h < (table.length + 1) / 2) {
            if (!table[index].isRemoved() && table[index].getKey().equals(key)) {
                return true;
            }
            h++;
            index = ((h * h) + ogIndex) % table.length;
        }
        return false;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * <p>
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        HashSet<K> setView = new HashSet<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && table[i].getKey() != null && !table[i].isRemoved()) {
                setView.add(table[i].getKey());
            }
        }
        return setView;
    }

    /**
     * Returns a List view of the values contained in this map.
     * <p>
     * Use java.util.ArrayList or java.util.LinkedList.
     * <p>
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        ArrayList<V> listView = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && table[i].getKey() != null && !table[i].isRemoved()) {
                listView.add(table[i].getValue());
            }
        }
        return listView;
    }

    /**
     * Resize the backing table to length.
     * <p>
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     * <p>
     * Note: This method does not have to handle the case where the new length
     * results in collisions that cannot be resolved without resizing again. It
     * also does not have to handle the case where size = 0, and length = 0 is
     * passed into the function.
     * <p>
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     * <p>
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     * <p>
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash map
     */
    public void resizeBackingTable(int length) {
        if (length < 0 || length < size) {
            throw new IllegalArgumentException("Invalid Length");
        }

        QuadraticProbingMapEntry<K, V>[] newBacking =
                (QuadraticProbingMapEntry<K, V>[]) new QuadraticProbingMapEntry[length];

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                K currKey = table[i].getKey();
                V currValue = table[i].getValue();

                int ogIndex = Math.abs(currKey.hashCode() % length);
                int index = ogIndex;
                int h = 0;

                while (newBacking[index] != null) {
                    h++;
                    index = (ogIndex + (h * h)) % length;
                }
                newBacking[index] = new QuadraticProbingMapEntry<>(currKey, currValue);
            }
        }
        table = newBacking; //remember to set table to new one
    }

    /**
     * Clears the map.
     * <p>
     * Resets the table to a new array of the INITIAL_CAPACITY and resets the
     * size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        table = (QuadraticProbingMapEntry<K, V>[]) new QuadraticProbingMapEntry[INITIAL_CAPACITY];
        size = 0;

    }

    /**
     * Returns the table of the map.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public QuadraticProbingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
