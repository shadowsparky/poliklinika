package ru.shadowsparky.autisticsdevelopers.poliklinika;/*
 * Copyright (c) Samsonov Eugene, 2018.
 */

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.sql.Time;
import java.util.TimeZone;

import ru.shadowsparky.autisticsdevelopers.poliklinika.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationService {
    public static final String CHANNEL_ID = "123";

    private Context _context;

    NotificationService(Context _context){
        this._context = _context;
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "PushNotify";
            String description ="Experemental Function";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = _context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public void setNotification(Notification notification, Time t){
        createNotificationChannel();
//        Time t = new Time(18, 25, 00);
        Intent notificationIntent = new Intent(_context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(_context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = t.getTime();
        AlarmManager alarmManager = (AlarmManager)_context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);


//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(_context, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_audiotrack_black_24dp)
//                .setContentTitle("test")
//                .setContentText("tes")
//                .setWhen(t.getTime())
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(_context);
//        notificationManager.notify(1, mBuilder.build());
    }

    public Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(_context);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_audiotrack_black_24dp);
        return builder.build();
    }

}

class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(1, notification);

    }
}
