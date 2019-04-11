package com.example.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class EchoClient {

    public static void main(String[] args){

        //worker负责读写数据
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            //辅助启动类
            Bootstrap bootstrap = new Bootstrap();

            //设置线程池
            bootstrap.group(worker);

            //设置socket工厂
            bootstrap.channel(NioSocketChannel.class);

            //设置管道
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    //获取管道
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    //字符串解码器
                    pipeline.addLast(new StringDecoder());
                    //字符串编码器
                    pipeline.addLast(new StringEncoder());
                    //处理类
                    pipeline.addLast(new ClientHandler());
                }
            });

            //发起异步连接操作
            ChannelFuture futrue = bootstrap.connect(new InetSocketAddress("127.0.0.1",8888)).sync();

            //等待客户端链路关闭
            futrue.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅的退出，释放NIO线程组
            worker.shutdownGracefully();
        }

    }


    public static class ClientHandler extends ChannelDuplexHandler {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("server response ： "+msg);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {

            for( int i=0;i<10000;i++) {
                TimeUnit.SECONDS.sleep(1);
                //给服务器发消息
                ctx.channel().writeAndFlush("i am client !");
            }

            System.out.println("channelActive");
        }
    }
}
