/**
 * @author Mary Pwint
 * CMPS-2200 Project1 Part2
 * class Sorter is a basic implementation of sorting algorithms - mergesort, quicksort, and heapsort
 */
public class Sorter {
    /**
     * Mergesort
     *
     * @param arr array to be sorted
     * @return sorted array
     */
    public int[] mergesort(int[] arr) {
        return mergesort(arr, new int[arr.length], 0, arr.length - 1);
    }

    private int[] mergesort(int[] arr, int[] temp, int left, int right) {
        if (left >= right) {
            return arr;
        }
        int mid = (left + right) / 2;
        mergesort(arr, temp, left, mid);
        mergesort(arr, temp, mid + 1, right);
        merge(arr, temp, left, right);
        return temp;
    }

    private void merge(int[] arr, int[] temp, int leftStart, int rightEnd) {
        int size = rightEnd - leftStart + 1;
        int leftEnd = (leftStart + rightEnd) / 2;
        int rightStart = leftEnd + 1;
        int leftPointer = leftStart;
        int rightPointer = rightStart;
        int currIndex = leftStart;

        while (leftPointer <= leftEnd && rightPointer <= rightEnd) {
            // copy over the smaller element
            if (arr[leftPointer] <= arr[rightPointer]) {
                temp[currIndex] = arr[leftPointer];
                leftPointer++;
            } else {
                temp[currIndex] = arr[rightPointer];
                rightPointer++;
            }
            currIndex++;
        }
        // copy over the rest of the elements
        System.arraycopy(arr, leftPointer, temp, currIndex, (leftEnd - leftPointer + 1));
        System.arraycopy(arr, rightPointer, temp, currIndex, (rightEnd - rightPointer + 1));
        System.arraycopy(temp, leftStart, arr, leftStart, size);
    }

    /**
     * Quicksort
     *
     * @param arr array to be sorted
     * @return sorted array
     */
    public int[] quicksort(int[] arr) {
        quicksort(arr, 0, arr.length - 1);
        return arr;
    }

    private void quicksort(int[] arr, int leftStart, int rightEnd) {
        if (leftStart >= rightEnd) {
            return;
        }
        int pivot = arr[(leftStart + rightEnd) / 2];
        int partitionPoint = partition(arr, leftStart, rightEnd, pivot);
        quicksort(arr, leftStart, partitionPoint - 1);
        quicksort(arr, partitionPoint, rightEnd);
    }

    private int partition(int[] arr, int leftPointer, int rightPointer, int pivot) {
        while (leftPointer <= rightPointer) {
            while (arr[leftPointer] < pivot) {
                leftPointer++;
            }
            while (arr[rightPointer] > pivot) {
                rightPointer--;
            }
            if (leftPointer <= rightPointer) {
                // swap left and right elements
                int temp = arr[leftPointer];
                arr[leftPointer] = arr[rightPointer];
                arr[rightPointer] = temp;
                leftPointer++;
                rightPointer--;
            }
        }
        return leftPointer;
    }

    public int[] heapsort(int[] arr) {

        // Build max heap
        for (int i = (arr.length / 2) - 1; i >= 0; i--) {
            maxHeapify(arr, arr.length, i);
        }

        for (int i = arr.length - 1; i >= 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // call maxHeapify on the reduced heap
            maxHeapify(arr, i, 0);
        }

        return arr;
    }

    private void maxHeapify(int[] arr, int n, int root) {
        int largest = root; // Initialize largest as root
        int left = 2 * root + 1;
        int right = 2 * root + 2;

        // If left child is larger than root
        if (left < n && arr[left] > arr[largest])
            largest = left;

        // If right child is larger than largest so far
        if (right < n && arr[right] > arr[largest])
            largest = right;

        // If largest is not root
        if (largest != root) {
            int swap = arr[root];
            arr[root] = arr[largest];
            arr[largest] = swap;
            // Recursively heapify the affected sub-tree
            maxHeapify(arr, n, largest);
        }
    }
}