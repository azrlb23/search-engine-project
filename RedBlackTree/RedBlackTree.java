package RedBlackTree;

import java.io.IOException;
import java.util.Scanner;

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
            } else {
                x = x.right;
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
        
        // 1. Cari node yang akan dihapus
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

    private Node minimum(Node node) {
        while (node.left != TNULL) {
            node = node.left;
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
        return TNULL;
    }

    public void inorder() {
        System.out.print("Inorder (Terkecil ke Terbesar): ");
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
            String color = root.color == RED ? "RED" : "BLACK";
            System.out.println(root.key + " (" + color + ")");
            
            printHelper(root.left, indent, false);
            printHelper(root.right, indent, true);
        }
    }

    public static void main(String[] args) {
        RedBlackTree rbt = new RedBlackTree();
        Scanner sc = new Scanner(System.in);
        int option = -1;

        while (option != 0) {
            System.out.println("\n--- RED BLACK TREE MENU ---");
            System.out.println("1. Insert Value");
            System.out.println("2. Delete Value");
            System.out.println("3. Print Inorder (Sorted)");
            System.out.println("4. Print Tree Structure");
            System.out.println("0. Exit");
            System.out.print(": ");

            try {
                option = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Input tidak valid, masukkan angka.");
                sc.next();
                continue;
            }

            switch (option) {
                case 1:
                    System.out.print("Masukkan nilai untuk di-insert: ");
                    try {
                        int valueToInsert = sc.nextInt();
                        rbt.insert(valueToInsert);
                        System.out.println("Berhasil insert " + valueToInsert);
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
                        int valueToDelete = sc.nextInt();
                        rbt.delete(valueToDelete);
                        System.out.println("Setelah delete " + valueToDelete + ":");
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
                    rbt.printTree();
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