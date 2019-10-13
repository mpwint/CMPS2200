/**
 * @author Mary Pwint
 * CMPS-2200 HW-4
 * Unit tests for class AVLTree.java
 */
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class AVLTreeTest {
    private AVLTree<Integer> tree = new AVLTree<>();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        tree.insert(5);
        tree.insert(2);
        tree.insert(8);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        tree = null;
    }

    @Test
    void testPrintInOrder() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        tree.printInOrder();
        Assertions.assertEquals("2 5 8 ", outContent.toString());
    }

    @Test
    void testInsert() {
        tree.insert(3);
        Assertions.assertTrue(tree.contains(3));
    }

    @Test
    void testFindImbalance() {
        Assertions.assertNull(tree.findImbalance());
    }

    @Test
    void testSingleRotateWithLeftChild() {
        tree.insert(1);
        tree.insert(0);
        Assertions.assertNull(tree.findImbalance());
    }

    @Test
    void testSingleRotateWithRightChild() {
        tree.insert(10);
        tree.insert(12);
        Assertions.assertNull(tree.findImbalance());
    }

    @Test
    void testDoubleRotateWithRightChild() {
        tree.insert(3);
        tree.insert(4);
        Assertions.assertNull(tree.findImbalance());
    }

    @Test
    void testDoubleRotateWithLeftChild() {
        tree.insert(7);
        tree.insert(6);
        Assertions.assertNull(tree.findImbalance());
    }

    @Test
    void testRemoveLeaf() {
        tree.remove(2);
        Assertions.assertTrue(tree.contains(5));
        Assertions.assertTrue(tree.contains(8));
        Assertions.assertFalse(tree.contains(2));
    }

    @Test
    void testRemoveNodeWithLeftChild() {
        tree.insert(1);
        tree.remove(2);
        Assertions.assertFalse(tree.contains(2));
        Assertions.assertTrue(tree.contains(1));
    }

    @Test
    void testRemoveNodeWithRightChild() {
        tree.insert(10);
        tree.remove(8);
        Assertions.assertFalse(tree.contains(8));
        Assertions.assertTrue(tree.contains(10));
    }

    @Test
    void testRemoveNodeWith2Children() {
        tree.insert(0);
        tree.insert(1);
        tree.remove(2);
        Assertions.assertFalse(tree.contains(2));
        Assertions.assertTrue(tree.contains(0));
        Assertions.assertTrue(tree.contains(1));
    }

    @Test
    void testFindMin() {
        Assertions.assertEquals(2, tree.findMin());
    }
}