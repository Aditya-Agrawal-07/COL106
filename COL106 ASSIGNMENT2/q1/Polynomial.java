public class Polynomial extends LinkedList{
    
    public Polynomial add(Polynomial p){
        //to be implemented by the student
        Polynomial sum = new Polynomial();
        if (len()>p.len()){
            int diff = len() - p.len();
            Node ptr1 = head;
            while (diff-- > 0){
                sum.insert(ptr1.data);
                ptr1 = ptr1.next;
            }
            Node ptr2 = p.head;
            while (ptr1!=null){
                sum.insert(ptr1.data + ptr2.data);
                ptr1 = ptr1.next;
                ptr2 = ptr2.next;
            }
        }
        else{
            int diff = p.len() - len();
            Node ptr2 = p.head;
            while (diff-- > 0){
                sum.insert(ptr2.data);
                ptr2 = ptr2.next;
            }
            Node ptr1 = head;
            while (ptr1!=null){
                sum.insert(ptr1.data + ptr2.data);
                ptr1 = ptr1.next;
                ptr2 = ptr2.next;
            }
        }
        while (sum.head.data == 0 && sum.head.next!=null){
            sum.head = sum.head.next;
        }
        return sum;
    }

    public Polynomial mult(Polynomial p){
        //to be implemented by the student
        Polynomial mul = new Polynomial();
        int degree = len() + p.len() - 2;
        while (degree-- >= 0){mul.insert(0);}
        Node ptr1 = head;
        Node mulptr1 = mul.head;
        while (ptr1!=null){
            Node ptr2 = p.head;
            Node mulptr2 = mulptr1;
            while (ptr2!=null){
                mulptr2.data += ptr1.data * ptr2.data;
                ptr2 = ptr2.next;
                mulptr2 = mulptr2.next;
            }
            ptr1 = ptr1.next;
            mulptr1 = mulptr1.next;
        }
        while (mul.head.data == 0 && mul.head.next!=null){
            mul.head = mul.head.next;
        }
        return mul;
    }


}