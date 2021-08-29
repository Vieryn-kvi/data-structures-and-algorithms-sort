package ru.vieryn;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    private static final int[] gaps = {1, 8, 23, 77, 281, 1073, 4193, 16577, 65921, 262913, 1050113, 4197377, 16783361,
            67121153, 268460033, 1073790977};

    private static void bubbleSort(int @NotNull [] arr) {
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
    public static void insertionSortRecursive(int[] input) {
        if (input == null || input.length < 2) return;
        insertionSortRecursive(input, input.length);
    }
    private static void insertionSortRecursive(int @NotNull [] input, int itemsToSort) {
        if (itemsToSort < 2) return;
        insertionSortRecursive(input, itemsToSort - 1);
        int i = itemsToSort - 1;
        int current = input[i];
        while (i > 0 && input[i - 1] > current) input[i] = input[--i];
        input[i] = current;
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
    private static void mergeSort(int[] arr) throws ExecutionException, InterruptedException {
        if (arr == null) throw new IllegalArgumentException();
        System.arraycopy(mergeSort(arr, 0, arr.length), 0, arr, 0, arr.length);
    }
    private static int @NotNull [] mergeSort(int[] arr, int startInclusive, int endExclusive) throws ExecutionException, InterruptedException {
        if (endExclusive - startInclusive == 1) return new int[] { arr[startInclusive] };
        int mid = (startInclusive + endExclusive) / 2;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<int[]> leftFuture = executor.submit(() -> mergeSort(arr, startInclusive, mid));
        int[] right = mergeSort(arr, mid, endExclusive);
        int[] left = leftFuture.get();
        executor.shutdown();
        int[] result = new int[endExclusive - startInclusive];
        int leftIndex = 0;
        int rightIndex = 0;
        for (int i = 0; i < result.length; i++) {
            if (leftIndex == left.length) {
                result[i] = right[rightIndex++];
            } else if (rightIndex == right.length) {
                result[i] = left[leftIndex++];
            } else {
                int leftValue = left[leftIndex];
                int rightValue = right[rightIndex];
                if (leftValue <= rightValue) {
                    result[i] = leftValue;
                    leftIndex++;
                } else {
                    result[i] = rightValue;
                    rightIndex++;
                }
            }
        }
        return result;
    }
    public static void quickSort(int[] arr){
        if (arr == null) throw new IllegalArgumentException();
        quickSort(arr, 0, arr.length);
    }
    private static void quickSort(int[] arr, int startInclude, int endExclude) {
        if (endExclude - startInclude < 2) return;
        if (endExclude - startInclude == 2) {
            if (arr[startInclude + 1] < arr[startInclude]) {
                final int tmp = arr[startInclude];
                arr[startInclude] = arr[startInclude + 1];
                arr[startInclude + 1] = tmp;
            }
            return;
        }
        {
            final int midIndex = (endExclude + startInclude) / 2;
            final int endIndex = endExclude - 1;

            final int startValue = arr[startInclude];
            final int midValue = arr[midIndex];
            final int endValue = arr[endIndex];
            if (startValue < midValue && startValue < endValue) {
                if (midValue < endValue) {
                    arr[startInclude] = midValue;
                    arr[midIndex] = startValue;
                } else {
                    arr[startInclude] = endValue;
                    arr[endIndex] = startValue;
                }
            } else if (startValue > midValue && startValue > endValue) {
                if (midValue < endValue) {
                    arr[startInclude] = endValue;
                    arr[endIndex] = startValue;
                } else {
                    arr[startInclude] = midValue;
                    arr[midIndex] = startValue;
                }
            }
        }
        final int pivot = arr[startInclude];

        int lessIndex = startInclude;
        int greaterIndex = endExclude - 1;
        boolean findLess = true;
        while (greaterIndex != lessIndex) {
            if (findLess) {
                final int greaterValue = arr[greaterIndex];
                if (greaterValue < pivot) {
                    arr[lessIndex++] = greaterValue;
                    findLess = false;
                } else {
                    greaterIndex--;
                }
            } else {
                final int lessValue = arr[lessIndex];
                if (lessValue >= pivot) {
                    arr[greaterIndex--] = lessValue;
                    findLess = true;
                } else {
                    lessIndex++;
                }
            }
        }

        final int midIndex = greaterIndex;
        arr[midIndex] = pivot;

        var thread = new Thread(() -> quickSort(arr, startInclude, midIndex));
        thread.start();

        quickSort(arr, midIndex + 1, endExclude);

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void countingSort(int[] arr, int min, int max) {
        int[] counts = new int[max - min + 1];
        for (int k : arr) counts[k - min]++;
        int resultIndex = 0;
        for (int i = 0; i < counts.length; i++) {
            int currentCount = counts[i];
            Arrays.fill(arr, resultIndex, resultIndex + currentCount, i + min);
            resultIndex += currentCount;
        }
    }
    private static int getCountPosition(int item, int radix, int position) {
        for (int i = 0; i < position; i++) item /= radix;
        return radix + item % radix - 1;
    }
    public static void radixSort(int[] arr, int radix, int width) {
        for (int position = 0; position < width; position++) {
            int[] counts = new int[2 * radix - 1];
            for (int value : arr) counts[getCountPosition(value, radix, position)]++;
            for (int i = 1, sum = counts[0]; i < counts.length; i++) {
                counts[i] = (sum += counts[i]);
            }
            int[] tempArr = Arrays.copyOf(arr, arr.length);
            for(int i = tempArr.length - 1; i >= 0; i--) {
                int value = tempArr[i];
                arr[--counts[getCountPosition(value, radix, position)]] = value;
            }
        }
    }
    public static void mergeSortReversed(int[] input) {
        mergeSortReversed(input, 0, input.length);
    }
    private static void mergeSortReversed(int[] input, int startInclude, int endExclude) {
        int length = endExclude - startInclude;
        if (length < 2) return;
        if (length == 2) {
            if (input[startInclude] < input[startInclude + 1]) {
                int tmp = input[startInclude];
                input[startInclude] = input[startInclude + 1];
                input[startInclude + 1] = tmp;
            }
            return;
        }
        int mid = (startInclude + endExclude) / 2;
        mergeSortReversed(input, startInclude, mid);
        mergeSortReversed(input, mid, endExclude);
        mergeReversed(input, startInclude, mid, endExclude);
    }

    private static void mergeReversed(int[] input, int startInclude, int mid, int endExclude) {
        if (input[mid - 1] >= input[mid]) return;
        int[] tempArray = new int[endExclude - startInclude];
        int leftIndex = startInclude;
        int rightIndex = mid;
        int tempIndex = 0;

        while (leftIndex < mid && rightIndex < endExclude) {
            tempArray[tempIndex++] = input[leftIndex] >= input[rightIndex]? input[leftIndex++] : input[rightIndex++];
        }
        System.arraycopy(input, leftIndex, input, endExclude - mid + leftIndex, mid - leftIndex);
        System.arraycopy(tempArray, 0, input, startInclude, tempIndex);
    }

    public static void radixSortOfLatinLowercaseStrings(String[] input, int width) {
        for (int position = width - 1; position >= 0; position--) {
            int[] counts = new int['z' - 'a' + 1];
            for (String item : input) counts[item.charAt(position) - 'a']++;
            for (int i = 1, sum = counts[0]; i < counts.length; i++) counts[i] = (sum += counts[i]);
            String[] temp = Arrays.copyOf(input, input.length);
            for (int i = input.length - 1; i >= 0; i--) {
                String item = temp[i];
                input[--counts[item.charAt(position) - 'a']] = item;
            }
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] sourceArray = {3, 15, -15, 35, -22, 55, 0, 1, -7, 15};
        int[] bubbleArray = Arrays.copyOf(sourceArray, sourceArray.length);
        int[] selectionArray = Arrays.copyOf(sourceArray, sourceArray.length);
        int[] insertionArray = Arrays.copyOf(sourceArray, sourceArray.length);
        int[] shellBubbleArray = Arrays.copyOf(sourceArray, sourceArray.length);
        int[] shellInsertionArray = Arrays.copyOf(sourceArray, sourceArray.length);
        int[] mergeSortArray = Arrays.copyOf(sourceArray, sourceArray.length);
        int[] quickSortArray = Arrays.copyOf(sourceArray, sourceArray.length);
        int[] mergeSortReversedArray = Arrays.copyOf(sourceArray, sourceArray.length);
        int[] insertionSortRecursiveArray = Arrays.copyOf(sourceArray, sourceArray.length);
        String[] radixSortOfLatinLowercaseStringsArray = {"bcdef", "dbaqc", "abcde", "omadd", "bbbbb"};

        int[] countingSortArray = {1, 5, 6, 8, 2, 4, 4, 4, 9, 10, 1, 6, 10, 5};
        int[] radixSortArray = {234, -12, 0, 564, -12, 12, 278, 912, -563, 713};

        bubbleSort(bubbleArray);
        selectionSort(selectionArray);
        insertionSort(insertionArray);
        shellSwapSort(shellBubbleArray);
        shellInsertionSort(shellInsertionArray);
        mergeSort(mergeSortArray);
        quickSort(quickSortArray);
        countingSort(countingSortArray, 1, 10);
        radixSort(radixSortArray, 6, 4);
        mergeSortReversed(mergeSortReversedArray);
        insertionSortRecursive(insertionSortRecursiveArray);
        radixSortOfLatinLowercaseStrings(radixSortOfLatinLowercaseStringsArray, 5);

        System.out.println(Arrays.toString(bubbleArray));
        System.out.println(Arrays.toString(selectionArray));
        System.out.println(Arrays.toString(insertionArray));
        System.out.println(Arrays.toString(shellBubbleArray));
        System.out.println(Arrays.toString(shellInsertionArray));
        System.out.println(Arrays.toString(mergeSortArray));
        System.out.println(Arrays.toString(quickSortArray));
        System.out.println(Arrays.toString(countingSortArray));
        System.out.println(Arrays.toString(radixSortArray));
        System.out.println(Arrays.toString(mergeSortReversedArray));
        System.out.println(Arrays.toString(insertionSortRecursiveArray));
        System.out.println(Arrays.toString(radixSortOfLatinLowercaseStringsArray));
    }
    private static void swap(int @NotNull [] arr, int i, int j) {
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
