package common;

/**
 * Created by wei.zw on 2017/5/21.
 */
public class RingBuffer<T> {
	private final static int bufferSize = 1024;
	private String[] buffer = new String[bufferSize];
	private int head = 0;
	private int tail = 0;
	
	private Boolean empty() {
		return head == tail;
	}
	private Boolean full() {
		return ((tail + 1) &(bufferSize-1)) == head;
	}
	public Boolean put(String v) {
		if (full()) {
			return false;
		}
		buffer[tail] = v;
		tail = (tail+1)&(bufferSize-1);
		return true;
	}
	public String get() {
		if (empty()) {
			return null;
		}
		String result = buffer[head];
		head = (head + 1) & (bufferSize-1);
		return result;
	}
	public String[] getAll() {
		if (empty()) {
			return new String[0];
		}
		int copyTail = tail;
		int cnt = head < copyTail ? copyTail - head : bufferSize - head + copyTail;
		String[] result = new String[cnt];
		if (head < copyTail) {
			for (int i = head; i < copyTail; i++) {
				result[i - head] = buffer[i];
			}
		} else {
			for (int i = head; i < bufferSize; i++) {
				result[i - head] = buffer[i];
			}
			for (int i = 0; i < copyTail; i++) {
				result[bufferSize - head + i] = buffer[i];
			}
		}
		head = copyTail;
		return result;
	}
    /**
     * 测试代码
     */
    public static void main(final String[] args) {
        final RingBuffer<Integer> buffer = new RingBuffer<Integer>();
        for (int i = 0; i < 99; i++) {
            buffer.put(String.valueOf(i));
            System.out.println("tail="+buffer.tail);
        }

        final String[] queueArray =buffer.getAll();
        System.out.println("按入队列的先后顺序打印出来：");
        for (final Object o : queueArray) {
            System.out.println(o);
        }
      
        System.out.println("head index: " + buffer.head);
        System.out.println("tail index: " + buffer.tail);

    }

}
