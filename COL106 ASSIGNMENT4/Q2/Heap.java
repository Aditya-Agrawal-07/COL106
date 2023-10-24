//package heap_package;
import java.util.ArrayList;
 
public class Heap{

	class Node{

		public int key;
		public int value;
		public Node left;
		public Node right;
		public Node parent;
		public int height;
		public boolean is_complete;
	
		public Node(int key, int value, Node parent){
	
			this.key = key;
			this.value = value;
			this.parent = parent;
	
			this.left = null;
			this.right = null;
			this.height = 1;     // Height of sub-tree rooted at this node. 
			this.is_complete = true;   // Whether binary sub-tree rooted at this node is complete or not.
		}
	}

	protected Node root;								// root of the heap
	protected Node[] nodes_array;                    // Stores the address of node corresponding to the keys
	private int max_size;                           // Maximum number of nodes heap can have 
	private static final String NullKeyException = "NullKey";      // Null key exception
	private static final String NullRootException = "NullRoot";    // Null root exception
	private static final String KeyAlreadyExistsException = "KeyAlreadyExists";   // Key already exists exception

	/* 
	   1. Can use helper methods but they have to be kept private. 
	   2. Not allowed to use any data structure. 
	*/

	private void swap(Node node1, Node node2){
		int k = node1.key;
		int v = node1.value;
		node1.value = node2.value;
		node1.key = node2.key;
		node2.value = v;
		node2.key = k;
		nodes_array[node2.key] = node2;
		nodes_array[node1.key] = node1;
	}





	private void Heapify(Node node){
		if (node.left == null){
			return;
		}
		else if (node.right == null){
			if (node.left.value > node.value){
				swap(node, node.left);
			}
			return;
		}
		else{
			if (node.left.value > node.right.value){
				if (node.left.value > node.value){
					swap(node, node.left);
					Heapify(node.left);
				}
			}
			else{
				if (node.right.value > node.value){
					swap(node, node.right);
					Heapify(node.right);
				}
			}
		}
	}






	private Node Heap_helper(int[] key_arr, int[] val_arr, int size, int i, Node parent, Node[] nodes_array){
		Node newnode = null;
		if (i < size){
			newnode = new Node(key_arr[i], val_arr[i], parent);
			nodes_array[key_arr[i]] = newnode;
			newnode.left = Heap_helper(key_arr, val_arr, size, 2*i + 1, newnode, nodes_array);
			newnode.right = Heap_helper(key_arr, val_arr, size, 2*i + 2, newnode, nodes_array);
			if (newnode.left != null){
				newnode.height = newnode.left.height + 1;
				if (newnode.right == null){
					newnode.is_complete = false;
				}
				else{
					if (newnode.left.height == newnode.right.height && newnode.left.is_complete == true && newnode.right.is_complete == true){
						newnode.is_complete = true;
					}
					else{
						newnode.is_complete = false;
					}
				}
			}
			if (i == 0){
				this.root = newnode;
			}
		}
		return newnode;
	}

	public Heap(int max_size, int[] keys_array, int[] values_array) throws Exception{

		/* 
		   1. Create Max Heap for elements present in values_array.
		   2. keys_array.length == values_array.length and keys_array.length number of nodes should be created. 
		   3. Store the address of node created for keys_array[i] in nodes_array[keys_array[i]].
		   4. Heap should be stored based on the value i.e. root element of heap should 
		      have value which is maximum value in values_array.
		   5. max_size denotes maximum number of nodes that could be inserted in the heap. 
		   6. keys will be in range 0 to max_size-1.
		   7. There could be duplicate keys in keys_array and in that case throw KeyAlreadyExistsException. 
		*/

		/* 
		   For eg. keys_array = [1,5,4,50,22] and values_array = [4,10,5,23,15] : 
		   => So, here (key,value) pair is { (1,4), (5,10), (4,5), (50,23), (22,15) }.
		   => Now, when a node is created for element indexed 1 i.e. key = 5 and value = 10, 
		   	  that created node address should be saved in nodes_array[5]. 
		*/ 

		/*
		   n = keys_array.length
		   Expected Time Complexity : O(n).
		*/

		this.max_size = max_size;
		this.nodes_array = new Node[this.max_size];
		int size = keys_array.length;

		// To be filled in by the student
		for (int i = 0; i < size; i++){
			if (nodes_array[keys_array[i]] != null){
				Heap_helper(keys_array, values_array, i, 0, null, nodes_array);
				for (int j = i/2; j >= 0; j--){
					Heapify(nodes_array[keys_array[j]]);
				}
				throw new Exception(Heap.KeyAlreadyExistsException);
			}
			Node newnode = new Node(keys_array[i], values_array[i], null);
			nodes_array[keys_array[i]] = newnode;
		}
		Heap_helper(keys_array, values_array, size, 0, null, nodes_array);
		for (int i = size/2; i >= 0; i--){
			Heapify(nodes_array[keys_array[i]]);
		}
	}





