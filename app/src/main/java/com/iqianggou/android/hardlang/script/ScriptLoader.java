package com.iqianggou.android.hardlang.script;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/10/6.
 */

public class ScriptLoader {

    public static String loadScript(Context context, String fileName){
        try {
            //Return an AssetManager instance for your application's package
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            return new String(buffer, "GB2312");
        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }
    }
}
