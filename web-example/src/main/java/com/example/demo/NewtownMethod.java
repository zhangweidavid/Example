package com.example.demo;


public class NewtownMethod {
    public static void main(String[] args){
        System.out.println(getCubeRoot(8));
    }


    public  static double getCubeRoot(double input){
        if(input==0)
            return 0;
        double x0,x1;
        x0=input;
        x1=(2*x0/3)+(input/(x0*x0*3));//利用迭代法求解
        while(Math.abs(x1-x0)>0.000001){
            x0=x1;
            x1=(2*x0/3)+(input/(x0*x0*3));
        }
        return x1;
    }
}
