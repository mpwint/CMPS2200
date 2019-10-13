/**
 * @author Mary Pwint
 * CMPS-2200 HW-4
 * Unit tests for class BinarySearchTree.java
 */
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class BinarySearchTreeTest {

    private BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();

    @BeforeEach
    void setUp() {
        tree.insert(5);
        tree.insert(2);
        tree.insert(8);
    }

    @AfterEach
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
    void testContains() {
        Assertions.assertTrue(tree.contains(5));
    }

    @Test
    void testInsert() {
        tree.insert(3);
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        tree.printInOrder();
        Assertions.assertTrue(tree.contains(3));
        Assertions.assertEquals("2 3 5 8 ", outContent.toString());
    }

    @Test
    void testRemoveLeaf() {
        tree.remove(2);
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        tree.printInOrder();
        Assertions.assertTrue(tree.contains(5));
        Assertions.assertTrue(tree.contains(8));
        Assertions.assertFalse(tree.contains(2));
        Assertions.assertEquals("5 8 ", outContent.toString());
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
}