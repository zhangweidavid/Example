/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月7日
 */

package core.demo;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月7日 下午6:38:14
 * @version v 0.1
 */
public class StringTest {

	public static String i = "";

	public static void main(String[] args) {
		String a = "a";
		String a1 = new String("a");
		String a2 = a1.trim() + "";
		String a3 = "a" + "";
		String a4 = "a".trim() + "";
		String a5 = i + "a";
		System.out.println(a == a1);
		System.out.println(a.intern() == a1.intern());
		System.out.println(a2 == a1);
		System.out.println(a3 == a);
		System.out.println(a4 == a);

	}

}
