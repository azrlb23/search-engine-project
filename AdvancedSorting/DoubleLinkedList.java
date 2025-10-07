package AdvancedSorting;

import java.io.IOException;
import java.util.Scanner;

public class DoubleLinkedList {
    Node head;
    Node tail;

    public class Node{
        Node next;
        Node prev;
        int value;
        int indexAt;

        Node(int value, int indexAt){
            this.value = value;
            this.next = null;
            this.prev = null;
            this.indexAt = indexAt;
        }
    }

    public void insertAtEnd(int value, int index){
        Node newNode = new Node(value, index);
        if(head == null){
            head = newNode;
            tail = newNode;
            head.prev = null;
            tail.next = null;
        }else{
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            tail.next = null;
        }
    }

    //remove element from the front
    public int deleteAtFront(){
        if (head == null){
            return '\0';
        }
        int value = head.value;
        head = head.next;
        if (head == null) {
            tail = null;
            return value;
        }
        head.prev = null;
        return value;
    }

    public Node getNodeAtIndex(int index){
        int pos = 0;
        Node currentNode = head;
        while(currentNode != null){
            if(pos == index){
                return currentNode;
            }
            pos++;
            currentNode = currentNode.next;
        }
        return null;
    }

    public void swapGlue(int index1, int index2){
        if (index1 == index2) {
            return;
        };

        if (index1 < 0 || index2 < 0) {
            System.out.println("Gagal swap: Indeks tidak valid.");
            return;
        }

        if (index1 > index2) {
            int temp = index1;
            index1 = index2;
            index2 = temp;
        }

        Node nodeX = getNodeAtIndex(index1);
        Node nodeY = getNodeAtIndex(index2);

        if (nodeX == null || nodeY == null) {
            System.out.println("Gagal swap: Indeks di luar jangkauan.");
            return;
        }
        swap(nodeX, nodeY);
    }

    public void swap(Node nodeX, Node nodeY){
        if (nodeX == null || nodeY == null || nodeX == nodeY) {
            return;
        }

        Node prevX = nodeX.prev;
        Node nextX = nodeX.next;
        Node prevY = nodeY.prev;
        Node nextY = nodeY.next;

        if (nextX == nodeY){
            if (prevX != null) {
                prevX.next = nodeY;
            }
            if (nextY != null) {
                nextY.prev = nodeX;
            }

            nodeY.prev = prevX;
            nodeY.next = nodeX;
            nodeX.prev = nodeY;
            nodeX.next = nextY;
        }
        else if (nextY == nodeX) {
            if(prevY != null){
                prevY.next = nodeX;
            }
            if(nextX != null){
                nextX.prev = nodeY;
            }

            nodeX.prev = prevY;
            nodeX.next = nodeY;
            nodeY.prev = nodeX;
            nodeY.next = nextX;
        }
        else{
            if(prevX != null){
                prevX.next = nodeY;
            }
            if(nextX != null){
                nextX.prev = nodeY;
            }
            nodeY.prev = prevX;
            nodeY.next = nextX;

            if(prevY != null){
                prevY.next = nodeX;
            }
            if(nextY != null){
                nextY.prev = nodeX;
            }
            nodeX.prev = prevY;
            nodeX.next = nextY;
        }

        if(head == nodeX){
            head = nodeY;
        }else if(head == nodeY){
            head = nodeX;
        }

        if(tail == nodeX){
            tail = nodeY;
        }else if(tail == nodeY){
            tail = nodeX;
        }
    }

    public boolean isEmpty(){
        return head == null;
    }

    public void traverseForward(){
        if(isEmpty()){
            System.out.println("Queue is empty");
        }

        Node currNode = head;
        System.out.println("Value: ");
        while(currNode != null){
            System.out.print(currNode.value);
            if(currNode.next != null){
                System.out.print(" | ");
            }
            currNode = currNode.next;
        }
        System.out.println();

        System.out.println("Index: ");
        currNode = head;
        while(currNode != null){
            System.out.print(currNode.indexAt);
            if(currNode.next != null){
                System.out.print(" | ");
            }
            currNode = currNode.next;
        }
        System.out.println();
    }

    public void traverseBackward(){
        if(isEmpty()){
            System.out.println("List is empty");
        }

        Node currNode = tail;

        while(currNode != null){
            System.out.print(currNode.value);
            if(currNode.prev != null){
                System.out.print(" | ");
            }
            currNode = currNode.prev;
        }
        System.out.println();
    }

    public static void pressEnter(){
        System.out.println("\nTEKAN ENTER UNTUK MELANJUTKAN");
        try{
            int read = System.in.read(new byte[2]);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void addAuto(int value){

    }
    public static void main(String[] args) {

        int option = 0;
        DoubleLinkedList list = new DoubleLinkedList();
        Scanner sc = new Scanner(System.in);
        while (option != 6) {
            System.out.print("DOUBLE LINKED LIST MENUS: \n1.Insert Beginning \n2.Delete Front\n3.Swap\n4.Print Forward\n5.Auto add (a -> z)\n6. Exit\n: ");
            option = sc.nextInt();

            switch(option){
                case 1:
                    sc = new Scanner(System.in);
                    System.out.print("Masukkan 1 karakter: ");
                    int charInput = sc.nextInt();
                    int index = sc.nextInt();
                    list.insertAtEnd(charInput, index);
                    list.traverseForward();

                    pressEnter();
                    break;
                case 2:
                    list.deleteAtFront();
                    list.traverseForward();
                    pressEnter();
                    break;
                case 3:
                    System.out.print("Masukkan Index pertama: ");
                    int index1 = sc.nextInt();
                    System.out.print("Masukkan Index kedua: ");
                    int index2 = sc.nextInt();

                    list.swapGlue(index1, index2);
                    list.traverseForward();

                    pressEnter();
                    break;
                case 4:
                    list.traverseForward();
                    pressEnter();
                    break;
                case 5:
                    int index4 = 0;
                    for (char i = 'a'; i <= 'z'; i++){
                        list.insertAtEnd(i, index4);
                    }
                    list.traverseForward();
                    pressEnter();
                    break;
                case 6:
                    break;
            }
        }
    }
}