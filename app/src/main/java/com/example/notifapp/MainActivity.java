package com.example.notifapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private NotificationManager mNotif;
    private final static String CHANNEL_ID = "primary-channel";
    private int NOTIFICATION_ID = 0;
    private final static String ACTION_UPDATE_NOTIFICATION = "ACTION_UPDATE_NOTIFICATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotif = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mNotifChannel = new NotificationChannel(CHANNEL_ID, "app notif", NotificationManager.IMPORTANCE_HIGH);
            mNotif.createNotificationChannel(mNotifChannel);
        }

        findViewById(R.id.btnNotif).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendNotification();
            }
        });

        findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateNotification();
            }
        });

        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelNotification();
            }
        });
    }

    private NotificationCompat.Builder getNotificationBuilder(){
        Intent intent = new Intent(this, MainActivity2.class);
        PendingIntent notifPendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("MISI ADA NOTIF LEWATT")
                .setContentText("ini adalah notif lewatt")
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(notifPendingIntent);
        return notifyBuilder;
    }

    private void SendNotification(){
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notifyBuilder.addAction(R.drawable.mobil, "Update Notification", updatePendingIntent);
        mNotif.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    public void UpdateNotification(){
        Bitmap androidImage = BitmapFactory.decodeResource(getResources(), R.drawable.gudeg1);
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle("SELAMAT ANDA TELAH MEMBELI GUDEG")
        );
        mNotif.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    public void cancelNotification(){
        mNotif.cancel(NOTIFICATION_ID);
    }


}