package common.proxy.jdk;

import common.proxy.DBQuery;
import common.proxy.IDBQuery;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by wei.zw on 2017/6/7.
 */
public class JdkDBQueryHandler implements InvocationHandler {

    IDBQuery real=null;

    public Object invoke(Object proxy, Method method, Object[] args) throws  Throwable{
        if(real==null){
            real=new DBQuery();
        }
        return real.request();
    }


}
