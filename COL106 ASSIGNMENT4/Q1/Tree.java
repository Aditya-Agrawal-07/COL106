import java.util.ArrayList;

public class Tree {

    public TreeNode root;

    public Tree() {
        root = null;
    }


    private boolean is_leaf(TreeNode node){
        if (node.children.isEmpty() == true){
            return true;
        }
        return false;
    }



    private void overflow_helper(TreeNode node, TreeNode parent){
        TreeNode leftnode = new TreeNode();
        TreeNode rightnode = new TreeNode();
        leftnode.s.add(node.s.get(0));
        leftnode.val.add(node.val.get(0));
        if (is_leaf(node) == false){
            leftnode.children.add(node.children.get(0));
            leftnode.children.add(node.children.get(1));
            rightnode.children.add(node.children.get(2));
            rightnode.children.add(node.children.get(3));
            rightnode.children.add(node.children.get(4));
        }
        leftnode.height = node.height;
        rightnode.s.add(node.s.get(2));
        rightnode.s.add(node.s.get(3));
        rightnode.val.add(node.val.get(2));
        rightnode.val.add(node.val.get(3));
        rightnode.height = node.height;
        String s = node.s.get(1);
        int val = node.val.get(1);
        if (node == this.root){
            TreeNode _root = new TreeNode();
            _root.s.add(s);
            _root.val.add(val);
            _root.children.add(leftnode);
            _root.children.add(rightnode);
            _root.height = leftnode.height + 1;
            this.root = _root;
            return;
        }
        int pos;
        if (s.compareTo(parent.s.get(0)) < 0){
            pos = 0;
        }
        else if (parent.s.size() == 1 || s.compareTo(parent.s.get(1)) < 0){
            pos = 1;
        }
        else if (parent.s.size() == 2 || s.compareTo(parent.s.get(2)) < 0){
            pos = 2;
        }
        else{
            pos = 3;
        }
        parent.s.add(pos, s);
        parent.val.add(pos, val);
        parent.children.set(pos, leftnode);
        parent.children.add(pos + 1, rightnode);
    }



    private void insert_helper(String s, TreeNode node, TreeNode parent){
        if (is_leaf(node) == true){
            int i;
            for (i = 0; i < node.s.size(); i++){
                if (s.compareTo(node.s.get(i)) < 0){
                    node.s.add(i, s);
                    node.val.add(i, 1);
                    break;
                }
            }
            if (i == node.s.size()){
                node.s.add(s);
                node.val.add(1);
            }
        }
        else{
            if (s.compareTo(node.s.get(0)) < 0){
                insert_helper(s, node.children.get(0), node);
            }
            else if (node.s.size() == 1 || s.compareTo(node.s.get(1)) < 0){
                insert_helper(s, node.children.get(1), node);
            }
            else if (node.s.size() == 2 || s.compareTo(node.s.get(2)) < 0){
                insert_helper(s, node.children.get(2), node);
            }
            else{
                insert_helper(s, node.children.get(3), node);
            }
        }
        if (node.s.size() == 4){
            overflow_helper(node, parent);
        }
        return;
    }

    public void insert(String s) {
        // TO be completed by students
        if (this.root == null){
            TreeNode newnode = new TreeNode();
            newnode.s.add(s);
            newnode.val.add(1);
            this.root = newnode;
            return;
        }
        else{
            insert_helper(s, this.root, null);
        }
    }







    private void internal_deletion(TreeNode node, TreeNode parent, TreeNode internal_node, int pos){
        if (is_leaf(node) == true){
            String str = internal_node.s.get(pos);
            int val = internal_node.val.get(pos);
            internal_node.s.set(pos, node.s.get(node.s.size() - 1));
            internal_node.val.set(pos, node.val.get(node.val.size() - 1));
            node.s.set(node.s.size() - 1, str);
            node.val.set(node.val.size() - 1, val);
            delete_helper1(node, parent, node.s.size() - 1);
        }
        else{
            internal_deletion(node.children.get(node.children.size() - 1), node, internal_node, pos);
        }
        cascading_helper(node, parent);
        return;
    }

    private void rotator(TreeNode node1, int pos1, TreeNode node2, int pos2, TreeNode parent, int par_pos){
        if (node1.s.size() > 0){
            node1.s.set(pos1, parent.s.get(par_pos));
            node1.val.set(pos1, parent.val.get(par_pos));
        }
        else{
            node1.s.add(parent.s.get(par_pos));
            node1.val.add(parent.val.get(par_pos));
        }
        parent.s.set(par_pos, node2.s.get(pos2));
        parent.val.set(par_pos, node2.val.get(pos2));
        node2.s.remove(pos2);
        node2.val.remove(pos2);
    }

