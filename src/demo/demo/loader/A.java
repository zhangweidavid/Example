package demo.demo.loader;

/**
 * Created by wei.zw on 2017/6/2.
 */
public abstract  class A {

    public A(){
        System.out.println("初始化 A");
        list();
    }

    public void list(){
        System.out.println("A.list");
        test();
    }
    abstract void   test();
}
