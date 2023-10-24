public class BalancedBST extends BST {

    public BSTNode falseroot;

    public void insert(int key){
        //System.out.println("Insert"+key);
        if(root == null){
            BSTNode node = new BSTNode(key);
            root = node;
            return;
        }
        insert_recursive(root,key);
    }

    public int insert_recursive(BSTNode node,int key){
        int flag = -1;
        if(key < node.value){
            if(node.left == null){
                BSTNode leaf = new BSTNode(key);
                leaf.height = 1;
                node.left = leaf;
                
                if(node.left == null)
                    node.height = node.right.height + 1;
                else if (node.right == null)
                    node.height = node.left.height + 1;
                else
                    node.height = Math.max(node.left.height,node.right.height) + 1;

                return 1;
            }
            else
                flag = insert_recursive(node.left,key);
        }
        else{
            if(node.right == null){
                BSTNode leaf = new BSTNode(key);
                leaf.height = 1;
                node.right = leaf;
                
                ht_update(node);

                return 1;
            }
            else
                flag = insert_recursive(node.right,key);
        }
        
        if(flag == 0)   return 0;

        ht_update(node);

        int x = 1;//temp
        if(node.left == null || node.right == null)     
            x = x + 1;
        else if(node.left == null && node.right.height == 1)    return 1;
        else if(node.right == null && node.left.height == 1)    return 1;
        else if(Math.abs(node.left.height - node.right.height)<=1)  return 1;
        
        balance(node);
        return 0;
    }

    public boolean delete(int key){
        //System.out.println("Delete"+key);
        if(root.height == 1){
            root = null;
            return true;
        }
        int check = 0;

        if(root.value == key){
            //False root
            BSTNode fr = new BSTNode(key+1);
            falseroot = fr;
            falseroot.left = root;
            check = delete_recursive(root,falseroot,key);
            falseroot = null;
        }
        else if(root.value < key){
            check = delete_recursive(root.right,root,key);
        }
        else{
            check = delete_recursive(root.left,root,key);
        }

        balance(root);
        ht_update(root);

        if(check == 0)  return false;
        return true;
    }

    public int delete_recursive(BSTNode node,BSTNode parent, int key){
        int flag = -1;
        if(node.value < key){
            if(node.right == null)
                return 0;
            else
                flag = delete_recursive(node.right,node,key);
        }

        else if(node.value > key){
            if(node.left == null)
                return 0;
            else
                flag = delete_recursive(node.left,node,key);
        }
        
        else{
            if(node.left == null || node.right == null){
                delete0or1(node, parent);
                flag = 1;
            }

            else{
                flag = 1;
                BSTNode l = node.left;
                if(l.right == null){
                    int v1 = node.value;
                    int v2 = l.value;
                    node.value = v2;
                    l.value = v1;
                    delete0or1(l, node);
                }
                else{
                    flag = 1;
                    node.value = right_hunt(l);
                }
            }
        }

        if(flag == 0)
            return 0;

        int x = 1;
        if((node.left == null && node.right == null)) ht_update(node);
        else if(node.left == null){
            if(node.right.height == 1)
                node.height = 2;
                //ht_update(node);
            else{
                balance(node);
                ht_update(node);
            }
        }
        else if(node.right == null){
            if(node.left.height == 1)
                ht_update(node);
            else{
                balance(node);
                ht_update(node);
            }
        }
        else if(Math.abs(node.left.height - node.right.height)<=1)  ht_update(node);
        else{
            balance(node);
            ht_update(node);
        }

        return flag;
    }

    public int right_hunt(BSTNode node){
        if(node.right.right == null){
            int v = node.right.value;
            node.right = node.right.left;
            balance(node);
            ht_update(node); 
            return v;
        }
        else{
            int val = right_hunt(node.right);
            balance(node);
            ht_update(node);
            return val;
        }  
    }

    public void delete0or1(BSTNode node, BSTNode parent){
        if(node.left == null && node.right == null){
            if(parent.left == node){
                parent.left = null;
            }
            else{
                parent.right = null;
            }
        }

        else if(node.left == null){
            if(parent.left == node){
                parent.left = node.right;
            }
            else{
                parent.right = node.right;
            }
        }

        else if(node.right == null){
            if(parent.left == node){
                parent.left = node.left;
            }
            else{
                parent.right = node.left;
            }
        }
    }

    public void balance(BSTNode node){
        int lh = (node.left == null)?0:node.left.height;
        int rh = (node.right == null)?0:node.right.height;

        if(lh > rh + 1){
            int llh = (node.left.left == null)?0:node.left.left.height;
            int lrh = (node.left.right == null)?0:node.left.right.height;
            if(llh >= lrh){
                llcase(node);
            }
            else{
                lrcase(node);
            }
        }
        else if (rh > lh + 1){
            int rrh = (node.right.right == null)?0:node.right.right.height;
            int rlh = (node.right.left == null)?0:node.right.left.height;
            if(rrh >= rlh){
                rrcase(node);
            }
            else{
                rlcase(node);
            }
        }
    }

    public void llcase(BSTNode node){
        BSTNode q_new = new BSTNode(node.value);
        q_new.left = node.left.right;
        q_new.right = node.right;

        BSTNode p = node.left;
        node.value = node.left.value;
        node.left = p.left;
        node.right = q_new;

        ht_update(q_new);
        ht_update(node);
    }

    public void rrcase(BSTNode node){
        BSTNode q_new = new BSTNode(node.value);
        q_new.left = node.left;
        q_new.right = node.right.left;

        BSTNode p = node.right;
        node.value = node.right.value;
        node.left = q_new;
        node.right = p.right;

        ht_update(q_new);
        ht_update(node);
    }

    public void lrcase(BSTNode node){
        llcase(node.left);
        rrcase(node);
    }

    public void rlcase(BSTNode node){
        rrcase(node.right);
        llcase(node);
    }

    public void ht_update(BSTNode node){
        if(node.left == null && node.right == null)
            node.height = 1;
        else if(node.left == null)
            node.height = node.right.height + 1;
        else if (node.right == null)
            node.height = node.left.height + 1;
        else
            node.height = Math.max(node.left.height,node.right.height) + 1;
    }

}