package common.jvm;

/**
 * Created by wei.zw on 2017/6/17.
 */
public class ExceptionDemo {
    void cantBeZero(int i) throws IllegalArgumentException{
        if(i==0){
            throw new IllegalArgumentException();
        }
    }
}
