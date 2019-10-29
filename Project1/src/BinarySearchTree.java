import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * @author Mary Pwint
 * CMPS-2200 Project1 Part1
 * class BinarySearchTree is a basic implementation of a binary search tree
 */

public class BinarySearchTree<E extends Comparable<? super E>> {
    private Node root;
    private int size;

    public void insert(E element) {
        this.root = insert(this.root, element);
        this.size++;
    }

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
     * Remove the element from this BST.
     * @param element the element to remove
     */
    public void remove(E element) {
        this.root = remove(this.root, element);
        this.size--;
    }

    private Node remove(Node node, E element) {
        //if subtree is empty, return null
        if (node == null) {
            return null;
        }
        //if subtree does not contain the element to remove, return the original subtree
        if (!this.contains(node, element)) {
            return node;
        }
        //check left subtree or right subtree
        if (element.compareTo(node.element) > 0) {
            node.right =  remove(node.right, element);
        }
        else if (element.compareTo(node.element) < 0) {
            node.left =  remove(node.left, element);
        }
        // 3 cases of removal: leaf, node with one child, node with two children
        else {
            if (node.left == null && node.right == null) {
                node = null;
            }
            else if (node.left != null && node.right == null) {
                E temp = findMax(node.left);
                node.element = temp;
                node.left =  remove(node.left, temp);
            }
            else {
                E temp = findMin(node.right);
                node.element = temp;
                node.right = remove(node.right,temp);
            }
        }
        return node;
    }

    /**
     * Return the minimum element in the subtree rooted at node
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

    public boolean contains(E element) {
        return contains(this.root, element);
    }

    /**
     * As for insert and remove, a private helper is used for a recursive
     * implementation.
     * @param element the element to search for
     * @param node the root of the subtree to search in
     * @return true if this subtree contains the element, false otherwise
     */
    private boolean contains(Node node, E element) {
        if(node == null) {
            return false;
        }
        if(element.compareTo(node.element) == 0) {
            return true;
        }
        if(element.compareTo(node.element) < 0) {
            return contains(node.left, element);
        } else {
            return contains(node.right, element);
        }
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
        // since this is a private inner class, and the outer BinarySearchTree class
        // will need to freely modify the connections and update the height
        // of its nodes, the following three variables are not private.
        Node left;
        Node right;
        int height = -1;
        E element;

        /**
         * Construct an BSTNode. At instantiation, each node has no
         * children and therefore a height of 0.
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
     * Driver method for testing the performance of this BST Tree
     */
    public static void main(String[] args) throws IOException {
        for (int iter=1; iter <= 5; iter++) {
            System.out.println("Iteration: " + iter);
            BinarySearchTree<Integer> newTree = new BinarySearchTree<>();
            String filename = "BSTRuntime/BSTRuntime" + iter + ".csv";
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
                System.out.println("Performance of 'contains' method for the BST of size " + i + " = " + (elaspedTime) + " ns");
                output.println(i + "," + elaspedTime);
            }
            output.close();
            System.out.println();
        }
    }
}