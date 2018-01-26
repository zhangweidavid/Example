package common.guarded;

/**
 * Created by wei.zw on 2017/6/9.
 */
public class TestClass {

    public static void main(String[] args) {

       // Object
        RequestQueue requestQueue = new RequestQueue();
        for (int i = 0; i < 10; i++) {
            new ServerThread(requestQueue, "ServerThread" + i).start();
        }

        for (int i = 0; i < 10; i++) {
            new ClientThread(requestQueue, "ClientThread" + i).start();
        }
    }
}
