package com.example.intent_pocapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Flag23ResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_response_flag23);
        PendingIntent pendingintent = (PendingIntent)getIntent().getParcelableExtra("pending_intent");
        Intent intent = new Intent();
        intent.putExtra("code",42);
        try {
            pendingintent.send(this,0,intent);
        }catch (PendingIntent.CanceledException e){
            e.printStackTrace();
        }
    }
}