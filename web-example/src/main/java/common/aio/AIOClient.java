package common.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

/**
 * Created by wei.zw on 2017/6/8.
 */
public class AIOClient {
    public static void main(String... args) throws Exception {
        AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
        client.connect(new InetSocketAddress("localhost", 8000));
        client.write(ByteBuffer.wrap("test".getBytes()));
    }
}
