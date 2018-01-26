package common;

/**
 * Created by wei.zw on 2017/5/17.
 */
public class ThreadStackTest {

    public static void main(String[] args){
        printStack(getStackByThread());
        printStack(getStackByException());
    }
    private static void printStack(StackTraceElement[] stacks){
        //System.out.println(Reflection.getCallerClass());
        for(StackTraceElement element:stacks){
            System.out.println(element);
        }
        System.out.println("\n");
    }

    private static StackTraceElement[] getStackByThread(){
        return Thread.currentThread().getStackTrace();
    }

    private static StackTraceElement[] getStackByException(){
        return  new Exception().getStackTrace();
    }
}
