package Queue;

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
        } else {
            front.prev = null;
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

    public void swap(char data1, char data2) {
        if (data1 == data2) return; //klo isi sama gausa ditukar

        Node node1 = null, node2 = null;
        Node penunjuk = front;

        while (penunjuk != null) {
            if (penunjuk.data == data1) {
                node1 = penunjuk;
            }
            if (penunjuk.data == data2) {
                node2 = penunjuk;
            }
            penunjuk = penunjuk.next;
        }

        if (node1 == null || node2 == null) {
            System.out.println("Gagal swap, nodenya gada");
            return;
        }
        
        // Cukup tukar datanya saja untuk simplisitas
        char temp = node1.data;
        node1.data = node2.data;
        node2.data = temp;
        System.out.println("Data '" + data1 + "' dan '" + data2 + "' ditukar.");
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

        antrianHuruf.tambah('D');
        antrianHuruf.cetakAntrian();

        antrianHuruf.tambah('E');
        antrianHuruf.cetakAntrian();
        
        System.out.println("Karakter di depan: '" + antrianHuruf.lihat() + "'");

        System.out.println("Ngeswap 'B' dan 'D': ");
        antrianHuruf.swap('B', 'D');
        antrianHuruf.cetakAntrian();
        
        antrianHuruf.keluar(); //a keluar
        antrianHuruf.cetakAntrian();
        
        System.out.println("Karakter di depan skrg: '" + antrianHuruf.lihat() + "'");

        antrianHuruf.keluar();
        antrianHuruf.keluar();
        antrianHuruf.keluar();
        antrianHuruf.keluar();

        antrianHuruf.cetakAntrian(); //cetak pas kosong
        
        antrianHuruf.keluar(); //nyoba method keluar pas kosong
        
    }
}
