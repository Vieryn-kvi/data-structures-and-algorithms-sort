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
    private static void selectionSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        for (int lastUnsortedIndex = arr.length - 1; lastUnsortedIndex > 0; lastUnsortedIndex--) {
            int maxIndex = 0;
            int maxValue = arr[maxIndex];
            for (int i = 1; i < lastUnsortedIndex; i++) {
                int curValue = arr[i];
                if (curValue > maxValue) {
                    maxValue = curValue;
                    maxIndex = i;
                }
            }
            if (maxValue > arr[lastUnsortedIndex])
                swap(arr, maxIndex, lastUnsortedIndex);
        }
    }
    private static  void insertionSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        for (int firstUnsortedIndex = 1; firstUnsortedIndex < arr.length; firstUnsortedIndex++) {
            int current = arr[firstUnsortedIndex];
            int i;
            for (i = firstUnsortedIndex; i > 0; i--) {
                int toMove = arr[i - 1];
                if (toMove <= current) break;
                arr[i] = toMove;
            }
            arr[i] = current;
        }
    }
    public static void main(String[] args) {
        int[] sourceArray = {3, 15, -15, 35, -22, 55, 0};
        int[] bubbleArray = Arrays.copyOf(sourceArray, sourceArray.length);
        int[] selectionArray = Arrays.copyOf(sourceArray, sourceArray.length);
        int[] insertionArray = Arrays.copyOf(sourceArray, sourceArray.length);
        bubbleSort(bubbleArray);
        selectionSort(selectionArray);
        insertionSort(insertionArray);
        System.out.println(Arrays.toString(bubbleArray));
        System.out.println(Arrays.toString(selectionArray));
        System.out.println(Arrays.toString(insertionArray));
    }
    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
