import java.util.ArrayList;

import Includes.*;

public class FrequencyAnalysis {
    //sizes of hash-tables are updated
    static final int[] hashTableSizes = {173, 6733, 100003};
    COL106Dictionary<String, Integer> dict1 = new COL106Dictionary<String, Integer>(hashTableSizes[0]);
    COL106Dictionary<String, Integer> dict2 = new COL106Dictionary<String, Integer>(hashTableSizes[1]);
    COL106Dictionary<String, Integer> dict3 = new COL106Dictionary<String, Integer>(hashTableSizes[2]);

    void fillDictionaries(String inputString) throws NullKeyException, KeyAlreadyExistException, KeyNotFoundException {
        /*
         * To be filled in by the student
         */
        if(inputString == null){
            throw new NullKeyException();
        }
        String str = "";
        for (int i = 0; i < inputString.length(); i++){
            int ascii = (int)inputString.charAt(i);
            if (ascii == 32){
                if (str != "" && str != " "){
                    if (dict3.searchkey(str) == -1){
                        dict1.insert(str, 1);
                        dict2.insert(str, 1);
                        dict3.insert(str, 1);
                    }
                    else{
                        int old_freq = dict3.get(str);
                        old_freq++;
                        dict1.update(str, old_freq);
                        dict2.update(str, old_freq);
                        dict3.update(str, old_freq);
                    }
                }
            str = "";
            }
            else if (ascii>64 && ascii<91){
                ascii += 32;
                str = str + Character.toString(ascii);
            }
            else if (ascii > 96 && ascii < 123){
                str = str + Character.toString(ascii);
            }
        }
        if (str != "" && str != " "){
            if (dict3.searchkey(str) == -1){
                dict1.insert(str, 1);
                dict2.insert(str, 1);
                dict3.insert(str, 1);
            }
            else{
                int old_freq = dict3.get(str);
                old_freq++;
                dict1.update(str, old_freq);
                dict2.update(str, old_freq);
                dict3.update(str, old_freq);
            }
        }
        str = "";
    }
    
    long[] profile() {
        /*
         * To be filled in by the student
         */
        return new long[4];
    }

    int[] noOfCollisions() {
        /*
         * To be filled in by the student
         */
        int[] collisions = new int[3];
        for (int i = 0; i < 3; i++){collisions[i] = 0;}
        for (int i = 0; i < 173; i++){
            if (dict1.hashTable[i] != null){
                int length = dict1.hashTable[i].size();
                if (length > 1){
                    collisions[0] += length - 1;
                }
            }
        }
        for (int i = 0; i < 6733; i++){
            if (dict2.hashTable[i] != null){
                int length = dict2.hashTable[i].size();
                if (length > 1){
                    collisions[1] += length - 1;
                }
            }
        }
        for (int i = 0; i < 100003; i++){
            if (dict3.hashTable[i] != null){
                int length = dict3.hashTable[i].size();
                if (length > 1){
                    collisions[2] += length - 1;
                }
            }
        }
        return collisions;
    }

    String compare(int bit){
        if (bit == 0){return "0";}
        if (bit == 1){return "1";}
        if (bit == 10){return "2";}
        if (bit == 11){return "3";}
        if (bit == 100){return "4";}
        if (bit == 101){return "5";}
        if (bit == 110){return "6";}
        if (bit == 111){return "7";}
        if (bit == 1000){return "8";}
        if (bit == 1001){return "9";}
        if (bit == 1010){return "A";}
        if (bit == 1011){return "B";}
        if (bit == 1100){return "C";}
        if (bit == 1101){return "D";}
        if (bit == 1110){return "E";}
        else{return "F";}
    }

    String binarytohexa(String bin){
        String newbin = bin;
        int len = newbin.length();
        if (len % 4 != 0){
            int addbit = 4 - (len % 4);
            len += addbit;
            for (int i = 0; i < addbit; i++){
                newbin = "0" + newbin;
            }
        }
        String hexa = "";
        String temp = "";
        for (int i = 0; i < len; i++){
            if ((i+1) % 4 == 0){
                temp = temp + Character.toString(newbin.charAt(i));
                hexa = hexa + compare(Integer.valueOf(temp));
                temp = "";
            }
            else{ 
                temp = temp + Character.toString(newbin.charAt(i));
            }
        }
        return hexa;
    }

    String[] hashTableHexaDecimalSignature() {
        /*
         * To be filled in by the student
         */
        String[] hexa = new String[3];
        String str1 = "";
        for (int i = 0; i < 173; i++){
            if (dict1.hashTable[i] != null){
                str1 = str1 + "1";
            }
            else{
                str1 = str1 + "0";
            }
        }
        hexa[0] = binarytohexa(str1);
        String str2 = "";
        for (int i = 0; i < 6733; i++){
            if (dict2.hashTable[i] != null){
                str2 = str2 + "1";
            }
            else{
                str2 = str2 + "0";
            }
        }
        hexa[1] = binarytohexa(str2);
        String str3 = "";
        for (int i = 0; i < 100003; i++){
            if (dict3.hashTable[i] != null){
                str3 = str3 + "1";
            }
            else{
                str3 = str3 + "0";
            }
        }
        hexa[2] = binarytohexa(str3);
        return hexa;
    }
    
    String[] distinctWords() {
        /*
         * To be filled in by the student
         */
        String[] words = dict1.keys(String.class);
        return words;
    }

    Integer[] distinctWordsFrequencies() {
        /*
         * To be filled in by the student
         */
        Integer[] frequency = dict1.values(Integer.class);
        return frequency;
    }
}
