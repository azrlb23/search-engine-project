package Queue;
import java.util.Scanner;

class Node {
    char data;
    Node next;
    Node prev;

    public Node(char data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}

public class Queue {
    Node front;
    Node rear;
    private int size;

    public Queue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return front == null;
    }
    
    public int size() {
        return this.size;
    }

    public void enqueue(char data) {
        Node newNode = new Node(data);
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            newNode.prev = rear;
            rear = newNode;
        }
        size++;
        System.out.println("'" + data + "' berhasil masuk ke Queue.");
    }

    public char dequeue() {
        if (isEmpty()) {
            System.out.println("Queue kosong, tidak ada yang bisa keluar.");
            return '\0';
        }
        char outChar = front.data;
        front = front.next;
        size--;
        if (front == null) {
            rear = null;
        } else {
            front.prev = null;
        }
        System.out.println("'" + outChar + "' telah keluar dari queue.");
        return outChar;
    }

    public void peek() {
        if (isEmpty()) {
            System.out.println("Queue kosong, tidak ada yang bisa dilihat.");
        } else {
            System.out.println("Elemen paling depan adalah: '" + front.data + "'");
        }
    }

    public void swap(int index1, int index2) {
        if (index1 < 0 || index2 < 0 || index1 >= size || index2 >= size) {
            System.out.println("Gagal swap: Indeks tidak valid.");
            return;
        }
        if (index1 == index2) return;

        if (index1 > index2) {
            int temp = index1;
            index1 = index2;
            index2 = temp;
        }

        Node node1 = front;
        for (int i = 0; i < index1; i++) {
            node1 = node1.next;
        }

        Node node2 = front;
        for (int i = 0; i < index2; i++) {
            node2 = node2.next;
        }

        if (node1.next == node2) {
            Node prev1 = node1.prev;
            Node next2 = node2.next;
            if (prev1 != null) prev1.next = node2;
            if (next2 != null) next2.prev = node1;
            node2.prev = prev1;
            node1.next = next2;
            node2.next = node1;
            node1.prev = node2;
        } else {
            Node prev1 = node1.prev;
            Node next1 = node1.next;
            Node prev2 = node2.prev;
            Node next2 = node2.next;
            if (prev1 != null) prev1.next = node2;
            next1.prev = node2;
            node2.prev = prev1;
            node2.next = next1;
            if (prev2 != null) prev2.next = node1;
            if (next2 != null) next2.prev = node1;
            node1.prev = prev2;
            node1.next = next2;
        }

        if (front == node1) front = node2;
        else if (front == node2) front = node1;
        if (rear == node1) rear = node2;
        else if (rear == node2) rear = node1;
        
        System.out.println("Node di indeks " + index1 + " dan " + index2 + " berhasil ditukar.");
    }

    public void printqueue() {
        if (isEmpty()) {
            System.out.println("-> Queue saat ini: Kosong");
            return;
        }
        System.out.print("-> Queue saat ini: ");
        Node penunjuk = front;
        int index = 0;
        while (penunjuk != null) {
            System.out.print(penunjuk.data + "(" + index + ") ");
            penunjuk = penunjuk.next;
            index++;
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Queue queue = new Queue();
        int pilihan = 0;

        while (pilihan != 6) {
            System.out.println("\n--- Program Simulasi Queue ---");
            System.out.println("1. Tambah (Enqueue) Karakter");
            System.out.println("2. Keluar (Dequeue) Karakter");
            System.out.println("3. Lihat Depan (Peek)");
            System.out.println("4. Tukar Posisi (Swap by Index)");
            System.out.println("5. Print Queue");
            System.out.println("6. Keluar Program");
            System.out.print("Masukkan pilihan Anda: ");

            try {
                pilihan = input.nextInt();

                switch (pilihan) {
                    case 1:
                        System.out.print("Masukkan satu karakter untuk ditambahkan: ");
                        char dataMasuk = input.next().charAt(0);
                        queue.enqueue(dataMasuk);
                        break;
                    case 2:
                        queue.dequeue();
                        break;
                    case 3:
                        queue.peek();
                        break;
                    case 4:
                        System.out.print("Masukkan indeks pertama: ");
                        int idx1 = input.nextInt();
                        System.out.print("Masukkan indeks kedua: ");
                        int idx2 = input.nextInt();
                        queue.swap(idx1, idx2);
                        break;
                    case 5:
                        break;
                    case 6:
                        System.out.println("Program selesai.");
                        break;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
                
                if (pilihan != 6) {
                    queue.printqueue();
                }

            } catch (Exception e) {
                System.out.println("Input tidak valid, Harap masukkan angka.");
                input.next();
            }
        }

        input.close();
    }
}
