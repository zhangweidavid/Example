/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月10日
 */

package common.nio.pipe;

import java.io.IOException;
import java.nio.channels.Pipe;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月10日 上午9:31:12
 * @version v 0.1
 */
public class PipeDemo {
	public static void main(String[] args) {
		// 创建一个管道
		Pipe pipe = null;
		try {
			pipe = Pipe.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ExecutorService exec = Executors.newFixedThreadPool(2);
		exec.submit(new PipeSink(pipe.sink()));
		exec.submit(new PipeSource(pipe.source()));
	}
}
