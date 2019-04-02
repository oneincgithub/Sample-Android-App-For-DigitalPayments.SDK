package com.digitalpayments.android.sample.javaSample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.digitalpayments.android.sample.databinding.ActivityMakePaymentTestBinding;
import com.digitalpayments.android.sample.R;
import com.digitalpayments.android.sdk.MakePaymentActivity;
import com.digitalpayments.android.sdk.models.*;

public class MakePaymentJavaSampleActivity extends PaymentBaseJavaSampleActivity {
    private ActivityMakePaymentTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_make_payment_test);
        binding.setMakePaymentRequest(new MakePaymentRequest());

        Spinner paymentCategory = findViewById(R.id.paymentCategory);
        paymentCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, PaymentCategory.values()));

        Spinner feeContext = findViewById(R.id.feeContext);
        feeContext.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, FeeContext.values()));

        findViewById(R.id.openDialog).setEnabled(false);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.openDialog: {
                eventService.paymentForm = MakePaymentActivity
                    .init(sessionKey, url)
                    .makePayment(binding.getMakePaymentRequest())
                    .onLoad(eventService.onLoad)
                    .onPaymentComplete(eventService.onPaymentComplete)
                    .onPaymentCanceled(eventService.onPaymentCanceled)
                    .onError(eventService.onError)
                    .onClose(eventService.onClose)
                    .startWebView(this);
                break;
            }
            default:
                break;
        }
    }
}
