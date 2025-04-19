package com.example.intent_pocapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Flag10ResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flag10_response);
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_DEBUG_LOG_RESOLUTION);
        if(intent != null){
            String flag = intent.getStringExtra("flag");
            Log.d("Flag10ResponseActivity",flag);
        }
    }
}