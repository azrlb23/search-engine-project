package RedBlackTree;

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

public class RedBlackTree {

    private final int RED = 0;
    private final int BLACK = 1;

    private class Node {
        int key;
        Node parent;
        Node left;
        Node right;
        int color;

        Node(int key) {
            this.key = key;
            this.color = RED;
            this.left = TNULL;
            this.right = TNULL;
            this.parent = null;
        }
    }

    private Node root;
    private final Node TNULL;

    public RedBlackTree() {
        TNULL = new Node(0);
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
        Node s;
        while (x != root && x.color == BLACK) {
            if (x == x.parent.left) {
                s = x.parent.right;
                if (s.color == RED) {
                    s.color = BLACK;
                    x.parent.color = RED;
                    leftRotate(x.parent);
                    s = x.parent.right;
                }

                if (s.left.color == BLACK && s.right.color == BLACK) {
                    s.color = RED;
                    x = x.parent;
                } else {
                    if (s.right.color == BLACK) {
                        s.left.color = BLACK;
                        s.color = RED;
                        rightRotate(s);
                        s = x.parent.right;
                    }
                    s.color = x.parent.color;
                    x.parent.color = BLACK;
                    s.right.color = BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                s = x.parent.left;
                if (s.color == RED) {
                    s.color = BLACK;
                    x.parent.color = RED;
                    rightRotate(x.parent);
                    s = x.parent.left;
                }

                if (s.right.color == BLACK && s.left.color == BLACK) {
                    s.color = RED;
                    x = x.parent;
                } else {
                    if (s.left.color == BLACK) {
                        s.right.color = BLACK;
                        s.color = RED;
                        leftRotate(s);
                        s = x.parent.left;
                    }
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
        return TNULL;
    }

    public void search(int key) {
        Node node = findNode(key);
        if (node != TNULL) {
            System.out.println("Nilai " + key + " DITEMUKAN.");
        } else {
            System.out.println("Nilai " + key + " TIDAK DITEMUKAN.");
        }
    }

    public void printNodeColor(int key) {
        Node node = findNode(key);
        if (node != TNULL) {
            String color = (node.color == RED) ? "MERAH" : "HITAM";
            System.out.println("Node " + key + " berwarna: " + color);
        } else {
            System.out.println("Nilai " + key + " TIDAK DITEMUKAN.");
        }
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

    public int getBlackHeight() {
        Node current = this.root;
        int bh = 0;
        while (current != TNULL) {
            if (current.color == BLACK) {
                bh++;
            }
            current = current.left;
        }
        return bh;
    }

    public void getMinimum() {
        if (root == TNULL) {
            System.out.println("Pohon kosong.");
            return;
        }
        System.out.println("Nilai Minimum: " + minimum(this.root).key);
    }

    public void getMaximum() {
        if (root == TNULL) {
            System.out.println("Pohon kosong.");
            return;
        }
        System.out.println("Nilai Maksimum: " + maximum(this.root).key);
    }

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
    
    private int getHeightForBstPrint(Node node) {
        if (node == TNULL) return 0;
        return 1 + Math.max(getHeightForBstPrint(node.left), getHeightForBstPrint(node.right));
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
        int height = getHeightForBstPrint(root);
        int level = 0;
        final int NODE_WIDTH = 12; 

        while (!queue.isEmpty() && level < height) {
            int levelSize = queue.size();
            int spaces = (int) Math.pow(2, height - level - 1) - 1;
            int between = (int) Math.pow(2, height - level) - 1;

            printSpaces(spaces * NODE_WIDTH);

            for (int i = 0; i < levelSize; i++) {
                Node current = queue.poll();

                if (current != TNULL) {
                    String color = (current.color == RED) ? "MERAH" : "HITAM";
                    String output = String.format("%d(%s)", current.key, color);
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
                sc.next();
                continue;
            }

            int value;
            
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