class Stack {
    private Node top; // node paling atas

    public Stack() {
        top = null; // awalnya kosong
    }

    // push: tambah data
    public void push(int data) {
        Node newNode = new Node(data);
        newNode.next = top; // sambungkan ke top lama
        top = newNode;      // update top ke node baru
        System.out.println(data + " dimasukkan ke stack");
    }

    // pop: ambil data
    public int pop() {
        if (isEmpty()) {
            System.out.println("Stack kosong!");
            return -1;
        }
        int popped = top.data;
        top = top.next; // geser top
        return popped;
    }

    // peek: lihat data paling atas
    public int peek() {
        if (isEmpty()) {
            System.out.println("Stack kosong!");
            return -1;
        }
        return top.data;
    }

    // cek kosong
    public boolean isEmpty() {
        return top == null;
    }
}

public class Main {
    public static void main(String[] args) {
        Stack stack = new Stack();

        // push data
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);

        // peek data
        System.out.println("Data paling atas: " + stack.peek());

        // pop data
        System.out.println("Ambil data paling atas: " + stack.pop());
        System.out.println("Ambil data paling atas: " + stack.pop());

        // mengecek kosong
        System.out.println("Apakah datanya kosong? " + stack.isEmpty());

        // pop lagi
        System.out.println("Ambil data paling atas: " + stack.pop());
        System.out.println("Ambil data paling atas: " + stack.pop());





        // cek kosong lagi
        System.out.println("Apakah datanya kosong? " + stack.isEmpty());
    }
}
