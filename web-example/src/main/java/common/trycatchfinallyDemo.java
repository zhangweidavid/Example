package common;

/**
 * Created by wei.zw on 2017/5/28.
 */
public class trycatchfinallyDemo {
    public static void main(String[] args){
        System.out.println(returnMethod());
        System.out.print(finllayMethod());
    }

    public static String returnMethod(){
        try{
            return "a";
        }finally{
            return "b";
        }
    }

    public static String finllayMethod(){
        try{
            System.exit(0);
        }finally{
            return "finally";
        }
    }
}
