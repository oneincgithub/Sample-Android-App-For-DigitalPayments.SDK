package com.digitalpayments.android.sample.javaSample;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.widget.Toast;
import com.digitalpayments.android.sample.R;
import com.digitalpayments.android.sdk.context.ErrorHandler;
import com.digitalpayments.android.sdk.context.PaymentCompleteHandler;
import com.digitalpayments.android.sdk.context.SaveCompleteHandler;
import com.digitalpayments.android.sdk.models.DigitalPaymentForm;

import java.util.Random;

import static android.content.Context.NOTIFICATION_SERVICE;

public class EventServiceJava {
    private Activity _activity;
    private NotificationManager _notificationManager;
    public PaymentCompleteHandler onPaymentComplete;
    public SaveCompleteHandler onSaveComplete;
    public ErrorHandler onError;
    public Runnable onSaveCanceled;
    public Runnable onPaymentCanceled;
    public Runnable onClose;
    public Runnable onLoad;
    public DigitalPaymentForm paymentForm;

    EventServiceJava(Activity activity) {
        _activity = activity;
        _notificationManager = (NotificationManager) activity.getSystemService(NOTIFICATION_SERVICE);

        onPaymentComplete = new PaymentCompleteHandler() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    sendNotification(_activity, _notificationManager, "onPaymentComplete", getPaymentInfo() != null ? getPaymentInfo().serialize() : "");
                }
                else{
                    Toast toast = Toast.makeText(_activity.getApplicationContext(), "onPaymentComplete", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        };

        onSaveComplete = new SaveCompleteHandler() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    sendNotification(_activity, _notificationManager, "onSaveComplete", getResponse() != null ? getResponse().serialize() : "");
                }
                else{
                    Toast toast = Toast.makeText(_activity.getApplicationContext(), "onSaveComplete", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        };

        onError = new ErrorHandler() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    sendNotification(_activity, _notificationManager, "onError", getErrorResponse() != null ? getErrorResponse().serialize() : "", true);
                }
                else{
                    Toast toast = Toast.makeText(_activity.getApplicationContext(), "onError", Toast.LENGTH_SHORT);
                    toast.show();
                }

                if((getErrorResponse() != null && getErrorResponse().getDescription() != null) && getErrorResponse().getDescription().contains("Unavailable")){
                    paymentForm.closeForm();
                }
            }
        };

        onSaveCanceled = new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    sendNotification(_activity, _notificationManager, "onSaveCanceled", "");
                }
                else{
                    Toast toast = Toast.makeText(_activity.getApplicationContext(), "onSaveCanceled", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        };

        onPaymentCanceled = new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    sendNotification(_activity, _notificationManager, "onPaymentCanceled", "");
                }
                else{
                    Toast toast = Toast.makeText(_activity.getApplicationContext(), "onPaymentCanceled", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        };

        onClose = new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    sendNotification(_activity, _notificationManager, "onClose", "");
                }
                else{
                    Toast toast = Toast.makeText(_activity.getApplicationContext(), "onClose", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        };

        onLoad = new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    sendNotification(_activity, _notificationManager, "onLoad", "");
                }
                else{
                    Toast toast = Toast.makeText(_activity.getApplicationContext(), "onLoad", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        };
    }

    private static void sendNotification(Activity activity, NotificationManager notificationManager, String title, String data) {
        sendNotification(activity, notificationManager, title, data, false);
    }

    private static void sendNotification(Activity activity, NotificationManager notificationManager, String title, String data, Boolean isError) {
        String channelID = "com.digitalpayments.events";
        Integer notificationID = random(1, 1000);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Notification notification = new Notification.Builder(activity, channelID)
                    .setContentTitle(title)
                    .setStyle(new Notification.BigTextStyle().bigText(data))
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setColor(isError ? Color.RED : Color.GREEN)
                    .setChannelId(channelID)
                    .build();

            notificationManager.notify(notificationID, notification);
        }
    }

    private static int random(int start, int endInclusive) {
        return new Random().nextInt((endInclusive + 1) - start) + start;
    }

}
