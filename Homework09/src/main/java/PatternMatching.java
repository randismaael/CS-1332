import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various string searching algorithms.
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
public class PatternMatching {

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     * <p>
     * Make sure to implement the buildLastTable() method before implementing
     * this method. Do NOT implement the Galil Rule in this method.
     * <p>
     * Note: You may find the getOrDefault() method from Java's Map class
     * useful.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each and every match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0 || text == null || comparator == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        int patternLen = pattern.length();
        int textLen = text.length();
        List<Integer> matches = new LinkedList<Integer>();

        if (patternLen > textLen) {
            return matches;  // pattern longer than text = no matches possible
        }

        // Build last occurrence table
        Map<Character, Integer> lastTable = buildLastTable(pattern);

        // === ALGORITHM ===
        int textIndex = 0;  // where pattern is aligned in text

        while (textIndex <= (textLen - patternLen)) {

            // Start comparing from END of pattern (right to left)
            int patternIndex = patternLen - 1;

            // Keep matching while characters are equal
            while (patternIndex >= 0) {
                char patternChar = pattern.charAt(patternIndex);
                char textChar = text.charAt(textIndex + patternIndex);

                if (comparator.compare(patternChar, textChar) == 0) {
                    patternIndex--;  // match! move left
                } else {
                    break;  // mismatch! stop comparing
                }
            }

            if (patternIndex == -1) {
                // Entire pattern matched!
                matches.add(textIndex);
                textIndex++;  // shift by 1 to find overlapping matches
            } else {
                // Mismatch — use last occurrence table to shift
                char mismatchChar = text.charAt(textIndex + patternIndex);
                int lastOccur = lastTable.getOrDefault(mismatchChar, -1);

                if (lastOccur < patternIndex) {
                    // Shift to align last occurrence with mismatch position
                    textIndex += patternIndex - lastOccur;
                } else {
                    // Last occurrence is at or after mismatch — just shift by 1
                    textIndex++;
                }
            }
        }
        return matches;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     * <p>
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     * <p>
     * Ex. pattern = octocat
     * <p>
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     * <p>
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Invalid input");
        }
        Map<Character, Integer> table = new HashMap<>(pattern.length() + 1); //+1 for *
        for (int i = 0; i < pattern.length(); i++) {
            table.put(pattern.charAt(i), i);
        }
        return table;
    }

    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table
     * (also called failure function). Works better with small alphabets.
     * <p>
     * Make sure to implement the buildFailureTable() method before implementing
     * this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each and every match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0 || text == null || comparator == null) {
            throw new IllegalArgumentException("Invalid input");
        }
        int m = pattern.length();
        int n = text.length();
        List<Integer> occur = new LinkedList<Integer>();
        if (m > n) {
            return occur; //invalid
        }

        int[] table = buildFailureTable(pattern, comparator);
        int i = 0;
        int j = 0;
        while (i <= n - m) {
            while (j < m && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j++;
            }
            if (j == 0) {
                i++;
            } else {
                if (j == m) {
                    occur.add(i);
                }
                i += j - table[j - 1];
                j = table[j - 1];
            }
        }
        return occur;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     * <p>
     * The table built should be the length of the input pattern.
     * <p>
     * Note that a given index i will contain the length of the largest prefix
     * of the pattern indices [0..i] that is also a suffix of the pattern
     * indices [1..i]. This means that index 0 of the returned table will always
     * be equal to 0
     * <p>
     * Ex. pattern = ababac
     * <p>
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     * <p>
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this to check if characters are equal
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (comparator == null || pattern == null) {
            throw new IllegalArgumentException("Invalid Input");
        }
        int m = pattern.length();
        if (m == 0) {
            return new int[0];
        }
        int[] failed = new int[m];
        int i = 0;
        int j = 1;
        failed[0] = 0;

        while (j < m) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                failed[j] = i + 1;
                i++;
                j++;
            } else {
                if (i == 0) {
                    failed[j] = 0;
                    j++;
                } else {
                    i = failed[i - 1];
                }
            }
        }
        return failed;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 113;

    /**
     * Runs the Rabin-Karp algorithm. This algorithm generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     * <p>
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     * <p>
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     * <p>
     * sum of: c * BASE ^ (pattern.length - 1 - i)
     * c is the integer value of the current character, and
     * i is the index of the character
     * <p>
     * We recommend building the hash for the pattern and the first m characters
     * of the text by starting at index (m - 1) to efficiently exponentiate the
     * BASE. This allows you to avoid using Math.pow().
     * <p>
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow; you will not need to handle this case.
     * You may assume that all powers and calculations CAN be done without
     * overflow. However, be careful with how you carry out your calculations.
     * For example, if BASE^(m - 1) is a number that fits into an int, it's
     * possible for BASE^m will overflow. So, you would not want to do
     * BASE^m / BASE to calculate BASE^(m - 1).
     * <p>
     * Ex. Hashing "bunn" as a substring of "bunny" with base 113
     * = (b * 113 ^ 3) + (u * 113 ^ 2) + (n * 113 ^ 1) + (n * 113 ^ 0)
     * = (98 * 113 ^ 3) + (117 * 113 ^ 2) + (110 * 113 ^ 1) + (110 * 113 ^ 0)
     * = 142910419
     * <p>
     * Another key point of this algorithm is that updating the hash from
     * one substring to the next substring must be O(1). To update the hash,
     * subtract the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar as shown by this formula:
     * (oldHash - oldChar * BASE ^ (pattern.length - 1)) * BASE + newChar
     * <p>
     * Ex. Shifting from "bunn" to "unny" in "bunny" with base 113
     * hash("unny") = (hash("bunn") - b * 113 ^ 3) * 113 + y
     * = (142910419 - 98 * 113 ^ 3) * 113 + 121
     * = 170236090
     * <p>
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^(m - 1) is for updating the hash.
     * <p>
     * Do NOT use Math.pow() in this method.
     *
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each and every match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0 || text == null || comparator == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        int m = pattern.length();
        int n = text.length();
        List<Integer> occur = new ArrayList<>();
        if (m > n) {
            return occur;
        }
        long hashingPattern = 0;
        long pow = 1;
        for (int k = m - 1; k >= 0; k--) {
            hashingPattern += pattern.charAt(k) * pow;
            pow *= BASE;
        }
        long hashingText = 0;
        pow = 1;
        for (int k = m - 1; k >= 0; k--) {
            hashingText += text.charAt(k) * pow;
            pow *= BASE;
        }
        int i = 0;
        pow /= BASE;
        while (i <= n - m) {
            if (hashingPattern == hashingText) {
                int j = 0;
                while (j < m && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                    j++;
                }
                if (j == m) {
                    occur.add(i);
                }
            }
            if (i + m < n) {
                hashingText = (hashingText - text.charAt(i) * pow) * BASE + text.charAt(i + m);
            }
            i++;
        }
        return occur;
    }

    /**
     * This method is OPTIONAL and for extra credit only.
     * <p>
     * The Galil Rule is an addition to Boyer Moore that optimizes how we shift the pattern
     * after a full match. Please read Extra Credit: Galil Rule section in the homework pdf for details.
     * <p>
     * Make sure to implement the buildLastTable() method and buildFailureTable() method
     * before implementing this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each and every match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMooreGalilRule(CharSequence pattern,
                                                    CharSequence text,
                                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0 || text == null || comparator == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        int m = pattern.length();
        int n = text.length();
        List<Integer> matches = new ArrayList<>();

        if (m > n) {
            return matches;
        }

        Map<Character, Integer> lastTable = buildLastTable(pattern);
        int[] failure = buildFailureTable(pattern, comparator);

        int safe = 0;
        int period = m;
        if (m > 1 && failure[m - 1] > 0) {
            safe = failure[m - 1];
            period = m - safe;
        }

        int i = 0;
        int galilEnd = 0;

        while (i <= n - m) {
            int j = m - 1;

            if (i < galilEnd) { //skip
                j = safe - 1;
            }

            while (j >= 0 && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j--;
            }

            if (j < 0) {
                matches.add(i);

                if (safe > 0) {
                    galilEnd = i + period + safe;
                    i += period;
                } else {
                    i++;
                }
            } else {
                galilEnd = 0;

                char c = text.charAt(i + j);
                int lastIdx = lastTable.getOrDefault(c, -1);
                int shift = (lastIdx < j) ? (j - lastIdx) : 1;
                i += shift;
            }
        }

        return matches;
    }
}
