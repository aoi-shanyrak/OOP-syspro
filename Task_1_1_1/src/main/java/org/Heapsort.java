package org;

/**
 * provides an inplace implementation of heapsort algorithm
 */
public class Heapsort {
    /**
     * sorts given array in order
     * @param arr array of integers
     */
    public static void sort(int[] arr) {
        if (arr == null ||arr.length < 2) return;

        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        for (int i = n - 1; i >= 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    /**
     * restores the max-heap property for subtree at index
     * @param arr  array
     * @param size current size of array
     * @param idx root index of subtree to heapify
     */
    public static void heapify(int[] arr, int size, int idx) {
        int parent = idx;
        int left = 2 * idx + 1;
        int right = 2 * idx + 2;

        if (left < size && arr[left] > arr[parent]) {
            parent = left;
        }
        if (right < size && arr[right] > arr[parent]) {
            parent = right;
        }

        if (parent != idx) {
            swap(arr, idx, parent);
            heapify(arr, size, parent);
        }
    }

    /**
     * side method to swap elements in array
     * @param arr array
     * @param i   first index
     * @param j   second index
     */
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}