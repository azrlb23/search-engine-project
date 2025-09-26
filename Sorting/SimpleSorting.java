import jdk.swing.interop.SwingInterOpUtils;

import java.lang.reflect.Array;
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

    public Node removeFrom(Node nodeX){
        nodeX.prev.next = nodeX.next;
        if(nodeX.next != null){
            nodeX.next.prev = nodeX.prev;
        }else{
            tail = nodeX.prev;
        }
        return nodeX;
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

    public SimpleSorting cloneList(){
        SimpleSorting cloned = new SimpleSorting();
        Node current = this.head;
        while (current != null) {
            cloned.insertAtEnd(current.value);
            current = current.next;
        }
        return cloned;
    }

    public static void main(String[] args) {
        SimpleSorting list = new SimpleSorting();
        SimpleSorting list2;
        Scanner sc = new Scanner(System.in);
        int option = -1;
        int initialSetup = -1;

        System.out.println("Initial Menu\nChoose Data Type: ");
        System.out.println("1. Linked List");
        System.out.println("2. Array");
        System.out.print(": ");
        initialSetup = sc.nextInt();
        while (option != 0) {
            System.out.println("\u001B[42m  \u001B[30mSIMPLE SORTING MENUS:  \u001B[0m");
            System.out.println("6. Random add");
            System.out.println("7. Bubble Sort");
            System.out.println("8. Insertion Sort");
            System.out.println("9. Selection Sort");
            System.out.println("10. Clear List");
            System.out.println("11. Print list (Original)");
            System.out.println("\u001B[31m0. Exit\u001B[0m");
            System.out.print(": ");
            option = sc.nextInt();

            switch (option) {
                case 6:
                    System.out.print("Length To Insert: ");
                    int length = sc.nextInt();
                    Random r = new Random();
                    for (int i = 0; i < length; i++) {
                        list.insertAtEnd(r.nextInt(1000));
                    }
                    list.traverseForward();
                    break;
                case 7:
                    list2 = list.cloneList();
                    System.out.println("\u001B[32mSORTING\u001B[0m");
                    long startime = System.nanoTime();
                    list2.bubbleSort();
                    long endtime = System.nanoTime();
                    System.out.println("\u001B[32mSORTING DONE\u001B[0m");
                    System.out.println("\nRESULT");
                    list2.traverseForward();
                    System.out.print("\u001B[32m TIME ELAPSED:\u001B[0m ");
                    double durationTime = (endtime - startime) / 1_000_000.0;
                    System.out.println("\u001B[31m" + durationTime + "\u001B[0m ms");
                    list2.clearList();
                    break;
                case 8:
                    list2 = list.cloneList();
                    System.out.println("\u001B[32mSORTING\u001B[0m");
                    long startime2 = System.nanoTime();
                    list2.insertionSort();
                    long endtime2 = System.nanoTime();
                    System.out.println("\u001B[32mSORTING DONE\u001B[0m");
                    System.out.println("\nRESULT");
                    list2.traverseForward();
                    System.out.print("\u001B[32m TIME ELAPSED:\u001B[0m ");
                    double durationTime2 = (endtime2 - startime2) / 1_000_000.0;
                    System.out.println("\u001B[31m" + durationTime2 + "\u001B[0m ms");
                    list2.clearList();
                    break;
                case 9:
                    list2 = list.cloneList();
                    System.out.println("\u001B[32mSORTING\u001B[0m");
                    long startime3 = System.nanoTime();
                    list2.selectionSort();
                    long endtime3 = System.nanoTime();
                    System.out.println("\u001B[32mSORTING DONE\u001B[0m");
                    System.out.println("\nRESULT");
                    list2.traverseForward();
                    double durationTime3 = (endtime3 - startime3) / 1_000_000.0;
                    System.out.print("\u001B[32m TIME ELAPSED:\u001B[0m ");
                    System.out.println("\u001B[31m" + durationTime3 + "\u001B[0m ms");
                    list2.clearList();
                    break;
                case 10:
                    System.out.println("CLEARING");
                    list.clearList();
                    System.out.println("DONE");
                    list.traverseForward();
                    break;
                case 11:
                    list.traverseForward();
                    break;
                case 0:
                    break;
            }
        }
    }
}
