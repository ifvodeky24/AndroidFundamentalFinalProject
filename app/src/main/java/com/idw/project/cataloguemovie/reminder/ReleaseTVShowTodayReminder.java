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

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.idw.project.cataloguemovie.R;
import com.idw.project.cataloguemovie.model.TvShow;
import com.idw.project.cataloguemovie.response.ResponseReleaseTodayTvShow;
import com.idw.project.cataloguemovie.rest.ApiClient;
import com.idw.project.cataloguemovie.rest.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.idw.project.cataloguemovie.config.Config.API_KEY;

public class ReleaseTVShowTodayReminder extends BroadcastReceiver {

    private static int notifId;
    private static final int NOTIF_ID_REPEATING = 100;

    private List<TvShow> tvShowList = new ArrayList<>();

    public ReleaseTVShowTodayReminder() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String currentDate = sdf.format(date);

        System.out.println("tanggal current" +currentDate);

        apiService.getReleaseTvShow(API_KEY, currentDate, currentDate).enqueue(new Callback<ResponseReleaseTodayTvShow>() {
            @Override
            public void onResponse(@NonNull Call<ResponseReleaseTodayTvShow> call, @NonNull Response<ResponseReleaseTodayTvShow> response) {
                System.out.println("responseIn"+response);
                if (response.isSuccessful()){

                    if (response.body() != null) {
                        tvShowList = response.body().getResults();
                    }

                    for (int i = 0; i < tvShowList.size(); i++) {

                        TvShow tvShow = tvShowList.get(i);
                        Date tvShowDate = dateFormatter(tvShow.getFirstAirDate());
                        System.out.println("hari ini " + tvShowDate);

                        if (tvShowDate.equals(currentDate)) {
                            tvShowList.add(tvShow);
                        }

                        String title = tvShow.getOriginalName();
                        notifId = tvShow.getId();

                        System.out.println("tvshow size " + tvShowList.size());
                        System.out.println("tvshow name " + tvShow.getOriginalName());

                        showAlarmNotification(context, title, notifId);
                    }
                }else {
                    Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseReleaseTodayTvShow> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void showAlarmNotification(Context context, String title, int notifId) {
        String CHANNEL_ID = "release_today_reminder1";
        String CHANNEL_NAME = "Release Today Reminder";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_creation_24dp)
                .setContentTitle(title)
                .setContentText("Rilis Hari ini "+title)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
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
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);

        Intent intent = new Intent(context, ReleaseTVShowTodayReminder.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID_REPEATING, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Toast.makeText(context, "Mengaktifkan Release Today Reminder", Toast.LENGTH_SHORT).show();

    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, ReleaseTVShowTodayReminder.class);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(context, notifId, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

        Toast.makeText(context, "Mematikan Release Today Reminder", Toast.LENGTH_SHORT).show();
    }

    private Date dateFormatter(String tvShowDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = null;
        try {
            date = sdf.parse(tvShowDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

}
