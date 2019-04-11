package com.example.corejava.io.forgenio;

import com.example.corejava.io.bio.ServerHandler;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerForgeNIO {
    private static final int PORT=12345;

    private static ServerSocket serverSocket;

    //线程池 懒汉式的单例
    private static ExecutorService executorService = Executors.newFixedThreadPool(60);

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
                executorService.execute(new ServerHandler(socket));
            }
        }catch (Exception e){

        }

    }
}
