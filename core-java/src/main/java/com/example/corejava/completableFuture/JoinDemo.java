package com.example.corejava.completableFuture;

/**
 * Created by wei.zw on 2017/5/17.
 */
public class JoinDemo {

    static class Computer extends Thread{
        private int start;
        private int end;
        private int result;
        private int[] array;

        public Computer(int[] array,int start,int end){
            this.array=array;
            this.start=start;
            this.end=end;
        }

        public void run(){
            for(int i=start;i<end;i++){
                result+=array[i];
                if(result<0){
                    result&=Integer.MAX_VALUE;
                }
            }
        }

        public int getResult(){
            return result;
        }



        private static final int counter=1000000;

        public static void main(String[] args) throws Exception{
            int[] array=new int[counter];
            for(int i=0;i<counter;i++){
                array[i]=i;
            }
            long startTime=System.currentTimeMillis();
            Computer c1=new Computer(array,0,counter/2);
            Computer c2=new Computer(array,counter/2,counter);
            c1.start();
            c2.start();
            c1.join();
            c2.join();

            System.out.println(System.currentTimeMillis()-startTime);
            System.out.println(c1.getResult()+c2.getResult());
        }
    }
}
