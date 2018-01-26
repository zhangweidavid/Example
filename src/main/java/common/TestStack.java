package common;

/**
 * Created by wei.zw on 2017/6/9.
 */
public class TestStack {

    private int count = 0;

    public void recursion() {
        count++;
        recursion();
    }

    public static void main(String[] args) {
        TestStack ts = new TestStack();
        try {
            ts.recursion();
        } catch (Throwable t) {
            System.out.println(" deep of stack is " + ts.count);
        }
    }
}
