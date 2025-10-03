package AdvancedSorting;

import java.util.Random;

public class AdvancedSortingLinkedList extends DoubleLinkedList{
    void randomizer(int length, int bound){
        if(bound == 0){
            bound = 1000;
        }

        Random random = new Random();
        for(int i = 0; i < length; i++){
            insertAtEnd(random.nextInt(bound));
        }
    }


    //MERGE SORT STARTS HERE
    public void mergeSort() {
        // Panggil helper rekursif pada seluruh list
        this.head = mergeSortRecursive(this.head);

        // PENTING: Setelah sorting, referensi 'tail' yang lama mungkin tidak valid.
        // Kita harus menemukan dan memperbarui 'tail' yang baru.
        if (this.head == null) {
            this.tail = null;
        } else {
            Node current = this.head;
            while (current.next != null) {
                current = current.next;
            }
            this.tail = current;
        }
    }

    // --- PRIVATE IMPLEMENTATION HELPERS ---

    /**
     * Fungsi rekursif inti untuk Merge Sort.
     * Mengurutkan list yang dimulai dari 'head' dan mengembalikan head baru dari list terurut.
     */
    private Node mergeSortRecursive(Node head) {
        // Base case: jika list kosong atau hanya punya 1 elemen, ia sudah terurut
        if (head == null || head.next == null) {
            return head;
        }

        // Bagi list menjadi dua bagian
        Node secondHalf = split(head);

        // Urutkan kedua bagian secara rekursif
        head = mergeSortRecursive(head);
        secondHalf = mergeSortRecursive(secondHalf);

        // Gabungkan kedua bagian yang sudah terurut
        return merge(head, secondHalf);
    }

    /**
     * Membagi list menjadi dua bagian dari 'head' dan mengembalikan head dari bagian kedua.
     * Menggunakan metode pointer cepat & lambat.
     */
    private Node split(Node head) {
        Node fast = head;
        Node slow = head;

        // Gerakkan fast 2x lebih cepat dari slow
        // Ini akan menempatkan slow di tengah list
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        // Pisahkan list
        Node secondHalf = slow.next;
        slow.next = null;
        if (secondHalf != null) {
            secondHalf.prev = null;
        }
        return secondHalf;
    }

    /**
     * Menggabungkan dua list yang sudah terurut (first dan second).
     * Mengembalikan head dari list yang sudah digabung.
     */
    private Node merge(Node first, Node second) {
        // Base cases
        if (first == null) return second;
        if (second == null) return first;

        // Pilih yang lebih kecil, lalu panggil rekursif untuk sisanya
        if (first.value < second.value) {
            first.next = merge(first.next, second);
            first.next.prev = first; // Set pointer prev
            first.prev = null;      // Head baru tidak punya prev
            return first;
        } else {
            second.next = merge(first, second.next);
            second.next.prev = second; // Set pointer prev
            second.prev = null;       // Head baru tidak punya prev
            return second;
        }
    }
    //MERGE SORT ENDS HERE

    //QUICK SORT STARTS HERE
    private static class PartitionResult {
        Node head;
        Node tail;

        PartitionResult(Node h, Node t) {
            this.head = h;
            this.tail = t;
        }
    }

    public void quickSort() {
        if (head == null || head == tail) {
            return;
        }

        PartitionResult sortedResult = quickSortRecursive(this.head, this.tail);

        // Perbarui head dan tail utama dari list
        this.head = sortedResult.head;
        this.tail = sortedResult.tail;
    }

