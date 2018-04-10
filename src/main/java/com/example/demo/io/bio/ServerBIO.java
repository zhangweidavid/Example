package com.example.demo.io.bio;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerBIO {
    private static final int PORT=12345;

    private static ServerSocket serverSocket;

    public static  void start(){
        start(PORT);
    }

    public synchronized static void   start(int port){
            if(serverSocket!=null){
                return ;
            }
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("服务器已启动，端口号：" + port);
                while(true){
                    Socket socket = serverSocket.accept();
                    //当有新的客户端接入时，会执行下面的代码
                    //然后创建一个新的线程处理这条Socket链路
                    new Thread(new ServerHandler(socket)).start();
                }
            }catch (Exception e){

            }

    }
}
