package ru.asntl.callsharklibrary.webview;

import android.os.Build;
import android.os.Bundle;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import ru.asntl.callsharklibrary.R;
import ru.asntl.callsharklibrary.config.CallSharkConfig;
import ru.asntl.callsharklibrary.jsinterface.JavaScriptInterface;
import ru.asntl.callsharklibrary.utilities.CallSharkUtility;

import static ru.asntl.callsharklibrary.utilities.CallSharkUtility.PERMISSION_REQUEST_CODE_RECORD_AUDIO;

public class CallSharkActivity extends AppCompatActivity {


    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (CallSharkUtility.checkPermissionForRecordAudio(this,this)){
            startCallShark();
            CallSharkUtility.checkPermissionForCamera(this,this);
        }
    }

    private void startCallShark() {
        setContentView(R.layout.activity_callshark);
        webView = findViewById(R.id.webView);

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        request.grant(request.getResources());
                }
            }
        });


        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.addJavascriptInterface(new JavaScriptInterface(this), "AndroidFunction");
        webView.loadUrl(CallSharkConfig.getCallSharkUrl()+"/calls/callHttp?c="+ CallSharkConfig.getClientId()+"&g=0&s="+ CallSharkConfig.getSiteId()+"&mode=video");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==PERMISSION_REQUEST_CODE_RECORD_AUDIO){
            if (grantResults[0]==-1){
                finish();
                Toast.makeText(this, "Разрешите доступ к микрофону для продолжения работы.", Toast.LENGTH_SHORT).show();
            }else {
                startCallShark();
                CallSharkUtility.checkPermissionForCamera(this,this);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}

