/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月27日
 */

package core.demo;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月27日 下午10:43:37
 * @version v 0.1
 */
public class A {
	public static int a=1;
	static{
		System.out.println("A static");
	}
	public int i, j;

	public A() {
		i = 100;
		j = 200;
		this.println();
	}

	public void println() {
		System.out.println(i + " |" + j);
	}

}
