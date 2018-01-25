package common.proxy;

import java.util.concurrent.TimeUnit;

import com.sun.istack.internal.NotNull;

import common.LogUitl;
import common.proxy.jdk.JdkDBQueryProxyFactory;

/**
 * Created by wei.zw on 2017/6/7.
 */
public class Test {
	public static void main(String[] args)
			throws Exception {
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					profile();
				}
			}).start();
		}
		
		TimeUnit.SECONDS.sleep(10);

		// long r = new ForkJoinPool()
		// .invoke(new ForkJoinSumCalculator(LongStream.rangeClosed(1, 10 * 1000 * 1000*10).toArray()));
		//
		// begin = System.currentTimeMillis();
		// d = CglibDBQuryProxyFactory.createProxy();
		//
		// System.out.println(" createCglibProxy: " + (System.currentTimeMillis() - begin));
		//
		// System.out.println("CglibProxy class: " + d.getClass().getName());
		// d.request();
		// TimeUnit.SECONDS.sleep(30);
		//
		//
		// begin = System.currentTimeMillis();
		//
		// d = JavassistDBQueryProxyFactory.createProxy();
		//
		// System.out.println(" createJavassistProxy: " + (System.currentTimeMillis() - begin));
		//
		// System.out.println("JavassistProxy class: " + d.getClass().getName());
		// begin = System.currentTimeMillis();
		// for (int i = 0; i < 10000; i++) {
		// d.request();
		// }
		// long proxyTime = System.currentTimeMillis() - begin;
		// System.out.println(proxyTime);
		// begin = System.currentTimeMillis();
		// for (int i = 0; i < 10000; i++) {
		// real.request();
		// }
		// long allTime = System.currentTimeMillis() - begin;
		//
		// System.out.println(allTime + "性能占比：" + ((double)(allTime + 1) / (proxyTime + 1)));
		//
		//
		// begin = System.currentTimeMillis();
		// d = JavassistDBQueryProxyFactory.createProxy();
		//
		// System.out.println(" createJavassistBytecodeProxy: " + (System.currentTimeMillis() - begin));
		//
		// System.out.println("JavassistByteCodeProxy class: " + d.getClass().getName());
	}

	@NotNull
	public static void profile() {
		IDBQuery real = new DBQuery();
		IDBQuery d = null;
		long begin = System.currentTimeMillis();
		d = JdkDBQueryProxyFactory.createJdkProxy();
		LogUitl.log("test");
		d.request();

		LogUitl.log("end");
	}
}
