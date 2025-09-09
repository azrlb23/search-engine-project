public class Main {
    public static void main(String[] args) {
        MyQueue antrian = new MyQueue();

        antrian.enqueue(10);
        antrian.enqueue(20);
        antrian.enqueue(30);
        antrian.enqueue(40);
        antrian.enqueue(50);
        antrian.printQueue();

        System.out.println("front: " + antrian.peek());
        antrian.dequeue();
        antrian.printQueue();

        System.out.println("Ukuran: " + antrian.size());
        System.out.println("Kosong: " + antrian.isEmpty());

        antrian.dequeue();
        antrian.dequeue();
        antrian.dequeue();
        antrian.dequeue();
        antrian.dequeue();
        System.out.println("Kosong: " + antrian.isEmpty());
    }
}
