import java.util.HashMap;
import java.util.ArrayList;

/**
 * Solve a challenge using Java's built-in HashMap.
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
 * Agree Here: REPLACE THIS TEXT
 */
public class HashMapChallenge {

    /**
     * We want to know the amount of each distinct character contained in a string.
     * Given an input string, create a HashMap where the keys are characters, and
     * the values are integers representing the frequency of that character in the
     * string.
     * <p>
     * In other words, find the amount of each character in the string and put it in
     * a char-to-int HashMap.
     * <p>
     * You should use Java's HashMap for this method. No credit will be given for
     * solutions that do not use a HashMap. Try to make it as efficient as possible.
     * <p>
     * Example: a string "java!"
     * Expected Output: the HashMap with the key-value pairs below:
     * 'j' -> 1
     * 'a' -> 2
     * 'v' -> 1
     * '!' -> 1
     * <p>
     * Hint: See the Java HashMap documentation:
     * https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/HashMap.html
     *
     * @param str the string to create a character count HashMap for.
     * @return a HashMap where each character (key) maps to its integer count (value).
     * @throws IllegalArgumentException if str is null
     */
    public static HashMap<Character, Integer> characterCountMap(String str) {
        // Use java.util.HashMap, NOT your own QuadraticProbingHashMap.
        if (str == null) {
            throw new IllegalArgumentException("String is invalid.");
        }

        HashMap<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1); //count how many times letter comes up
        }

        return map;

    }

    /**
     * A string is an anagram of another string if the two strings contain the same
     * characters, regardless of order. For example, "listen" and "silent" contain
     * the same letters, but in a different order. Thus, these are anagrams. Your
     * task is, given a list of strings, verify that all the strings are anagrams
     * of each other. If so, return true. If at least one string is NOT an anagram
     * of any other string, then return false.
     * <p>
     * You should use Java's HashMap for this method. No credit will be given for
     * solutions that do not use a HashMap. Try to make it as efficient as possible.
     * <p>
     * Spaces should be treated the same as any other letter. Strings can contain any
     * Unicode characters. This means there may be symbols, numbers, emojis, etc.
     * A string counts as an anagram of itself.
     * <p>
     * Example: a list containing "abba", "baba", "bbaa", "aabb"
     * Expected output: True, because all strings contain two a's and two b's.
     * <p>
     * Example: a list containing "affect", "ffacet", "effect"
     * Expected output: False, because "effect" is not an anagram of the other strings.
     * <p>
     * Hint: It is extremely helpful to call your characterCountMap() in this method.
     *
     * @param strings the list of strings to examine for anagrammatic relationships.
     * @return true if all strings are anagrams of each other, otherwise return false.
     * @throws IllegalArgumentException if list of strings is null or empty
     */
    public static boolean stringsAreAnagrams(ArrayList<String> strings) {
        // Use java.util.HashMap, NOT your own QuadraticProbingHashMap.
        if (strings == null || strings.isEmpty()) {
            throw new IllegalArgumentException("List cannot be null or empty");
        }

        HashMap<Character, Integer> firstMap = characterCountMap(strings.get(0)); //how many times each letter shows up

        for (int i = 1; i < strings.size(); i++) {
            //if # of letters are equal from word to word, then they are anagrams
            HashMap<Character, Integer> currentMap = characterCountMap(strings.get(i)); //count new word

            if (!firstMap.equals(currentMap)) { // maps not equal, not anagrams
                return false;
            }
        }

        return true;
    }
}
