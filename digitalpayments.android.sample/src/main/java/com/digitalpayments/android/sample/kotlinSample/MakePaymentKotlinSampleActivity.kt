package com.digitalpayments.android.sample.kotlinSample

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_make_payment_test.*
import com.digitalpayments.android.sdk.MakePaymentActivity
import com.digitalpayments.android.sample.R
import android.databinding.DataBindingUtil
import com.digitalpayments.android.sample.databinding.ActivityMakePaymentTestBinding
import android.widget.ArrayAdapter
import com.digitalpayments.android.sdk.models.*

class MakePaymentKotlinSampleActivity : PaymentBaseKotlinSampleActivity() {
    private var binding: ActivityMakePaymentTestBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_make_payment_test)
        binding!!.makePaymentRequest = MakePaymentRequest()

        paymentCategory.adapter = ArrayAdapter<PaymentCategory>(this, android.R.layout.simple_dropdown_item_1line, PaymentCategory.values())
        feeContext.adapter = ArrayAdapter<FeeContext>(this, android.R.layout.simple_dropdown_item_1line, FeeContext.values())

        openDialog.isEnabled = false
    }

    fun onClick(view: View) {
        return when (view.id) {
            R.id.openDialog -> {
                eventService!!.paymentForm = MakePaymentActivity
                    .init(sessionKey, url!!)
                    .makePayment(binding!!.makePaymentRequest!!)
                    .onLoad { eventService!!.onLoad() }
                    .onPaymentComplete { paymentInfo ->  eventService!!.onPaymentComplete(paymentInfo) }
                    .onPaymentCanceled { eventService!!.onPaymentCanceled() }
                    .onError { error -> eventService!!.onError(error) }
                    .onClose { eventService!!.onClose() }
                    .startWebView(this)
            }
            else -> {
            }
        }
    }
}
