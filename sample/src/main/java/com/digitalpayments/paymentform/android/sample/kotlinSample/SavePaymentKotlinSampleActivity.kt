package com.digitalpayments.paymentform.android.sample.kotlinSample

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.digitalpayments.paymentform.android.sample.R
import com.digitalpayments.paymentform.android.sample.databinding.ActivitySavePaymentTestBinding
import com.digitalpayments.paymentform.android.sample.services.SessionCreateResponse
import com.digitalpayments.paymentform.android.sdk.SavePaymentMethodActivity
import com.digitalpayments.paymentform.android.sdk.models.*
import kotlinx.android.synthetic.main.activity_save_payment_test.*

class SavePaymentKotlinSampleActivity : PaymentBaseKotlinSampleActivity() {
    private var binding: ActivitySavePaymentTestBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_save_payment_test)
        binding!!.savePaymentMethodRequest = SavePaymentMethodRequest()
        binding!!.theme = ThemeType.Default

        paymentCategory.adapter =
            ArrayAdapter<PaymentCategory>(this, android.R.layout.simple_dropdown_item_1line, PaymentCategory.values())
        themeType.adapter =
            ArrayAdapter<ThemeType>(this, android.R.layout.simple_dropdown_item_1line, ThemeType.values())

        binding?.executePendingBindings()
    }

    fun onClick(view: View) {
        setupProgressBarVisibility(true)
        return when (view.id) {
            R.id.openDialog -> {
                InitializeSessionTaskKotlin(genericModalSessionServiceUrl!!) { result: SessionCreateResponse ->
                    run {
                        setupProgressBarVisibility(false)
                        if (result.isSuccessful && result.sessionKey != "") {
                            sessionKey = result.sessionKey
                            Log.i("SessionKey created", result.sessionKey)
                            var paymentForm: DigitalPaymentForm? = null
                            paymentForm = SavePaymentMethodActivity
                                .init(sessionKey, genericModalUrl!!)
                                .savePaymentMethod(binding!!.savePaymentMethodRequest!!)
                                .onLoad { eventService!!.onLoad() }
                                .onSaveComplete { saveResponse -> eventService!!.onSaveComplete(saveResponse) }
                                .onSaveCanceled { eventService!!.onSaveCanceled() }
                                .onError { error ->
                                    run {
                                        eventService!!.onError(error)
                                        if (error.description?.contains("InternalServerError") == true) {
                                            paymentForm?.close()
                                        }
                                    }
                                }
                                .onClose { eventService!!.onClose() }
                                .startWebView(this)
                        } else {
                            Log.i("Session service failed", result.errorDescription)
                            val toast =
                                Toast.makeText(this.applicationContext, "Session service failed", Toast.LENGTH_SHORT)
                            toast.show()
                        }
                    }
                }.execute()
                return
            }
            R.id.openNative -> {
                InitializeSessionTaskKotlin(portalOneApiSessionServiceUrl!!) { result: SessionCreateResponse ->
                    run {
                        setupProgressBarVisibility(false)
                        if (result.responseCode == "Success" && result.portalOneSessionKey != "") {
                            sessionKey = result.portalOneSessionKey
                            Log.i("SessionKey created", result.portalOneSessionKey)
                            var paymentForm: DigitalPaymentForm? = null
                            paymentForm = SavePaymentMethodActivity
                                .init(sessionKey, portalOneApiUrl!!)
                                .savePaymentMethod(binding!!.savePaymentMethodRequest!!)
                                .onLoad { eventService!!.onLoad() }
                                .onSaveComplete { saveResponse -> eventService!!.onSaveComplete(saveResponse) }
                                .onSaveCanceled { eventService!!.onSaveCanceled() }
                                .onError { error ->
                                    run {
                                        eventService!!.onError(error)
                                        if (error.description?.contains("InternalServerError") == true) {
                                            paymentForm?.close()
                                        }
                                    }
                                }
                                .onClose { eventService!!.onClose() }
                                .setStyle(binding!!.theme ?: ThemeType.Default, colorScheme)
                                .start(this)
                        } else {
                            Log.i("Session service failed", result.responseMessage)
                            val toast =
                                Toast.makeText(this.applicationContext, "Session service failed", Toast.LENGTH_SHORT)
                            toast.show()
                        }
                    }
                }.execute()
                return
            }
            else -> {
            }
        }
    }

}
