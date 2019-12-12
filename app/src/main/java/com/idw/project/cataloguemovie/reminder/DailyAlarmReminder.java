package com.idw.project.cataloguemovie.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.idw.project.cataloguemovie.R;

import java.util.Calendar;

public class DailyAlarmReminder extends BroadcastReceiver {

    private static final int NOTIF_ID_REPEATING = 101;
    public static int notifId = NOTIF_ID_REPEATING;

    public DailyAlarmReminder() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = context.getString(R.string.notifikasi);
        String title = context.getString(R.string.app_name);
        notifId = NOTIF_ID_REPEATING;

        showAlarmNotification(context, title, message, notifId);
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId) {

        String CHANNEL_ID = "daily_reminder_1";
        String CHANNEL_NAME = "Daily Reminder";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_creation_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }

    }


    public void setRepeatingAlarm(Context context) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);

        Intent intent = new Intent(context, DailyAlarmReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID_REPEATING, intent, 0);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Toast.makeText(context, "Mengaktifkan Daily Reminder", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyAlarmReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID_REPEATING, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, "Mematikan Daily Reminder", Toast.LENGTH_SHORT).show();
    }

}

