/**
 * A new KMP instance is created for every substring search performed. Both the
 * pattern and the text are passed to the constructor and the search method. You
 * could, for example, use the constructor to create the match table and the
 * search method to perform the search itself.
 */
public class KMP {
    private static boolean bruteForce = true;
     /**
     * Creates a new KMP instance with the specified pattern and text.
     *
     * @param pattern the pattern to search for
     * @param text    the text to search in
     */
    public KMP(String pattern, String text) {}
    /**
     * Computes the match table for the KMP search algorithm.
     *
     * @param pattern the pattern to build the table for
     * @return the computed match table
     */
    public static int[] buildMatchTable(String pattern) {
        if (pattern.length() == 0) {return null;}
        else if (pattern.length() == 1) {return new int[] { -1 };}
        int m = pattern.length(); // Length of the pattern
        int[] lps = new int[m]; // Array to store the Longest Proper Prefix which is also Suffix
        int len = 0; // Length of the previous longest prefix suffix
        int i = 1;
        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len > 0) {
                    // Mismatch occurred, update len based on the lps array
                    len = lps[len - 1];
                } else {
                    // No proper prefix suffix found, set lps value to 0 and move to the next character in the pattern
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }
    /**
     * Perform KMP substring search on the given text with the given pattern.
     * 
     * This should return the starting index of the first substring match if it
     * exists, or -1 if it doesn't.
     */
    public static int search(String pattern, String text) {
        // TODO fill this in.
        int m = pattern.length(); // Length of the pattern
        int n = text.length(); // Length of the text
        if (m == 0 || n == 0 || m > n) {
            return -1; // Pattern or text is empty, or pattern is longer than text
        }
        int[] mtable = buildMatchTable(pattern); // Compute the Longest Proper Prefix which is also Suffix array
        int i = 0; // Index for pattern
        int j = 0; // Index for text
        while (j < n) {
            if (pattern.charAt(i) == text.charAt(j)) {
                i++;
                j++;
                if (i == m) {
                    // Match found, return the starting index of the match
                    return j - i;
                }
            } else {
                if (i > 0) {
                    // Mismatch occurred, update i based on the lps array
                    i = mtable[i - 1];
                } else {
                    // Mismatch occurred at the beginning of the pattern, move to the next character in the text
                    j++;
                }
            }
        }
        // Pattern not found
        return -1;
    }
}
