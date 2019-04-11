package com.example.corejava;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wei.zw on 2017/5/31.
 */
public class RegexDemo {

    public final static Pattern USER_NAME_PATTERN = Pattern
            .compile("^([\u4e00-\u9fa5|A-Z]+?\\s*\\.??\\s*)+");

    public final static Pattern NAME = Pattern.compile("[A-Za-z0-9_\\-\u4e00-\u9fa5]+");


    public static void main(String[] args) {
        Matcher pm
                = USER_NAME_PATTERN.matcher("QWERTYUIOPASDFGHJKLZXCVBNMQWERTYUIOPASDC1");
        System.out.println(pm.matches());
    }


}
