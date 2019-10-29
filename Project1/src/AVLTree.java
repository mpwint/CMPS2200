import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * @author Mary Pwint
 * CMPS-2200 HW-4
 * Class AVLTree is a basic implementaion of Adelson-Velskii and Landis' Balanced Binary Search Tree.
 */

public class AVLTree<E extends Comparable<? super E>> {
    private Node root;
    private int size;

    /**
     * Construct an empty AVLTree.
     */
    public AVLTree() {
        // not necessary, but explicit stating root starts at null
        this.root = null;
        this.size = 0;
    }

    /**
     * Finds the node that causes the imbalance of the tree
     * Method used to unit-test if the tree is balanced
     *
     * @return the leaf node that causes the imbalance of the tree, null if the tree is balanced
     */
    Node findImbalance() {
        return findImbalance(this.root);
    }

    /**
     * Insert the element into this AVLTree.
     *
     * @param element the element to insert into the tree. Duplicates are
     *                allowed
     */
    public void insert(E element) {
        this.root = insert(this.root, element);
        this.size++;
        //balance if the resulting tree is imbalanced
        if (Math.abs(height(this.root.left) - height(this.root.right)) > 1) {
            this.root = balance(this.root);
        }
        this.root.height = this.height(this.root);
    }

    /**
     * Remove the element from this AVLTree.
     *
     * @param element the element to remove
     */
    public void remove(E element) {
        this.root = remove(this.root, element);
        this.size--;
        //balance if the resulting tree is imbalanced
        if (Math.abs(height(this.root.left) - height(this.root.right)) > 1) {
            this.root = balance(this.root);
        }
        assert this.root != null;
        this.root.height = this.height(this.root);
    }

    /**
     * Check if this tree contains the element.
     *
     * @return true if this tree contains the element, false otherwise
     */
    public boolean contains(E element) {
        return contains(this.root, element);
    }

    /**
     * Return the minimum elemnent in this tree.
     *
     * @return the mininum element in this tree
     */
    public E findMin() {
        return findMin(this.root);
    }

    /**
     * A private helper method for insertion.
     * By taking a Node as a parameter, we can write this method
     * recursively, continuing to call insert on subtrees until the element
     * can be inserted.
     *
     * @param node    the root of some subtree of this AVLTree
     * @param element the element to insert into this subtree
     * @return the tree with inserted node
     */
    private Node insert(Node node, E element) {
        if (node == null) {
            return new Node(element);
        }
        // if element is less than the value contained by node...
        if (element.compareTo(node.element) < 0) {
            // insert element into the left subtree
            node.left = insert(node.left, element);
        } else {
            // insert element into the right subtree
            node.right = insert(node.right, element);
        }
        return node;
    }

    /**
     * A private helper method for removal.
     * By taking a Node as a parameter, we can write this method
     * recursively, continuing to call remove on subtrees until the element
     * is removed.
     *
     * @param node    the root of some subtree of this AVLTree
     * @param element the element to remove from this subtree
     */
    private Node remove(Node node, E element) {
        //if subtree is empty, return null
        if (node == null) {
            return null;
        }
        //if subtree does not contain the element to remove, return the original subtree
        if (!this.contains(element)) {
            return node;
        }
        //check left subtree or right subtree
        if (element.compareTo(node.element) > 0) {
            node.right = remove(node.right, element);
        } else if (element.compareTo(node.element) < 0) {
            node.left = remove(node.left, element);
        }
        // 3 cases of removal: leaf, node with one child, node with two children
        else {
            if (node.left == null && node.right == null) {
                node = null;
            } else if (node.left != null && node.right == null) {
                E temp = findMax(node.left);
                node.element = temp;
                node.left = remove(node.left, temp);
            } else {
                E temp = findMin(node.right);
                node.element = temp;
                node.right = remove(node.right, temp);
            }
        }
        return node;
    }

