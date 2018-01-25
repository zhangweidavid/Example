package concurrency.demo;

/**
 * Created by wei.zw on 2017/5/22.
 */
public class ShutDownHookDemo {
    public static void main(String[] args){
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("shutDownHook processed");
        }));
        System.exit(1);
    }
}
