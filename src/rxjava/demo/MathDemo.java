/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年3月3日
 */

package rxjava.demo;

import io.reactivex.Flowable;

/**
 * Desc:TODO 
 * @author wei.zw
 * @since 2017年3月3日 下午1:42:48
 * @version  v 0.1
 */
public class MathDemo {

	/**
	 * 
	 * @param args
	 * @author wei.zw
	 */
	public static void main(String[] args) {
		/**
		 * reduce
		 */
		Flowable.range(1, 10).reduce((a,b)->a+b).subscribe(System.out::println);
	}

}
