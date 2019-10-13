/**
 * @author Mary Pwint
 * CMPS-2200 Project1 Part2
 * Unit tests for sorting algorithms in Sorter.java
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SorterTest {

    private Sorter sorter = new Sorter();

    @Test
    void mergesortTest() {
        int[] arr = {12, 9, 7, 15, 8, 4};
        int[] sorted_array = {4, 7, 8, 9, 12, 15};
        assertArrayEquals(sorted_array, sorter.mergesort(arr));
    }

    @Test
    void quicksortTest() {
        int[] arr = {12, 9, 7, 15, 8, 4};
        int[] sorted_array = {4, 7, 8, 9, 12, 15};
        assertArrayEquals(sorted_array, sorter.quicksort(arr));
    }

    @Test
    void heapsortTest() {
        int[] arr = {12, 9, 7, 15, 8, 4};
        int[] sorted_array = {4, 7, 8, 9, 12, 15};
        assertArrayEquals(sorted_array, sorter.heapsort(arr));
    }
}