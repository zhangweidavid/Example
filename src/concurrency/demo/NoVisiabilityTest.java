package concurrency.demo;

/**
 * Created by wei.zw on 2017/5/18.
 */
public class NoVisiabilityTest {

    private static  class ReadThread extends  Thread{
        private boolean ready;
        private int numbr;

        public void run(){
            while(!ready){
                numbr++;
            }
            System.out.println("thread:"+ready);
        }

        public void readyOn(){
            this.ready=true;
        }
    }

    public static void  main(String[] args) throws InterruptedException {
        ReadThread readThread=new ReadThread();
        readThread.start();
        Thread.sleep(200);
        readThread.readyOn();
        System.out.println(readThread.ready);
    }
}