    /**
     * As for insert and remove, a private helper is used for a recursive
     * implementation.
     *
     * @param element the element to search for
     * @param node    the root of the subtree to search in
     * @return true if this subtree contains the element, false otherwise
     */
    private boolean contains(Node node, E element) {
        if (node == null) {
            return false;
        }
        if (element.compareTo(node.element) == 0) {
            return true;
        }
        if (element.compareTo(node.element) < 0) {
            return contains(node.left, element);
        } else {
            return contains(node.right, element);
        }
    }

    /**
     * Return the minimum element in the subtree rooted at node
     *
     * @param node the root of the subtree
     * @return the minimum element in this subtree
     */
    private E findMin(Node node) {
        //base case
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node.element;
    }

    /**
     * Return the maximum element in the subtree rooted at node
     *
     * @param node the root of the subtree
     * @return the maximum element in this subtree
     */
    private E findMax(Node node) {
        //base case
        if (node == null) {
            return null;
        }
        while (node.right != null) {
            node = node.right;
        }
        return node.element;
    }

    /**
     * Gets the difference in height between left and right subtrees
     *
     * @param node the root of the subtree
     * @return the difference in height between left and right subtrees
     * if getBalance >= +2, left subtree is imbalanced;
     * if getBalance <= -2, right subtree is imbalanced;
     */
    private int getBalance(Node node) {
        if (node == null) {
            return -1;
        } else {
            return height(node.left) - height(node.right);
        }
    }

    /**
     * Balance the subtree rooted at this node.
     *
     * @param node the root of the subtree to balance
     * @return the new root of the balanced subtree
     */
    private Node balance(Node node) {
        // Determine which type of rotation should be used and call on it.
        // NOTE: Don't forget to update the heights of the nodes after you
        // manipulate the subtree. Implementor's perogative on best way to
        // maintain correct heights.

        Node imbalanced_leaf = findImbalance(node);
        return (imbalanced_leaf == null ? node : balance(node, imbalanced_leaf.element));
    }

    /**
     * Finds the leaf that is causing the imbalance
     * Helper method for balance and findImbalance for unit testing
     *
     * @param node the root of the subtree in which to find the imbalance
     * @return the leaf node that is causing the imbalance
     */
    private Node findImbalance(Node node) {
        if (node.left == null && node.right == null) {
            return node;
        }
        if (node.left != null && Math.abs(getBalance(node.left)) != 0) {
            findImbalance(node.left);
        }
        if (node.right != null && Math.abs(height(node.right.left) - height(node.right.right)) != 0) {
            findImbalance(node.right);
        }
        return null;
    }

    /**
     * Balance the subtree rooted at this node
     *
     * @param node  the root of the subtree to balance
     * @param value the leaf element causing the imbalance
     * @return calls the appropriate rotation method;return the new root of the balanced subtree
     */
    private Node balance(Node node, E value) {
        if (getBalance(node) > 1 && value.compareTo(node.left.element) < 0) {
            return singleRotateWithLeftChild(node);
        } else if (getBalance(node) > 1 && value.compareTo(node.left.element) > 0) {
            return doubleRotateWithRightChild(node);
        } else if (getBalance(node) < -1 && value.compareTo(node.right.element) < 0) {
            return doubleRotateWithLeftChild(node);
        } else if (getBalance(node) < -1 && value.compareTo(node.right.element) > 0) {
            return singleRotateWithRightChild(node);
        } else {
            return node;    //tree is already balanced
        }
    }

    /**
     * Perform a single rotation for left outside case.
     *
     * @param node the root of the subtree to rotate
     * @return the new root of this subtree
     */
    private Node singleRotateWithLeftChild(Node node) {
        Node original_root = node;
        Node orphan = node.left.right;
        node = original_root.left;
        node.right = original_root;
        original_root.left = orphan;
        node.height = (int) Math.log(this.size);
        return node;
    }

    /**
     * Perform a single rotation for right outside case.
     *
     * @param node the root of the subtree to rotate
     * @return the new root of this subtree
     */
    private Node singleRotateWithRightChild(Node node) {
        Node original_root = node;
        Node orphan = node.right.left;
        node = original_root.right;
        node.left = original_root;
        original_root.right = orphan;
        node.height = (int) Math.log(this.size);
        return node;
    }

