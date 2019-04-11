package common.proxy.javassist;

import common.proxy.DBQuery;
import common.proxy.IDBQuery;
import javassist.*;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

import java.lang.reflect.Method;

/**
 * Created by wei.zw on 2017/6/7.
 */
public class JavassistDBQueryProxyFactory {

    public static class JavassistDBQuryHandler implements MethodHandler {

        IDBQuery real = null;

        public Object invoke(Object proxy, Method method, Method proceed, Object[] args)throws  Throwable {
            System.out.println(proxy.getClass().getName()+", method:"+method.getName());
            if (real == null) {
                real = new DBQuery();
            }
            return real.request();
        }
    }

    public static IDBQuery createProxy() throws Exception {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(new Class[]{IDBQuery.class});
        Class proxyClass = proxyFactory.createClass();
        ProxyObject proxyObject = (ProxyObject) proxyClass.newInstance();
        proxyObject.setHandler(new JavassistDBQuryHandler());
        return (IDBQuery) proxyObject;

    }

    public static IDBQuery createJavassistBytecodeProxy() throws Exception {
        ClassPool mPool =ClassPool.getDefault();
        CtClass mCtc = mPool.getCtClass(DBQuery.class.getName());
        CtMethod method=  mCtc.getDeclaredMethod("request");
        method.insertBefore("System.out.println(\""+method.getName()+"\" ");
        
        return null;
    }
}
