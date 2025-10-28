package BinarySearchTree;
//print content
//print tree

import AdvancedSorting.DoubleLinkedList;

import java.util.Random;
import java.util.Scanner;

class Node{
    char key;
    Node left, right;

    public Node(char key){
        this.key = key;
        left = right = null;
    }
}

public class BinarySearchTree {
    Node root;

    public BinarySearchTree(){
        root = null;
    }

    void insert(char key){
        root = insertRec(root, key);
    }

    Node insertRec(Node root, char key){
        if(root == null){
            root = new Node(key);
            return root;
        }
        if(key < root.key){
            root.left = insertRec(root.left, key);
        } else if(key > root.key){
            root.right = insertRec(root.right, key);
        }

        return root;
    }

    void delete(char key){
        root = deleteRec(root, key);
    }

    Node deleteRec(Node root, char key){
        if (root == null){
            return root;
        }

        if(key < root.key){
            root.left = deleteRec(root.left, key);
        }else if(key > root.key){
            root.right = deleteRec(root.right, key);
        } else{
            if(root.left == null){
                return root.right;
            } else if(root.right == null){
                return root.left;
            }

            root.key = minValue(root.right);
            root.right = deleteRec(root.right, root.key);
        }
        return root;
    }

    char minValue(Node root){
        char minv = root.key;

        while(root.left != null){
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }

    boolean search(int key){
        return searchRec(root, key);
    }

    boolean searchRec(Node root, int key){
        if(root == null){
            return false;
        }
        if(root.key == key){
            return true;
        }
        if(root.key < key){
            return searchRec(root.right, key);
        }
        return searchRec(root.left, key);
    }

    void inorder(){
        inorderRec(root);
        System.out.println("\n");
    }

    void inorderRec(Node root){
        if(root != null){
            inorderRec(root.left);
            System.out.print(root.key + " ");
            inorderRec(root.right);
        }
    }

    void preorder() {
        preorderRec(root);
        System.out.println("\n");

    }

    void preorderRec(Node root) {
        if (root != null) {
            System.out.print(root.key + " ");
            preorderRec(root.left);
            preorderRec(root.right);
        }
    }

    void postorder() {
        postorderRec(root);
        System.out.println("\n");
    }

    void postorderRec(Node root) {
        if (root != null) {
            postorderRec(root.left);
            postorderRec(root.right);
            System.out.print(root.key + " ");
        }
    }

    //DEBUG START
    int getHeight(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    void printTreeHelper(Node node, int level, int left, int right) {
        if (node == null) return;

        int mid = (left + right) / 2;

        // Print current level
        if (level == 0) {
            printSpaces(mid);
            System.out.println(node.key);
        }

        // Print branches for children
        if (node.left != null || node.right != null) {
            printSpaces(left);
            for (int i = left; i <= right; i++) {
                if (i == (left + mid) / 2 && node.left != null) System.out.print("/");
                else if (i == (mid + right) / 2 && node.right != null) System.out.print("\\");
                else if (i > (left + mid) / 2 && i < mid) System.out.print(" ");
                else if (i > mid && i < (mid + right) / 2) System.out.print(" ");
                else System.out.print(" ");
            }
            System.out.println();
        }

        // Print children on same line
        if (node.left != null || node.right != null) {
            printSpaces((left + mid) / 2);
            if (node.left != null) System.out.print(node.left.key);
            else System.out.print(" ");

            printSpaces(mid - (left + mid) / 2 - 1);
            if (node.right != null) System.out.print(node.right.key);
            System.out.println();
        }

        // Recursively print subtrees
        printTreeHelper(node.left, level + 1, left, mid - 1);
        printTreeHelper(node.right, level + 1, mid + 1, right);
    }

    void printSpaces(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print(" ");
        }
    }

    void printTree() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }

        java.util.Queue<Node> queue = new java.util.LinkedList<>();
        queue.add(root);
        int height = getHeight(root);
        int level = 0;

