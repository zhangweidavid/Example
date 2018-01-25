package demo.demo.loader;

/**
 * Created by wei.zw on 2017/6/2.
 */
public class B extends A {
    private final static B instance = new B();

    public static B getInstance() {
        return instance;
    }


    public B() {
        System.out.println("初始化B");
    }

    public void test() {
        System.out.println("B.test");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        instance.test2();
    }

    public void test2() {
        System.out.println("B.test2");
    }
}
