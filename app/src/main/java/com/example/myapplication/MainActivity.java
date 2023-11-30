package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.Person;

public class MainActivity extends AppCompatActivity {

    // Идентификатор уведомления
    private static final int NOTIFY_ID = 101;

    private int counter = 101;

    // Идентификатор канала
    private static String CHANNEL_ID = "Cat channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Notification Channel", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager notificationManager = getSystemService(NotificationManager.class);
                    assert notificationManager != null;
                    notificationManager.createNotificationChannel(channel);
                }

                Intent notificationIntent = new Intent(MainActivity.this, MainActivity.this.getClass());
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                        0, notificationIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);

                Person murzik = new Person.Builder().setName("Мурзик").build();
                Person vaska = new Person.Builder().setName("Васька").build();

                NotificationCompat.MessagingStyle messagingStyle = new NotificationCompat.MessagingStyle
                        (murzik)
                        .setConversationTitle("Android chat")
                        .addMessage("Привет котаны!", System.currentTimeMillis(), murzik)
                        .addMessage("А вы знали, что chat по-французски кошка?", System
                                        .currentTimeMillis(),
                                murzik)
                        .addMessage("Круто!", System.currentTimeMillis(),
                                vaska)
                        .addMessage("Ми-ми-ми", System.currentTimeMillis(), vaska)
                        .addMessage("Мурзик, откуда ты знаешь французский?", System.currentTimeMillis(),
                                vaska)
                        .addMessage("Шерше ля фам, т.е. ищите кошечку!", System.currentTimeMillis(),
                                murzik);

                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                                .setSmallIcon(R.drawable.cat)
                                .setContentIntent(pendingIntent)
                                .addAction(R.drawable.cat, "Запустить активность",
                                        pendingIntent)
                                .setStyle(messagingStyle)
                                .setAutoCancel(true); // автоматически закрыть уведомление после нажатия

                NotificationManagerCompat notificationManager =
                        NotificationManagerCompat.from(MainActivity.this);
                if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                notificationManager.notify(NOTIFY_ID, builder.build());
            }
        });
    }
}