package com.iqianggou.android.hardlang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iqianggou.android.hardlang.script.Command;
import com.iqianggou.android.hardlang.script.IExecuteScript;
import com.iqianggou.android.hardlang.script.Parser;
import com.iqianggou.android.hardlang.script.ScriptEngine;
import com.iqianggou.android.hardlang.script.ScriptLoader;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements IExecuteScript {

    private TextView mConsole;

    private ScriptEngine engine;
    private String mHistorty = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("ScriptEngine", "Thread id = " + Thread.currentThread().getId());
        findViews();
        engine = new ScriptEngine(this);
        engine.startEngine();
    }

    private void findViews(){
        mConsole = (TextView) findViewById(R.id.tv_console);
    }


    @Override
    public void executeScript(String[] tokens) {
        switch (tokens[0]){
            case Command.SET_NPC_DIR:
                String dir = Parser.getStringParam(tokens[1]);
                setNPCDir(dir);
                break;
            case Command.MOVE_NPC:
                int from = Parser.getIntParam(tokens[1]);
                int to = Parser.getIntParam(tokens[2]);
                moveNPC(from, to);
                break;
            case Command.SHOW_TEXT_BOX:
                String message = Parser.getStringParam(tokens[1]);
                showTextBox(message);
                break;
            case Command.PAUSE:
                int time = Parser.getIntParam(tokens[1]);
                pause(time);
                break;
            default:
                Log.e("ScriptEngine", "Command not supported.");
                break;
        }
    }

    private void setNPCDir(String dir){

        String direction = "";

        switch (dir){
            case "Up":
                direction = "北方";
                break;
            case "Left":
                direction = "西方";
                break;
            case "Right":
                direction = "东方";
                break;
            case "Down":
                direction = "南方";
                break;
            default:
                Log.e("ScriptEngine", "Wrong dir.");
                break;
        }

        mHistorty += getResources().getString(R.string.npc_rotate, direction);
        mConsole.setText(mHistorty);


    }

    private void moveNPC(int from, int to){
        mHistorty += getResources().getString(R.string.npc_move, from, to);
        mConsole.setText(mHistorty);
    }

    private void showTextBox(String message){
        Log.d("ScriptEngine", "Show text " + message);
        mHistorty += getResources().getString(R.string.npc_speak, message);
        mConsole.setText(mHistorty);
    }

    private void pause(int time){
        mHistorty += getResources().getString(R.string.npc_rest, time);
        mConsole.setText(mHistorty);
    }
}
