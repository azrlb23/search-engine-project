package stackAndQueus;

import java.io.IOException;
import java.util.Scanner;

public class Stack {

    Node head;
    Stack(){
        this.head = null;
    }

    class Node{
        Node next;
        char value;

        Node(char value){
            this.next = null;
            this.value = value;
        }
    }

    public boolean isEmpty(){
        return head == null;
    }

    public void push(char value){
        Node newNode = new Node(value);
        newNode.next = head;
        head = newNode;
    }

    public char pop(){
        if (isEmpty()){
            System.out.println("Stack kosong !!!");
            return '\0';
        }
        char value = head.value;
        head = head.next;
        return value;
    }

    public void swap(int index1, int index2){
        int pos = 0;
        Node currNode = head, prevNode = null;
        Node currX= null, prevX= null;
        Node currY = null, prevY= null;

        if(index1 == index2){
            System.out.println("Index yang ingin ditukar sama.");
            return;
        }

        while(currNode != null){
            if(pos == index1){
                prevX = prevNode;
                currX = currNode;
            }else if (pos == index2) {
                prevY = prevNode;
                currY = currNode;
            }
            prevNode = currNode;
            currNode = currNode.next;
            pos++;
        }

        if(currX == null || currY == null){
            System.out.println("Salah satu atau kedua index melebihi kapasitas");
            return;
        }

        //cek kalau nilai x di head
        if(prevX != null){
            prevX.next = currY;
        }else{
            head = currY;
        }

        //cek kalau nilai y di head
        if(prevY != null){
            prevY.next = currX;
        }else{
            head = currX;
        }

        Node temp = currY.next;
        currY.next = currX.next;
        currX.next = temp;
    }

    public void printStack(){
        if(isEmpty()){
            System.out.println("Stack is empty");
        }

        Node currNode = head;

        while(currNode != null){
            System.out.print(currNode.value);
            currNode = currNode.next;
        }
        System.out.println();
    }
    public char peek(){
        if(head == null){
            System.out.println("Stack masih kosong");
            return '\0';
        }
        return head.value;
    }

    public static void main(String[] args) {
        int option = 0;
        Stack stack = new Stack();
        while (option != 6) {
            System.out.print("STACK MENUS: \n1.Push\n2.Pop\n3.Swap\n4.Print\n5.Peek\n6.Exit\n: ");
            Scanner sc = new Scanner(System.in);
            option = sc.nextInt();

            switch(option){
                case 1:
                    Scanner sc2 = new Scanner(System.in);
                    System.out.print("Masukkan 1 karakter: ");
                    char charInput = sc2.next().charAt(0);
                    stack.push(charInput);
                    stack.printStack();

                    Interruptor.pressEnter();
                    break;
                case 2:
                    stack.pop();
                    stack.printStack();
                    Interruptor.pressEnter();
                    break;
                case 3:
                    Scanner scan = new Scanner(System.in);
                    System.out.print("Masukkan Index pertama: ");
                    int index1 = sc.nextInt();
                    System.out.print("Masukkan Index kedua: ");
                    int index2 = sc.nextInt();

                    stack.swap(index1, index2);
                    stack.printStack();

                    Interruptor.pressEnter();
                    break;
                case 4:
                    stack.printStack();
                    Interruptor.pressEnter();
                    break;
                case 5:
                    System.out.println(stack.peek());
                    Interruptor.pressEnter();
                    break;
                case 6:
                    break;
            }
        }
    }
}

class Interruptor {
    public static void pressEnter(){
        System.out.println("\nTEKAN ENTER UNTUK MELANJUTKAN");
        try{
            int read = System.in.read(new byte[2]);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
