package com.digitalpayments.android.sample.kotlinSample

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.digitalpayments.android.sample.R
import com.digitalpayments.android.sample.services.SessionCreateResponse
import kotlinx.android.synthetic.main.activity_make_payment_test.*

open class PaymentBaseKotlinSampleActivity : AppCompatActivity() {
    private var sharedPreferences: SharedPreferences? = null
    protected var eventService: EventServiceKotlin? = null
    protected var sessionKey: String = ""
    protected var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        eventService = EventServiceKotlin(this)
        val sessionServiceUrl =
            sharedPreferences?.getString("session_service_url", resources.getString(R.string.session_service_url))
        url = sharedPreferences?.getString("generic_modal_url", resources.getString(R.string.generic_modal_url))

        val initializeSessionTask =
            InitializeSessionTaskKotlin(sessionServiceUrl!!) { result: SessionCreateResponse ->
                run {
                    progressBar.visibility = View.GONE
                    if (result != null && result.isSuccessful && result.sessionKey != "") {
                        sessionKey = result.sessionKey
                        openDialog.isEnabled = true
                        Log.i("SessionKey created", result.sessionKey)
                    } else {
                        Log.i("Session service failed", result.errorDescription)
                        val toast = Toast.makeText(this.applicationContext, "Session service failed", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                }
            }

        initializeSessionTask.execute()
    }
}