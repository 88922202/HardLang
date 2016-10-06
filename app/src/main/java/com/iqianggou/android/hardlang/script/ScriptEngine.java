package com.iqianggou.android.hardlang.script;

import android.util.Log;

import com.iqianggou.android.hardlang.HardApplication;

/**
 * Created by Administrator on 2016/10/6.
 */

public class ScriptEngine {

    private IExecuteScript mExecutor;

    public ScriptEngine(IExecuteScript executor){
        mExecutor = executor;
    }

    public void startEngine(){
        Thread worker = new Thread(new Runnable() {
            @Override
            public void run() {
                loadScript("move.txt");
            }
        });
        worker.start();
    }

    private void loadScript(String fileName){
        String script = ScriptLoader.loadScript(HardApplication.getInstance(), fileName);
        Log.d("ScriptEngine", script);
        String[] commands = Parser.parseScript(script);
        for (String eachCommand : commands){
            String[] tokens = Parser.parseCommand(eachCommand);
            mExecutor.executeScript(tokens);
        }
    }

}
