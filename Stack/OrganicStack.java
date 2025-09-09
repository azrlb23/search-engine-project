public class OrganicStack {

    Node head;
    OrganicStack(){
        this.head = null;
    }

    class Node{
        Node next;
        char value;

        Node(char value){
            this.next = null;
            this.value = value;
        }
    }

    public boolean isEmpty(){
        return head == null;
    }

    public void push(char value){
        Node newNode = new Node(value);
        newNode.next = head;
        head = newNode;
    }

    public void pop(){
        if (isEmpty()){
            return;
        }
        head = head.next;
    }

    public char peek(){
        if(head != null){
            return head.value;
        }
        return '\0'; // perlu perbaikan lagi
    }

    public void swap(char x, char y){
        Node currNode = head, prevNode = null;
        Node currX= null, prevX= null;
        Node currY = null, prevY= null;

        while(currNode != null){
            if(currNode.value == x){
                prevX = prevNode;
                currX = currNode;
            }else if (currNode.value == y) {
                prevY = prevNode;
                currY = currNode;
            }
            prevNode = currNode;
            currNode = currNode.next;
        }

        if(currX == null || currY == null){
            return;
        }

        //cek kalau nilai x di head
        if(prevX != null){
            prevX.next = currY;
        }else{
            head = currY;
        }

        //cek kalau nilai y di head
        if(prevY != null){
            prevY.next = currX;
        }else{
            head = currX;
        }

        Node temp = currY.next;
        currY.next = currX.next;
        currX.next = temp;

    }

    public void printStack(){
        if(isEmpty()){
            System.out.println("Stack is empty");
        }

        Node currNode = head;

        while(currNode != null){
            System.out.print(currNode.value);
            currNode = currNode.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        OrganicStack newStack = new OrganicStack();

        newStack.push('a');
        newStack.push('b');
        newStack.push('c');
        newStack.push('d');
        newStack.printStack();
//        System.out.println(newStack.peek());
        newStack.swap('b', 'a');
        newStack.printStack();
    }
}
