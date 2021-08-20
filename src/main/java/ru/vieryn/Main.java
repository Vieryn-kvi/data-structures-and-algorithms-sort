package ru.vieryn;

import java.util.Arrays;

public class Main {
    private static void bubbleSort(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j+1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }
    public static void main(String[] args) {
        int[] sourceArray = new int[] {3, 15, -15, 35, -22, 55, 0};
        int[] bubbleArray = Arrays.copyOf(sourceArray, sourceArray.length);
        bubbleSort(bubbleArray);
        printArray(bubbleArray);
    }
    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
    private static void printArray(int[] arr) {
        if (arr == null) {
            System.out.println("null");
            return;
        }
        System.out.print('[');
        for (int i = 0; i < arr.length; i++) {
            if (i != 0) System.out.print(", ");
            System.out.print(arr[i]);
        }
        System.out.println(']');
    }
}
