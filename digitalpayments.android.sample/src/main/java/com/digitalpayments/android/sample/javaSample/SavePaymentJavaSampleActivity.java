package com.digitalpayments.android.sample.javaSample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.digitalpayments.android.sample.R;
import com.digitalpayments.android.sample.databinding.ActivitySavePaymentTestBinding;
import com.digitalpayments.android.sdk.SavePaymentMethodActivity;
import com.digitalpayments.android.sdk.models.PaymentCategory;
import com.digitalpayments.android.sdk.models.SavePaymentMethodRequest;

public class SavePaymentJavaSampleActivity extends PaymentBaseJavaSampleActivity {
    private ActivitySavePaymentTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_save_payment_test);
        binding.setSavePaymentMethodRequest(new SavePaymentMethodRequest());

        Spinner paymentCategory = findViewById(R.id.paymentCategory);
        paymentCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, PaymentCategory.values()));

        findViewById(R.id.openDialog).setEnabled(false);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.openDialog: {
                SavePaymentMethodActivity
                    .init(sessionKey, url)
                    .savePaymentMethod(binding.getSavePaymentMethodRequest())
                    .onSaveComplete(eventService.onSaveComplete)
                    .onSaveCanceled(eventService.onSaveCanceled)
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
