package com.gradescope.assignment1;

import com.gradescope.assignment1.AbstractMethodOverloading;

public class MethodOverloading extends AbstractMethodOverloading {
    /*
     * To be filled in by the student
     * Implement all the three overloaded methods here:
     *      Method name : "calculate"
     *      Return type : "double"
     *      And method should be "public" member of the class.
     */
    public double calculate(int a){
        return (double)a*a;
    }
    public double calculate(int a,int b){
        return (double)a*b;
    }
    public double calculate(int a,int b,int c){
        double s = (double)(a+b+c)/2;
        double area = Math.sqrt((double)s*(s-a)*(s-b)*(s-c));
        return area;
    }
}
