package com.qts.gopik_money.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.qts.gopik_money.Activity.AppConstants;
import com.qts.gopik_money.Activity.SharedPref;
import com.qts.gopik_money.Activity.SplashActivity;
import com.qts.gopik_money.R;

import java.util.Calendar;
import java.util.Date;

public class MyFirebase extends FirebaseMessagingService {
    private static final String TAG = "fire";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());
//        if (!remoteMessage.getData().isEmpty()) {
//            Log.e(TAG, "message 1 " + remoteMessage.getData());
////            sendNotification(remoteMessage.getData().get("body"), remoteMessage.getData().get("title"));
//            SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATION_TYPE, remoteMessage.getData().get("notification_type"), getApplicationContext());
//        }
        if (remoteMessage.getNotification() != null) {
            sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());
            if (!remoteMessage.getData().isEmpty()) {
                Log.e(TAG, "Message 2 Notification Body: "
                        + remoteMessage.getData().get("notification_type")
                        + "=" + remoteMessage.getNotification().getBody()
                        + "=" + remoteMessage.getNotification().getTitle());
                SharedPref.saveStringInSharedPref(AppConstants.NOTIFICATION_TYPE, remoteMessage.getData().get("notification_type"), getApplicationContext());

            }
        }

    }

    @Override
    public void onNewToken(String token) {
        Log.e(TAG, "Refreshed token: " + token);
    }

    private void sendNotification(String messageBody, String messageTitle) {
        Intent mIntent = new Intent(this, SplashActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, mIntent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.app_name);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.gu)
                        .setContentTitle(messageTitle)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(mPendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int mSec = calendar.get(Calendar.MILLISECOND);
        Log.e("notificationid ", "" + mSec);
        notificationManager.notify(mSec /* ID of notification */, notificationBuilder.build());
    }
}
