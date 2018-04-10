package com.example.demo.io.aio.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int DEFAULT_PORT=12345;

    private static AsyncServerHandler serverHandler;
    private static ExecutorService executorService= Executors.newFixedThreadPool(1);
    public volatile static long clientCount = 0;

    public static void  start (){
        start(DEFAULT_PORT);
    }

    public static synchronized void start(int port){
        if(serverHandler!=null)
            return;
        serverHandler = new AsyncServerHandler(port);
        executorService.execute(serverHandler);

    }
}
