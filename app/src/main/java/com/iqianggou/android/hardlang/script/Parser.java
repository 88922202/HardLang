package com.iqianggou.android.hardlang.script;

/**
 * Created by Administrator on 2016/10/6.
 */

public class Parser {

    public static String[] parseScript(String script){
        return script.split("\\n");
    }

    public static String[] parseCommand(String command){
        return command.split(" ");
    }

    public static float getFloatParam(String param){
        return Float.valueOf(param);
    }

    public static String getStringParam(String param){
        return param.substring(1, param.length() - 1);
    }
}
