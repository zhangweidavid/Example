package com.example.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoServer {

    private static final Logger logger= LoggerFactory.getLogger(EchoServer.class);

    public static void main(String[] args) {


        EventLoopGroup boss = new NioEventLoopGroup(0,(runable)->{
            return new Thread(runable,"boss-Thread");
        });
        EventLoopGroup worker = new NioEventLoopGroup(0,(runable)->{
            return new Thread(runable,"worker-Thread");
        });
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker);

            serverBootstrap.channel(NioServerSocketChannel.class);

            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    //获取管道
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    //字符串解码器
                    pipeline.addLast(new StringDecoder());
                    //字符串编码器
                    pipeline.addLast(new StringEncoder());
                    //处理类
                    pipeline.addLast(new ServerHandler());
                }
            });

            //设置TCP参数
            //1.链接缓冲池的大小（ServerSocketChannel的设置）
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            //维持链接的活跃，清除死链接(SocketChannel的设置)
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            //关闭延迟发送
            serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);

            //绑定端口
            ChannelFuture future = serverBootstrap.bind(8888).sync();
           logger.info("server start ...... ");

            //等待服务端监听端口关闭z
            future.channel().closeFuture().sync();
        } catch (Exception e) {

        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }


    public static class ServerHandler extends ChannelInboundHandlerAdapter {


        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            logger.info("client response :" + msg);
            ctx.channel().writeAndFlush("i am server !");
        }

        //新客户端接入
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("channelActive");
        }

        //客户端断开
        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("channelInactive");
        }

        //异常
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            //关闭通道
            ctx.channel().close();
            //打印异常
            cause.printStackTrace();
        }
    }
}
