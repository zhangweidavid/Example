package common.common.invocation;

import java.lang.reflect.Method;

/**
 * Created by wei.zw on 2017/5/23.
 */
public class MethodInvokeDemo {

    public static void main(String[] args)throws Exception{

//        List<String> list=Arrays.asList("a");
//        list.remove("a");
//        list.add("a");
//        System.out.println(list);

        Method method=MethodInvokeDemo.class.getDeclaredMethod("test",String.class,int.class);
      System.out.println(method.invoke(null,"invokeDemo",1));
    }

    public static String test(String a,int i){
        return "传入的参数 1 ="+a+",参数2 "+i;
    }
}
