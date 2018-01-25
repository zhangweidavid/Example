/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月11日
 */

package core.demo.load;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月11日 下午12:23:47
 * @version v 0.1
 */
public class B extends A {

	private final static B instance = new B();

	public static B getInstance() {
		return instance;
	}

	/**
	 * @see core.demo.load.A#test()
	 */
	@Override
	public void test() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// logger.error("", e);
		}
		instance.test2();
	}

	public void test2() {
	}

}
