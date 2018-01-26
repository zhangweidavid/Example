/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月5日
 */

package common;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Desc:TODO 
 * @author wei.zw
 * @since 2017年5月5日 下午5:23:12
 * @version  v 0.1
 */
public class LogUitl {

	public static void log(String info){
		try {
			TimeUnit.SECONDS.sleep(2);
		}catch(Exception e) {
			
		}
		System.out.println(new Date()+"["+System.currentTimeMillis()+"],"+Thread.currentThread().getName()+","+info);
	}
	public static void main(String[] args){
		System.out.println(("1,\n" +
				"\n" +
				"2,\n" +
				"3\n").replaceAll("\n","").replaceAll(" ", "").replaceAll("，", ","));
	}

}
