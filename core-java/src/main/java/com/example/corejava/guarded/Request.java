package com.example.corejava.guarded;

/**
 * Created by wei.zw on 2017/6/9.
 */
public class Request {
    private String name;

    public Request(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public String toString() {
        return "[ Request " + name + "]";
    }
}
