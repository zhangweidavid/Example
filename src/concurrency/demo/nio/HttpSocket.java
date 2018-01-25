/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月9日
 */

package concurrency.demo.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import common.LogUitl;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月9日 下午7:31:20
 * @version v 0.1
 */
public class HttpSocket {
	private Selector selector;
	private final Map<String, Long> startTimeMap = new HashMap<>();

	/**
	 * @param host
	 * @param port
	 */
	public HttpSocket() {
		super();

		try {
			selector = Selector.open();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * 
	 * 
	 * @author wei.zw
	 */
	public void start() {
		LogUitl.log("running...");
		long start = System.currentTimeMillis();

		while (startTimeMap.size() > 0) {
			try {
				selector.select(1);
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectionKeys.iterator();
				SelectionKey key = null;
				while (it.hasNext()) {
					key = it.next();
					it.remove();
					try {
						handleInput(key);
					} catch (Exception e) {
						if (key != null) {
							key.cancel();
							if (key.channel() != null) {
								key.channel().close();
							}
						}
					}
				}
			} catch (IOException e) {
				System.exit(1);
			}

		}
		System.out.println(System.currentTimeMillis() - start);
		// end
		if (selector != null) {
			try {
				LogUitl.log("close");
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		LogUitl.log("end...");

	}

	/**
	 * 处理输入
	 * @param key
	 * @author wei.zw
	 * @throws IOException
	 */
	private void handleInput(SelectionKey key) throws IOException {
		if (key.isValid()) {
			SocketChannel sc = (SocketChannel) key.channel();
			if (key.isConnectable()) {
				if (sc.finishConnect()) {
					String str = sc.getRemoteAddress().toString();
					String host = str.substring(0, str.indexOf("/"));
					doWrite(sc, host);
				} else {
					System.exit(1);
				}
			}
			if (key.isReadable()) {
				String str = sc.getRemoteAddress().toString();
				String host = str.substring(0, str.indexOf("/"));
				if (!startTimeMap.containsKey(host)) {
					return;
				}
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if (readBytes > 0) {

					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					readBuffer.clear();
					// String body = new String(bytes, "UTF-8");
					// System.out.println(body);

					LogUitl.log("接收：" + host + "的请求耗时：" + (System.currentTimeMillis() - startTimeMap.get(host)));
					startTimeMap.remove(host);
					// stop--;
				} else if (readBytes < 0) {
					key.cancel();
					sc.close();
				}
			}
		}
	}

	/**
	 * 创建SocketChannel 向Selector注册SocketChannel 链接channel的socket
	 * 
	 * @author wei.zw
	 * @throws IOException
	 */
	public void doHttpConnection(String host, int port) throws IOException {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		startTimeMap.put(host, System.currentTimeMillis());
		LogUitl.log("connecting  to " + host);
		socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
		socketChannel.connect(new InetSocketAddress(InetAddress.getByName(host), port));
	}

	/**
	 * 
	 * @param socketChannel2
	 * @author wei.zw
	 * @throws IOException
	 */
	private void doWrite(SocketChannel socketChannel, String host) throws IOException {
		StringBuilder sb = new StringBuilder().append("GET / HTTP/1.1\r\n").append("Host: " + host + " \r\n")
				.append("\r\n");
		byte[] req = sb.toString().getBytes();
		//创建一个Buffer
		ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
		//将数据写入Buffer
		writeBuffer.put(req);
		//调用flip方法
		writeBuffer.flip();
		//将buffer数据写入到channel
		socketChannel.write(writeBuffer);

		if (!writeBuffer.hasRemaining()) {

		}
	}

}
