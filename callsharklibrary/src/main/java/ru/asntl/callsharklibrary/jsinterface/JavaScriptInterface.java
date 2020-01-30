package ru.asntl.callsharklibrary.jsinterface;

import android.app.Activity;
import android.util.Log;
import android.webkit.JavascriptInterface;

public class JavaScriptInterface {
    private Activity activity;

    public JavaScriptInterface(Activity c) {
        activity = c;
    }

    @JavascriptInterface
    public void closeWindow() {
        activity.finish();
        /*Toast.makeText(activity, toast, Toast.LENGTH_SHORT).show();*/
        Log.i("CLOSE WINDOW","CLOSEEEEEE!!!!!!!!!!!!!!!!!!!");
    }
}
