/*
 * Forest Class
 * This class represents a binary tree where each node has a coin value.
 * It provides methods to:
 * 1. Create a binary tree with nodes.
 * 2. Check if a binary tree satisfies specific conditions.
 * 3. Traverse the tree and compute a maximum sum based on these conditions.
 */
public class Forest {
    // Static variable to keep track of the maximum sum of coins
    public static int maxSum = 0;

    // Nested static class representing a node in the binary tree
    public static class Node {
        Node left;    // Left child node
        Node right;   // Right child node
        int coin;     // Coin value at this node

        // Constructor to initialize a node with a given coin value
        public Node(int coin) {
            this.coin = coin;
            this.left = null;
            this.right = null;
        }
    }

    // Method to create a new node with a given coin value
    public static Node createBT(int coin) {
        return new Node(coin);
    }

    // Method to check if the binary tree rooted at 'root' satisfies specific conditions
    public static boolean isBinaryTreeSatisfied(Node root) {
        if (root == null)
            return false;

        // Check if left and right children meet the satisfaction conditions
        boolean isLeftSatisfied = root.left == null || (root.left.coin < root.coin);
        boolean isRightSatisfied = root.right == null || (root.right.coin >= root.coin);

        return isLeftSatisfied && isRightSatisfied;
    }

    // Method to traverse the binary tree and compute the maximum sum of coins
    public static void travelForest(Node forest) {
        if (forest == null)
            return;

        // If the current node satisfies the conditions
        if (isBinaryTreeSatisfied(forest)) {
            // Add coin values of the current node and its children to maxSum
            if (forest.left != null && forest.right != null) {
                maxSum += forest.coin;
            }
            if (forest.left != null) {
                maxSum += forest.left.coin;
            }
            if (forest.right != null) {
                maxSum += forest.right.coin;
            }
        }

        // Recursively traverse the left and right children
        travelForest(forest.left);
        travelForest(forest.right);
    }

    // Main method to create a sample binary tree and compute the maximum sum
    public static void main(String[] args) {
        // Create a sample binary tree with specific coin values
        Node root = createBT(1);
        root.left = new Node(4);
        root.right = new Node(3);
        root.left.left = new Node(2);
        root.left.right = new Node(4);
        root.right.left = new Node(2);
        root.right.right = new Node(5);
        root.left.left.left = new Node(4);
        root.left.left.right = new Node(6);

        // Traverse the tree and compute the maximum sum
        travelForest(root);
        System.out.println("Maximum sum of magical grove: " + maxSum);
    }
}
