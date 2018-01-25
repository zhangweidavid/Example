/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * <p>
 * Date: 2017年5月5日
 */

package disruptor.demo;

/**
 * Desc:TODO
 *
 * @author wei.zw
 * @since 2017年5月5日 下午1:37:35
 * @version v 0.1
 */
public class LongEvent {
    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LongEvent{value=" + value + '}';
    }
}