	private void getMax_helper(Node node, ArrayList<Integer> arr, int val){
		if (node.value == val){
			arr.add(node.key);
			if (node.left != null){
				getMax_helper(node.left, arr, val);
				if (node.right != null){
					getMax_helper(node.right, arr, val);
				}
			}
		}			
		else{
			return;
		}
	}

	public ArrayList<Integer> getMax() throws Exception{

		/* 
		   1. Returns the keys with maximum value in the heap.
		   2. There could be multiple keys having same maximum value. You have
		      to return all such keys in ArrayList (order doesn't matter).
		   3. If heap is empty, throw NullRootException.

		   Expected Time Complexity : O(1).
		*/

		ArrayList<Integer> max_keys = new ArrayList<Integer>();    // Keys with maximum values in heap.

		// To be filled in by the student

		if (this.root == null){
			throw new Exception(Heap.NullRootException);
		}
		else{
			int max_val = this.root.value;
			getMax_helper(root, max_keys, max_val);
			return max_keys;
		}
	}





	private void insert_helper(Node newnode){
		if (this.root == null){
			root = newnode;
		}
		else{
			Node node = root;
			if (node.is_complete == true){
				while (node.height != 1){
					node = node.left;
				}
			}
			else{
				while (node.is_complete == false){
					if (node.left.is_complete == false){
						node = node.left;
					}
					else if (node.right == null){
						break;
					}
					else{
						node = node.right;
					}
				}
			}
			newnode.parent = node;
			if (node.left == null){
				node.left = newnode;
			}
			else{
				node.right = newnode;
			}
			Node temp = newnode;
			while (temp != this.root){
				if (temp.value > temp.parent.value){
					swap(temp,temp.parent);
				}
				temp = temp.parent;
				if (temp.right == null){
					temp.is_complete = false;
				}
				else if (temp.left.is_complete == true && temp.right.is_complete == true && temp.left.height == temp.right.height){
					temp.is_complete = true;
				}
				else{
					temp.is_complete = false;
				}
				temp.height = temp.left.height + 1;
			}
		}
	}

	public void insert(int key, int value) throws Exception{

		/* 
		   1. Insert a node whose key is "key" and value is "value" in heap 
		      and store the address of new node in nodes_array[key]. 
		   2. If key is already present in heap, throw KeyAlreadyExistsException.

		   Expected Time Complexity : O(logn).
		*/

		// To be filled in by the student
		if (nodes_array[key] != null){
			throw new Exception(Heap.KeyAlreadyExistsException);
		}
		else{
			Node newnode = new Node(key, value, null);
			nodes_array[key] = newnode;
			insert_helper(newnode);
		}
	}






