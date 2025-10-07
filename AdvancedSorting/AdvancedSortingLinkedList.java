package AdvancedSorting;

import java.util.Random;

public class AdvancedSortingLinkedList extends DoubleLinkedList{
    void randomizer(int length, int bound){
        if(bound == 0){
            bound = 1000;
        }

        Random random = new Random();
        for(int i = 0; i < length; i++){
            insertAtEnd(random.nextInt(bound), i);
        }
    }

    AdvancedSortingLinkedList cloner(){
        AdvancedSortingLinkedList list2 = new AdvancedSortingLinkedList();

        Node walker = head;
        int indexValue = head.indexAt;
        while(walker != null){
            list2.insertAtEnd(walker.value, indexValue);
            walker = walker.next;
            if(walker != null){
                indexValue = walker.indexAt;
            }
        }

        return list2;
    }


    //MERGE SORT STARTS HERE
    public Node split(Node head) {
        Node fast = head;
        Node slow = head;

        while (fast != null && fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        Node temp = slow.next;
        slow.next = null;
        if (temp != null) {
            temp.prev = null;
        }
        return temp;
    }

    public Node merge(Node first, Node second) {
        if (first == null)
            return second;
        if (second == null)
            return first;

        if (first.value < second.value) {
            first.next = merge(first.next, second);
            if (first.next != null) {
                first.next.prev = first;
            }
            first.prev = null;
            return first;
        } else {
            second.next = merge(first, second.next);
            if (second.next != null) {
                second.next.prev = second;
            }
            second.prev = null;
            return second;
        }
    }

    public Node mergeSortRecursive(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node second = split(head);
        head = mergeSortRecursive(head);
        second = mergeSortRecursive(second);
        return merge(head, second);
    }

    public void mergeSort() {
        this.head = mergeSortRecursive(this.head);
        updateTail();
    }

    private void updateTail() {
        if (head == null) {
            tail = null;
            return;
        }
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        tail = current;

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

        this.head = sortedResult.head;
        this.tail = sortedResult.tail;

    }


    private PartitionResult quickSortRecursive(Node low, Node high) {
        if (low == null || high == null || low == high) {
            return new PartitionResult(low, high);
        }


        Node lesserHead = null, lesserTail = null;
        Node equalHead = null, equalTail = null;
        Node greaterHead = null, greaterTail = null;

        int pivotValue = high.value;
        Node current = low;
        while (current != null) {
            Node next = current.next;
            current.next = null;
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

            if (current == high) break;
            current = next;
        }

        PartitionResult sortedLesser = quickSortRecursive(lesserHead, lesserTail);
        PartitionResult sortedGreater = quickSortRecursive(greaterHead, greaterTail);

        if (sortedLesser.head != null) {
            sortedLesser.tail.next = equalHead;
            equalHead.prev = sortedLesser.tail;
            equalTail.next = sortedGreater.head;
            if(sortedGreater.head != null) sortedGreater.head.prev = equalTail;

            Node newHead = sortedLesser.head;
            Node newTail = sortedGreater.tail != null ? sortedGreater.tail : equalTail;
            return new PartitionResult(newHead, newTail);
        } else {
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
            return;
        }

        int n = 0;
        Node current = head;
        while (current != null) {
            n++;
            current = current.next;
        }

        for (int gap = n / 2; gap > 0; gap /= 2) {
            insertionSortWithGap(gap);
        }
        updateIndices();
    }

    private void insertionSortWithGap(int gap) {
        for (int startIndex = 0; startIndex < gap; startIndex++) {
            Node startNode = getNodeAtIndex(startIndex + gap);

            Node current = startNode;
            while (current != null) {
                int tempValue = current.value;
                Node insertionPoint = current;

                while (true) {
                    Node prevNode = insertionPoint;
                    for (int i = 0; i < gap && prevNode != null; i++) {
                        prevNode = prevNode.prev;
                    }

                    if (prevNode == null || prevNode.value <= tempValue) {
                        break;
                    }

                    insertionPoint.value = prevNode.value;
                    insertionPoint = prevNode;
                }

                insertionPoint.value = tempValue;

                Node next = current;
                for (int i = 0; i < gap && next != null; i++) {
                    next = next.next;
                }
                current = next;
            }
        }
    }

    public Node getNodeAtIndex(int index) {
        Node current = head;
        for (int i = 0; i < index && current != null; i++) {
            current = current.next;
        }
        return current;
    }

    private void updateIndices() {
        Node current = head;
        int index = 0;
        while (current != null) {
            current.indexAt = index++;
            current = current.next;
        }
    }
    //SHELL SORT ENDS HERE

    public static void main(String[] args) {
        AdvancedSortingLinkedList list = new AdvancedSortingLinkedList();

        list.randomizer(10, 1000);
        System.out.println("Initial List");
        list.traverseForward();
        System.out.println();

        AdvancedSortingLinkedList list2;
        list2 = list.cloner();
        System.out.println("\u001B[32mMERGE SORT\u001B[0m");
        long startime1 = System.nanoTime();
        list2.mergeSort();
        long endtime1 = System.nanoTime();
        System.out.println("RESULT");
        list2.traverseForward();
        double durationTime3 = (endtime1 - startime1) / 1_000_000.0;
        System.out.print("\u001B[32m TIME ELAPSED:\u001B[0m ");
        System.out.println("\u001B[31m" + durationTime3 + "\u001B[0m ms\n");

        AdvancedSortingLinkedList list3 = list.cloner();
        System.out.println("\u001B[32mQUICK SORT\u001B[0m");
        startime1 = System.nanoTime();
        list3.quickSort();
        endtime1 = System.nanoTime();
        System.out.println("RESULT");
        list3.traverseForward();
        durationTime3 = (endtime1 - startime1) / 1_000_000.0;
        System.out.print("\u001B[32m TIME ELAPSED:\u001B[0m ");
        System.out.println("\u001B[31m" + durationTime3 + "\u001B[0m ms\n");

        AdvancedSortingLinkedList list4 = list.cloner();
        System.out.println("\u001B[32mSHELL SORT\u001B[0m");
        startime1 = System.nanoTime();
        list4.shellSort();
        endtime1 = System.nanoTime();
        System.out.println("RESULT");
        list4.traverseForward();
        durationTime3 = (endtime1 - startime1) / 1_000_000.0;
        System.out.print("\u001B[32m TIME ELAPSED:\u001B[0m ");
        System.out.println("\u001B[31m" + durationTime3 + "\u001B[0m ms");
    }
}
