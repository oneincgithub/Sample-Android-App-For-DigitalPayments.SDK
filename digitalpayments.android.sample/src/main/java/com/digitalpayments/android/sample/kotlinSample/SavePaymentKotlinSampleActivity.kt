package com.digitalpayments.android.sample.kotlinSample

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.digitalpayments.android.sample.R
import com.digitalpayments.android.sample.databinding.ActivitySavePaymentTestBinding
import com.digitalpayments.android.sdk.SavePaymentMethodActivity
import com.digitalpayments.android.sdk.models.PaymentCategory
import com.digitalpayments.android.sdk.models.SavePaymentMethodRequest
import kotlinx.android.synthetic.main.activity_make_payment_test.*

class SavePaymentKotlinSampleActivity : PaymentBaseKotlinSampleActivity() {
    private var binding: ActivitySavePaymentTestBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_save_payment_test)
        binding!!.savePaymentMethodRequest = SavePaymentMethodRequest()

        paymentCategory.adapter = ArrayAdapter<PaymentCategory>(this, android.R.layout.simple_dropdown_item_1line, PaymentCategory.values())

        openDialog.isEnabled = false
    }

    fun onClick(view: View) {
        return when (view.id) {
            R.id.openDialog -> {
                SavePaymentMethodActivity
                    .init(sessionKey, url!!)
                    .savePaymentMethod(binding!!.savePaymentMethodRequest !!)
                    .onSaveComplete { saveResponse -> eventService!!.onSaveComplete(saveResponse) }
                    .onSaveCanceled { eventService!!.onSaveCanceled() }
                    .onError { error -> eventService!!.onError(error) }
                    .onClose { eventService!!.onClose() }
                    .startWebView(this)
            }
            else -> {
            }
        }
    }
}
