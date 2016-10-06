package com.iqianggou.android.hardlang;

import android.app.Application;

/**
 * Created by Administrator on 2016/10/6.
 */

public class HardApplication extends Application {

    private static HardApplication INSTANCE;

    @Override
    public void onCreate(){
        super.onCreate();

        INSTANCE = this;
    }

    public static HardApplication getInstance(){
        return INSTANCE;
    }
}
