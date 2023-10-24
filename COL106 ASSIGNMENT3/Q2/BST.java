import java.util.*;
public class BST {

    public BSTNode root;

    public BST() {
        root = null;
    }

    public void insert(int num) {
        // TO be completed by students
        BSTNode newnode = new BSTNode(num);
        if (root == null){
            root = newnode;
        }
        else{
            BSTNode currnode = root;
            BSTNode parentnode = root;
            while (currnode != null){
                if (num < currnode.value){
                    parentnode = currnode;
                    currnode = currnode.left;
                }
                else if (num > currnode.value){
                    parentnode = currnode;
                    currnode = currnode.right;
                }
            }
            currnode = newnode;
            if (currnode.value > parentnode.value){
                parentnode.right = currnode;
            }
            else if (currnode.value < parentnode.value){
                parentnode.left = currnode;
            }
        }
    }



    


    public void delete_helper(BSTNode parent, String str){
        BSTNode d_node;
        if (str.equals("left") == true){
            d_node = parent.left;
        }
        else if(str.equals("right") == true){
            d_node = parent.right;
        }
        else{
            d_node = parent;
        }
        BSTNode temp_node = d_node.left;
        if (temp_node.right == null){
            d_node.value = temp_node.value;
            d_node.left = temp_node.left;
        }
        else{
            BSTNode parent_node = temp_node;
            temp_node = temp_node.right;
            while (temp_node.right != null){
                parent_node = temp_node;
                temp_node = temp_node.right;
            }
            d_node.value = temp_node.value;
            parent_node.right = temp_node.left;
        }
    }

    public boolean delete(int num) {
        // TO be completed by students
		if (root == null){return false;}
        if (root.value == num){
            if (root.left == null && root.right == null){
                root.height = 0;
                root = null;
            }
            else if (root.left == null){
                root = root.right;
            }
            else if (root.right == null){
                root = root.left;
            }
            else {
                delete_helper(root,"root");
            }
            return true;
        }
        BSTNode currnode = root;
        while (currnode != null){
            if (currnode.left == null && currnode.right == null){
                return false;
            }
            else if (currnode.left == null){
                if (currnode.value > num){
                    return false;
                }
                else if (currnode.right.value == num){
                    break;
                }
                else{
                    currnode = currnode.right;
                }
            }
            else if (currnode.right == null){
                if (currnode.value < num){
                    return false;
                }
                else if (currnode.left.value == num){
                    break;
                }
                else{
                    currnode = currnode.left;
                }
            }
            else{
                if (currnode.left.value == num || currnode.right.value == num){
                    break;
                }
                else if (currnode.value < num){
                    currnode = currnode.right;
                }
                else{
                    currnode = currnode.left;
                }
            }
        }
        if (currnode.left != null){
            if (currnode.left.value == num){
                if (currnode.left.left == null && currnode.left.right == null){
                    currnode.left = null;
                    return true;
                }
                else if (currnode.left.left == null){
                    currnode.left = currnode.left.right;
                    return true;
                }
                else if (currnode.left.right == null){
                    currnode.left = currnode.left.left;
                    return true;
                }
                else {
                    delete_helper(currnode,"left");
                    return true;
                }
            }
        }
        if (currnode.right != null){
            if (currnode.right.value == num){
                if (currnode.right.left == null && currnode.right.right == null){
                    currnode.right = null;
                    return true;
                }
                else if (currnode.right.left == null){
                    currnode.right = currnode.right.right;
                    return true;
                }
                else if (currnode.right.right == null){
                    currnode.right = currnode.right.left;
                    return true;
                }
                else {
                    delete_helper(currnode,"right");
                    return true;
                }
            }
        }
		return false;
    }





    public boolean search(int num) {
        // TO be completed by students
        if (root == null){return false;}
        BSTNode currnode = root;
        while (currnode != null){
            if (num < currnode.value){
                currnode = currnode.left;
            }
            else if (num > currnode.value){
                currnode = currnode.right;
            }
            else {
                return true;
            }
        }
        return false;
    }





    public void _inorder(BSTNode node, ArrayList<Integer> al){
        if (node == null){return;}
        _inorder(node.left, al);
        al.add(node.value);
        _inorder(node.right, al);
    }

    public ArrayList<Integer> inorder() {
        // TO be completed by students
		ArrayList<Integer> al = new ArrayList<>();
        if (this.root == null){return al;}
        _inorder(root, al);
		return al;
    }






    public void _preorder(BSTNode node, ArrayList<Integer> al){
        al.add(node.value);
        if (node.left != null){
            _preorder(node.left, al);
        }
        if (node.right != null){
            _preorder(node.right, al);
        }
    }

    public ArrayList<Integer> preorder() {
        // TO be completed by students
		ArrayList<Integer> al = new ArrayList<>();
        if (this.root == null){return al;}
        _preorder(root, al);
		return al;
    }






    public void _postorder(BSTNode node, ArrayList<Integer> al){
        if (node == null){return;}
        if (node.left != null){
            _postorder(node.left, al);
        }
        if (node.right != null){
            _postorder(node.right, al);
        }
        al.add(node.value);
    }

    public ArrayList<Integer> postorder() {
        // TO be completed by students
        ArrayList<Integer> al = new ArrayList<>();
        if (root == null){return al;}
        _postorder(root, al);
		return al;
    }
}