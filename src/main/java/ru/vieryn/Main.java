package ru.vieryn;

import java.util.Arrays;

public class Main {
    private static int[] gaps = {1, 8, 23, 77, 281, 1073, 4193, 16577, 65921, 262913, 1050113, 4197377, 16783361,
            67121153, 268460033, 1073790977};

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
    private static void insertionSort(int[] arr) {
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
    private static void shellSwapSort(int[] arr) {
        int n;
        if (arr == null || (n = arr.length) <= 1) return;

        for(int gapIndex = chooseGapIndex(n); gapIndex >= 0; gapIndex--) {
            int gap = gaps[gapIndex];
            for (int i = gap; i < n; i++) {
                for (int j = i; j > 0 && arr[j] < arr[j - gap]; j -= gap)
                    swap(arr, j, j - gap);
            }
        }
    }
    private static void shellInsertionSort(int[] arr) {
        int n;
        if (arr == null || (n = arr.length) <= 1) return;

        for(int gapIndex = chooseGapIndex(n); gapIndex >= 0; gapIndex--) {
            int gap = gaps[gapIndex];
            for (int i = gap; i < n; i++) {
                int item = arr[i];
                int j;
                for (j = i; j > 0 && item < arr[j - gap]; j -= gap)
                    arr[j] = arr[j - gap];
                arr[j] = item;
            }
        }
    }
    public static void main(String[] args) {
        int[] sourceArray = {3, 15, -15, 35, -22, 55, 0, 1, -7, 15};
        int[] bubbleArray = Arrays.copyOf(sourceArray, sourceArray.length);
        int[] selectionArray = Arrays.copyOf(sourceArray, sourceArray.length);
        int[] insertionArray = Arrays.copyOf(sourceArray, sourceArray.length);
        int[] shellBubbleArray = Arrays.copyOf(sourceArray, sourceArray.length);
        int[] shellInsertionArray = Arrays.copyOf(sourceArray, sourceArray.length);

        bubbleSort(bubbleArray);
        selectionSort(selectionArray);
        insertionSort(insertionArray);
        shellSwapSort(shellBubbleArray);
        shellInsertionSort(shellInsertionArray);
        System.out.println(Arrays.toString(bubbleArray));
        System.out.println(Arrays.toString(selectionArray));
        System.out.println(Arrays.toString(insertionArray));
        System.out.println(Arrays.toString(shellBubbleArray));
        System.out.println(Arrays.toString(shellInsertionArray));
    }
    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
    private static int chooseGapIndex(int n) {
        for (int gapIndex = gaps.length - 1; gapIndex > 0; gapIndex--) {
            if (gaps[gapIndex] < n) return gapIndex;
        }
        return 0;
    }
}