    private PartitionResult quickSortRecursive(Node low, Node high) {
        if (low == null || high == null || low == high) {
            return new PartitionResult(low, high);
        }

        // 1. PARTISI LIST
        // Pisahkan menjadi 3 bagian: lesser, equal, greater
        Node lesserHead = null, lesserTail = null;
        Node equalHead = null, equalTail = null;
        Node greaterHead = null, greaterTail = null;

        int pivotValue = high.value; // Pivot diambil dari node terakhir
        Node current = low;
        while (current != null) {
            Node next = current.next;
            current.next = null; // Putuskan hubungan node untuk dipindahkan
            current.prev = null;

            if (current.value < pivotValue) {
                if (lesserHead == null) {
                    lesserHead = lesserTail = current;
                } else {
                    lesserTail.next = current; current.prev = lesserTail; lesserTail = current;
                }
            } else if (current.value == pivotValue) {
                if (equalHead == null) {
                    equalHead = equalTail = current;
                } else {
                    equalTail.next = current; current.prev = equalTail; equalTail = current;
                }
            } else {
                if (greaterHead == null) {
                    greaterHead = greaterTail = current;
                } else {
                    greaterTail.next = current; current.prev = greaterTail; greaterTail = current;
                }
            }

            // Berhenti jika sudah memproses node 'high'
            if (current == high) break;
            current = next;
        }

        // 2. URUTKAN BAGIAN REKURSIF
        PartitionResult sortedLesser = quickSortRecursive(lesserHead, lesserTail);
        PartitionResult sortedGreater = quickSortRecursive(greaterHead, greaterTail);

        // 3. GABUNGKAN HASIL
        // Gabungkan lesser yang terurut dengan bagian equal
        if (sortedLesser.head != null) {
            sortedLesser.tail.next = equalHead;
            equalHead.prev = sortedLesser.tail;
            // Gabungkan bagian equal dengan greater yang terurut
            equalTail.next = sortedGreater.head;
            if(sortedGreater.head != null) sortedGreater.head.prev = equalTail;

            Node newHead = sortedLesser.head;
            Node newTail = sortedGreater.tail != null ? sortedGreater.tail : equalTail;
            return new PartitionResult(newHead, newTail);
        } else {
            // Jika tidak ada bagian lesser, gabungkan equal dengan greater
            equalTail.next = sortedGreater.head;
            if(sortedGreater.head != null) sortedGreater.head.prev = equalTail;

            Node newHead = equalHead;
            Node newTail = sortedGreater.tail != null ? sortedGreater.tail : equalTail;
            return new PartitionResult(newHead, newTail);
        }
    }
    //QUICK SORT ENDS HERE

    //SHELL SORT STARTS HERE
    public void shellSort() {
        if (head == null || head.next == null) {
            return; // Tidak ada yang perlu diurutkan
        }

        // 1. Hitung jumlah node (n)
        int n = 0;
        Node current = head;
        while (current != null) {
            n++;
            current = current.next;
        }

        // 2. Lakukan iterasi dengan gap sequence (n/2, n/4, n/8, ..., 1)
        for (int gap = n / 2; gap > 0; gap /= 2) {
            // Panggil gapped insertion sort untuk setiap gap
            insertionSortWithGap(gap);
        }
    }

    private void insertionSortWithGap(int gap) {
        // Mulai dari elemen ke-'gap'
        Node startNode = head;
        for (int i = 0; i < gap; i++) {
            if (startNode != null) {
                startNode = startNode.next;
            }
        }

        // Iterasi dari node awal (elemen ke-'gap') hingga akhir list
        Node current = startNode;
        while (current != null) {
            int tempValue = current.value;
            Node insertionPoint = current;

            // Cari posisi penyisipan yang benar dengan mundur sejauh 'gap'
            while (true) {
                // Temukan node 'gap' langkah di belakang insertionPoint
                Node prevNode = insertionPoint;
                for (int i = 0; i < gap; i++) {
                    if (prevNode != null) {
                        prevNode = prevNode.prev;
                    } else {
                        break;
                    }
                }

                // Jika sudah sampai di awal sublist atau menemukan posisi yang tepat
                if (prevNode == null || prevNode.value <= tempValue) {
                    break;
                }

                // Geser nilai dari prevNode ke posisi insertionPoint saat ini
                insertionPoint.value = prevNode.value;

                // Mundur ke posisi prevNode untuk iterasi berikutnya
                insertionPoint = prevNode;
            }

            // Tempatkan nilai sementara ke posisi penyisipan yang benar
            insertionPoint.value = tempValue;

            current = current.next;
        }
    }

    //SHELL SORT ENDS HERE

    //DEBUGS

    //DEBUGS ENDS HERE

    public static void main(String[] args) {
        AdvancedSortingLinkedList list = new AdvancedSortingLinkedList();

        list.randomizer(100, 1000);
        System.out.println("Initial List");
        list.traverseForward();
        System.out.println();

        list.mergeSort();
//        list.quickSort();
//        list.shellSort();
        System.out.println();
        System.out.println("Done Sorting");
        list.traverseForward();

    }
}
