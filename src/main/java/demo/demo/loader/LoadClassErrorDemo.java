package demo.demo.loader;

/**
 * Created by wei.zw on 2017/6/2.
 */
public class LoadClassErrorDemo {
    public static void main(String[] args){
        new Thread(()->{
            try{
                Thread.sleep(300);
            }catch (Exception e){}
            B.getInstance().test2();
        }).start();
    }

}
