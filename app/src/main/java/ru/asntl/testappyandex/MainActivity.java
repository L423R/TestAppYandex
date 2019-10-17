package ru.asntl.testappyandex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ru.asntl.callsharklibrary.config.CallSharkConfig;
import ru.asntl.callsharklibrary.CallSharkStarter;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onMyButtonClick(View view) {
        CallSharkConfig.setCallSharkUrl("https://develop.callshark.ru");
        CallSharkConfig.setYandexVisitorId(999);
        CallSharkConfig.setClientId(1190);
        CallSharkConfig.setSiteId(1857);
        CallSharkConfig.setLayoutResIDForVideoCaptureActivity(R.layout.activity_test);
        CallSharkConfig.setVideoDurationLimit(15);
        CallSharkStarter starter = new CallSharkStarter(this,this);
        starter.execute(CallSharkConfig.getURLForStarter());

    }


}
