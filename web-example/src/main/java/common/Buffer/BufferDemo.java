package common.Buffer;

import java.nio.ByteBuffer;

/**
 * Created by wei.zw on 2017/6/8.
 */
public class BufferDemo {

    public static void main(String[] args) {
        ByteBuffer b = ByteBuffer.allocate(15);
        System.out.println("limit=" + b.limit() + " capacity=" + b.capacity() + " position=" + b.position());

        for (int i = 0; i < 10; i++) {
            b.put((byte) i);
        }

        System.out.println("limit=" + b.limit() + " capacity=" + b.capacity() + " position=" + b.position());
        b.flip();
        System.out.println("limit=" + b.limit() + " capacity=" + b.capacity() + " position=" + b.position());
        for (int i = 0; i < 5; i++) {
            System.out.print(b.get());
        }
        System.out.println();
        System.out.println("limit=" + b.limit() + " capacity=" + b.capacity() + " position=" + b.position());
        b.flip();
        System.out.println("limit=" + b.limit() + " capacity=" + b.capacity() + " position=" + b.position());


        b = ByteBuffer.allocate(15);
        for (int i = 0; i < 10; i++) {
            b.put((byte) i);
        }
        b.flip();
        for (int i = 0; i < b.limit(); i++) {
            System.out.print(b.get());
            if (i == 4) {
                b.mark();
                System.out.println("\n(mark at " + i + ")");
            }
        }
        b.reset();
        System.out.println("\nreset to mark");
        while (b.hasRemaining()) {
            System.out.print(b.get());
        }
        System.out.println();

        ByteBuffer bf=ByteBuffer.allocate(15);
        for(int i=0;i<10;i++){
            bf.put((byte)i);
        }

        ByteBuffer c=bf.duplicate();
        System.out.println("After b.duplicate()");

        System.out.println(bf);
        System.out.println(c);
        c.flip();
        System.out.println(bf);
        System.out.println(c);
        c.put((byte)100);
        System.out.println("After c.put((byte)100)");
        System.out.println("bf.get(0)="+bf.get(0));
        System.out.println("c.get(0)="+c.get(0));


        bf=ByteBuffer.allocate(15);
        for(int i=0;i<10;i++){
            bf.put((byte)i);
        }
        bf.position(2);
        bf.limit(6);

        ByteBuffer sub=bf.slice();
        for(int i=0;i<sub.capacity();i++){
            byte bb=sub.get(i);
            bb*=10;
            sub.put(i,bb);
        }
        bf.position(0);
        bf.limit(bf.capacity());
        while(bf.hasRemaining()){
            System.out.println(bf.get()+" ");
        }
    }
}
