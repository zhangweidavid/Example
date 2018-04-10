package com.example.demo.io.aio.server;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

public class AsyncServerHandler implements Runnable {
    public AsynchronousServerSocketChannel channel;

    public AsyncServerHandler(int port) {
        try {
            //开启AsynchronouseServerSocket
            channel = AsynchronousServerSocketChannel.open();
            //绑定端口
            channel.bind(new InetSocketAddress(port), 1024);
            System.out.println("服务器已启动，端口号：" + port);
        } catch (Exception e) {

        }
    }

    @Override
    public void run() {

        //用于接收客户端的连接
        channel.accept(this,new AcceptHandler());
    }
}
