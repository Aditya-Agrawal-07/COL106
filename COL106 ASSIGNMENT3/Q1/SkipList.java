import java.util.ArrayList;
import java.util.Collections;

public class SkipList {

        public SkipListNode head;
        public SkipListNode tail;
        public int height;
        public Randomizer randomizer;
        private final int NEG_INF = Integer.MIN_VALUE;
        private final int POS_INF = Integer.MAX_VALUE;

        SkipList(){
            /*
            * DO NOT EDIT THIS FUNCTION
            */
            this.head = new SkipListNode(NEG_INF,1);
            this.tail = new SkipListNode(POS_INF,1);
            this.head.next.add(0,this.tail);
            this.tail.next.add(0,null);
            this.height = 1;
            this.randomizer = new Randomizer();
        }

        public boolean delete(int num){
            // TO be completed by students
            SkipListNode current_node = this.head;
            for (int i = (this.height) - 1; i >= 0; i--){
                while (current_node.next.get(i) != null && current_node.next.get(i).value < num){
                    current_node = current_node.next.get(i);
                }
                if (current_node.next.get(i).value == num){
                    current_node.next.set(i, current_node.next.get(i).next.get(i));
                    if (i == 0){
                        if (this.head.next.get(height-1) == tail && this.height > 1){
                            this.head.next.remove(height-1);
                            this.tail.next.remove(height-1);
                            this.head.height--;
                            this.tail.height--;
                            this.height--;
                        }
                        return true;
                    }
                }
            }
            return false;
        }

        public boolean search(int num){
            // TO be completed by students
            SkipListNode current_node = this.head;
            for (int i = (this.height) - 1; i >= 0; i--){
                while (current_node.next.get(i) != null && current_node.next.get(i).value < num){
                    current_node = current_node.next.get(i);
                }
                if (current_node.next.get(i).value == num){return true;}
            }
            return false;
        }

        public Integer upperBound(int num){ 
            // TO be completed by students           
            SkipListNode current_node = this.head;
            for (int i = (this.height) - 1; i >= 0; i--){
                while (current_node.next.get(i) != null && current_node.next.get(i).value < num){
                    current_node = current_node.next.get(i);
                }
            }
            while (current_node.next.get(0) != null && current_node.next.get(0).value <= num){
                current_node = current_node.next.get(0);
            }
            return current_node.next.get(0).value;
        }

        public void insert(int num){
            // TO be completed by students
            SkipListNode prevnode_container[] = new SkipListNode[height];
            SkipListNode current_node = this.head;
            for (int i = (this.height)-1; i >= 0; i--){
                while (current_node.next.get(i) != null && current_node.next.get(i).value < num){
                    current_node = current_node.next.get(i);
                }
                prevnode_container[i] = current_node;
            }
            SkipListNode newnode = new SkipListNode(num, 1);
            newnode.next.add(prevnode_container[0].next.get(0));
            prevnode_container[0].next.set(0, newnode);
            while (randomizer.binaryRandomGen() == true){
                newnode.height++;
                if (newnode.height > this.height){
                    this.height++;
                    this.head.height++;
                    this.tail.height++;
                    this.head.next.add(newnode);
                    newnode.next.add(this.tail);
                    this.tail.next.add(null);
                    break;
                }
                newnode.next.add(prevnode_container[newnode.height-1].next.get(newnode.height-1));
                prevnode_container[newnode.height-1].next.set(newnode.height-1, newnode);
            }
        }

        public void print(){
            /*
            * DO NOT EDIT THIS FUNCTION
            */
            for(int i = this.height ; i>=1; --i){
                SkipListNode it = this.head;
                while(it!=null){
                    if(it.height >= i){
                        System.out.print(it.value + "\t");
                    }
                    else{
                        System.out.print("\t");
                    }
                    it = it.next.get(0);
                }
                System.out.println("null");
            }
        }
}