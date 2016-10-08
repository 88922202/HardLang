package com.iqianggou.android.hardlang;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iqianggou.android.hardlang.script.Command;
import com.iqianggou.android.hardlang.script.IExecuteScript;
import com.iqianggou.android.hardlang.script.Parser;
import com.iqianggou.android.hardlang.script.ScriptEngine;
import com.iqianggou.android.hardlang.script.ScriptLoader;
import com.iqianggou.android.hardlang.script.exception.IllegalCommandException;
import com.iqianggou.android.hardlang.script.exception.IllegalParamException;
import com.iqianggou.android.hardlang.utils.FileUtils;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements IExecuteScript {

    private static final String TAG = "ScriptEngine";
    private static final int FILE_SELECT_CODE = 10;

    private TextView mConsole;
    private Button mChooseFile;

    private ScriptEngine engine;
    private String mHistory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setupViews();
        initComponents();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String path = FileUtils.getPath(this, uri);
                    Log.d(TAG, path);
                    engine.startEngine(path);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void findViews(){
        mConsole = (TextView) findViewById(R.id.tv_console);
        mChooseFile = (Button) findViewById(R.id.btn_file);
    }

    private void setupViews(){
        mChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
    }

    private void initComponents(){
        engine = new ScriptEngine(this);
    }


    @Override
    public void executeScript(String[] tokens) {
        try {
            switch (tokens[0]) {
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
                    throw new IllegalCommandException("Invalid command.");
            }
        }catch (IllegalParamException e){
//            Toast.makeText(getApplicationContext(), "参数错误", Toast.LENGTH_SHORT).show();
            Log.e("ScriptEngine", e.getMessage());
        }catch (IllegalCommandException e){
//            Toast.makeText(getApplicationContext(), "命令错误", Toast.LENGTH_SHORT).show();
            Log.e("ScriptEngine", e.getMessage());
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

        mHistory += getResources().getString(R.string.npc_rotate, direction);
        mConsole.setText(mHistory);


    }

    private void moveNPC(int from, int to){
        mHistory += getResources().getString(R.string.npc_move, from, to);
        mConsole.setText(mHistory);
    }

    private void showTextBox(String message){
        Log.d("ScriptEngine", "Show text " + message);
        mHistory += getResources().getString(R.string.npc_speak, message);
        mConsole.setText(mHistory);
    }

    private void pause(int time){
        mHistory += getResources().getString(R.string.npc_rest, time);
        mConsole.setText(mHistory);
    }


    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/plain");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.",  Toast.LENGTH_SHORT).show();
        }
    }
}