        while (!queue.isEmpty() && level < height) {
            int levelSize = queue.size();
            int spaces = (int) Math.pow(2, height - level - 1) - 1;
            int between = (int) Math.pow(2, height - level) - 1;

            printSpaces(spaces);

            for (int i = 0; i < levelSize; i++) {
                Node current = queue.poll();

                if (current != null) {
                    System.out.print(current.key);
                    queue.add(current.left);
                    queue.add(current.right);
                } else {
                    System.out.print(" ");
                    queue.add(null);
                    queue.add(null);
                }

                if (i < levelSize - 1) {
                    printSpaces(between);
                }
            }
            System.out.println();
            level++;
        }
    }
    //DEBUG ENDS

    //DEBUG STARTS (SEARCH PRINT LEFT RIGHT)
    boolean printChild(int key){
        return printChildRec(root, key);
    }

    boolean printChildRec(Node root, int key){
        if(root == null){
            return false;
        }
        if(root.key == key){
            if(root.left == null && root.right == null){
                System.out.println("Left: null");
                System.out.println("Right: null");
                return true;
            }
            if(root.left == null){
                System.out.println("Left: null");
                System.out.println("Right: [" + root.right.key + "]");
                return true;
            }
            if(root.right == null){
                System.out.println("Right: null");
                System.out.println("Left: [" + root.left.key + "]");
                return true;
            }
            System.out.println("Left: [" + root.left.key + "]");
            System.out.println("Right: [" + root.right.key + "]");
            return true;
        }
        if(root.key < key){
            return printChildRec(root.right, key);
        }
        return printChildRec(root.left, key);
    }
    //DEBUG ENDS


    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        int option = -1;
        Scanner sc = new Scanner(System.in);
        char value;
        while(option != 0){
            System.out.println("1. Insert manual");
            System.out.println("2. Random bounded insert");
            System.out.println("3. Value Search");
            System.out.println("4. Print Child of");
            System.out.println("5. Value Deletion");
            System.out.println("6. BST Print (Inorder)");
            System.out.println("7. BST Print (Preorder)");
            System.out.println("8. BST Print (Postorder)");
            System.out.println("9. Hierarchy Printer /EXPERIMENTAL/");
            System.out.println("10. Clear Tree");
            System.out.println("\033[31m0. EXIT\033[0m");
            System.out.print(": ");
            option = sc.nextInt();
            switch(option){
                case 1:
                    //insert
                    System.out.print("\033[1;32mValue to insert: \033[0m");
                    String values = sc.next();
                    for(int i = 0; i < values.length(); i++){
                        bst.insert(values.charAt(i));
                    }
                    System.out.print("\033[0;32m");
                    bst.inorder();
                    System.out.print("\033[0m");
                    DoubleLinkedList.pressEnter();
                    break;
                case 2:
                    //Random insert
                    Random random = new Random();
                    System.out.print("\033[1;32mLength: \033[0m");
                    int length = sc.nextInt();
                    char randomCreated;
                    char[] valueList = new char[length];
                    String abc = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

                    for(int i = 0; i < length; i++){
                        randomCreated = abc.charAt(random.nextInt(abc.length()));
                        bst.insert(randomCreated);
                        valueList[i] = randomCreated;
                    }
                    System.out.print("\033[1;32mValue: \033[0m");
                    for(int i = 0; i < valueList.length; i++)
                        System.out.print(" " + valueList[i]);
                    System.out.println();

                    System.out.print("Tree: ");
                    System.out.print("\033[0;32m");
                    bst.inorder();
                    System.out.print("\033[0m");

                    DoubleLinkedList.pressEnter();
                    break;
                case 3:
                    //search
                    System.out.print("\033[1;32mKey To Search: \033[0m");
                    value = sc.next().charAt(0);
                    System.out.println();

                    boolean result = bst.search(value);
                    String finalResult = result ? "\033[31m" + result + "\033[0m" : "\033[32m" + result + "\033[0m";
                    System.out.println("\033[31m0. EXIT\033[0m");
                    System.out.println("Is [" + value + "] exist ? " + finalResult);
                    DoubleLinkedList.pressEnter();
                    break;
                case 4:
                    //search
                    System.out.print("");
                    System.out.print("\033[1;32mChild of to print: \033[0m");
                    value = sc.next().charAt(0);
                    System.out.println();

                    bst.printChild(value);
                    DoubleLinkedList.pressEnter();
                    break;
                case 5:
                    //deletion
                    System.out.print("\033[1;31mKey to delete: \033[0m");
                    value = sc.next().charAt(0);
                    System.out.println();

                    bst.delete(value);
                    System.out.println("After deletion: ");
                    bst.inorder();
                    DoubleLinkedList.pressEnter();
                    break;
                case 6:
                    //print inorder
                    bst.inorder();
                    DoubleLinkedList.pressEnter();
                    break;
                case 7:
                    bst.preorder();
                    DoubleLinkedList.pressEnter();
                    break;
                case 8:
                    bst.postorder();
                    DoubleLinkedList.pressEnter();
                    break;
                case 9:
                    bst.printTree();
                    DoubleLinkedList.pressEnter();
                    break;
                    //add triple traversal
                case 10:
                    bst.root = null;
                    bst.inorder();
                    break;
                case 0:
                    break;
            }
        }

    }
}
