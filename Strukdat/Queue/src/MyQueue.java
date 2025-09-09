public class MyQueue {
    private Node front; // depan antrian
    private Node rear;  // belakang antrian
    private int size;   // jumlah elemen

    public MyQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    // tambah ke belakang (enqueue) — O(1)
    public void enqueue(int data) {
        Node baru = new Node(data);
        if (rear == null) {     // antrian kosong
            front = rear = baru;
        } else {
            rear.next = baru;
            rear = baru;
        }
        size++;
        System.out.println(data + " masuk ke antrian");
    }

    // keluarkan dari depan (dequeue) — O(1)
    public int dequeue() {
        if (front == null) {
            System.out.println("Antrian kosong, tidak bisa dequeue");
            return -1; // bisa diganti lempar exception sesuai kebutuhan
        }
        int val = front.data;
        front = front.next;
        if (front == null) rear = null; // jadi kosong
        size--;
        System.out.println(val + " keluar dari antrian");
        return val;
    }

    // lihat depan tanpa menghapus (peek)
    public int peek() {
        if (front == null) {
            System.out.println("Antrian kosong");
            return -1;
        }
        return front.data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // cetak isi antrian dari depan ke belakang
    public void printQueue() {
        if (front == null) {
            System.out.println("Antrian kosong");
            return;
        }
        System.out.print("Isi antrian: ");
        Node cur = front;
        while (cur != null) {
            System.out.print(cur.data + (cur.next != null ? " -> " : ""));
            cur = cur.next;
        }
        System.out.println();
    }
}