package com.example.intent_pocapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Flag11ResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flag11_response);
        //返回结果的intent
        //解决Flag12Activity需要配合 am start -n io.hextree.attacksurface/.activities.Flag12Activity --ez "LOGIN" true来使用
        Intent intent = new Intent();
        intent.putExtra("token",1094795585);
        setResult(RESULT_OK,intent);
        finish();

    }
}