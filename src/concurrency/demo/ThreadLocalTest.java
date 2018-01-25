package concurrency.demo;

/**
 * Created by wei.zw on 2017/5/18.
 */
public class ThreadLocalTest {

    static class ResourceClass{
        public static  final ThreadLocal<String> RESOURCE_1=new ThreadLocal<>();

        public static final ThreadLocal<String> RESOURCE_2=new ThreadLocal<>();
    }

    static  class A{
        public void setOne(String value){
            ResourceClass.RESOURCE_1.set(value);
        }

        public void setTwo(String value){
            ResourceClass.RESOURCE_2.set(value);
        }


    }

    static class B{
        public void display(){
            System.out.println(ResourceClass.RESOURCE_1.get()+":"+ResourceClass.RESOURCE_2.get());
        }
    }

    public static void main(String[] args){
        final A a=new A();
        final B b=new B();
        for(int i=0;i<15;i++){
            final  String resource1="线程-"+i;
            final String resource2="value= ("+i+")";
            new Thread(()->{
                try{
                    a.setOne(resource1);
                    a.setTwo(resource2);
                    b.display();
                }finally {
                    ResourceClass.RESOURCE_1.remove();
                    ResourceClass.RESOURCE_2.remove();
                }
            }).start();


        }
    }
}
