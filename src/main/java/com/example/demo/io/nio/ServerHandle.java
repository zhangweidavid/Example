package com.example.demo.io.nio;

import com.example.demo.io.Calculator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerHandle implements Runnable {
    private Selector selector;
    private ServerSocketChannel serverChannel;
    private volatile boolean started;

    public ServerHandle(int port) {
        try {
            //创建selector
            selector = Selector.open();
            //打开ServerSocketChannel
            serverChannel = ServerSocketChannel.open();
            //设置serverSocketChannel为非阻塞
            serverChannel.configureBlocking(false);
            //绑定端口
            serverChannel.bind(new InetSocketAddress(port), 1024);
            //将serverchannel注册到selector 监听 accept
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            started = true;
            System.out.println("服务器已启动，端口号：" + port);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
        started = false;
    }

    @Override
    public void run() {

        while (started) {
            try {
                //
                selector.select(1000);
                //获取准备就绪到selectionKey
                Set<SelectionKey> keys = selector.selectedKeys();
                //遍历
                Iterator<SelectionKey> it = keys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    //获取selectionKey
                    key = it.next();
                    //将当前selectionKey从迭代器中删除
                    it.remove();
                    try {
                        //对selectionKey进行处理
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
            } catch (Exception e) {
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            //处理接入请求
            if (key.isAcceptable()) {
                //获取selectionKey的ServerSocketChannel
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                //接受连接请求
                SocketChannel socketChannel = serverSocketChannel.accept();
                //配置非阻塞
                socketChannel.configureBlocking(false);
                //注册到selector
                socketChannel.register(selector, SelectionKey.OP_READ);
            }
            //可读
            if (key.isReadable()) {
                SocketChannel sc = (SocketChannel) key.channel();
                //创建ByteBuffer，并开辟一个1K的缓冲区
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                //读取请求码流，返回读取到的字节数
                int readBytes = sc.read(buffer);
                //读取到字节，对字节进行编解码
                if (readBytes > 0) {
                    //将缓冲区当前的limit设置为position=0，用于后续对缓冲区的读取操作
                    buffer.flip();
                    //根据缓冲区可读字节数创建字节数组
                    byte[] bytes = new byte[buffer.remaining()];
                    //将缓冲区可读字节数组复制到新建的数组中
                    buffer.get(bytes);
                    String expression = new String(bytes, "UTF-8");
                    System.out.println("服务器收到消息：" + expression);
                    Object result = Calculator.cal(expression);
                    doWrite(sc, result);
                } else if (readBytes < 0) {
                    key.cancel();
                    sc.close();
                }
            }
        }
    }

    //异步发送应答消息
    private void doWrite(SocketChannel channel, Object response) throws IOException {
        //将消息编码为字节数组
        byte[] bytes = String.valueOf(response).getBytes();
        //根据数组容量创建ByteBuffer
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        //将字节数组复制到缓冲区
        writeBuffer.put(bytes);
        //flip操作
        writeBuffer.flip();
        //发送缓冲区的字节数组
        channel.write(writeBuffer);
        //****此处不含处理“写半包”的代码
    }
}
