package core.demo;

/**
 * Created by wei.zw on 2017/5/28.
 */
public class StaticDemo {
    public static final  int i=1;

    public static void main(String[] args){
        System.out.print(i);
        b();
    }
    public static void b(){
        System.out.print(i);
    }
}
