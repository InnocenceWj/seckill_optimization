package com.wj.utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Random;

/**
 * @创建人 wj
 * @创建时间 2018/11/15
 * @描述
 */
public class VerfyUtil {
    private static char[] ops = new char[]{'+', '-', '*'};

    public static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer) engine.eval(exp);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * + - *
     */
    public static String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        String exp = "" + num1 + op1 + num2 + op2 + num3;
        System.out.println(exp);
        return exp;
    }
}
