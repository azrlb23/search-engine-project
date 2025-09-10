package stackAndQueus;

import java.io.IOException;
import java.util.Scanner;

public class Queues {
    Node head;
    Node tail;

    class Node{
        Node next;
        Node prev;
        char value;

        Node(char c){
            this.value = c;
        }
    }

    public void enqueu(char c){
        Node newNode = new Node(c);
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
    public char dequeue(){
        if (head == null){
            System.out.println("Queue masih kosong.");
            return '\0';
        }
        char value = head.value;
        head = head.next;
        if (head == null) {
            tail = null;
            return value;
        }
        head.prev = null;
        return value;
    }

    public void swap(int index1, int index2){
        int pos = 0;

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

        Node nodeX = null;
        Node nodeY = null;
        Node currNode = head;

        while (currNode != null) {
            if (index1 == pos) {
                nodeX = currNode;
            }

            if (index2 == pos) {
                nodeY = currNode;
            }

            if(nodeX != null && nodeY != null){
                break;
            }
            currNode = currNode.next;
            pos++;
        }

        if (nodeX == null || nodeY == null) {
            System.out.println("Gagal swap: Indeks di luar jangkauan.");
            return;
        }

        if(head == nodeX){
            head = nodeY;
        } else if(head == nodeY){
            head = nodeX;
        }

        if(tail == nodeX){
            tail = nodeY;
        } else if(tail == nodeY){
            tail = nodeX;
        }

        if (nodeX.next == nodeY) {
            Node prevX = nodeX.prev;
            Node nextY = nodeY.next;

            if (prevX != null) {
                prevX.next = nodeY;
            }
            if (nextY != null) {
                nextY.prev = nodeX;
            }
            nodeY.prev = prevX;
            nodeX.next = nextY;
            nodeY.next = nodeX;
            nodeX.prev = nodeY;
        } else {
            Node prevX = nodeX.prev;
            Node nextX = nodeX.next;
            Node prevY = nodeY.prev;
            Node nextY = nodeY.next;

            if (prevX != null) {
                prevX.next = nodeY;
            }

            nextX.prev = nodeY;
            nodeY.prev = prevX;
            nodeY.next = nextX;

            if (prevY != null) {
                prevY.next = nodeX;
            }
            if (nextY != null) {
                nextY.prev = nodeX;
            }

            nodeX.prev = prevY;
            nodeX.next = nextY;
        }
    }

    public boolean isEmpty(){
        return head == null;
    }

    public void printQueue(){
        if(isEmpty()){
            System.out.println("Queue is empty");
        }

        Node currNode = head;

        while(currNode != null){
            System.out.print(currNode.value);
            currNode = currNode.next;
        }
        System.out.println();
    }
    public static void main(String[] args) {
        int option = 0;
        Queues queue = new Queues();
        while (option != 5) {
            System.out.print("QUEUE MENUS: \n1.Enqueu\n2.Dequeu\n3.Swap\n4.Print\n5.Exit\n: ");
            Scanner sc = new Scanner(System.in);
            option = sc.nextInt();

            switch(option){
                case 1:
                    Scanner sc2 = new Scanner(System.in);
                    System.out.print("Masukkan 1 karakter: ");
                    char charInput = sc2.next().charAt(0);
                    queue.enqueu(charInput);
                    queue.printQueue();

                    Interrupt.pressEnter();
                    break;
                case 2:
                    queue.dequeue();
                    queue.printQueue();
                    Interrupt.pressEnter();
                    break;
                case 3:
                    Scanner scan = new Scanner(System.in);
                    System.out.print("Masukkan Index pertama: ");
                    int index1 = sc.nextInt();
                    System.out.print("Masukkan Index kedua: ");
                    int index2 = sc.nextInt();

                    queue.swap(index1, index2);
                    queue.printQueue();

                    Interrupt.pressEnter();
                    break;
                case 4:
                    queue.printQueue();
                    Interrupt.pressEnter();
                    break;
                case 5:
                    break;
            }
        }
    }
}

class Interrupt {
    public static void pressEnter(){
        System.out.println("\nTEKAN ENTER UNTUK MELANJUTKAN");
        try{
            int read = System.in.read(new byte[2]);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}