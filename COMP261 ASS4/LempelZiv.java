
import java.util.*;

public class LempelZiv {
    /**
     * Take uncompressed input as a text string, compress it, and return it as a
     * text string.
     */
    public static String compress(String input) {
        // TODO fill this in.
         StringBuilder compressed = new StringBuilder();
        int pointer = 1;//keeps track of the current position or index while traversing the uncompressed input string
        //The sliding window represents a portion of the uncompressed input that is considered for finding matching sequences. 
        //The window starts from the current position (pointer) and goes back 100 chars as defined by windowSize
        int windowSize = 100;
        compressed.append("[0|0|" + input.charAt(0) + "]");  // Store the first character as a literal
        while (pointer < input.length()) {
            int matchLength = 0;// the length of the matching sequence found within the sliding window
            int prevMatchOffset = 0;//the offset or distance of the previous matching sequence from the current position(pointer)
            int currentMatchOffset;//the offset or distance of the current matching sequence from the current position (pointer)
            while (true) {
                int endOfWindow = pointer - 1;
                int startOfWindow = Math.max(0, pointer - windowSize);
                String window = input.substring(startOfWindow, endOfWindow + 1);
                String currentSequence = input.substring(pointer, pointer + matchLength + 1);
                currentMatchOffset = window.indexOf(currentSequence);  // Find the offset of the current sequence in the window
                if (currentMatchOffset != -1 && pointer + matchLength <= input.length() - 2) {
                    prevMatchOffset = currentMatchOffset;
                    matchLength = matchLength + 1;
                } else {
                    // Append the match information [offset|length|next_character]
                    compressed.append("[" + (matchLength == 0 ? 0 : pointer - (startOfWindow + prevMatchOffset)) 
                                          + "|" + matchLength + "|" + input.charAt(pointer + matchLength) + "]");
                    pointer = pointer + matchLength + 1;
                    break;
                }
            }
        }
        return compressed.toString();
    }
    /**
     * Take compressed input as a text string, decompress it, and return it as a
     * text string.
     */
    public static String decompress(String compressed) {
        // TODO fill this in.
        Scanner scanner = new Scanner(compressed);
        int pointer = 0;//current position of char being processed
        int offset = 0;//offset or distance of the matching sequence from the current position
        int length = 0;//of the matching sequence. # of characters to be copied from the previous matching sequence to the current position in the output
        String character;
        StringBuilder decompressed = new StringBuilder();
        scanner.useDelimiter("\\[|\\]\\[|\\]|\\|");
        while (scanner.hasNext()) {
            offset = scanner.nextInt();
            length = scanner.nextInt();
            character = scanner.next();
            for (int j = 0; j < length; j++) {
                // Append the decompressed character based on the offset
                decompressed.append(decompressed.charAt(pointer - offset));
                pointer++;
            }
            decompressed.append(character);  // Append the next character
            pointer++;
        }
        scanner.close();
        return decompressed.toString();
    }
    /**
     * The getInformation method is here for your convenience, you don't need to
     * fill it in if you don't want to. It is called on every run and its return
     * value is displayed on-screen. You can use this to print out any relevant
     * information from your compression.
     */
    public String getInformation() {
        return "\n This is the Lempel-Ziv part. \n";
    }
}