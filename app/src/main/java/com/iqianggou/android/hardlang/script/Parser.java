package com.iqianggou.android.hardlang.script;

import com.iqianggou.android.hardlang.script.exception.IllegalCommandException;
import com.iqianggou.android.hardlang.script.exception.IllegalParamException;

/**
 * Created by Administrator on 2016/10/6.
 */

public class Parser {

    public static String[] parseScript(String script){
        return script.split("\\r\\n");
    }

    public static String[] parseCommand(String command){
        return command.split(" ");
    }

    public static int getIntParam(String param) throws IllegalParamException{
        if (param.matches("^-?[1-9]\\d*|0")){
            return Integer.valueOf(param);
        }else if (param.matches("^\\w+")){
            return getConstantParam(param);
        }else {
            throw new IllegalParamException("Invalid int param.");
        }
    }

    public static float getFloatParam(String param) throws IllegalParamException{
        try {
            return Float.valueOf(param);
        }catch (NumberFormatException e){
            throw new IllegalParamException("Invalid float param.");
        }
    }

    public static boolean getBooleanParam(String param) throws IllegalParamException{
        switch (param){
            case "true":
                return true;
            case "false":
                return false;
            default:
                throw new IllegalParamException("Invalid boolean param.");
        }
    }

    public static String getStringParam(String param) throws IllegalParamException{
        try {
            return param.substring(1, param.length() - 1);
        }catch (Exception e){
            throw new IllegalParamException("Invalid string param");
        }
    }

    public static int getConstantParam(String param) throws IllegalParamException{
        try {
            return ConstantMap.getConstant(param);
        }catch (Exception e){
            throw new IllegalParamException("Invalid constant param");
        }
    }
}
