package com.gradescope.assignment1;

import com.gradescope.assignment1.AbstractParenthesisMatching;
import com.gradescope.assignment1.DemoStack;

public class ParenthesisMatching extends AbstractParenthesisMatching {
    
    public Boolean match_parenthesis(String s){
        /*
         * To be filled in by the student
         * Input: String made up of characters ‘(’, ‘{’, ‘[’, ‘)’, ‘}’, and ‘]’ 
         * Return: Whether input string is a matching parenthesis
         */
        DemoStack S = new DemoStack();
        for (Integer i = 0; i < s.length(); i++){
            Character ch = s.charAt(i);
            if (ch=='(' || ch=='{' || ch=='['){
                S.push(ch);   
            }
            else if (ch == ')'){
                if (S.size()==0){
                    return false;
                }
                Character top_char = S.pop();
                if (top_char!='('){
                    return false;
                }
            }
            else if (ch == '}'){
                if (S.size()==0){
                    return false;
                }
                Character top_char = S.pop();
                if (top_char!='{'){
                    return false;
                }
            }
            else{
                if (S.size()==0){
                    return false;
                }
                Character top_char = S.pop();
                if (top_char!='['){
                    return false;
                }
            }
        }
        if (S.size() == 0){return true;}
        else{return false;}
    }
}
