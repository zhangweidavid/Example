/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月5日
 */

package disruptor.demo;

import java.nio.ByteBuffer;

import com.lmax.disruptor.RingBuffer;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月5日 下午1:38:26
 * @version v 0.1
 */
public class LongEventProducer {
	private final RingBuffer<LongEvent> ringBuffer;

	public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	/**
	 * onData用来发布事件，每调用一次就发布一次事件事件 它的参数会通过事件传递给消费者
	 * 
	 * @param bb
	 */
	public void onData(ByteBuffer bb) {
		// 可以把ringBuffer看做一个事件队列，那么next就是得到下面一个事件槽
		//Increment and return next sequence for ring buffer
		long sequence = ringBuffer.next();
		try {
			// 用上面的索引取出一个空的事件用于填充
			// get the event for a given  sequence in the ring buffer
			LongEvent event = ringBuffer.get(sequence);// for the sequence
			event.setValue(bb.getLong(0));
		} finally {
			// 发布事件
			System.out.println("发布事件["+sequence+"]");
			ringBuffer.publish(sequence);
		}
	}
}
