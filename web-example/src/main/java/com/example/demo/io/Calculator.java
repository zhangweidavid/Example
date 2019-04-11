package com.example.demo.io;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Calculator {
    private final static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

    public static Object cal(String expression) {
        try {
            return jse.eval(expression);
        }catch ( Exception e){
            return "nio";
        }
    }
}
