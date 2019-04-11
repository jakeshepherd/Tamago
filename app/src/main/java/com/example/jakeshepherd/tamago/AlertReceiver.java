package com.example.jakeshepherd.tamago;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

public class AlertReceiver extends BroadcastReceiver {
    private final static AtomicInteger c = new AtomicInteger(0);
    private static String CHANNEL_ID = "default";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Alert", "ALERT RECEIVED");

        String date = intent.getStringExtra("date");
        createNotification(context, "TITLE", date);
    }

    public static int getID() {
        return c.incrementAndGet();
    }

    public void createNotification(Context context, String title, String text){
        PendingIntent notificIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.tamagoicon)
                .setContentTitle(title)
                .setContentText(text);

        mbuilder.setContentIntent(notificIntent);

        mbuilder.setLights(Color.GREEN,1000,1000);
        mbuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        mbuilder.setDefaults(NotificationCompat.DEFAULT_LIGHTS);
        mbuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // might change getID to 0
        notificationManager.notify(getID(), mbuilder.build());
    }
}
