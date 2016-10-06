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

    private TextView mDialog;
    private LinearLayout mPeople;

    private ScriptEngine engine;

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
        mPeople = (LinearLayout) findViewById(R.id.ll_people);
        mDialog = (TextView) findViewById(R.id.tv_dialog);
    }

    private void setupTranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta){
        Log.d("ScriptEngine", "Start move");
        Log.d("ScriptEngine", "Thread id = " + Thread.currentThread().getId());
        final TranslateAnimation moveAnimation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        moveAnimation.setDuration(3000);
        moveAnimation.setFillAfter(true);
        mPeople.startAnimation(moveAnimation);
    }

    private void setupRotateAnimation(float fromDegrees, float toDegrees, float pivotX, float pivotY){
        Log.d("ScriptEngine", "Start rotate");
        Log.d("ScriptEngine", "Thread id = " + Thread.currentThread().getId());
        final RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, pivotX, Animation.RELATIVE_TO_SELF, pivotY);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setFillAfter(true);
        mPeople.startAnimation(rotateAnimation);
    }


    @Override
    public void executeScript(String[] tokens) {
        switch (tokens[0]){
            case Command.SET_NPC_DIR:
                String dir = Parser.getStringParam(tokens[1]);
                setNPCDir(dir);
                break;
            case Command.MOVE_NPC:
                float from = Parser.getFloatParam(tokens[1]);
                float to = Parser.getFloatParam(tokens[2]);
                moveNPC(from, to);
                break;
            case Command.SHOW_TEXT_BOX:
                String message = Parser.getStringParam(tokens[1]);
                showTextBox(message);
                break;
            case Command.PAUSE:
                float time = Parser.getFloatParam(tokens[1]);
                pause(time);
                break;
            default:
                Log.e("ScriptEngine", "Command not supported.");
                break;
        }
    }

    private void setNPCDir(String dir){
        switch (dir){
            case "Up":
                setupRotateAnimation(0, 90, 0.5f, 0.5f);
                break;
            case "Left":
                setupRotateAnimation(0, 0, 0.5f, 0.5f);
                break;
            case "Right":
                setupRotateAnimation(0, 180, 0.5f, 0.5f);
                break;
            case "Down":
                setupRotateAnimation(0, 270, 0.5f, 0.5f);
                break;
            default:
                Log.e("ScriptEngine", "Wrong dir.");
                break;
        }

    }

    private void moveNPC(float from, float to){
        setupTranslateAnimation(from, to, 0, 0);
    }

    private void showTextBox(String message){
        Log.d("ScriptEngine", "Show text " + message);
        mDialog.setText(message);
    }

    private void pause(float time){
        try {
            Thread.sleep((long) time);
        }catch (InterruptedException e){
            Log.e("ScriptEngine", e.getMessage());
        }
    }
}
