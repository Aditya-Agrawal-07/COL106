public class LinkedList{ 
    
    public Node head;
    
    public LinkedList(){
        head = null;
    }

    public void insert(int c){
        //to be completed by the student
        Node newnode = new Node(c);
        if (head == null){head = newnode;}
        else{
            Node ptr = head;
            while(ptr.next!=null){
                ptr = ptr.next;
            }
            ptr.next = newnode;
        }
    }

    public int len(){
        //to be completed by the student
        int size = 0;
        if (head == null){return 0;}
        Node ptr = head;
        while(ptr!=null){
            ptr = ptr.next;
            size++;
        }
        return size;
    }
}


