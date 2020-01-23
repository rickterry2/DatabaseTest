package com.terry.testdemo;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class NoticeTest extends AppCompatActivity {
    private static final String TAG = "NoticeTest";
    private static final int FLAG = 0x01000000;
    BroadcastReceiver br;
    private TextView mText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_test);

        br = new BcReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        this.registerReceiver(br, filter);

        mText = findViewById(R.id.textView);
        findViewById(R.id.sendbc).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("com.terry.testdemo.bctest");
                intent.setPackage("com.terry.databasetest");
//                intent.setComponent(new ComponentName(
//                        getPackageName(),"com.terry.testdemo.NoticeTest$bcReceiver"));
                sendBroadcast(intent);
                Log.d(TAG, "Send a broadcase");
            }
        });

        String title = "TEST NOTICE";
        String text = "GG Is there a wallpaper?";

        sendNotice(title, text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(br);
    }

    public void sendNotice(String title, String text) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, TAG)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(text);

        //设置点击通知之后的响应，启动SettingActivity类
        Intent resultIntent = new Intent(this, TestDemo.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            // Create a channel, it will be used while send the notice.
            NotificationChannel notificationChannel =
                    new NotificationChannel(TAG, TAG, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.notify(1, notification);
        } else {
            Log.d(TAG, "Can not get NOTIFICATION_SERVICE");
        }
    }

    public class BcReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "action " + intent.getAction());
        }
    }
}
