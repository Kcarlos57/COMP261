import java.util.*;

public class BoyerMoore {

    public static int search(String pattern, String text) {
        int m = pattern.length();
        int n = text.length();
        
        if (m == 0 || n == 0 || m > n) {
            return -1;
        }
        
        int[] last = buildLast(pattern);
        
        int i = m - 1; // index for pattern
        int j = m - 1; // index for text
        
        while (j < n) {
            if (pattern.charAt(i) == text.charAt(j)) {
                if (i == 0) {
                    // Match found
                    return j;
                }
                i--;
                j--;
            } else {
                int lastOccurrence = last[text.charAt(j)];
                i = m - 1;
                j = j + m - Math.min(i, 1 + lastOccurrence);
            }
        }
        
        // Pattern not found
        return -1;
    }
    
    private static int[] buildLast(String pattern) {
        int[] last = new int[256]; // Assuming ASCII character set
        
        for (int i = 0; i < 256; i++) {
            last[i] = -1; // Initialize all occurrences as -1
        }
        
        int m = pattern.length();
        
        for (int i = 0; i < m; i++) {
            last[pattern.charAt(i)] = i; // Update the last occurrence of the character
        }
        
        return last;
    }
}
