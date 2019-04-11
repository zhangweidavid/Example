package common.Buffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by wei.zw on 2017/6/9.
 */
public class GatheringAndScatteringDemo {

    public static void main(String[] args) throws IOException {
        ByteBuffer bookBuf=ByteBuffer.wrap("java 性能优化技巧".getBytes());
        ByteBuffer autBuf=ByteBuffer.wrap("葛一鸣".getBytes());

        int booklen=bookBuf.limit();
        int authLen=autBuf.limit();
        ByteBuffer[] bufs=new ByteBuffer[]{bookBuf,autBuf};

        File file=new File("/Users/david/work/test1.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fos=new FileOutputStream(file);
        FileChannel fc=fos.getChannel();
        fc.write(bufs);
        fc.close();
        fos.close();

        ByteBuffer b1=ByteBuffer.allocate(booklen);
        ByteBuffer b2=ByteBuffer.allocate(authLen);

        ByteBuffer[] bs=new ByteBuffer[]{b1,b2};

        FileInputStream fis=new FileInputStream(file);
        fc=fis.getChannel();
        fc.read(bs);
        String bookName=new String(bs[0].array());
        String authName=new String(bs[1].array());
        System.out.println(bookName+authName);
        fc.close();
        fis.close();
        file.delete();

    }
}
