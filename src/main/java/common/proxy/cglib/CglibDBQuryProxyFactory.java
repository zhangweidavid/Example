package common.proxy.cglib;


import common.proxy.DBQuery;
import common.proxy.IDBQuery;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by wei.zw on 2017/6/7.
 */
public class CglibDBQuryProxyFactory {

    public static IDBQuery createProxy(){
        Enhancer enhancer=new Enhancer();
        enhancer.setCallback(new CglibDBQueryInterceptor());
        return (IDBQuery)enhancer.create();
    }

    public static class CglibDBQueryInterceptor implements MethodInterceptor{
        IDBQuery real=null;

        public Object intercept(Object arg0, Method method, Object[] args, MethodProxy methodProxy){
            if(real==null){
                real=new DBQuery();
            }
            return real.request();
        }

    }
}
