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
 * @since 2017年5月27日 下午10:44:34
 * @version v 0.1
 */
public class B extends A {
	public int i, j;
	static{
		System.out.print("B static ");
	}
	private static int b=1;

	public B() {
		super();
		i = 20;
		j = 10;
	}

	public void println() {
		System.out.println((1<<16)-1);
		printlnStack();
		System.out.println(i + ":" + j);
	}

	private void printlnStack() {
		StackTraceElement elements[] = Thread.currentThread().getStackTrace();
		for (int i = 0; i < elements.length; i++) {
			StackTraceElement stackTraceElement = elements[i];
			String className = stackTraceElement.getClassName();
			String methodName = stackTraceElement.getMethodName();
			String fileName = stackTraceElement.getFileName();
			int lineNumber = stackTraceElement.getLineNumber();
			System.out.println(className + "." + methodName + ",L " + lineNumber);
		}
	}

	public static void main(String[] args) {
		new B().println();
	}
}
