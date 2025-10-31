# search-engine-project
Tempat latihan strukdat.
# 🌳 Implementasi Red Black Tree (Java)

Ini adalah implementasi dari struktur data **Red Black Tree (RBT)** menggunakan Java. Program ini ditulis untuk tujuan edukasi, guna mendemonstrasikan konsep-konsep inti dari *Binary Search Tree* (BST) yang dapat menyeimbangkan dirinya sendiri (*self-balancing*).

## 1. Konsep Keseluruhan: "Mengapa RBT?"

`BinarySearchTree` biasa memiliki satu kelemahan fatal: ia rentan terhadap **kasus terburuk**. Jika Anda memasukkan data yang sudah terurut (misal: 10, 20, 30, 40), pohon akan menjadi "miring" atau "berat sebelah", yang mengubahnya menjadi sebuah *Linked List*. Akibatnya, performa untuk *search*, *insert*, dan *delete* anjlok dari $O(\log n)$ menjadi $O(n)$.

**Red Black Tree memecahkan masalah ini.**

RBT adalah sebuah BST yang memberlakukan 5 aturan ketat (properti) untuk memastikan pohon **selalu** dalam keadaan seimbang (atau "cukup seimbang"). Setiap kali Anda melakukan `insert` atau `delete`, pohon secara otomatis memeriksa apakah ada aturan yang dilanggar. Jika ya, ia akan melakukan "perbaikan" (disebut *fixup*) untuk menyeimbangkan kembali strukturnya.

Hasilnya, RBT menjamin performa **$O(\log n)$** untuk *search*, *insert*, dan *delete*, bahkan dalam skenario terburuk sekalipun.

---

## 2. Aturan Inti: 5 Properti Red Black Tree

Sebuah *Binary Search Tree* adalah *Red Black Tree* jika dan hanya jika ia memenuhi 5 properti berikut:

1.  **Properti Node:** Setiap node (simpul) harus berwarna **MERAH** atau **HITAM**.
    * [Implementasi: `private final int RED = 0;`, `private final int BLACK = 1;`]

2.  **Properti Root:** *Root* (akar) dari pohon selalu berwarna **HITAM**.
    * [Implementasi: Aturan ini dijaga di akhir setiap operasi *fixup*. Contoh: `root.color = BLACK;`]

3.  **Properti Daun (NIL):** Semua daun (node `NULL` atau `NIL` di ujung cabang) dianggap berwarna **HITAM**.
    * [Implementasi: Ini adalah konsep `TNULL` yang krusial. Kita tidak menggunakan `null`, tapi satu node "sentinel" `TNULL` yang selalu berwarna HITAM]

4.  **Properti Merah:** Jika sebuah node berwarna **MERAH**, maka kedua anaknya *harus* berwarna **HITAM**.
    * (Ini adalah aturan terpenting: **Tidak boleh ada dua node MERAH berurutan** dalam satu jalur dari root ke daun).

5.  **Properti Hitam:** Setiap jalur dari sebuah node ke *setiap* daun `NIL` di bawahnya harus memiliki jumlah node **HITAM** yang sama.
    * (Jumlah ini disebut **Black-Height**. Inilah yang sebenarnya memaksa pohon untuk seimbang).

---

## 3. Cara Kerja: "Bagaimana RBT Menyeimbangkan Diri?"

Keseimbangan RBT dijaga oleh dua operasi mekanis: **Pewarnaan Ulang (Recoloring)** dan **Rotasi (Rotation)**. Operasi ini dijalankan oleh fungsi *fixup* setiap kali ada `insert` atau `delete`.

### 3.1. Operasi Rotasi

Ini adalah "alat" utama untuk mengubah struktur pohon.
* **`leftRotate(Node x)`:** Mengambil sebuah node `x` dan "memutarnya" ke kiri, sehingga anak kanannya (`y`) naik menjadi induk baru dari `x`.
* **`rightRotate(Node x)`:** Mengambil sebuah node `x` dan "memutarnya" ke kanan, sehingga anak kirinya (`y`) naik menjadi induk baru dari `x`.

### 3.2. Penjelasan `insert(int key)`

1.  **Langkah 1: Insert BST Biasa.**
    * Pohon melakukan *search* untuk menemukan lokasi yang tepat, lalu menyisipkan node baru persis seperti BST biasa.