    private boolean delete_helper1(TreeNode node, TreeNode parent, int pos){
        if (is_leaf(node) == true){
            if (node.s.size() > 1){
                node.s.remove(pos);
                node.val.remove(pos);
                return true;
            }
            else if (node == this.root){
                this.root = null;
                return true;
            }
            else{
                int node_index = parent.children.indexOf(node);
                if (node_index == 0){
                    if (parent.children.get(1).s.size() > 1){
                        rotator(node, 0, parent.children.get(1), 0, parent, 0);
                        return true;
                    }
                    else{
                        TreeNode newnode = new TreeNode();
                        newnode.s.add(parent.s.get(0));
                        newnode.val.add(parent.val.get(0));
                        newnode.s.add(parent.children.get(1).s.get(0));
                        newnode.val.add(parent.children.get(1).val.get(0));
                        parent.s.remove(0);
                        parent.val.remove(0);
                        parent.children.set(0, newnode);
                        parent.children.remove(1);
                        return true;
                    }
                }
                else{
                    if (parent.children.get(node_index-1).s.size() > 1){
                        rotator(node, 0, parent.children.get(node_index-1), parent.children.get(node_index-1).s.size() - 1, parent, node_index-1);
                        return true;
                    }
                    else{
                        TreeNode newnode = new TreeNode();
                        newnode.s.add(parent.children.get(node_index - 1).s.get(0));
                        newnode.val.add(parent.children.get(node_index - 1).val.get(0));
                        newnode.s.add(parent.s.get(node_index - 1));
                        newnode.val.add(parent.val.get(node_index - 1));
                        parent.s.remove(node_index - 1);
                        parent.val.remove(node_index - 1);
                        parent.children.set(node_index - 1, newnode);
                        parent.children.remove(node_index);
                        return true;
                    }
                }
            }
        }
        else{
            TreeNode pred_node = node.children.get(pos);
            internal_deletion(pred_node, node, node, pos);
            return true;
        }
    }

    private void cascading_helper(TreeNode node, TreeNode parent){
        if (node.s.size() > 0){return;}
        int node_index = parent.children.indexOf(node);
        if (node_index == 0){
            if (parent.children.get(1).s.size() > 1){
                rotator(node, 0, parent.children.get(1), 0, parent, 0);
                node.children.add(parent.children.get(1).children.get(0));
                parent.children.get(1).children.remove(0);
                return;
            }
            else{
                TreeNode newnode = new TreeNode();
                newnode.height = node.height;
                newnode.s.add(parent.s.get(0));
                newnode.val.add(parent.val.get(0));
                newnode.s.add(parent.children.get(1).s.get(0));
                newnode.val.add(parent.children.get(1).val.get(0));
                newnode.children.add(node.children.get(0));
                newnode.children.add(parent.children.get(1).children.get(0));
                newnode.children.add(parent.children.get(1).children.get(1));
                parent.s.remove(0);
                parent.val.remove(0);
                parent.children.set(0, newnode);
                parent.children.remove(1);
                return;
            }
        }
        else{
            if (parent.children.get(node_index - 1).s.size() > 1){
                rotator(node, 0, parent.children.get(node_index-1), parent.children.get(node_index-1).s.size() - 1, parent, node_index-1);
                node.children.add(0, parent.children.get(node_index - 1).children.get(parent.children.get(node_index - 1).children.size() - 1));
                parent.children.get(node_index - 1).children.remove(parent.children.get(node_index - 1).children.size() - 1);
                return;
            }
            else{
                TreeNode newnode = new TreeNode();
                newnode.height = node.height;
                newnode.s.add(parent.children.get(node_index - 1).s.get(0));
                newnode.val.add(parent.children.get(node_index - 1).val.get(0));
                newnode.s.add(parent.s.get(node_index - 1));
                newnode.val.add(parent.val.get(node_index - 1));
                newnode.children.add(parent.children.get(node_index - 1).children.get(0));
                newnode.children.add(parent.children.get(node_index - 1).children.get(1));
                newnode.children.add(node.children.get(0));
                parent.s.remove(node_index - 1);
                parent.val.remove(node_index - 1);
                parent.children.set(node_index - 1, newnode);
                parent.children.remove(node_index);
                return;
            }
        }
    }

