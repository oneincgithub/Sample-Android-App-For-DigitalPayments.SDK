@file:Suppress("DEPRECATION")

package com.digitalpayments.paymentform.android.sample.kotlinSample

import android.app.*
import android.content.Context
import android.graphics.Color
import java.util.*
import com.digitalpayments.paymentform.android.sample.R
import com.digitalpayments.paymentform.android.sdk.models.IPaymentInfo
import android.widget.Toast
import com.digitalpayments.paymentform.android.sdk.models.ErrorResponse
import com.digitalpayments.paymentform.android.sdk.models.ISavePaymentMethodResponse

class EventServiceKotlin(var activity: Activity){
    private var notificationManager: NotificationManager? = null
    init {
        notificationManager =  activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    var onPaymentComplete: (paymentInfo: IPaymentInfo) -> Unit =
        fun (paymentInfo: IPaymentInfo) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                sendNotification(
                    activity,
                    notificationManager!!,
                    "onPaymentComplete",
                    paymentInfo.serialize()
                )
            }
            else{
                val toast = Toast.makeText(activity.applicationContext, "onPaymentComplete", Toast.LENGTH_SHORT)
                toast.show()
            }
        }


    var onSaveComplete: (saveResponse: ISavePaymentMethodResponse) -> Unit =
        fun (saveResponse: ISavePaymentMethodResponse) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                sendNotification(
                    activity,
                    notificationManager!!,
                    "onSaveComplete",
                    saveResponse.serialize()
                )
            } else {
                val toast = Toast.makeText(activity.applicationContext, "onSaveComplete", Toast.LENGTH_SHORT)
                toast.show()
            }
        }

    var onError: (errorResponse: ErrorResponse) -> Unit =
        fun (errorResponse: ErrorResponse) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                sendNotification(
                    activity,
                    notificationManager!!,
                    "onError",
                    errorResponse.serialize(),
                    true
                )
            } else {
                val toast = Toast.makeText(activity.applicationContext, "onError", Toast.LENGTH_SHORT)
                toast.show()
            }
        }

    var onSaveCanceled: () -> Unit =
        fun() {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
                sendNotification(
                    activity,
                    notificationManager!!,
                    "onSaveCanceled",
                    ""
                )
            } else {
                val toast = Toast.makeText(activity.applicationContext, "onSaveCanceled", Toast.LENGTH_SHORT)
                toast.show()
            }
        }

    var onPaymentCanceled: () -> Unit =
        fun() {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                sendNotification(
                    activity,
                    notificationManager!!,
                    "onPaymentCanceled",
                    ""
                )
            } else {
                val toast = Toast.makeText(activity.applicationContext, "onPaymentCanceled", Toast.LENGTH_SHORT)
                toast.show()
            }
        }

    var onClose: () -> Unit =
        fun() {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
                sendNotification(
                    activity,
                    notificationManager!!,
                    "onClose",
                    ""
                )
            } else {
                val toast = Toast.makeText(activity.applicationContext, "onClose", Toast.LENGTH_SHORT)
                toast.show()
            }
        }

    var onLoad: () -> Unit =
        fun() {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
                sendNotification(
                    activity,
                    notificationManager!!,
                    "onLoad",
                    ""
                )
            } else {
                val toast = Toast.makeText(activity.applicationContext, "onLoad", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
}

private fun sendNotification(activity: Activity, notificationManager: NotificationManager, title: String, data: String, isError: Boolean = false) {
    val channelID = "com.digitalpayments.events"
    val notificationID = (1 until 1000).random()

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        val notification = Notification.Builder(activity, channelID)
            .setContentTitle(title)
            .setStyle(Notification.BigTextStyle().bigText(data))
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setColor(if (isError) Color.RED else Color.GREEN)
            .setChannelId(channelID)
            .build()

        notificationManager.notify(notificationID, notification)
    }
}

private fun IntRange.random() = Random().nextInt((endInclusive + 1) - start) + start
