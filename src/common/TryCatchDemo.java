package common;

/**
 * Created by wei.zw on 2017/6/9.
 */
public class TryCatchDemo {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int a=0;
        for (double i = 0; i < 1000000000; i++) {
            try {
                a++;
            } catch (Exception e) {
            }
        }

        System.out.println(System.currentTimeMillis()-start);

        start=System.currentTimeMillis();
        for (double i = 0; i < 1000000000; i++) {
                a++;
        }
        System.out.println(System.currentTimeMillis()-start);

    }
}
