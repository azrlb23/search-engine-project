import java.util.Random;

public class SimpleSortingArray {

   public static int[] arrayRandomizer(int length){
       Random random = new Random();
       int[] arrayToCreate = new int[length];
       for(int i = 0; i < length; i++){
           arrayToCreate[i] = random.nextInt(100);
       }

       return arrayToCreate;
   }

   public static void arrayPrinter(int[] arrayToPrint){
       for(int i = 0; i < arrayToPrint.length; i++){
           if(i == (arrayToPrint.length - 1)){
               System.out.println(arrayToPrint[i]);
               System.out.println();
               break;
           }
           System.out.print(arrayToPrint[i]);
           System.out.print(" | ");
       }
   }

   public static int[] arrayCloner(int[] arrayToClone){
        int[] clonedArray = new int[arrayToClone.length];

        for(int i = 0; i < arrayToClone.length; i++){
            clonedArray[i] = arrayToClone[i];
        }
        return clonedArray;
   }

   public static void bubbleSortArray(int[] arrayToSort, int length){
       boolean swapped;
       for(int i = 0; i < length - 1; i++){
           swapped = false;

           for(int j = 0; j < length - i - 1; j++){
               if(arrayToSort[j] > arrayToSort[j + 1]){
                   int temp = arrayToSort[j];
                   arrayToSort[j] = arrayToSort[j + 1];
                   arrayToSort[j + 1] = temp;
                   swapped = true;
               }
           }
           if(!swapped){
               break;
           }
       }
   }

    public static void selectionSortArray(int[] arrayToSort, int length){
        int minValue;
        for(int i = 0; i < length - 1; i++){
            minValue = i;

            for(int j = i + 1; j < length; j++){
                if(arrayToSort[j] < arrayToSort[minValue]){
                    minValue = j;
                }
            }

            int temp = arrayToSort[i];
            arrayToSort[i] = arrayToSort[minValue];
            arrayToSort[minValue] = temp;
        }
    }

    public static void insertionSort(int[] arrayToSort, int length){
       for(int i = 0; i < length; i++){
           int key = arrayToSort[i];
           int j = i - 1;

           while(j >= 0 && arrayToSort[j] > key){
               arrayToSort[j + 1] = arrayToSort[j];
               j = j -1;
           }
           arrayToSort[j + 1] = key;
       }
    }

   public static void main(String[] args) {
       int[] anArray = arrayRandomizer(10);
       arrayPrinter(anArray);

       System.out.println("Bubble Sort");
       int[] aClonedArray = arrayCloner(anArray);
       bubbleSortArray(aClonedArray, aClonedArray.length);
       arrayPrinter(aClonedArray);

       System.out.println("Selection Sort");
       int[] aClonedArray2 = arrayCloner(anArray);
       selectionSortArray(aClonedArray2, aClonedArray2.length);
       arrayPrinter(aClonedArray2);

       System.out.println("Insertion Sort");
       int[] aClonedArray3 = arrayCloner(anArray);
       selectionSortArray(aClonedArray3, aClonedArray3.length);
       arrayPrinter(aClonedArray3);
   }
}
