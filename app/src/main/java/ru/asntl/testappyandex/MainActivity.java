package ru.asntl.testappyandex;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import ru.asntl.callsharklibrary.config.CallSharkConfig;
import ru.asntl.callsharklibrary.CallSharkStarter;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onMyButtonClick(View view) {
        /*required setting*/
        CallSharkConfig.setCallSharkUrl("https://develop.callshark.ru");
        CallSharkConfig.setClientId(1205);
        CallSharkConfig.setSiteId(3636);

        /*not required setting*/
        CallSharkConfig.setYandexVisitorId(999);

        /*video recording setup*/
        CallSharkConfig.setVideoDurationLimitMs(180000);

        /*start*/
        CallSharkStarter starter = new CallSharkStarter(this,this, true);
        starter.execute(CallSharkConfig.getURLForStarter());

    }


}
