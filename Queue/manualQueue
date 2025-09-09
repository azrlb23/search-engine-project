package Queue;
class Node {
    char data;    
    Node next;    

    public Node(char data) {
        this.data = data;
        this.next = null;
    }
}

class manualQueue {
    Node front;
    Node rear; 

    public manualQueue() {
        this.front = null;
        this.rear = null;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public void tambah(char data) {
        Node nodeBaru = new Node(data);
        System.out.println("'" + data + "' masuk ke antrian.");

        if (isEmpty()) {
            front = nodeBaru;
            rear = nodeBaru;
        } else {
            rear.next = nodeBaru;
            rear = nodeBaru;
        }
    }

    public char keluar() {
        if (isEmpty()) {
            System.out.println("Antrian kosong, gada yg bisa keluar");
            return '\0'; 
        }

        char dataYangKeluar = front.data;
        System.out.println("'" + dataYangKeluar + "' keluar dari antrian.");

        front = front.next;

        if (front == null) {
            rear = null;
        }

        return dataYangKeluar;
    }

    public char lihat() {
        if (isEmpty()) {
            System.out.println("Antrian kosong, gada yang bisa dilihat.");
            return '\0';
        }
        return front.data;
    }

    public void cetakAntrian() {
        if (isEmpty()) {
            System.out.println("Kosong, gada yang bisa dicetak.");
            return;
        }
        
        System.out.print("Isi: ");
        Node penunjuk = front; 
        while (penunjuk != null) {
            System.out.print(penunjuk.data + " "); 
            penunjuk = penunjuk.next; 
        }
    }
    
    public static void main(String[] args) {
        
        System.out.println("Testing Manual Queue:");
        
        manualQueue antrianHuruf = new manualQueue();
        
        antrianHuruf.cetakAntrian(); //cetak pas kosong

        //nyoba nambah beberapa karakter ke queue
        antrianHuruf.tambah('A');
        antrianHuruf.cetakAntrian();
        
        antrianHuruf.tambah('B');
        antrianHuruf.cetakAntrian();

        antrianHuruf.tambah('C');
        antrianHuruf.cetakAntrian();
        
        System.out.println("Karakter di depan: '" + antrianHuruf.lihat() + "'");
        
        antrianHuruf.keluar(); //a keluar
        antrianHuruf.cetakAntrian();
        
        System.out.println("Karakter di depan skrg: '" + antrianHuruf.lihat() + "'");

        antrianHuruf.keluar(); //b keluar
        antrianHuruf.keluar(); //c keluar

        antrianHuruf.cetakAntrian(); //cetak pas kosong
        
        antrianHuruf.keluar(); //nyoba method keluar pas kosong
        
    }
}
