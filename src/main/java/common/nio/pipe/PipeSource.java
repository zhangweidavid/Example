/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月10日
 */

package common.nio.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe.SourceChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月10日 上午9:39:22
 * @version v 0.1
 */
public class PipeSource implements Runnable {
	private Selector selector;
	private SourceChannel sourceChannel;

	public PipeSource(SourceChannel sourceChannel) {
		this.sourceChannel = sourceChannel;
		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void init() throws IOException {
		sourceChannel.configureBlocking(false);
		this.selector = Selector.open();
		sourceChannel.register(selector, SelectionKey.OP_READ);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void run() {
		System.out.println("=========The source is start!===========");
		try {
			while (true) {
				selector.select();
				Iterator ite = this.selector.selectedKeys().iterator();
				while (ite.hasNext()) {
					SelectionKey key = (SelectionKey) ite.next();
					ite.remove();
					if (key.isReadable())
						read(key);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param key
	 * @throws IOException
	 */
	private void read(SelectionKey key) throws IOException {
		SourceChannel channel = (SourceChannel) key.channel();
		ByteBuffer buf = ByteBuffer.allocate(100);
		channel.read(buf);
		byte[] data = buf.array();
		String msg = new String(data, "UTF-8").trim();
		System.out.println("message come from sink:" + msg);
	}
}