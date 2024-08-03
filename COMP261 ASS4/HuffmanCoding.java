/**
 * A new instance of HuffmanCoding is created for every run. The constructor is
 * passed the full text to be encoded or decoded, so this is a good place to
 * construct the tree. You should store this tree in a field and then use it in
 * the encode and decode methods.
 */

import java.util.*;
public class HuffmanCoding {
    private HuffmanTree huffmanTree;
    private HashMap<Character, Integer> frequency;
    private HashMap<Character, String> huffmanCodes;
    /**
     * This would be a good place to compute and store the tree.
     */
    public HuffmanCoding(String text) {
        // TODO fill this in.
        buildFrequencyMap(text);
        buildHuffmanTree();
        buildHuffmanCodes();
    }
    /**
     * Take an input string, text, and encode it with the stored tree. Should
     * return the encoded text as a binary string, that is, a string containing
     * only 1 and 0.
     */
    public String encode(String text) {
        // TODO fill this in.
        StringBuilder encoded = new StringBuilder();
        for (char c : text.toCharArray()) {
            String code = huffmanCodes.get(c);
            if (code != null) {
                encoded.append(code);
            }
        }
        return encoded.toString();
    }
    /**
     * Take encoded input as a binary string, decode it using the stored tree,
     * and return the decoded text as a text string.
     */
    public String decode(String encoded) {
        // TODO fill this in.
        StringBuilder decoded = new StringBuilder();
        char[] charArray = encoded.toCharArray();
        int index = 0;
        HuffmanNode root = huffmanTree.getRoot();
        HuffmanNode nodePointer = root;
        while (index < charArray.length) {
            char charPointer = charArray[index];
            if (charPointer == '0') {
                nodePointer = nodePointer.getLeftNode();
            } else if (charPointer == '1') {
                nodePointer = nodePointer.getRightNode();
            }
            if (nodePointer.isLeaf()) {
                decoded.append(nodePointer.getCharacter());
                nodePointer = root;
            }
            index++;
        }
        //System.out.println(decoded.toString());
        return decoded.toString();
        }
    private void buildFrequencyMap(String text) {
        frequency = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }
    }
    private void buildHuffmanTree() {
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {
            HuffmanNode node = new HuffmanNode(entry.getKey(), entry.getValue());
            queue.offer(node);
        }
        while (queue.size() > 1) {
            HuffmanNode left = queue.poll();
            HuffmanNode right = queue.poll();
            HuffmanNode parent = new HuffmanNode('\0', left.getFrequency() + right.getFrequency());
            parent.setLeftNode(left);
            parent.setRightNode(right);
            queue.offer(parent);
        }
        huffmanTree = new HuffmanTree(queue.poll()); 
    }
    private void buildHuffmanCodes() {
        huffmanCodes = new HashMap<>();
        RecursiveCodes(huffmanTree.getRoot(), "");
    }
    private void RecursiveCodes(HuffmanNode node, String code) {
        if (node.isLeaf()) {
            char character = node.getCharacter();
            huffmanCodes.put(character, code); 
        } else {
            RecursiveCodes(node.getLeftNode(), code + '0');
            RecursiveCodes(node.getRightNode(), code + '1');
        }
    }
    /**
     * The getInformation method is here for your convenience, you don't need to
     * fill it in if you don't wan to. It is called on every run and its return
     * value is displayed on-screen. You could use this, for example, to print
     * out the encoding tree.
     */
    public String getInformation() {
        StringBuilder output = new StringBuilder("\nThe encoding of each char:\n");
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            output.append(entry.getKey() + "=" + entry.getValue() + "   ");
        }
        return output.toString();
    }
    private class HuffmanTree {
        private HuffmanNode root;
        public HuffmanTree(HuffmanNode root) {
            this.root = root;
        }
        public HuffmanNode getRoot() {
            return root;
        }
    }
    private class HuffmanNode implements Comparable<HuffmanNode> {
        private final char character;
        private String code;
        private int frequency;
        private HuffmanNode leftNode;
        private HuffmanNode rightNode;
        public HuffmanNode(char character, int frequency) {
            this.character = character;
            this.frequency = frequency;
        }
        public boolean isLeaf() {//IE THE END OF THE BRANCH
             return leftNode == null && rightNode == null;
        }
        @Override
        public int compareTo(HuffmanNode other) {
            return this.frequency - other.frequency;
        }
        public char getCharacter() {
            return character;
        }
        public String getCode() {
            return code;
        }
        public void setCode(String code) {
            this.code = code;
        }
        public int getFrequency() {
            return frequency;
        }
        public HuffmanNode getLeftNode() {
            return leftNode;
        }
        public void setLeftNode(HuffmanNode leftNode) {
            this.leftNode = leftNode;
        }
        public HuffmanNode getRightNode() {
            return rightNode;
        }
        public void setRightNode(HuffmanNode rightNode) {
            this.rightNode = rightNode;
        }
    }
}


