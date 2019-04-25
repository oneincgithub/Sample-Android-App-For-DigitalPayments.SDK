package com.digitalpayments.paymentform.android.sample.kotlinSample

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.digitalpayments.paymentform.android.sample.R
import com.digitalpayments.paymentform.android.sdk.models.ColorType

open class PaymentBaseKotlinSampleActivity : AppCompatActivity() {
    private var sharedPreferences: SharedPreferences? = null
    protected var eventService: EventServiceKotlin? = null
    protected var sessionKey: String = ""
    protected var genericModalUrl: String? = null
    protected var portalOneApiUrl: String? = null
    protected var genericModalSessionServiceUrl: String? = null
    protected var portalOneApiSessionServiceUrl: String? = null
    protected var colorScheme: Map<ColorType, String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        eventService = EventServiceKotlin(this)
        getUrlsFromSettings()
        colorScheme = getCustomColors()
    }

    private fun getUrlsFromSettings(){
        genericModalSessionServiceUrl = sharedPreferences?.getString(
            "generic_modal_session_service_url",
            resources.getString(R.string.generic_modal_session_service_url)
        )
        portalOneApiSessionServiceUrl = sharedPreferences?.getString(
            "portal_one_api_session_service_url",
            resources.getString(R.string.portal_one_api_session_service_url)
        )
        genericModalUrl = sharedPreferences?.getString(
            "generic_modal_url",
            resources.getString(R.string.generic_modal_url)
        )
        portalOneApiUrl = sharedPreferences?.getString(
            "portal_one_api_url",
            resources.getString(R.string.portal_one_api_url)
        )
    }

    private fun getCustomColors(): Map<ColorType, String>? {
        var colorScheme : Map<ColorType, String> = mutableMapOf()

        ColorType.values().forEach {
            val customValue = sharedPreferences?.getString(it.getDescription(), "")
            if (!customValue.isNullOrBlank()){
                colorScheme = colorScheme.plus(it to customValue)
            }
        }

        return if (colorScheme.isEmpty()) null else colorScheme
    }

    protected fun setupProgressBarVisibility(visible: Boolean){
        findViewById<ProgressBar>(R.id.progressBar).visibility = if (visible) View.VISIBLE else View.GONE
        findViewById<Button>(R.id.openDialog).isEnabled = !visible
        findViewById<Button>(R.id.openNative).isEnabled = !visible
    }
}