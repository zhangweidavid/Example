package common.string;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HiddenDemo {

    public static void main(String[] args) {

        String input = "1998";
        List<Integer> hiddens=Arrays.asList(2,3);
        int len = 0;
        if (input.contains(".")) {
            len = input.indexOf(".");
        } else {
            len = input.length();
        }
        char[] charArray = input.toCharArray();

        for (int i = 0; i < len; i++) {
            System.out.println(len - i);
            if (hiddens.contains(len-i)) {
                charArray[i] = '?';
            }
        }
        System.out.println(String.valueOf(charArray));
    }
}
