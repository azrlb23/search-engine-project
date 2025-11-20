package RedBlackTreeChar;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
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

public class RedBlackTreeChar {

    private final int RED = 0;
    private final int BLACK = 1;

    private class Node {
        char key;
        Node parent;
        Node left;
        Node right;
        int color;

        Node(char key) {
            this.key = key;
            this.color = RED;
            this.left = TNULL;
            this.right = TNULL;
            this.parent = null;
        }
    }

    private Node root;
    private final Node TNULL;

    public RedBlackTreeChar() {
        TNULL = new Node('\0');
        TNULL.color = BLACK;
        TNULL.left = null;
        TNULL.right = null;
        root = TNULL;
    }

    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    public void insert(char key) {
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
                System.out.println("Nilai '" + key + "' sudah ada. Duplikat tidak diizinkan.");
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
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left;
                if (u.color == RED) {
                    u.color = BLACK;
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rightRotate(k);
                    }
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right;
                if (u.color == RED) {
                    u.color = BLACK;
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = BLACK;
    }

    public int getHeight() {
        return getHeightHelper(this.root);
    }

    private int getHeightHelper(Node node) {
        if (node == TNULL) {
            return -1;
        }
        return 1 + Math.max(getHeightHelper(node.left), getHeightHelper(node.right));
    }
    
    private void printSpaces(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print(" ");
        }
    }

    public void printTree() {
        if (root == TNULL) {
            System.out.println("Pohon kosong.");
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        int originalHeight = getHeight();
        int visualHeight = originalHeight + 1; 
        int level = 0;
        final int NODE_WIDTH = 10; 

        while (!queue.isEmpty() && level < visualHeight) {
            int levelSize = queue.size();
            int spaces = (int) Math.pow(2, visualHeight - level - 1) - 1;
            int between = (int) Math.pow(2, visualHeight - level) - 1;

            printSpaces(spaces * NODE_WIDTH);

            for (int i = 0; i < levelSize; i++) {
                Node current = queue.poll();

                if (current != TNULL) {
                    String colorStr = (current.color == RED) ? "MERAH" : "HITAM";
                    String output = String.format("%s(%s)", current.key, colorStr);
                    
                    System.out.printf("%-" + NODE_WIDTH + "s", output);

                    queue.add(current.left);
                    queue.add(current.right);
                } else {
                    printSpaces(NODE_WIDTH); 
                    queue.add(TNULL);
                    queue.add(TNULL);
                }

                if (i < levelSize - 1) {
                    printSpaces(between * NODE_WIDTH);
                }
            }
            System.out.println("\n");
            level++;
        }
    }

    public void inorder() {
        System.out.print("Inorder (Kiri, Root, Kanan): ");
        inorderHelper(this.root);
        System.out.println();
    }

    private void inorderHelper(Node node) {
        if (node != TNULL) {
            String colorStr = (node.color == RED) ? "MERAH" : "HITAM";
            inorderHelper(node.left);
            System.out.print(node.key + "|" + colorStr + "|" + " ");
            inorderHelper(node.right);
        }
    }

    public void preorder() {
        System.out.print("Preorder (Root, Kiri, Kanan): ");
        preorderHelper(this.root);
        System.out.println();
    }

    private void preorderHelper(Node node) {
        if (node != TNULL) {
            String colorStr = (node.color == RED) ? "MERAH" : "HITAM";
            System.out.print(node.key + "|" + colorStr + "|" + " ");
            preorderHelper(node.left);
            preorderHelper(node.right);
        }
    }

    public void postorder() {
        System.out.print("Postorder (Kiri, Kanan, Root): ");
        postorderHelper(this.root);
        System.out.println();
    }

    private void postorderHelper(Node node) {
        if (node != TNULL) {
            String colorStr = (node.color == RED) ? "MERAH" : "HITAM";
            postorderHelper(node.left);
            postorderHelper(node.right);
            System.out.print(node.key + "|" + colorStr + "|" + " ");
        }
    }

    public static void main(String[] args) {
        RedBlackTreeChar rbt = new RedBlackTreeChar();
        Scanner sc = new Scanner(System.in);
        
        System.out.println("\n--- PILIH SUMBER DATA ---");
        System.out.println("1. Gunakan Data Statis (thelazydog)");
        System.out.println("2. Input Data Manual");
        System.out.print("Pilihan: ");
        
        int dataSource = 0;
        try {
            dataSource = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Input tidak valid.");
            sc.next();
        }

        if (dataSource == 1) {
            String dataStatis = "thelazydog";
            System.out.println("\n--- Memasukkan Data Statis ---");
            for (int i = 0; i < dataStatis.length(); i++) {
                char c = dataStatis.charAt(i);
                System.out.println("Memasukkan: " + c);
                rbt.insert(c);
            }
        } else if (dataSource == 2) {
            System.out.print("\nMasukkan kata (contoh: PROGRAMMING): ");
            String input = sc.next();
            System.out.println("\n--- Memasukkan Data Input ---");
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                System.out.println("Memasukkan: " + c);
                rbt.insert(c);
            }
        } else {
            System.out.println("Pilihan tidak valid. Memulai dengan pohon kosong.");
        }
        
        System.out.println("\n--- Pohon Awal ---");
        rbt.printTree();
        
        MenuHelper.pressEnter(); 

        int option = -1;
        while (option != 0) {
            System.out.println("\n--- MENU TRAVERSAL RBT ---");
            System.out.println("1. Print Inorder (Terurut)");
            System.out.println("2. Print Preorder");
            System.out.println("3. Print Postorder");
            System.out.println("4. Print Struktur Pohon (Visual)");
            System.out.println("5. Insert Node (Tambah Karakter)"); // MENU INSERT DITAMBAHKAN
            System.out.println("0. Exit");
            System.out.print("Pilihan Anda: ");

            try {
                option = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Input tidak valid.");
                sc.next();
                continue;
            }

            switch (option) {
                case 1:
                    rbt.inorder();
                    MenuHelper.pressEnter();
                    break;
                case 2:
                    rbt.preorder();
                    MenuHelper.pressEnter();
                    break;
                case 3:
                    rbt.postorder();
                    MenuHelper.pressEnter();
                    break;
                case 4:
                    rbt.printTree();
                    MenuHelper.pressEnter();
                    break;
                case 5: // LOGIKA INSERTION DITAMBAHKAN
                    System.out.print("Masukkan karakter untuk insert: ");
                    String inputStr = sc.next();
                    if (inputStr.length() > 0) {
                        char charToInsert = inputStr.charAt(0);
                        rbt.insert(charToInsert);
                        System.out.println("Berhasil insert: " + charToInsert);
                        rbt.printTree();
                    }
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