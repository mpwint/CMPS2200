/**
 * @author Mary Pwint
 * CMPS-2200 Project1 Part2
 * class MaxHeap is a basic implementation of a max heap
 */

import java.util.Arrays;

public class MaxHeap {
    private int[] heap;
    private int size;

    /**
     * Construct an empty MaxHeap
     * @param capacity max size of the MaxHeap
     */
    public MaxHeap(int capacity) {
        this.size = 0;
        this.heap = new int[capacity + 1];
        heap[0] = Integer.MIN_VALUE;    // placeholder(-inf) for unused index 0
    }

    /**
     * @param index the index of the element
     * @return the index of the left child of the element
     */
    private int leftChild(int index) {
        return (2 * index);
    }

    /**
     * @param index the index of the element
     * @return the index of the right child of the element
     */
    private int rightChild(int index) {
        return (2 * index) + 1;
    }

    /**
     * @param index the index of the element
     * @return true if the element is a leaf; false otherwise
     */
    private boolean isLeaf(int index) {
        return index >= (size / 2) && index <= size;
    }

    /**
     * Swaps elements at indices i1 and i2
     * @param i1 index of the first element to be swapped
     * @param i2 index of the second element to be swapped
     */
    private void swap(int i1, int i2) {
        int temp;
        temp = heap[i1];
        heap[i1] = heap[i2];
        heap[i2] = temp;
    }

    /**
     * Floats the element at index until the MaxHeap property is satisfied
     * @param index the index of the element
     */
    private void maxHeapify(int index) {
        if (isLeaf(index))
            return;

        if (heap[index] < heap[leftChild(index)] || heap[index] < heap[rightChild(index)]) {
            if (heap[leftChild(index)] > heap[rightChild(index)]) {
                swap(index, leftChild(index));
                maxHeapify(leftChild(index));
            }
            else {
                swap(index, rightChild(index));
                maxHeapify(rightChild(index));
            }
        }
    }

    private void heapsort() {
        for (int i=size/2; i>=1; i--) {
            maxHeapify(i);
        }
        for (int i=size; i>=2; i--) {
            swap(1,i);
            size--;
            maxHeapify(1);
        }
    }

    public static void main(String[] args) {
        MaxHeap maxHeap = new MaxHeap(6);
        int[] arr = {12, 9, 7, 15, 8, 4};
        System.arraycopy(arr,0, maxHeap.heap, 1, arr.length);
        maxHeap.size = arr.length;
        maxHeap.heapsort();
        int[] sorted_arr = maxHeap.heap;
        System.out.print(Arrays.toString(sorted_arr));
    }
}