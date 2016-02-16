package com.hardteam.moneytracker.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.NotificationCompat;

import com.hardteam.moneytracker.R;
import com.hardteam.moneytracker.ui.activities.MainActivity;
import com.hardteam.moneytracker.ui.activities.MainActivity_;

/**
 * Created by RG on 15.02.2016.
 */
public class NotificationUtil {

    public static void updateNotifications(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String displayNotificationKey = context.getString(R.string.pref_enable_notifications_key);
        boolean displayNotifications = prefs.getBoolean(displayNotificationKey,
                Boolean.parseBoolean(context.getString(R.string.pref_enable_notifications_default)));

        String displaySoundKey = context.getString(R.string.pref_enable_sound_key);
        boolean displaySound = prefs.getBoolean(displaySoundKey,
                Boolean.parseBoolean(context.getString(R.string.pref_enable_notifications_default)));

        String displayVibrationKey = context.getString(R.string.pref_enable_vibration_key);
        boolean displayVibration = prefs.getBoolean(displayVibrationKey,
                Boolean.parseBoolean(context.getString(R.string.pref_enable_notifications_default)));

        String displayLedKey = context.getString(R.string.pref_enable_led_key);
        boolean displayLed = prefs.getBoolean(displayLedKey,
                Boolean.parseBoolean(context.getString(R.string.pref_enable_notifications_default)));

        if(displayNotifications)
        {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            //Create Intent to launch this Activity again if the notification is clicked.
            Intent i= new Intent(context, MainActivity_.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent intent = PendingIntent.getActivity(context, 0, i,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(intent);
            //Setsthesmalliconfortheticker
            builder.setSmallIcon(R.mipmap.ic_launcher);
            //Setstheindicator
            if(displayLed) {
                builder.setLights(Color.CYAN, 300, 1500);
            }
            //Setsvibration
            if(displayVibration) {
                builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            }
            //Setsdefaultsound
            if(displaySound) {
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            }
            //Cancelthenotificationwhenclicked
            builder.setAutoCancel(true);

            String title = context.getString(R.string.app_name);
            String contentText = context.getResources().getString(R.string.notification_message);
            Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            builder.setLargeIcon(largeIcon);
            builder.setContentTitle(title);
            builder.setContentText(contentText);

            // Build the notification
            Notification notification= builder.build();
            // Use the NotificationManagerto show the notification
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(Constants.NOTIFICATION_ID, notification);
        }
    }
}
