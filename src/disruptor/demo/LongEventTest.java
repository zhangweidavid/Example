/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * <p>
 * Date: 2017年5月5日
 */

package disruptor.demo;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * Desc:TODO
 *
 * @author wei.zw
 * @version v 0.1
 * @since 2017年5月5日 下午1:33:57
 */
public class LongEventTest {
    public static void main(String[] args) throws Exception {
        // the factory for event
        LongEventFactory factory = new LongEventFactory();
        int bufferSize = 16;
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, Executors.defaultThreadFactory(), ProducerType.SINGLE, new BlockingWaitStrategy());

        disruptor.handleEventsWithWorkerPool(event ->
                        System.out.println("one: " + event)
                , event ->
                        System.out.println("two:" + event)
        ).then(new LongEventHandler());


        disruptor.start();
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        LongEventProducer producer = new LongEventProducer(ringBuffer);

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (
                long l = 0; true; l++)

        {
            bb.putLong(0, l);
            producer.onData(bb);
            Thread.sleep(500);
        }
    }
}
