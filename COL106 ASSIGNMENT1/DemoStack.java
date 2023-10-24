package com.gradescope.assignment1;

import java.util.EmptyStackException;

import com.gradescope.assignment1.AbstractDemoStack;

public class DemoStack extends AbstractDemoStack {
    Character[] base;
    Integer size = 0;

    public DemoStack(){
        super();
        base = new Character[10];
    }

    Integer N = 10;

    public void push(Character i){
        /*
         * To be filled in by the student
         * Input: Character to be inserted onto top of stack
         */
        if (size == N){
            N = 2*N;
            Character[] temp = base;
            base = new Character[N];
            for (Integer j = 0;j < size;j++){
                base[j] = temp[j];
            }
        }
        base[size] = i;
        size++;
    }

    public Character pop() throws EmptyStackException{
        /*
         * To be filled in by the student
         * Return: Character present at the top of the stack
         */
        if (size == 0){
            throw new EmptyStackException();
        }
        else{
            Character str;
            str = base[size-1];
            base[size-1] = null;
            size--;
            if (size < N/4 && N>40){
                N = N/2;
                Character[] temp = base;
                base = new Character[N];
                for (Integer i = 0; i < size; i++){
                    base[i] = temp[i];
                }
            }
            return str;
        }
    }
    
    public Boolean is_empty(){
        /*
         * To be filled in by the student
         * Return: Stack is empty or not
         */
        if (size == 0){
            return true;
        }
        return false;
    }
    
    public Integer size(){
        /*
         * To be filled in by the student
         * Return: Number of elements which are present in stack
         */
        return size;
    }
    
    public Character top() throws EmptyStackException{
        /*
         * To be filled in by the student
         * Return: Character present at the top of the stack
         */
        if (size == 0){
            throw new EmptyStackException();
        }
        else{
            return base[size-1];
        }
    }

    public Character[] return_base_array(){
       /*
        * To be filled in the by the student
        * Return: Return reference to the base array storing the elements of stack
        */
        return base;
    }
}
