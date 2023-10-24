import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Application extends Tree {

    private int max_val = 0;
    private int c_freq = 0;
    private String max_s = "";

    public boolean is_leaf(TreeNode node){
        if (node.children.isEmpty() == true){
            return true;
        }
        return false;
    }

    private void count_updater(TreeNode target, TreeNode node, String s){
        if (node == target){
            return;
        }
        else{
            if (s.compareTo(node.s.get(0)) < 0){
                count_updater(target, node.children.get(0), s);
            }
            else if (node.s.size() == 1 || s.compareTo(node.s.get(1)) < 0){
                count_updater(target, node.children.get(1), s);
            }
            else if (node.s.size() == 2 || s.compareTo(node.s.get(2)) < 0){
                count_updater(target, node.children.get(2), s);
            }
            else{
                count_updater(target, node.children.get(3), s);
            }
        }
        max_val_calc(node);
        count_helper(node);
        return;
    }

    private void count_helper(TreeNode node){
        int count = 0;
        if (is_leaf(node)){
            for (int i = 0; i < node.s.size(); i++){
                count += node.val.get(i);
            }
            node.count = count;
            return;
        }
        for (int i = 0; i < node.children.size(); i++){
            count += node.children.get(i).count;
        }
        for (int i = 0; i < node.s.size(); i++){
            count += node.val.get(i);
        }
        node.count = count;
    }

    private void max_val_calc(TreeNode node){
        if (node.children.isEmpty()){
            int max_val = node.val.get(0);
            String max_s = node.s.get(0);
            for (int i = 1; i < node.s.size(); i++){
                if (node.val.get(i) > max_val){
                    max_val = node.val.get(i);
                    max_s = node.s.get(i);
                }
            }
            node.max_s = max_s;
            node.max_value = max_val;
        }
        else{
            int max_val = node.children.get(0).max_value;
            String max_s = node.children.get(0).max_s;
            for (int i = 0; i < node.s.size(); i++){
                if (node.val.get(i) > max_val){
                    max_val = node.val.get(i);
                    max_s = node.s.get(i);
                }
                if (node.children.get(i+1).max_value > max_val){
                    max_val = node.children.get(i+1).max_value;
                    max_s = node.children.get(i+1).max_s;
                }
            }
            node.max_s = max_s;
            node.max_value = max_val;
        }
    }





    private void overflow_helper(TreeNode node, TreeNode parent){
        TreeNode leftnode = new TreeNode();
        TreeNode rightnode = new TreeNode();
        if (is_leaf(node) == false){
            leftnode.s.add(node.s.get(0));
            leftnode.val.add(node.val.get(0));
            leftnode.children.add(node.children.get(0));
            leftnode.children.add(node.children.get(1));
            rightnode.children.add(node.children.get(2));
            rightnode.children.add(node.children.get(3));
            rightnode.children.add(node.children.get(4));
            leftnode.height = node.height;
            rightnode.s.add(node.s.get(2));
            rightnode.s.add(node.s.get(3));
            rightnode.val.add(node.val.get(2));
            rightnode.val.add(node.val.get(3));
            rightnode.height = node.height;
            max_val_calc(leftnode);
            count_helper(leftnode);
            max_val_calc(rightnode);
            count_helper(rightnode);
            String s = node.s.get(1);
            int val = node.val.get(1);
            if (node == this.root){
                TreeNode _root = new TreeNode();
                _root.s.add(s);
                _root.val.add(val);
                _root.children.add(leftnode);
                _root.children.add(rightnode);
                _root.height = leftnode.height + 1;
                max_val_calc(_root);
                count_helper(_root);
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
            max_val_calc(parent);
            count_helper(parent);
        }
        else{
            leftnode.s.add(node.s.get(0));
            leftnode.val.add(node.val.get(0));
            leftnode.height = node.height;
            leftnode.count = node.val.get(0);
            leftnode.max_s = leftnode.s.get(0);
            leftnode.max_value = leftnode.count;
            rightnode.s.add(node.s.get(2));
            rightnode.s.add(node.s.get(3));
            rightnode.val.add(node.val.get(2));
            rightnode.val.add(node.val.get(3));
            rightnode.height = node.height;
            rightnode.count = rightnode.val.get(0) + rightnode.val.get(1);
            if (rightnode.val.get(0) >= rightnode.val.get(1)){
                rightnode.max_value = rightnode.val.get(0);
                rightnode.max_s = rightnode.s.get(0);
            }
            else{
                rightnode.max_value = rightnode.val.get(1);
                rightnode.max_s = rightnode.s.get(1);
            }
            String s = node.s.get(1);
            int val = node.val.get(1);
            if (node == this.root){
                TreeNode _root = new TreeNode();
                _root.s.add(s);
                _root.val.add(val);
                _root.children.add(leftnode);
                _root.children.add(rightnode);
                _root.height = leftnode.height + 1;
                max_val_calc(_root);
                count_helper(_root);
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
            max_val_calc(parent);
            count_helper(parent);
        }
    }

    private void insert_helper(String s, TreeNode node, TreeNode parent){
        if (is_leaf(node) == true){
            int i;
            for (i = 0; i < node.s.size(); i++){
                if (s.compareTo(node.s.get(i)) < 0){
                    node.s.add(i, s);
                    node.val.add(i, 1);
                    if (node.max_value == 1 && i == 0){
                        node.max_s = s;
                    }
                    break;
                }
            }
            if (i == node.s.size()){
                node.s.add(s);
                node.val.add(1);
                if (node.max_value == 1 && i == 0){
                    node.max_s = s;
                }
            }
            node.count++;
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
        else{
            max_val_calc(node);
            count_helper(node);
        }
        return;
    }


    public void insert(String s){
        // TO be completed by students
        if (this.root == null){
            TreeNode newnode = new TreeNode();
            newnode.s.add(s);
            newnode.val.add(1);
            newnode.count = 1;
            newnode.max_s = s;
            newnode.max_value = 1;
            this.root = newnode;
            return;
        }
        else{
            insert_helper(s, this.root, null);
        }

    }







    public int increment_helper(TreeNode node, String s){
        for (int i = 0; i < node.s.size(); i++){
            if (s.compareTo(node.s.get(i)) == 0){
                int val = node.val.get(i);
                val++;
                node.val.set(i, val);
                max_val_calc(node);
                count_helper(node);
                count_updater(node, this.root, s);
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
                max_val_calc(node);
                count_helper(node);
                count_updater(node, this.root, s);
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








    public void buildTree(String fileName){
        // TO be completed by students
        try{
            File file = new File(fileName);
            Scanner sc = new Scanner(file);
            while (sc.hasNext()){
                String str = sc.next();
                if (search(str) == true){
                    increment(str);
                }
                else{
                    insert(str);
                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println("FileNotFoundException");
        }
    }


    private TreeNode common_ancestor(String s1, String s2, TreeNode node){
        for (int i = 0; i < node.s.size(); i++){
            if (s1.compareTo(node.s.get(i)) == 0 || s2.compareTo(node.s.get(i)) == 0){
                return node;
            }
            else if (s1.compareTo(node.s.get(i)) < 0 && s2.compareTo(node.s.get(i)) > 0){
                return node;
            }
        }
        if (s1.compareTo(node.s.get(0)) < 0){
            return common_ancestor(s1, s2, node.children.get(0));
        }
        if (node.s.size() == 1 || s1.compareTo(node.s.get(1)) < 0){
            return common_ancestor(s1, s2, node.children.get(1));
        }
        if (node.s.size() == 2 || s1.compareTo(node.s.get(2)) < 0){
            return common_ancestor(s1, s2, node.children.get(2));
        }
        return common_ancestor(s1, s2, node.children.get(3));
    }

    private boolean same_node(String s1, String s2, TreeNode node){
        int i;
        int j;
        int max_val = 0;
        String max_s = "";
        int c_freq = 0;
        if (is_leaf(node) == false){
            for (i = 0; i < node.s.size(); i++){
                if (s1.compareTo(node.s.get(i)) == 0){
                    c_freq += node.val.get(i) + node.children.get(i + 1).count;
                    max_s = node.s.get(i);
                    max_val = node.val.get(i);
                    if (node.children.get(i + 1).max_value > max_val){
                        max_val = node.children.get(i + 1).max_value;
                        max_s = node.children.get(i + 1).max_s;
                    }
                    for (j = i + 1; j < node.s.size(); j++){
                        if (s2.compareTo(node.s.get(j)) == 0){
                            c_freq += node.val.get(j);
                            if (node.val.get(j) > max_val){
                                max_val = node.val.get(j);
                                max_s = node.s.get(j);
                            }
                            this.c_freq = c_freq;
                            this.max_s = max_s;
                            return true;
                        }
                        else{
                            c_freq += node.val.get(j) + node.children.get(j + 1).count;
                            if (node.children.get(j + 1).max_value > max_val && node.children.get(j + 1).max_value > node.val.get(j)){
                                max_val = node.children.get(j + 1).max_value;
                                max_s = node.children.get(j + 1).max_s;
                            }
                            else if (node.val.get(j) > max_val){
                                max_val = node.val.get(j);
                                max_s = node.s.get(j);
                            }
                        }
                    }
                }
            }
        }
        else{
            for (i = 0; i < node.s.size(); i++){
                if (s1.compareTo(node.s.get(i)) == 0){
                    c_freq += node.val.get(i);
                    max_s = node.s.get(i);
                    max_val = node.val.get(i);
                    for (j = i + 1; j < node.s.size(); j++){
                        if (s2.compareTo(node.s.get(j)) == 0){
                            c_freq += node.val.get(j);
                            if (node.val.get(j) > max_val){
                                max_val = node.val.get(j);
                                max_s = node.s.get(j);
                            }
                            this.c_freq = c_freq;
                            this.max_s = max_s;
                            return true;
                        }
                        else{
                            c_freq += node.val.get(j);
                            if (node.val.get(j) > max_val){
                                max_val = node.val.get(j);
                                max_s = node.s.get(j);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    private void left(TreeNode node, int pos){
        if (is_leaf(node) == false){
            for (int i = 0; i < pos + 1; i++){
                c_freq += node.val.get(i) + node.children.get(i).count;
                if (node.children.get(i).max_value > max_val && node.children.get(i).max_value >= node.val.get(i)){
                    max_val = node.children.get(i).max_value;
                    max_s = node.children.get(i).max_s;
                }
                else if (node.children.get(i).max_value == max_val && node.children.get(i).max_value >= node.val.get(i)){
                    if (max_s.compareTo(node.children.get(i).max_s) > 0){
                        max_val = node.children.get(i).max_value;
                        max_s = node.children.get(i).max_s;
                    }
                }
                else if (node.val.get(i) > max_val){
                    max_val = node.val.get(i);
                    max_s = node.s.get(i);
                }
                else if (node.val.get(i)  == max_val){
                    if (max_s.compareTo(node.s.get(i)) > 0){
                        max_val = node.val.get(i);
                        max_s = node.s.get(i);
                    }
                }
            }
        }
        else{
            for (int i = 0; i < pos + 1; i++){
                c_freq += node.val.get(i);
                if (node.val.get(i) > max_val){
                    max_val = node.val.get(i);
                    max_s = node.s.get(i);
                }
                else if (node.val.get(i)  == max_val){
                    if (max_s.compareTo(node.s.get(i)) > 0){
                        max_val = node.val.get(i);
                        max_s = node.s.get(i);
                    }
                }
            }
        }
    }



    private void right(TreeNode node, int pos){
        if (is_leaf(node) == false){
            for (int i = pos; i < node.s.size(); i++){
                c_freq += node.val.get(i) + node.children.get(i + 1).count;
                if (node.children.get(i + 1).max_value > max_val && node.children.get(i + 1).max_value > node.val.get(i)){
                    max_val = node.children.get(i + 1).max_value;
                    max_s = node.children.get(i + 1).max_s;
                }
                else if (node.children.get(i + 1).max_value == max_val && node.children.get(i + 1).max_value > node.val.get(i)){
                    if (max_s.compareTo(node.children.get(i + 1).max_s) > 0){
                        max_val = node.children.get(i + 1).max_value;
                        max_s = node.children.get(i + 1).max_s;
                    }
                }
                else if (node.val.get(i) > max_val){
                    max_val = node.val.get(i);
                    max_s = node.s.get(i);
                }
                else if (node.val.get(i)  == max_val){
                    if (max_s.compareTo(node.s.get(i)) > 0){
                        max_val = node.val.get(i);
                        max_s = node.s.get(i);
                    }
                }
            }
        }
        else{
            for (int i = pos; i < node.s.size(); i++){
                c_freq += node.val.get(i);
                if (node.val.get(i) > max_val){
                    max_val = node.val.get(i);
                    max_s = node.s.get(i);
                }
                else if (node.val.get(i)  == max_val){
                    if (max_s.compareTo(node.s.get(i)) > 0){
                        max_val = node.val.get(i);
                        max_s = node.s.get(i);
                    }
                }
            }
        }
    }

    private void s1(String s1, TreeNode node){
        for (int i = 0; i < node.s.size(); i++){
            if (s1.compareTo(node.s.get(i)) == 0){
                right(node, i);
                return;
            }
        }
        if (s1.compareTo(node.s.get(0)) < 0){
            right(node, 0);
            s1(s1, node.children.get(0));
        }
        else if (node.s.size() == 1 || s1.compareTo(node.s.get(1)) < 0){
            right(node, 1);
            s1(s1, node.children.get(1));
        }
        else if (node.s.size() == 2 || s1.compareTo(node.s.get(2)) < 0){
            right(node, 2);
            s1(s1, node.children.get(2));
        }
        else{
            s1(s1, node.children.get(3));
        }
    }

    private void s2(String s2, TreeNode node){
        for (int i = 0; i < node.s.size(); i++){
            if (s2.compareTo(node.s.get(i)) == 0){
                left(node, i);
                return;
            }
        }
        if (s2.compareTo(node.s.get(0)) < 0){
            s2(s2, node.children.get(0));
        }
        else if (node.s.size() == 1 || s2.compareTo(node.s.get(1)) < 0){
            left(node, 0);
            s2(s2, node.children.get(1));
        }
        else if (node.s.size() == 2 || s2.compareTo(node.s.get(2)) < 0){
            left(node, 1);
            s2(s2, node.children.get(2));
        }
        else{
            left(node, 2);
            s2(s2, node.children.get(3));
        }
    }

    private boolean only_s1(String s1, String s2, TreeNode node){
        for (int i = 0; i < node.s.size(); i++){
            if (s1.compareTo(node.s.get(i)) == 0){
                c_freq += node.val.get(i);
                if (node.val.get(i) > max_val){
                    max_val = node.val.get(i);
                    max_s = s1;
                }
                for (int j = i + 1; j < node.s.size(); j++){
                    if (s2.compareTo(node.s.get(j)) < 0){
                        s2(s2, node.children.get(j));
                        return true;
                    }
                    c_freq += node.children.get(j).count + node.val.get(j);
                    if (node.children.get(j).max_value > max_val && node.children.get(j).max_value >= node.val.get(j)){
                            max_val = node.children.get(j).max_value;
                            max_s = node.children.get(j).max_s;
                    }
                    else if (node.children.get(j).max_value == max_val && node.children.get(j).max_value >= node.val.get(j)){
                        if (max_s.compareTo(node.children.get(j).max_s) > 0){
                            max_val = node.children.get(j).max_value;
                            max_s = node.children.get(j).max_s;
                        }
                    }
                    else if (node.val.get(j) > max_val){
                            max_val = node.val.get(j);
                            max_s = node.s.get(j);
                    }
                    else if (node.val.get(j)  == max_val){
                        if (max_s.compareTo(node.s.get(j)) > 0){
                            max_val = node.val.get(j);
                            max_s = node.s.get(j);
                        }
                    }
                }
                s2(s2, node.children.get(node.s.size()));
                return true;
            }
        }
        return false;
    }

    private boolean only_s2(String s1, String s2, TreeNode node){
        for (int i = 0; i < node.s.size(); i++){
            if (s1.compareTo(node.s.get(i)) < 0){
                for (int j = i; j < node.s.size(); j++){
                    if (s2.compareTo(node.s.get(j)) == 0){
                        s1(s1, node.children.get(i));
                        for(int k = i ; k < j; k++){
                            c_freq += node.val.get(k) + node.children.get(k + 1).count;
                            if (node.children.get(k).max_value > max_val && node.children.get(k).max_value >= node.val.get(k)){
                                max_val = node.children.get(k).max_value;
                                max_s = node.children.get(k).max_s;
                            }
                            else if (node.children.get(k).max_value == max_val && node.children.get(k).max_value >= node.val.get(k)){
                                if (max_s.compareTo(node.children.get(k).max_s) > 0){
                                    max_val = node.children.get(k).max_value;
                                    max_s = node.children.get(k).max_s;
                                }
                            }
                            else if (node.val.get(k) > max_val){
                                max_val = node.val.get(k);
                                max_s = node.s.get(k);
                            }
                            else if (node.val.get(k) == max_val){
                                if (max_s.compareTo(node.s.get(i)) > 0){
                                    max_val = node.val.get(k);
                                    max_s = node.s.get(k);
                                }
                            }
                        }
                        c_freq += node.val.get(j);
                        if (node.val.get(j) > max_val){
                            max_val = node.val.get(j);
                            max_s = node.s.get(j);
                        }
                        else if (node.val.get(j) == max_val){
                            if (max_s.compareTo(node.s.get(i)) > 0){
                                max_val = node.val.get(j);
                                max_s = node.s.get(j);
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void none(String s1, String s2, TreeNode node){
        for (int i = 0; i < node.s.size(); i++){
            if (s1.compareTo(node.s.get(i)) < 0){
                s1(s1, node.children.get(i));
                c_freq += node.val.get(i);
                if (node.val.get(i) > max_val){
                    max_val = node.val.get(i);
                    max_s = node.s.get(i);
                }
                else if (node.val.get(i) == max_val){
                    if (max_s.compareTo(node.s.get(i)) > 0){
                        max_val = node.val.get(i);
                        max_s = node.s.get(i);
                    }
                }
                int j;
                for (j = i + 1; j < node.s.size(); j++){
                    if (s2.compareTo(node.s.get(j)) < 0){
                        s2(s2, node.children.get(j));
                        return;
                    }
                    c_freq += node.children.get(j).count + node.val.get(j);
                    if (node.children.get(j).max_value > max_val && node.children.get(j).max_value >= node.val.get(j)){
                        max_val = node.children.get(j).max_value;
                        max_s = node.children.get(j).max_s;
                    }
                    else if (node.children.get(j).max_value == max_val && node.children.get(j).max_value >= node.val.get(j)){
                        if (max_s.compareTo(node.children.get(j).max_s) > 0){
                            max_val = node.children.get(j).max_value;
                            max_s = node.children.get(j).max_s;
                        }
                    }
                    else if (node.val.get(j) > max_val){
                        max_val = node.val.get(j);
                        max_s = node.s.get(j);
                    }
                    else if (node.val.get(j) == max_val){
                        if (max_s.compareTo(node.s.get(j)) > 0){
                            max_val = node.val.get(j);
                            max_s = node.s.get(j);
                        }
                    }
                }
                s2(s2, node.children.get(j));
                return;
            }
        }
    }


    private void frequency_helper(String s1, String s2){
        TreeNode com_ancestor = common_ancestor(s1, s2, this.root);
        if (same_node(s1, s2, com_ancestor)){return;}
        if (only_s1(s1, s2, com_ancestor)){return;}
        if(only_s2(s1, s2, com_ancestor)){return;}
        none(s1, s2, com_ancestor);
    }

    public int cumulativeFreq(String s1, String s2){
        // TO be completed by students
        if (s1.compareTo(s2) == 0){
            return getVal(s2);
        }
        else{
            frequency_helper(s1, s2);
            int num = c_freq;
            c_freq = 0;
            return num;
        }
    }

    public String maxFreq(String s1, String s2){
        // TO be completed by students
        if (s1.compareTo(s2) == 0){
            return s1;
        }
        else{
            frequency_helper(s1, s2);
            String str = max_s;
            max_s = "";
            max_val = 0;
            return str;
        }
    }

}