	public ArrayList<Integer> deleteMax() throws Exception{

		/* 
		   1. Remove nodes with the maximum value in the heap and returns their keys.
		   2. There could be multiple nodes having same maximum value. You have
		      to delete all such nodes and return all such keys in ArrayList (order doesn't matter).
		   3. If heap is empty, throw NullRootException.

		   Expected Average Time Complexity : O(logn).
		*/

		ArrayList<Integer> max_keys = new ArrayList<Integer>();   // Keys with maximum values in heap that will be deleted.

		// To be filled in by the student

		if (this.root == null){
			throw new Exception(Heap.NullRootException);
		}
		else{
			int max_val = root.value;
			while(root.value == max_val){
				max_keys.add(root.key);
				Node node = root;
				String str = "";
				while (node.height != 1){
					if (node.right == null){
						node = node.left;
						str = "left";
					}
					else if (node.left.height != node.right.height){
						node = node.left;
						str = "left";
					}
					else{
						node = node.right;
						str = "right";
					}
				}
				if (node == this.root){
					nodes_array[node.key] = null;
					this.root = null;
					return max_keys;
				}
				swap(this.root, node);
				nodes_array[node.key] = null;
				Node temp = node.parent;
				if (str.compareTo("left") == 0){
					temp.left = null;
				}
				else{
					temp.right = null;	
				}
				while (temp != null){
					if (temp.left == null){
						temp.is_complete = true;
						temp.height = 1;
					}
					else if (temp.right == null){
						temp.is_complete = false;
						temp.height = temp.left.height + 1;
					}
					else if (temp.left.is_complete == true && temp.right.is_complete == true && temp.left.height == temp.right.height){
						temp.is_complete = true;
						temp.height = temp.left.height + 1;
					}
					else{
						temp.is_complete = false;
						temp.height = temp.left.height + 1;
					}
					temp = temp.parent;
				}
				Heapify(root);
			}
		}
		return max_keys;
	}





	public void update(int key, int diffvalue) throws Exception{

		/* 
		   1. Update the heap by changing the value of the node whose key is "key" to value+diffvalue.
		   2. If key doesn't exists in heap, throw NullKeyException.

		   Expected Time Complexity : O(logn).
		*/

		// To be filled in by the student
		if (nodes_array[key] == null){
			throw new Exception(Heap.NullKeyException);
		}
		else{
			nodes_array[key].value += diffvalue;
			Node temp = nodes_array[key];
			if (diffvalue == 0){return;}
			else if (diffvalue > 0){
				while (temp != root){
					if (temp.value > temp.parent.value){
						swap(temp, temp.parent);
						temp = temp.parent;
					}
					else{break;}
				}
			}
			else{
				Heapify(temp);
				return;
			}
		}
	}





	public int getMaxValue() throws Exception{

		/* 
		   1. Returns maximum value in the heap.
		   2. If heap is empty, throw NullRootException.

		   Expected Time Complexity : O(1).
		*/

		// To be filled in by the student

		if (this.root == null){
			throw new Exception (Heap.NullRootException);
		}
		else{
			return this.root.value;
		}
	}




	private void getKeys_Helper(Node node, ArrayList<Integer> arr){
		if (node == null){
			return;
		}
		else{
			arr.add(node.key);
			getKeys_Helper(node.left, arr);
			getKeys_Helper(node.right, arr);
		}
	}

	public ArrayList<Integer> getKeys() throws Exception{

		/*
		   1. Returns keys of the nodes stored in heap.
		   2. If heap is empty, throw NullRootException.
		 
		   Expected Time Complexity : O(n).
		*/

		ArrayList<Integer> keys = new ArrayList<Integer>();   // Stores keys of nodes in heap

		// To be filled in by the student
		if (this.root == null){
			throw new Exception(Heap.NullRootException);
		}
		else{
			getKeys_Helper(root, keys);
			return keys;
		}

	}

	// Write helper functions(if any) here (They have to be private).

	public static void main(String[] args) throws Exception{
	Tree t = new Tree();
    t.insert("h4");
    t.insert("h3");
    t.insert("h2");
    t.insert("h1");
    t.insert("h5");
    t.insert("h6");
    t.insert("h7");
    t.insert("h8");
    t.insert("h99");
    t.insert("h999");
    t.delete("h1");
	t.decrement("h1");
	}
}
