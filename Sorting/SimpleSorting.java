import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class SimpleSorting extends DoubleLinkedList {
    static int[] arrayToSort = null;

    public int getLength(){
        Node current = head;
        int size = 0;

        while(current != null ){
            size++;
            current = current.next;
        }
        return size;
    }

    public void bubbleSort(){
        if (head == null){
            System.out.println("Nothing to sort");
            return;
        }

        int len = getLength();
        int iterate = 0;
        boolean swapped;

        while (iterate < len){
            Node traverseNode = head;
            swapped = false;

            while(traverseNode.next != null){
                Node ptr = traverseNode.next;
                if(traverseNode.value > ptr.value){
                    swapped = true;
                    swap(traverseNode, ptr);
                    continue;
                }
                traverseNode = traverseNode.next;
            }

            if(!swapped){
                break;
            }
            iterate++;
        }
    }

    public void insertAfter(Node nodeAfter, Node toInsert){
        Node temp = nodeAfter.next;

        nodeAfter.next = toInsert;
        toInsert.prev = nodeAfter;
        temp.prev = toInsert;
        toInsert.next = temp;
    }

    public Node removeFrom(Node nodeX){
        nodeX.prev.next = nodeX.next;
        if(nodeX.next != null){
            nodeX.next.prev = nodeX.prev;
        }else{
            tail = nodeX.prev;
        }
        return nodeX;
    }

    //TESTER DEBUGGER
    public static void insertButUnRand(SimpleSorting list, int length){
        for(int i = 0; i < length; i++){
            list.insertAtEnd(i);
        }
    }

    public void insertionSort(){
        if(head == null || head.next == null){
            return;
        }

        Node key = head.next;

        while(key != null){
            //yang di iterate
            Node removedNode = key;
            Node traverseNode = key.next;

            Node walker = removedNode.prev;

            while(walker != null && walker.value > removedNode.value){
                walker = walker.prev;
            }

            if(walker != removedNode.prev){
                removeFrom(removedNode);

                if(walker == null){
                    removedNode.next = head;
                    removedNode.prev = null;
                    head.prev = removedNode;
                    head = removedNode;
                }else{
                    removedNode.next = walker.next;
                    removedNode.prev = walker;
                    walker.next.prev = removedNode;
                    walker.next = removedNode;
                }
            }
            key = traverseNode;
        }
    }

    public void selectionSort(){
        if (head == null){
            System.out.println("Nothing to sort");
            return;
        }
        for(Node currNode = head; currNode != null; currNode = currNode.next){
            Node minValue = currNode;
            for(Node walker = currNode.next; walker != null; walker = walker.next){
                if(walker.value < minValue.value){
                    minValue = walker;
                }
            }
            if(minValue != currNode){
                swap(currNode, minValue);
                currNode = minValue;
            }
        }
    }

    public void clearList(){
        head = null;
        tail = null;
    }

    public boolean isSorted() {
        if (head == null || head.next == null) {
            return true;
        }

        Node current = head;
        while (current.next != null) {
            if (current.value > current.next.value) {
                return false;
            }
            current = current.next;
        }
        return true;
    }

    public void shuffle() {
        if (getLength() <= 1) {
            return;
        }

        List<Integer> dataList = new ArrayList<>();
        Node current = head;
        while (current != null) {
            dataList.add(current.value);
            current = current.next;
        }

        Collections.shuffle(dataList);

        current = head;
        for (Integer data : dataList) {
            current.value = data;
            current = current.next;
        }
    }

    public void bogoSort() {
        long attempts = 0;
        while (!isSorted()) {
            shuffle();
            attempts++;
        }
        System.out.println("\nDaftar berhasil diurutkan setelah " + attempts + " kali percobaan!");
    }

    public static void generateArray(int length) {
        if (length <= 0) {
            System.out.println("Panjang array harus positif.");
            return;
        }
        arrayToSort = new int[length];
        Random r = new Random();
        for (int i = 0; i < arrayToSort.length; i++) {
            arrayToSort[i] = r.nextInt(1000);
        }
        System.out.println("Array acak berhasil dibuat dengan panjang " + length);
        System.out.println(Arrays.toString(arrayToSort));
    }

    public static void bubbleSortArray() {
        if (arrayToSort == null) {
            System.out.println("Array belum dibuat. Silakan buat array terlebih dahulu (Menu 12).");
            return;
        }
        System.out.println("\n\u001B[32mBUBBLE SORTING ARRAY...\u001B[0m");
        long startTime = System.nanoTime();
        int n = arrayToSort.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arrayToSort[j] > arrayToSort[j + 1]) {
                    int temp = arrayToSort[j];
                    arrayToSort[j] = arrayToSort[j + 1];
                    arrayToSort[j + 1] = temp;
                }
            }
        }
        long endTime = System.nanoTime();
        System.out.println("\u001B[32mSORTING DONE\u001B[0m");
        System.out.println("\nHASIL ARRAY");
        System.out.println(Arrays.toString(arrayToSort));
        double duration = (endTime - startTime) / 1_000_000.0;
        System.out.println("\u001B[32m TIME ELAPSED:\u001B[0m \u001B[31m" + duration + "\u001B[0m ms");
    }

    public static void selectionSortArray() {
        if (arrayToSort == null) {
            System.out.println("Array belum dibuat. Silakan buat array terlebih dahulu (Menu 12).");
            return;
        }
        System.out.println("\n\u001B[32mSELECTION SORTING ARRAY...\u001B[0m");
        long startTime = System.nanoTime();
        int n = arrayToSort.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arrayToSort[j] < arrayToSort[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = arrayToSort[minIndex];
            arrayToSort[minIndex] = arrayToSort[i];
            arrayToSort[i] = temp;
        }
        long endTime = System.nanoTime();
        System.out.println("\u001B[32mSORTING DONE\u001B[0m");
        System.out.println("\nHASIL ARRAY");
        System.out.println(Arrays.toString(arrayToSort));
        double duration = (endTime - startTime) / 1_000_000.0;
        System.out.println("\u001B[32m TIME ELAPSED:\u001B[0m \u001B[31m" + duration + "\u001B[0m ms");
    }

    public static void insertionSortArray() {
        if (arrayToSort == null) {
            System.out.println("Array belum dibuat. Silakan buat array terlebih dahulu (Menu 12).");
            return;
        }
        System.out.println("\n\u001B[32mINSERTION SORTING ARRAY...\u001B[0m");
        long startTime = System.nanoTime();
        int n = arrayToSort.length;
        for (int i = 1; i < n; ++i) {
            int key = arrayToSort[i];
            int j = i - 1;
            while (j >= 0 && arrayToSort[j] > key) {
                arrayToSort[j + 1] = arrayToSort[j];
                j = j - 1;
            }
            arrayToSort[j + 1] = key;
        }
        long endTime = System.nanoTime();
        System.out.println("\u001B[32mSORTING DONE\u001B[0m");
        System.out.println("\nHASIL ARRAY");
        System.out.println(Arrays.toString(arrayToSort));
        double duration = (endTime - startTime) / 1_000_000.0;
        System.out.println("\u001B[32m TIME ELAPSED:\u001B[0m \u001B[31m" + duration + "\u001B[0m ms");
    }


    public static void main(String[] args) {
        SimpleSorting list = new SimpleSorting();
        Scanner sc = new Scanner(System.in);
        int option = -1;

        while(option != 0){
            System.out.println("\n\u001B[42m  \u001B[30mSIMPLE SORTING MENUS:  \u001B[0m");
            System.out.println("--- Linked List Operations ---");
            System.out.println("6. Random add to List");
            System.out.println("7. Bubble Sort List");
            System.out.println("8. Insertion Sort List");
            System.out.println("9. Selection Sort List");
            System.out.println("10. Clear List");
            System.out.println("11. Bogo Sort List");
            System.out.println("\n--- Array Operations ---");
            System.out.println("12. Random add Array");
            System.out.println("13. Bubble Sort Array");
            System.out.println("14. Selection Sort Array");
            System.out.println("15. Insertion Sort Array");
            System.out.println("--------------------------");
            System.out.println("\u001B[31m0. Exit\u001B[0m");
            System.out.print(": ");
            option = sc.nextInt();

            switch(option){
                // Case untuk Linkedlist
                case 6:
                    System.out.print("Length To Insert: ");
                    int length = sc.nextInt();
                    Random r = new Random();
                    for (int i = 0; i < length;i++){
                        list.insertAtEnd(r.nextInt(1000));
                    }
                    list.traverseForward();
                    break;
                case 7:
                    System.out.println("\u001B[32mSORTING\u001B[0m");
                    long startime = System.nanoTime();
                    list.bubbleSort();
                    long endtime = System.nanoTime();
                    System.out.println("\u001B[32mSORTING DONE\u001B[0m");
                    System.out.println("\nRESULT");
                    list.traverseForward();
                    System.out.print("\u001B[32m TIME ELAPSED:\u001B[0m ");
                    double durationTime = (endtime - startime) / 1_000_000.0;
                    System.out.println("\u001B[31m"+ durationTime + "\u001B[0m ms");
                    break;
                case 8:
                    System.out.println("\u001B[32mSORTING\u001B[0m");
                    long startime2 = System.nanoTime();
                    list.insertionSort();
                    long endtime2 = System.nanoTime();
                    System.out.println("\u001B[32mSORTING DONE\u001B[0m");
                    System.out.println("\nRESULT");
                    list.traverseForward();
                    System.out.print("\u001B[32m TIME ELAPSED:\u001B[0m ");
                    double durationTime2 = (endtime2 - startime2) / 1_000_000.0;
                    System.out.println("\u001B[31m"+ durationTime2 + "\u001B[0m ms");
                    break;
                case 9:
                    System.out.println("\u001B[32mSORTING\u001B[0m");
                    long startime3 = System.nanoTime();
                    list.selectionSort();
                    long endtime3 = System.nanoTime();
                    System.out.println("\u001B[32mSORTING DONE\u001B[0m");
                    System.out.println("\nRESULT");
                    list.traverseForward();
                    double durationTime3 = (endtime3 - startime3) / 1_000_000.0;
                    System.out.print("\u001B[32m TIME ELAPSED:\u001B[0m ");
                    System.out.println("\u001B[31m"+ durationTime3 + "\u001B[0m ms");
                    break;
                case 10:
                    System.out.println("CLEARING");
                    list.clearList();
                    System.out.println("DONE");
                    list.traverseForward();
                    break;
                case 11:
                    System.out.println("BOGO SORT");
                    long startime4 = System.nanoTime();
                    list.bogoSort();
                    long endtime4 = System.nanoTime();
                    System.out.println("\u001B[32mSORTING DONE\u001B[0m");
                    System.out.println("\nRESULT");
                    list.traverseForward();
                    double durationTime4 = (endtime4 - startime4) / 1_000_000.0;
                    System.out.print("\u001B[32m TIME ELAPSED:\u001B[0m ");
                    System.out.println("\u001B[31m"+ durationTime4 + "\u001B[0m ms");
                    break;
                // Case untuk Array
                case 12:
                    System.out.print("Masukkan panjang array yang ingin dibuat: ");
                    int arrayLength = sc.nextInt();
                    generateArray(arrayLength);
                    break;
                case 13:
                    bubbleSortArray();
                    break;
                case 14:
                    selectionSortArray();
                    break;
                case 15:
                    insertionSortArray();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
                    break;
            }
        }
        sc.close();
    }
}
