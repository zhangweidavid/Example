package com.example.corejava;



import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**hasValue
 * Created by wei.zw on 2017/6/7.
 */
public class StringTestDemo {

    public static void main(String[] args) throws Exception {
        List<List<String>> ll=new ArrayList<>();
        ll.addAll(null);
        System.out.print(ll.stream().flatMap(li->li.stream()).findAny().isPresent());
        List<String> handler = new ArrayList<>(1500);

        for (int i = 0; i < 10000; i++) {
            HugeStr h = new HugeStr();
            String t = h.getSubString(1, 5);
            handler.add(t);

        }

        String orgStr = null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append(i).append(";");
        }
        orgStr = sb.toString();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            orgStr.split(";");
        }
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        StringTokenizer st = new StringTokenizer(orgStr, ";");
        for (int i = 0; i < 10000; i++) {
            while (st.hasMoreTokens()) {
                st.nextToken();
            }

            st = new StringTokenizer(orgStr, ";");
        }

        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();

        System.out.println(System.currentTimeMillis() - start);
    }

    static class HugeStr {
        static char[] c = new char[1000000];

        static {
            for (int i = 0; i < 1000000; i++) {
                c[i] = 'a';
            }
        }

        private String str = new String(new char[100000]);

        public String getSubString(int begin, int end) {
            return str.substring(begin, end);
        }

    }
}
