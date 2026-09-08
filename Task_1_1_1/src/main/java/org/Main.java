package org;

import java.util.Arrays;

/**
 * demo run
 */
public class Main {
    /**
     * main function
     * @param args not used
     */
    public static void main(String[] args) {
        int[] arr = {5, 3, 2, 45, 78, 2};
        System.out.println("then: " + Arrays.toString(arr));
        Heapsort.sort(arr);
        System.out.println("now:  " + Arrays.toString(arr));
    }
}