package com.example.demo.io.nio;

/**
 * 1. open selector
 * 2. open serverSocketChannel
 * 3. config non-blocking
 * 4. binding IntetSocketAddress
 * 5. register accpet
 * 6. 创建，启动 Reactor
 * 7. Selector轮询就绪对SelectionKey
 * 8. 处理acceptable
 *   8.1 获取selectionKey的 serverSocketChannel
 *   8.2 serverSocketChannel接受客户端连接请求
 *   8.3 将客户端连接注册到Selector监听读操作
 * 9 读取消息到ByteBuffer
 * 10 解码
 * 11 处理消息
 * 12 异步写ByteBuffer到SocketChannel
 */
public class Server {
    private static int DEFAULT_PORT = 12345;
    private static ServerHandle serverHandle;
    public static void start(){
        start(DEFAULT_PORT);
    }
    public static synchronized void start(int port){
        if(serverHandle!=null)
            serverHandle.stop();
        serverHandle = new ServerHandle(port);
        System.out.println(" Server start....");
        new Thread(serverHandle,"Server").start();
    }
    public static void main(String[] args){
        start();
    }
}
