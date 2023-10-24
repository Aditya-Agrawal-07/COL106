package com.gradescope.assignment1;

import com.gradescope.assignment1.AbstractFreqOfLetters;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FreqOfLetters extends AbstractFreqOfLetters {
    public Integer[] count_letters(String fname) throws FileNotFoundException, IOException {
        /*
         * To be filled in by the student
         * Input: File name
         * Return: Array of 26 length containing freq of characters present in the file
         * Note: Ignore ' ' and EOF characters from input file
         */
        Integer[] result = new Integer[26];
        for (Integer i = 0; i < 26; i++){
            result[i]=0;
        }
        try{
            FileReader fr = new FileReader(fname);
            int text;
            while((text = fr.read()) != -1){
                if (text>96 && text<123){
                    result[text-97]++;
                }
            }
            return result;
        }
        catch(FileNotFoundException e){
            throw new FileNotFoundException();
        }
        catch(IOException ex){
            throw new IOException();
        }
    }
}