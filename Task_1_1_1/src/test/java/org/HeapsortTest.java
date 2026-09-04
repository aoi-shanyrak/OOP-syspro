package org;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Heapsort class
 */
class HeapsortTest {

    @Test
    void sort_nullArray_doesNothing() {
        int[] arr = null;
        Heapsort.sort(arr);
        assertNull(arr);
    }

    @Test
    void sort_twoElementsUnsorted_sorted() {
        int[] arr = {2, 1};
        Heapsort.sort(arr);
        assertArrayEquals(new int[]{1, 2}, arr);
    }

    @Test
    void sort_alreadySortedArray_unchanged() {
        int[] arr = {-1, 2, 3, 4, 5};
        Heapsort.sort(arr);
        assertArrayEquals(new int[]{-1, 2, 3, 4, 5}, arr);
    }

    @Test
    void sort_reverseSortedArray_sorted() {
        int[] arr = {5, 4, 3, 2, 1};
        Heapsort.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void sort_arrayWithDuplicates_sorted() {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6};
        Heapsort.sort(arr);
        assertArrayEquals(new int[]{1, 1, 2, 3, 4, 5, 6, 9}, arr);
    }

    @Test
    void heapify_sizeZero_doesNothing() {
        int[] arr = {5, 4, 3, 2, 1};
        Heapsort.heapify(arr, 0, 0);
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, arr);
    }

    @Test
    void heapify_parentLargerThanChildren_unchanged() {
        int[] arr = {10, 5, 3};
        Heapsort.heapify(arr, 3, 0);
        assertArrayEquals(new int[]{10, 5, 3}, arr);
    }

    @Test
    void heapify_leftChildLarger_swapsWithLeft() {
        int[] arr = {4, 10, 3};
        Heapsort.heapify(arr, 3, 0);
        assertArrayEquals(new int[]{10, 4, 3}, arr);
    }

    @Test
    void heapify_rightChildLarger_swapsWithRight() {
        int[] arr = {4, 3, 10};
        Heapsort.heapify(arr, 3, 0);
        assertArrayEquals(new int[]{10, 3, 4}, arr);
    }

    @Test
    void heapify_recursiveCase_needsMultipleSwaps() {
        int[] arr = {1, 10, 8, 9, 7};
        Heapsort.heapify(arr, 5, 0);
        assertArrayEquals(new int[]{10, 9, 8, 1, 7}, arr);
    }

    @Test
    void swap_swapsTwoElements() {
        int[] arr = {1, 2, 3};
        Heapsort.swap(arr, 0, 2);
        assertArrayEquals(new int[]{3, 2, 1}, arr);
    }

}