2.  **Langkah 2: Warnai MERAH.**
    * Node yang baru disisipkan *selalu* diwarnai **MERAH**. Kenapa? Karena menyisipkan node HITAM akan langsung melanggar Properti 5 (Black-Height). Menyisipkan MERAH "hanya" berisiko melanggar Properti 4 (MERAH-MERAH), yang lebih mudah diperbaiki.
3.  **Langkah 3: Panggil `insertFix(Node k)`.**
    * Fungsi ini berjalan dalam *loop* selama ada pelanggaran MERAH-MERAH (induk dari `k` berwarna MERAH).
    * Logika utamanya adalah **memeriksa warna "Paman"** (saudara dari induk).
        * **Kasus 1: Paman MERAH.**
            * Ini kasus mudah. Kita hanya melakukan **Pewarnaan Ulang**: Warnai Induk (Parent) dan Paman (Uncle) menjadi HITAM, warnai Kakek (Grandparent) menjadi MERAH. Lalu, kita ulangi pengecekan dari Kakek.
        * **Kasus 2 & 3: Paman HITAM (atau NIL).**
            * Ini kasus yang memerlukan **Rotasi**. Tujuannya adalah mengubah struktur pohon (baik itu bentuk "segitiga" atau "garis lurus") agar seimbang, lalu mewarnai ulang node yang terlibat untuk memenuhi properti.

### 3.3. Penjelasan `delete(int key)`

Operasi `delete` jauh lebih kompleks karena berisiko melanggar Properti 5 (Black-Height).

1.  **Langkah 1: Delete BST Biasa.**
    * Pohon menemukan node yang akan dihapus (`z`). Ini menggunakan logika `rbTransplant` (untuk mengganti node) dan `minimum` (jika node memiliki dua anak), mirip seperti BST biasa.
2.  **Langkah 2: Panggil `deleteFix(Node x)`.**
    * Fungsi ini dipanggil jika node yang dipindahkan/dihapus (`y`) berwarna **HITAM**.
    * Menghapus node HITAM menciptakan "lubang" atau ketidakseimbangan pada Black-Height (Properti 5). Node `x` (anak dari node yang dihapus) mendapat "ekstra hitam" secara konseptual.
    * Tugas `deleteFix` adalah memperbaiki "ekstra hitam" ini dengan cara mendorongnya ke atas pohon.
    * Logikanya **memeriksa "Saudara" (Sibling)** dari `x`. Berdasarkan warna Saudara dan anak-anaknya, ia akan melakukan serangkaian Rotasi dan Pewarnaan Ulang yang rumit (Kasus 1, 2, 3, dan 4) sampai pohon seimbang kembali.

---

## 4. Detail Implementasi Kunci

* **`private class Node`:** Tidak seperti BST biasa, `Node` RBT **wajib** memiliki dua atribut tambahan:
    1.  `int color`: Menyimpan warna node (MERAH atau HITAM).
    2.  `Node parent`: Penunjuk ke node induk. Ini **krusial** untuk operasi *fixup* dan rotasi.

* **`private final Node TNULL` (Sentinel Node):**
    * Ini adalah "trik" implementasi yang sangat penting. Alih-alih menggunakan `null` di ujung cabang, kita menggunakan **satu objek `TNULL` global**.
    * `TNULL` ini selalu berwarna **HITAM**, yang secara otomatis memenuhi **Properti 3 (Daun NIL adalah HITAM)**.
    * Ini menyederhanakan kode secara drastis, karena kita tidak perlu terus-menerus mengecek `if (node != null)`. Kita cukup mengecek `if (node != TNULL)`, dan kita tahu `TNULL.color` pasti HITAM.

---

## 5. Fitur Program (Menu)

File `RedBlackTree.java` menyertakan `main` method dengan menu interaktif untuk menguji fungsionalitas pohon:

* **1. Insert Value:** Memasukkan nilai baru dan memicu `insertFix`.
* **2. Delete Value:** Menghapus nilai dan memicu `deleteFix`.
* **3. Print Inorder (Sorted):** Menampilkan data terurut (Kiri-Root-Kanan).
* **4. Print Tree Structure:** Mencetak visualisasi pohon (termasuk warna) untuk debugging.
* **0. Exit:** Keluar dari program.