/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月10日
 */

package com.example.corejava.nio.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe.SinkChannel;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月10日 上午9:31:42
 * @version v 0.1
 */
public class PipeSink implements Runnable {
	private SinkChannel sinkChannel;

	/**
	 * @param sinkChannel
	 */
	public PipeSink(SinkChannel sinkChannel) {
		super();
		this.sinkChannel = sinkChannel;
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		System.out.println("=========The sink is start!===========");
		try {
			sinkChannel.write(ByteBuffer.wrap(new String("Hello source!").getBytes("UTF-8")));
			System.out.println("send message to source is done...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SinkChannel getSinkChannel() {
		return sinkChannel;
	}

	public void setSinkChannel(SinkChannel sinkChannel) {
		this.sinkChannel = sinkChannel;
	}

}
