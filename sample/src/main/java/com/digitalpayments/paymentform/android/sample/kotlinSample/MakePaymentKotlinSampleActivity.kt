package com.digitalpayments.paymentform.android.sample.kotlinSample

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_make_payment_test.*
import com.digitalpayments.paymentform.android.sdk.MakePaymentActivity
import com.digitalpayments.paymentform.android.sample.R
import android.databinding.DataBindingUtil
import android.util.Log
import com.digitalpayments.paymentform.android.sample.databinding.ActivityMakePaymentTestBinding
import android.widget.ArrayAdapter
import android.widget.Toast
import com.digitalpayments.paymentform.android.sample.services.SessionCreateResponse
import com.digitalpayments.paymentform.android.sdk.models.*

class MakePaymentKotlinSampleActivity : PaymentBaseKotlinSampleActivity() {
    private var binding: ActivityMakePaymentTestBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_make_payment_test)
        binding!!.makePaymentRequest = MakePaymentRequest()
        binding!!.theme = ThemeType.Default

        amountContext.adapter =
            ArrayAdapter<AmountContext>(this, android.R.layout.simple_dropdown_item_1line, AmountContext.values())
        paymentCategory.adapter =
            ArrayAdapter<PaymentCategory>(this, android.R.layout.simple_dropdown_item_1line, PaymentCategory.values())
        feeContext.adapter =
            ArrayAdapter<FeeContext>(this, android.R.layout.simple_dropdown_item_1line, FeeContext.values())
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
                            MakePaymentActivity
                                .init(sessionKey, genericModalUrl!!)
                                .makePayment(binding!!.makePaymentRequest!!)
                                .onLoad { eventService!!.onLoad() }
                                .onPaymentComplete { paymentInfo -> eventService!!.onPaymentComplete(paymentInfo) }
                                .onPaymentCanceled { eventService!!.onPaymentCanceled() }
                                .onError { error -> eventService!!.onError(error) }
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
                            MakePaymentActivity
                                .init(sessionKey, portalOneApiUrl!!)
                                .makePayment(binding!!.makePaymentRequest!!)
                                .onLoad { eventService!!.onLoad() }
                                .onPaymentComplete { paymentInfo -> eventService!!.onPaymentComplete(paymentInfo) }
                                .onPaymentCanceled { eventService!!.onPaymentCanceled() }
                                .onError { error -> eventService!!.onError(error) }
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
