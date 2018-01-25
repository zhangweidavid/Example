/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月13日
 */

package demo.mbassdor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月13日 下午8:43:37
 * @version v 0.1
 */
public class TestDemo {

	private int value;
	//
	//
	private static final Logger logger=LogManager.getLogger();
	public static void main(String[] args) {
		int h="Test".hashCode()^"value".hashCode();
		System.out.println(Integer.toBinaryString(Integer.numberOfLeadingZeros(16)));
		System.out.println(Integer.toBinaryString(1<<15));
		int n=Integer.numberOfLeadingZeros(16) | (1 << (16 - 1));
		System.out.println((n<<16)+2);
		System.out.println(4>1?(16>>>3)/4:16);
	}

	public void setValue(int value){
		this.value=value;
	}
}
