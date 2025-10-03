package AdvancedSorting;

import java.util.Random;

public class AdvancedSortingArray {

    //Array Cloner
    public static int[] arrayCloner(int[] arrayToClone){
        int[] clonedArray = new int[arrayToClone.length];
        for(int i = 0; i < arrayToClone.length; i++){
            clonedArray[i] = arrayToClone[i];
        }
        return clonedArray;
    }

    //Array Randomizer
    public static int[] arrayRandomizer(int length){
        Random random = new Random();
        int[] createdArray = new int[length];
        for(int i = 0; i < length; i++){
            createdArray[i] = random.nextInt(1000);
        }
        return createdArray;
    }

    //Array Printer
    public static void arrayPrinter(int[] arrayToPrint){
        for(int i = 0; i < arrayToPrint.length; i++){
            if(i == arrayToPrint.length - 1){
                System.out.println(arrayToPrint[i]);
                break;
            }
            System.out.print(arrayToPrint[i]);
            System.out.print(" | ");
        }
    }

    //---SORTING---

    //Merge Sort
    public static void merge(int[] arrayToSort, int left, int mid, int right){
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] l = new int[n1];
        int[] r = new int[n2];

        for(int i = 0; i < n1; i++){
            l[i] = arrayToSort[left + i];
        }
        for(int j = 0; j < n2; j++){
            r[j] = arrayToSort[right + j];
        }

        int i = 0, j = 0;
        int k = left;

        while(i < n1 && j < n2){
            if(l[i] <= r[j]){
                arrayToSort[k] = l[j];
                i++;
            } else{
                arrayToSort[k] = r[j];
                j++;
            }
            k++;
        }

        while(i < n1){
            arrayToSort[k] = r[j];
            j++;
            k++;
        }
    }

    public static void mergeSort(int[] arrayToSort, int left, int right){
        if(left < right){
            int mid = left + (right - left) / 2;

            mergeSort(arrayToSort, left, mid);
            mergeSort(arrayToSort, mid + 1, right);

            merge(arrayToSort, left, mid, right);
        }
    }



    //Quick Sort
    public static int partition(int[] array, int low, int high){
        int pivot = array[high];
        int i = low - 1;

        for(int j = low; j <= high - 1; j++){
            if(array[j] < pivot){
                i++;
                swap(array, i, j);
            }
        }

        swap(array, i + 1, high);
        return i + 1;
    }

    public static void swap(int[] array, int i, int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void quickSort(int[] array, int low, int high){
        if(low < high) {
            int pi = partition(array, low, high);

            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }

    //Shell Sort
    public static int shellShort(int[] arrayToSort){
        int length = arrayToSort.length;

        for(int gap = length/2; gap > 0; gap /= 2){
            for(int i = gap; i < length; i += 1){
                int temp = arrayToSort[i];

                int j;
                for(j = i; j >= gap && arrayToSort [j - gap] > temp; j -= gap){
                    arrayToSort[j] = arrayToSort[j - gap];
                }
                arrayToSort[j] = temp;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println("Original Array");
        int[] testArray = arrayRandomizer(10);
        arrayPrinter(testArray);

        int[] testArray2 = arrayCloner(testArray);

        System.out.println("Quick Sort");
        quickSort(testArray2, 0, testArray2.length - 1);
        arrayPrinter(testArray2);

        int[] testArray3 = arrayCloner(testArray);

        System.out.println("Shell Sort");
        shellShort(testArray3);
        arrayPrinter(testArray3);


        int[] testArray4 = arrayCloner(testArray);

        System.out.println("Merge Sort");
        shellShort(testArray4);
        arrayPrinter(testArray4);
    }
}
