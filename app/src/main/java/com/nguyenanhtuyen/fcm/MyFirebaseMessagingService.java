package com.nguyenanhtuyen.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nguyenanhtuyen.appthuexe.DatXeActivity;
import com.nguyenanhtuyen.appthuexe.MyApplication;
import com.nguyenanhtuyen.appthuexe.R;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG = MyFirebaseMessagingService.class.getName();
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        /*RemoteMessage.Notification notification = message.getNotification();
        if(notification == null){
            return;
        }
        String strTitle = notification.getTitle();
        String strMessage = notification.getBody();*/

        //data message
        Map<String, String> strMap = message.getData();
        if(strMap == null){
            return;
        }
        String strTitle = "Thông báo hệ thống";
        String strMessage = "Bạn có yêu cầu đặt xe mới!";
        sendNotification(strTitle, strMessage);
    }
    private void sendNotification(String strTitle, String strMessage) {
        Intent intent = new Intent(this, DatXeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setContentTitle(strTitle)
                .setContentText(strMessage)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);

        Notification notification = notificationBuilder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager != null){
            notificationManager.notify(1, notification);
        }
    }
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.e(TAG, token);
    }
}
