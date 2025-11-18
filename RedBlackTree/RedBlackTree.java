package RedBlackTree;

import java.io.IOException;
import java.util.LinkedList; // Diperlukan untuk Level-Order
import java.util.Queue;       // Diperlukan untuk Level-Order
import java.util.Scanner;

import RedBlackTreeChar.MenuHelper;

class MenuHelper {
    public static void pressEnter() {
        System.out.println("\nTEKAN ENTER UNTUK MELANJUTKAN");
        try {
            System.in.read(new byte[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class RedBlackTree {

    private final int RED = 0;
    private final int BLACK = 1;

    private class Node {
        int key;
        Node parent;
        Node left;
        Node right;
        int color; // 0 untuk Merah, 1 untuk Hitam

        Node(int key) {
            this.key = key;
            this.color = RED; // Node baru selalu merah
            this.left = TNULL;
            this.right = TNULL;
            this.parent = null;
        }
    }

    private Node root;
    private final Node TNULL;

    public RedBlackTree() {
        TNULL = new Node(0); // Nilai key tidak penting
        TNULL.color = BLACK;
        TNULL.left = null;
        TNULL.right = null;
        root = TNULL;
    }

    // --- ROTASI ---
    private void leftRotate(Node x) {
        Node y = x.right;       // 1. Tentukan y
        x.right = y.left;     // 2. Pindahkan subtree kiri y ke subtree kanan x
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;  // 3. Hubungkan parent x ke y
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;           // 4. Jadikan x sebagai anak kiri y
        x.parent = y;
    }

    private void rightRotate(Node x) {
        Node y = x.left;        // 1. Tentukan y
        x.left = y.right;     // 2. Pindahkan subtree kanan y ke subtree kiri x
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;  // 3. Hubungkan parent x ke y
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;          // 4. Jadikan x sebagai anak kanan y
        x.parent = y;
    }

    // --- INSERTION ---
    public void insert(int key) {
        Node node = new Node(key);
        node.parent = null;
        node.left = TNULL;
        node.right = TNULL;

        Node y = null;
        Node x = this.root;

        while (x != TNULL) {
            y = x;
            if (node.key < x.key) {
                x = x.left;
            } else if (node.key > x.key) {
                x = x.right;
            } else {
                // Mencegah duplikat
                System.out.println("Nilai " + key + " sudah ada. Duplikat tidak diizinkan.");
                return;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.key < y.key) {
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null) {
            node.color = BLACK;
            return;
        }

        if (node.parent.parent == null) {
            return;
        }

        insertFix(node);
    }

    private void insertFix(Node k) {
        Node u;
        while (k.parent.color == RED) {
            if (k.parent == k.parent.parent.right) { // Parent adalah anak KANAN
                u = k.parent.parent.left; // Paman adalah anak KIRI
                if (u.color == RED) { // Kasus 1: Paman MERAH
                    u.color = BLACK;
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else { // Paman HITAM
                    if (k == k.parent.left) { // Kasus 2: Paman HITAM, k adalah anak KIRI (segitiga)
                        k = k.parent;
                        rightRotate(k);
                    }
                    // Kasus 3: Paman HITAM, k adalah anak KANAN (garis lurus)
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    leftRotate(k.parent.parent);
                }
            } else { // Parent adalah anak KIRI (simetris)
                u = k.parent.parent.right; // Paman adalah anak KANAN
                if (u.color == RED) { // Kasus 1
                    u.color = BLACK;
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) { // Kasus 2
                        k = k.parent;
                        leftRotate(k);
                    }
                    // Kasus 3
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = BLACK; // Properti 2: Root selalu HITAM
    }

    // --- DELETION ---
    private void rbTransplant(Node u, Node v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }
    
    public void delete(int key) {
        Node z = TNULL;
        Node x, y;
        
        z = findNode(key);
        if (z == TNULL) {
            System.out.println("Nilai " + key + " tidak ditemukan.");
            return;
        }

        y = z;
        int yOriginalColor = y.color;
        
        if (z.left == TNULL) {
            x = z.right;
            rbTransplant(z, z.right);
        } else if (z.right == TNULL) {
            x = z.left;
            rbTransplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                rbTransplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            rbTransplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }

        if (yOriginalColor == BLACK) {
            deleteFix(x);
        }
        System.out.println("Berhasil delete " + key);
    }

    private void deleteFix(Node x) {
        Node s; // s untuk Sibling (saudara)
        while (x != root && x.color == BLACK) {
            if (x == x.parent.left) { // x adalah anak KIRI
                s = x.parent.right; // Saudara adalah anak KANAN
                if (s.color == RED) { // Kasus 1: Saudara MERAH
                    s.color = BLACK;
                    x.parent.color = RED;
                    leftRotate(x.parent);
                    s = x.parent.right;
                }

                if (s.left.color == BLACK && s.right.color == BLACK) { // Kasus 2: Saudara HITAM, kedua anaknya HITAM
                    s.color = RED;
                    x = x.parent;
                } else {
                    if (s.right.color == BLACK) { // Kasus 3: Saudara HITAM, anak kiri MERAH, anak kanan HITAM
                        s.left.color = BLACK;
                        s.color = RED;
                        rightRotate(s);
                        s = x.parent.right;
                    }
                    // Kasus 4: Saudara HITAM, anak kanan MERAH
                    s.color = x.parent.color;
                    x.parent.color = BLACK;
                    s.right.color = BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            } else { // x adalah anak KANAN (simetris)
                s = x.parent.left; // Saudara adalah anak KIRI
                if (s.color == RED) { // Kasus 1
                    s.color = BLACK;
                    x.parent.color = RED;
                    rightRotate(x.parent);
                    s = x.parent.left;
                }

                if (s.right.color == BLACK && s.left.color == BLACK) { // Kasus 2
                    s.color = RED;
                    x = x.parent;
                } else {
                    if (s.left.color == BLACK) { // Kasus 3
                        s.right.color = BLACK;
                        s.color = RED;
                        leftRotate(s);
                        s = x.parent.left;
                    }
                    // Kasus 4
                    s.color = x.parent.color;
                    x.parent.color = BLACK;
                    s.left.color = BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = BLACK;
    }

    // --- FUNGSI HELPER & PENCARIAN ---
    
    private Node minimum(Node node) {
        while (node.left != TNULL) {
            node = node.left;
        }
        return node;
    }

    // ** BARU: Fungsi untuk mencari nilai Maksimum **
    private Node maximum(Node node) {
        while (node.right != TNULL) {
            node = node.right;
        }
        return node;
    }

    private Node findNode(int key) {
        Node node = this.root;
        while (node != TNULL) {
            if (key == node.key) {
                return node;
            }
            if (key < node.key) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return TNULL; // Mengembalikan TNULL jika tidak ditemukan
    }

    // ** BARU: Fungsi Search untuk menu **
    public void search(int key) {
        Node node = findNode(key);
        if (node != TNULL) {
            System.out.println("Nilai " + key + " DITEMUKAN.");
        } else {
            System.out.println("Nilai " + key + " TIDAK DITEMUKAN.");
        }
    }

    // ** BARU: Fungsi Get Node Color untuk menu **
    public void printNodeColor(int key) {
        Node node = findNode(key);
        if (node != TNULL) {
            String color = (node.color == RED) ? "MERAH" : "HITAM";
            System.out.println("Node " + key + " berwarna: " + color);
        } else {
            System.out.println("Nilai " + key + " TIDAK DITEMUKAN.");
        }
    }

    // ** BARU: Fungsi Get Tree Height untuk menu **
    public int getHeight() {
        return getHeightHelper(this.root);
    }

    private int getHeightHelper(Node node) {
        if (node == TNULL) {
            return -1; // Ketinggian pohon kosong adalah -1
        }
        return 1 + Math.max(getHeightHelper(node.left), getHeightHelper(node.right));
    }

    // ** BARU: Fungsi Get Black Height untuk menu **
    public int getBlackHeight() {
        // Cukup hitung node hitam di satu sisi (misal kiri)
        // karena Properti 5 menjamin semuanya sama.
        Node current = this.root;
        int bh = 0;
        while (current != TNULL) {
            if (current.color == BLACK) {
                bh++;
            }
            current = current.left;
        }
        return bh; // Tidak perlu +1 karena TNULL tidak dihitung di loop
    }

    // ** BARU: Fungsi Get Minimum untuk menu **
    public void getMinimum() {
        if (root == TNULL) {
            System.out.println("Pohon kosong.");
            return;
        }
        System.out.println("Nilai Minimum: " + minimum(this.root).key);
    }

    // ** BARU: Fungsi Get Maximum untuk menu **
    public void getMaximum() {
        if (root == TNULL) {
            System.out.println("Pohon kosong.");
            return;
        }
        System.out.println("Nilai Maksimum: " + maximum(this.root).key);
    }


    // --- FUNGSI TRAVERSAL & PRINTING ---

    public void inorder() {
        System.out.print("Inorder (Kiri, Root, Kanan): ");
        inorderHelper(this.root);
        System.out.println();
    }

    private void inorderHelper(Node node) {
        if (node != TNULL) {
            inorderHelper(node.left);
            System.out.print(node.key + " ");
            inorderHelper(node.right);
        }
    }

    // ** BARU: Fungsi Preorder Traversal **
    public void preorder() {
        System.out.print("Preorder (Root, Kiri, Kanan): ");
        preorderHelper(this.root);
        System.out.println();
    }

    private void preorderHelper(Node node) {
        if (node != TNULL) {
            System.out.print(node.key + " ");
            preorderHelper(node.left);
            preorderHelper(node.right);
        }
    }

    // ** BARU: Fungsi Postorder Traversal **
    public void postorder() {
        System.out.print("Postorder (Kiri, Kanan, Root): ");
        postorderHelper(this.root);
        System.out.println();
    }

    private void postorderHelper(Node node) {
        if (node != TNULL) {
            postorderHelper(node.left);
            postorderHelper(node.right);
            System.out.print(node.key + " ");
        }
    }
    
    // ** BARU: Fungsi Level-Order Traversal **
    public void levelOrder() {
        if (root == TNULL) {
            System.out.println("Pohon kosong.");
            return;
        }
        System.out.print("Level-Order: ");
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node temp = queue.poll();
            System.out.print(temp.key + " ");

            if (temp.left != TNULL) {
                queue.add(temp.left);
            }
            if (temp.right != TNULL) {
                queue.add(temp.right);
            }
        }
        System.out.println();
    }
    
    public void printTree() {
        System.out.println("Struktur Pohon (Key (Warna)):");
        printHelper(this.root, "", true);
    }
    
    private void printHelper(Node root, String indent, boolean last) {
        if (root != TNULL) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "   ";
            } else {
                System.out.print("L----");
                indent += "|  ";
            }
            String color = root.color == RED ? "MERAH" : "HITAM";
            System.out.println(root.key + " (" + color + ")");
            
            printHelper(root.left, indent, false);
            printHelper(root.right, indent, true);
        }
    }


    // --- MAIN METHOD (DIPERBARUI) ---
    public static void main(String[] args) {
        RedBlackTree rbt = new RedBlackTree();
        Scanner sc = new Scanner(System.in);
        int option = -1;

        while (option != 0) {
            System.out.println("\n--- 🌳 RED BLACK TREE MENU ---");
            System.out.println("--- Modifikasi ---");
            System.out.println(" 1. Insert Value");
            System.out.println(" 2. Delete Value");
            System.out.println("--- Traversal & Print ---");
            System.out.println(" 3. Print Inorder (Sorted)");
            System.out.println(" 4. Print Preorder");
            System.out.println(" 5. Print Postorder");
            System.out.println(" 6. Print Level-Order");
            System.out.println(" 7. Print Tree Structure (Visual)");
            System.out.println("--- Informasi & Properti ---");
            System.out.println(" 8. Search Value");
            System.out.println(" 9. Get Node Color");
            System.out.println("10. Get Tree Height");
            System.out.println("11. Get Black Height");
            System.out.println("12. Get Minimum Value");
            System.out.println("13. Get Maximum Value");
            System.out.println("--- Keluar ---");
            System.out.println(" 0. Exit");
            System.out.print("Pilihan Anda: ");

            try {
                option = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Input tidak valid, masukkan angka.");
                sc.next(); // Bersihkan buffer scanner
                continue;
            }

            int value; // Dipindahkan ke luar switch
            
            switch (option) {
                case 1:
                    System.out.print("Masukkan nilai untuk di-insert: ");
                    try {
                        value = sc.nextInt();
                        rbt.insert(value);
                        rbt.printTree();
                    } catch (Exception e) {
                        System.out.println("Input tidak valid.");
                        sc.next();
                    }
                    MenuHelper.pressEnter();
                    break;
                case 2:
                    System.out.print("Masukkan nilai untuk di-delete: ");
                     try {
                        value = sc.nextInt();
                        rbt.delete(value);
                        rbt.printTree();
                    } catch (Exception e) {
                        System.out.println("Input tidak valid.");
                        sc.next();
                    }
                    MenuHelper.pressEnter();
                    break;
                case 3:
                    rbt.inorder();
                    MenuHelper.pressEnter();
                    break;
                case 4:
                    rbt.preorder();
                    MenuHelper.pressEnter();
                    break;
                case 5:
                    rbt.postorder();
                    MenuHelper.pressEnter();
                    break;
                case 6:
                    rbt.levelOrder();
                    MenuHelper.pressEnter();
                    break;
                case 7:
                    rbt.printTree();
                    MenuHelper.pressEnter();
                    break;
                case 8:
                    System.out.print("Masukkan nilai untuk dicari: ");
                    try {
                        value = sc.nextInt();
                        rbt.search(value);
                    } catch (Exception e) {
                        System.out.println("Input tidak valid.");
                        sc.next();
                    }
                    MenuHelper.pressEnter();
                    break;
                case 9:
                    System.out.print("Masukkan nilai untuk dicek warnanya: ");
                    try {
                        value = sc.nextInt();
                        rbt.printNodeColor(value);
                    } catch (Exception e) {
                        System.out.println("Input tidak valid.");
                        sc.next();
                    }
                    MenuHelper.pressEnter();
                    break;
                case 10:
                    System.out.println("Ketinggian Pohon (Tree Height): " + rbt.getHeight());
                    MenuHelper.pressEnter();
                    break;
                case 11:
                    System.out.println("Ketinggian Hitam (Black Height): " + rbt.getBlackHeight());
                    MenuHelper.pressEnter();
                    break;
                case 12:
                    rbt.getMinimum();
                    MenuHelper.pressEnter();
                    break;
                case 13:
                    rbt.getMaximum();
                    MenuHelper.pressEnter();
                    break;
                case 0:
                    System.out.println("Keluar dari program.");
                    break;
                default:
                    System.out.println("Opsi tidak valid.");
                    MenuHelper.pressEnter();
                    break;
            }
        }
        sc.close();
    }
}