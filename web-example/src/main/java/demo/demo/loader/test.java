package demo.demo.loader;

/**
 * Created by wei.zw on 2017/6/2.
 */
public class test {
    public static void main(String[] args){
        String b="sentry-javaagent-collector-1.0.6.jar";
        String version2 = b.substring("entry-javaagent-collector-".length(),
                b.length() - ".jar".length());
        System.out.println(version2);
        String str="1.0.6.jar";
        String[] vv = str.split("\\.");
        System.out.println(vv[0]+","+vv[1]+","+vv[2]);
		int[] iv = new int[3];
		 iv[0] = Integer.parseInt(vv[0]);
		 iv[1] = Integer.parseInt(vv[1]);
		 iv[2] = Integer.parseInt(vv[2]);
    }
}
