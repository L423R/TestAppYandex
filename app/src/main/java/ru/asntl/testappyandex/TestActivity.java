package ru.asntl.testappyandex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent = getIntent();
        String url = intent.getStringExtra("fileName");

        EditText editText = (EditText)findViewById(R.id.editText);
        editText.setText(url, TextView.BufferType.EDITABLE);

    }
}