    /**
     * Perform a double rotation for left inside case.
     *
     * @param node the root of the subtree to rotate
     * @return the new root of this subtree
     */
    private Node doubleRotateWithLeftChild(Node node) {
        //STUBBED
        Node original_root = node;
        Node original_right = node.right;
        Node left_orphan = original_right.left.left;
        Node right_orphan = original_right.left.right;
        node = original_right.left;
        node.left = original_root;
        node.right = original_right;
        node.right.left = right_orphan;
        original_root.right = left_orphan;
        node.height = (int) Math.log(this.size);
        return node;
    }

    /**
     * Perform a double rotation for right inside case.
     *
     * @param node the root of the subtree to rotate
     * @return the new root of this subtree
     */
    private Node doubleRotateWithRightChild(Node node) {
        Node original_root = node;
        Node original_left = node.left;
        Node left_orphan = original_left.right.left;
        Node right_orphan = original_left.right.right;
        node = original_left.right;
        node.left = original_left;
        node.right = original_root;
        node.left.right = left_orphan;
        original_root.left = right_orphan;
        node.height = (int) Math.log(this.size);
        return node;
    }

    /**
     * Private helper method to calculate the height of a node. A node's
     * height is the larger of its left and right subtree's heights plus
     * one. To make this calculation consistent and easy, we define
     * height of an empty subtree is -1.
     *
     * @param node the node to calculate the height of
     * @return the height of the tree as determined by the heights of its subtrees
     */
    private int height(Node node) {
        // if the left child is null, its height is -1, otherwise, retrieve
        // its height
        if (node == null) {
            return -1;
        }
        int leftHeight = (node.left == null ? -1 : node.left.height);
        // same
        int rightHeight = (node.right == null ? -1 : node.right.height);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * prints the tree in order for testing/visualization
     */
    void printInOrder() {
        printInOrder(this.root);
    }

    /* Given a binary tree, print its nodes in inorder*/
    void printInOrder(Node node) {
        if (node == null)
            return;

        /* first recur on left child */
        printInOrder(node.left);

        /* then print the data of node */
        System.out.print(node.element + " ");

        /* now recur on right child */
        printInOrder(node.right);
    }

    private class Node {
        // since this is a private inner class, and the outer AVLTree class
        // will need to freely modify the connections and update the height
        // of its nodes, the following three variables are not private.
        Node left;
        Node right;
        int height = -1;
        E element;

        /**
         * Construct an AVLTreeNode. At instantiation, each node has no
         * children and therefore a height of 0.
         *
         * @param element the element that this node contains
         */
        public Node(E element) {
            this.left = null;
            this.right = null;
            this.height = 0;
            this.element = element;
        }
    }

    /**
     * Driver method for testing the performance of this AVL Tree
     */
    public static void main(String[] args) throws IOException {
        for (int iter=1; iter <= 5; iter++) {
            System.out.println("Iteration: " + iter);
            AVLTree<Integer> newTree = new AVLTree<>();
            String filename = "AVLTreeRuntime/AVLTreeRuntime" + iter + ".csv";
            PrintWriter output = new PrintWriter(new FileWriter(filename));
            output.println("Input size,Runtime(ns)");
            for (int i = 1; i <= 1000000; i = i * 10) {
                for (int j = i / 10; j < i; j++) {
                    newTree.insert(new Random().nextInt(i * 10));
                }
                final long startTime = System.nanoTime();
                newTree.contains(new Random().nextInt(i * 10));
                final long endTime = System.nanoTime();
                final long elaspedTime = endTime - startTime;
                System.out.println("Performance of 'contains' method for the AVLTree of size " + i + " = " + (elaspedTime) + " ns");
                output.println(i + "," + elaspedTime);
            }
            output.close();
            System.out.println();
        }
    }
}