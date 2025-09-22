import jdk.swing.interop.SwingInterOpUtils;

import java.util.*;

public class SimpleSorting extends DoubleLinkedList{

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
            return true; // Daftar kosong atau satu elemen dianggap terurut
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

        // 1. Salin data dari linked list ke ArrayList
        List<Integer> dataList = new ArrayList<>();
        Node current = head;
        while (current != null) {
            dataList.add(current.value);
            current = current.next;
        }

        // 2. Kocok ArrayList menggunakan Collections.shuffle()
        Collections.shuffle(dataList);

        // 3. Salin kembali data yang sudah dikocok ke linked list
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
            System.out.println("Percobaan ke-" + attempts + ": ");
            traverseForward();
        }
        System.out.println("\nDaftar berhasil diurutkan setelah " + attempts + " kali percobaan!");
    }

    public static void main(String[] args) {
        SimpleSorting list = new SimpleSorting();
        Scanner sc = new Scanner(System.in);
        int option = -1;

        while(option != 0){
            System.out.println("\u001B[42m  \u001B[30mSIMPLE SORTING MENUS:  \u001B[0m");
            System.out.println("6. Random add");
            System.out.println("7. Bubble Sort");
            System.out.println("8. Insertion Sort");
            System.out.println("9. Selection Sort");
            System.out.println("10. Clear List");
            System.out.println("11. Bogo Sort");
            System.out.println("\u001B[31m0. Exit\u001B[0m");
            System.out.print(": ");
            option = sc.nextInt();

            switch(option){
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
                case 0:
                    break;
            }
        }

    }
}