    private boolean delete_helper(String s, TreeNode node, TreeNode parent){
        if (node == null){return false;}
        boolean bool = false;
        for (int i = 0; i < node.s.size(); i++){
            if (s.compareTo(node.s.get(i)) == 0){
                bool = delete_helper1(node, parent, i);
            }
        }
        if (bool == false){
            if (node.children.isEmpty() == true){return false;}
            else if (s.compareTo(node.s.get(0)) < 0){
                bool = delete_helper(s, node.children.get(0), node);
            }
            else if (node.s.size() == 1 || s.compareTo(node.s.get(1)) < 0){
                bool = delete_helper(s, node.children.get(1), node);
            }
            else if (node.s.size() == 2 || s.compareTo(node.s.get(2)) < 0){
                bool = delete_helper(s, node.children.get(2), node);
            }
            else{
                bool = delete_helper(s, node.children.get(3), node);
            }
        }
        if (bool == false){
            return false;
        }
        if (node == this.root && node.s.size() == 0){
            this.root= this.root.children.get(0);
        }
        else{
            cascading_helper(node, parent);
        }
        return bool;
    }

    public boolean delete(String s) {
        // TO be completed by students
        if (this.root == null){return false;}
        return delete_helper(s, this.root, null);
    }







    public boolean search_helper(TreeNode node, String s){
        if (node == null){return false;}
        for (int i = 0; i < node.s.size(); i++){
            if (s.compareTo(node.s.get(i)) == 0){return true;}
        }
        if (node.children.isEmpty() == true){return false;}
        if (s.compareTo(node.s.get(0)) < 0){
            return search_helper(node.children.get(0), s);
        }
        if (node.s.size() == 1 || s.compareTo(node.s.get(1)) < 0){
            return search_helper(node.children.get(1), s);
        }
        if (node.s.size() == 2 || s.compareTo(node.s.get(2)) < 0){
            return search_helper(node.children.get(2), s);
        }
        return search_helper(node.children.get(3), s);
    }

    public boolean search(String s) {
        // TO be completed by students
        if (this.root == null){return false;}
        return search_helper(this.root, s);
    }






    private int increment_helper(TreeNode node, String s){
        for (int i = 0; i < node.s.size(); i++){
            if (s.compareTo(node.s.get(i)) == 0){
                int val = node.val.get(i);
                val++;
                node.val.set(i, val);
                return val;
            }
        }
        if (s.compareTo(node.s.get(0)) < 0){
            return increment_helper(node.children.get(0), s);
        }
        if (node.s.size() == 1 || s.compareTo(node.s.get(1)) < 0){
            return increment_helper(node.children.get(1), s);
        }
        if (node.s.size() == 2 || s.compareTo(node.s.get(2)) < 0){
            return increment_helper(node.children.get(2), s);
        }
        return increment_helper(node.children.get(3), s);
    }
    
    public int increment(String s) {
        // TO be completed by students
        return increment_helper(this.root, s);
    }








    private int decrement_helper(TreeNode node, String s){
        for (int i = 0; i < node.s.size(); i++){
            if (s.compareTo(node.s.get(i)) == 0){
                int val = node.val.get(i);
                val--;
                node.val.set(i, val);
                return val;
            }
        }
        if (s.compareTo(node.s.get(0)) < 0){
            return decrement_helper(node.children.get(0), s);
        }
        if (node.s.size() == 1 || s.compareTo(node.s.get(1)) < 0){
            return decrement_helper(node.children.get(1), s);
        }
        if (node.s.size() == 2 || s.compareTo(node.s.get(2)) < 0){
            return decrement_helper(node.children.get(2), s);
        }
        return decrement_helper(node.children.get(3), s);
    }

    public int decrement(String s) {
        // TO be completed by students
        return decrement_helper(this.root, s);
    }








    public int getHeight() {
        // TO be completed by students
        return this.root.height;
    }








    private int getVal_helper(TreeNode node, String s){
        for (int i = 0; i < node.s.size(); i++){
            if (s.compareTo(node.s.get(i)) == 0){
                return node.val.get(i);
            }
        }
        if (s.compareTo(node.s.get(0)) < 0){
            return getVal_helper(node.children.get(0), s);
        }
        if (node.s.size() == 1 || s.compareTo(node.s.get(1)) < 0){
            return getVal_helper(node.children.get(1), s);
        }
        if (node.s.size() == 2 || s.compareTo(node.s.get(2)) < 0){
            return getVal_helper(node.children.get(2), s);
        }
        return getVal_helper(node.children.get(3), s);
    }

    public int getVal(String s) {
        // TO be completed by students
        return getVal_helper(this.root, s);
    }
}
