/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月11日
 */

package common.load;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月11日 下午12:22:35
 * @version v 0.1
 */
public class LoadClassErrorDemo {
	public static void main(String[] args) {
		new Thread(()->{
			try{
				Thread.sleep(300);
			}catch(Exception e){
				e.printStackTrace();
			}
			B.getInstance().test2();
		}).start();
		
		new Thread(()->{
			try{
				Thread.sleep(300);
			}catch(Exception e){
				e.printStackTrace();
			}
			B.getInstance().test2();
		}).start();
	}
}
