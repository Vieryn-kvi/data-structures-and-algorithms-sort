package ru.vieryn;

import java.util.Arrays;

public class Main {
    private static void bubbleSort(int[] arr) {
        for (int lastUnsortedIndex = arr.length - 1; lastUnsortedIndex > 0; lastUnsortedIndex--) {
            for (int i = 0; i < lastUnsortedIndex; i++) {
                if (arr[i] > arr[i+1]) {
                    swap(arr, i, i + 1);
                }
            }
        }
    }
    public static void main(String[] args) {
        int[] sourceArray = {3, 15, -15, 35, -22, 55, 0};
        int[] bubbleArray = Arrays.copyOf(sourceArray, sourceArray.length);
        bubbleSort(bubbleArray);
        System.out.println(Arrays.toString(bubbleArray));
    }
    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
