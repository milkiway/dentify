package com.junction.healthtech.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.junction.healthtech.HomeActivity;
import com.junction.healthtech.R;
import com.junction.healthtech.utils.Utils;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private static final String EVENT_TYPE = "eventType";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived");
        Map<String, String> data = remoteMessage.getData();
        Log.d(TAG, "onMessageReceived data " + data.toString());

        String title = data.get("title");
        Log.d(TAG, "onMessageReceived title " + title);
        String body = data.get("body");
        Log.d(TAG, "onMessageReceived body " + body);

        switch (data.get(EVENT_TYPE)) {
        }

    }

    private void chatMessageSent(Map<String, String> data, String title, String body) {
        String avatarUrl = data.get("avatar");
        Log.d(TAG, "onMessageReceived avatar " + avatarUrl);
        String chatId = data.get("chatId");
        Log.d(TAG, "onMessageReceived chatId " + chatId);
        String senderId = data.get("senderId");
        Log.d(TAG, "onMessageReceived senderId " + senderId);

        String currentUserId = Utils.getCurrentUserId(this);
        if (currentUserId==null) {
            Log.e(TAG, "onMessageReceived currentUserId is null");
            return;
        }

        if (currentUserId.equals(senderId)) {
            Log.d(TAG, "onMessageReceived: This is your message");
            return;
        }

//        showMessageNotification(title, body, chatId, null);

        int width = getResources().getDimensionPixelSize(android.R.dimen.notification_large_icon_width);
        int height = getResources().getDimensionPixelSize(android.R.dimen.notification_large_icon_height);
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(avatarUrl))
                .setResizeOptions(new ResizeOptions(width, height))
                .build();

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        final DataSource<CloseableReference<CloseableImage>>
                dataSource = imagePipeline.fetchDecodedImage(imageRequest, this);

        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            public void onNewResultImpl(@Nullable Bitmap bitmap) {
//                if (dataSource.isFinished() && bitmap != null) {
                Log.d(TAG, "bitmap for avatar fetched successfully");
                Bitmap bmp = Bitmap.createBitmap(bitmap);
                showMessageNotification(title, body, chatId, bmp);
//                    dataSource.close();
//                } else {
//                    Log.d(TAG, "Error happened while fetching avatar");
//                    showMessageNotification(title, body, chatId, null);
//                }
            }

            @Override
            public void onFailureImpl(DataSource dataSource) {
                Log.e(TAG, "Failed to fetch avatar");
                showMessageNotification(title, body, chatId, null);
//                if (dataSource != null) {
//                    dataSource.close();
//                }
            }
        }, UiThreadImmediateExecutorService.getInstance());
    }

    private void showMessageNotification(String title, String body, String chatId, Bitmap bmp) {
        Log.d(TAG, "showMessageNotification");
        Intent pendingIntent = new Intent(getApplicationContext(), HomeActivity.class);
//        pendingIntent.putExtra(Chat.CHAT_ID, chatId);
//        pendingIntent.putExtra(ChatListActivity.NEW_MESSAGE, true);
        // Because clicking the notification opens a new ("special") activity, there's
        // no need to create an artificial back stack.
        PendingIntent chatPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        pendingIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(chatPendingIntent);

        if (bmp!=null)
            notificationBuilder.setLargeIcon(bmp);

        // Sets an ID for the notification
        int mNotificationId = 1;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, notificationBuilder.build());
    }

    private void showBasicNotification(String title, String body, Intent intent, @DrawableRes int smallIcon) {
        // Because clicking the notification opens a new ("special") activity, there's
        // no need to create an artificial back stack.
        PendingIntent pendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(smallIcon)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        // Sets an ID for the notification
        int mNotificationId = 1;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, notificationBuilder.build());
    }
}
