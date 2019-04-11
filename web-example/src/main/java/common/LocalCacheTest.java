package common;

/**
 * Created by wei.zw on 2017/5/18.
 */
public class LocalCacheTest {

    public static void main(String[] args){
        LocalCacheService localCache=LocalCacheService.getInstance();
        for(int i=0;i<10000000;i++){
            localCache.putValue("test_"+i,i,10000);
        }
        System.out.println(localCache.getLocalCacheSize());
    }
}
