package com.example.corejava;

/**
 * Created by wei.zw on 2017/5/22.
 */
public class TestKey {
    private int key;


    public TestKey(int key) {
        this.key = key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return String.valueOf(this.key);
    }
}
