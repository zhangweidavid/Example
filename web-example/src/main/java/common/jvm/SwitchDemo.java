package common.jvm;

/**
 * Created by wei.zw on 2017/6/17.
 */
public class SwitchDemo {

    int chooseNear(int i){
        switch (i){
            case 0: return 0;
            case 1:return 1;
            case 2:return 2;
            default: return  -1;
        }

    }

    int chooseFar(int i){
        switch (i){
            case -100:return -1;
            case 0:return 0;
            case 100:return 1;
            default: return -1;
        }
    }
}
