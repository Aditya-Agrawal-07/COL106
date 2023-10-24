import java.util.LinkedList;

import Includes.DictionaryEntry;
import Includes.HashTableEntry;
import Includes.KeyAlreadyExistException;
import Includes.KeyNotFoundException;
import Includes.NullKeyException;

import java.lang.reflect.Array;

public class COL106Dictionary<K, V> {

    private LinkedList<DictionaryEntry<K, V>> dict;
    /*
     * dict is a Linked-List, where every node of linked-list is of type DictionaryEntry.
     * DictionaryEntry is a key-value pair, where the type of key and value is K and V respectively.
     */ 
    public LinkedList<HashTableEntry<K, V>>[] hashTable;
    /*
     * hashTable is an array of Linked-Lists which is initialized by the COL106Dictionary constructor.
     * Each index of hashTable stores a linked-list whose nodes are of type HashTableEntry
     * HashTableEntry is a key-address pair, where the type of key is K and the corresponding address is the address of the DictionaryEntry in the linked-list corresponding to the key of HashTableEntry
     */ 
    
    @SuppressWarnings("unchecked")
    COL106Dictionary(int hashTableSize) {
        dict = new LinkedList<DictionaryEntry<K, V>>();
        // This statement initiailizes a linked-list where each node is of type DictionaryEntry with key and value of type K and V respectively.
        hashTable = (LinkedList<HashTableEntry<K, V>>[]) Array.newInstance(LinkedList.class, hashTableSize);
        // This statement initiailizes the hashTable with an array of size hashTableSize where at each index the element is an instance of LinkedList class and
        // this array is type-casted to an array of LinkedList where the LinkedList contains nodes of type HashTableEntry with key of type K. 
    }

    public int len = 0;

    public int searchkey(K key){
        int hash_val = hash(key);
        LinkedList<HashTableEntry<K, V>> ll = hashTable[hash_val];
        if (ll == null){return -1;}
        for (int i = 0 ; i < ll.size() ; i++){
            if ((ll.get(i).key).equals(key) == true){
                return i;
            }
        }
        return -1;
    }

    public void insert(K key, V value) throws KeyAlreadyExistException, NullKeyException {
        /*
         * To be filled in by the student
         * Input: A key of type K and it corresponding value of type V
         * Working: Inserts the argumented key-value pair in the Dictionary in O(1)
         */
        if (key == null){
            throw new NullKeyException();
        }
        else{
            if (searchkey(key) != -1){
                throw new KeyAlreadyExistException();
            }
            else{
                DictionaryEntry<K, V> e = new DictionaryEntry<K, V>(key, value);
                int hash_val = hash(key);
                HashTableEntry<K, V> h = new HashTableEntry<K, V>(key, e);
                dict.add(e);
                if(hashTable[hash_val] == null){
                    LinkedList<HashTableEntry<K,V>> he = new LinkedList<HashTableEntry<K,V>>();
                    he.add(h);
                    hashTable[hash_val] = he;
                } else {
                    hashTable[hash_val].add(h);
                }
                len++;
            }
        }
    }

    public V delete(K key) throws NullKeyException, KeyNotFoundException{
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the associated value of type V with the argumented key
         * Working: Deletes the key-value pair from the Dictionary in O(1)
         */
        if (key == null){
            throw new NullKeyException();
        }
        else{
            int index = searchkey(key);
            if (index == -1){
                throw new KeyNotFoundException();
            }
            else{
                int hash_val = hash(key);
                HashTableEntry<K, V> h = hashTable[hash_val].remove(index);
                dict.remove(h.dictEntry);
                len--;
                return h.dictEntry.value;
            }
        }
    }

    public V update(K key, V value) throws NullKeyException, KeyNotFoundException{
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the previously associated value of type V with the argumented key
         * Working: Updates the value associated with argumented key with the argumented value in O(1)
         */
        if (key == null){
            throw new NullKeyException();
        }
        else{
            int index = searchkey(key);
            if (index == -1){
                throw new KeyNotFoundException();
            }
            else{
                int hash_val = hash(key);
                HashTableEntry<K, V> h = hashTable[hash_val].get(index);
                V old_val = h.dictEntry.value;
                hashTable[hash_val].get(index).dictEntry.value = value;
                int i = dict.indexOf(h.dictEntry);
                dict.get(i).value = value;
                return old_val;
            }
        }
    }

    public V get(K key) throws NullKeyException, KeyNotFoundException {
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the associated value of type V with the argumented key in O(1)
         */
        if (key == null){
            throw new NullKeyException();
        }
        else{
            int index = searchkey(key);
            if (index == -1){
                throw new KeyNotFoundException();
            }
            else{
                int hash_val = hash(key);
                return hashTable[hash_val].get(index).dictEntry.value;
            }
        }
    }

    public int size() {
        /*
         * To be filled in by the student
         * Return: Returns the size of the Dictionary in O(1)
         */
        return len;
    }

    @SuppressWarnings("unchecked")
    public K[] keys(Class<K> cls) {
        /*
         * To be filled in by the student
         * Return: Returns array of keys stored in dictionary.
         */
        K[] _arr = (K[]) Array.newInstance(cls, len);
        for (int i = 0; i< len; i++){
            _arr[i] = dict.get(i).key;
        }
        return _arr;
    }

    @SuppressWarnings("unchecked")
    public V[] values(Class<V> cls) {
        /*
         * To be filled in by the student
         * Return: Returns array of keys stored in dictionary.
         */
        V[] _arr = (V[]) Array.newInstance(cls, len);
        for (int i = 0; i< len; i++){
            _arr[i] = dict.get(i).value;
        }
        return _arr;
    }

    public int hash(K key) {
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the hash of the argumented key using the Polynomial Rolling
         * Hash Function.
         */
        String s = "" + key;
        int m = hashTable.length;
        int hash_val = 0;
        int pow = 1;
        int l = s.length();
        for (int i = 0; i < l; i++){
            hash_val = (int)((hash_val + ((int)s.charAt(i)+1)*pow) % m);
            pow = (pow*131) % m;
        }
        return hash_val % m;
    }
}
