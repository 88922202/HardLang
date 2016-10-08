package com.iqianggou.android.hardlang.script;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/8.
 */

public class ConstantMap {

    private static Map<String, Integer> mConstantMap = new HashMap<>();

    public static Integer getConstant(String key){
        return mConstantMap.get(key);
    }

    public static void putConstant(String key, int value){
        mConstantMap.put(key, value);
    }
}
