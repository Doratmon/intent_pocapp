package com.example.intent_pocapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        getResultData()
        Log.d("Flag18Activity: ", intent.getStringExtra("flag"));

        //让Flag18Activity可以执行success()方法，
        //根据logcat，MyReceiver比Flag18Activity的匿名广播先接收到广播
        setResultCode(1);
    }

}