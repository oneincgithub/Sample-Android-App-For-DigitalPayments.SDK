package com.digitalpayments.paymentform.android.sample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.digitalpayments.paymentform.android.sample.javaSample.JavaSampleActivity
import com.digitalpayments.paymentform.android.sample.kotlinSample.KotlinSampleActivity

class MainActivity : AppCompatActivity() {

    private var notificationManager: NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationManager =
                    getSystemService(
                        Context.NOTIFICATION_SERVICE
                    ) as NotificationManager

            createNotificationChannel(
                "com.digitalpayments.events",
                "Digital Payments Events"
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.kotlinSample -> {
                val intent = Intent(this, KotlinSampleActivity::class.java)
                startActivity(intent)
            }
            R.id.javaSample -> {
                val intent = Intent(this, JavaSampleActivity::class.java)
                startActivity(intent)
            }
            else -> {
            }
        }
    }

    private fun createNotificationChannel(id: String, name: String) {
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(id, name, importance)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}
