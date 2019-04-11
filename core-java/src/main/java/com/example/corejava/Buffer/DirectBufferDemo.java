package com.example.corejava.Buffer;

import java.nio.ByteBuffer;

/**
 * Created by wei.zw on 2017/6/9.
 */
public class DirectBufferDemo {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(500);
        for (int i = 0; i < 100000; i++) {
            for (int j = 0; j < 99; j++) {
                directBuffer.put((byte) j);
            }
            directBuffer.flip();

            for (int j = 0; j < 99; j++) {
                directBuffer.get();
            }
            directBuffer.clear();
        }

        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        ByteBuffer buffer = ByteBuffer.allocate(500);
        for (int i = 0; i < 100000; i++) {
            for (int j = 0; j < 99; j++) {
                buffer.put((byte) j);
            }
            buffer.flip();

            for (int j = 0; j < 99; j++) {
                buffer.get();
            }
            buffer.clear();
        }

        System.out.println(System.currentTimeMillis() - start);
    }
}
