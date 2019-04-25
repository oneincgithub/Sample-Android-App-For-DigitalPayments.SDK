package com.digitalpayments.paymentform.android.sample.javaSample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.digitalpayments.paymentform.android.sample.R;
import com.digitalpayments.paymentform.android.sample.databinding.ActivitySavePaymentTestBinding;
import com.digitalpayments.paymentform.android.sdk.SavePaymentMethodActivity;
import com.digitalpayments.paymentform.android.sdk.models.DigitalPaymentForm;
import com.digitalpayments.paymentform.android.sdk.models.PaymentCategory;
import com.digitalpayments.paymentform.android.sdk.models.SavePaymentMethodRequest;
import com.digitalpayments.paymentform.android.sdk.models.ThemeType;

public class SavePaymentJavaSampleActivity extends PaymentBaseJavaSampleActivity {
    private ActivitySavePaymentTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_save_payment_test);
        binding.setSavePaymentMethodRequest(new SavePaymentMethodRequest());
        binding.setTheme(ThemeType.Default);

        Spinner paymentCategory = findViewById(R.id.paymentCategory);
        paymentCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, PaymentCategory.values()));

        Spinner themeType = findViewById(R.id.themeType);
        themeType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, ThemeType.values()));

        binding.executePendingBindings();
    }

    public void onClick(View view) {
        setupProgressBarVisibility(true);
        switch (view.getId()) {
            case R.id.openDialog: {
                new InitializeSessionTaskJava(genericModalSessionServiceUrl, new SessionCreatingCompleteHandler() {
                    @Override
                    public void run() {
                        setupProgressBarVisibility(false);
                        if (response != null && response.isSuccessful() && !response.getSessionKey().equals("")) {
                            sessionKey = response.getSessionKey();
                            Log.i("SessionKey created", response.getSessionKey());
                            eventService.paymentForm  = SavePaymentMethodActivity
                                    .init(sessionKey, genericModalUrl)
                                    .savePaymentMethod(binding.getSavePaymentMethodRequest())
                                    .onLoad(eventService.onLoad)
                                    .onSaveComplete(eventService.onSaveComplete)
                                    .onSaveCanceled(eventService.onSaveCanceled)
                                    .onError(eventService.onError)
                                    .onClose(eventService.onClose)
                                    .startWebView(SavePaymentJavaSampleActivity.this);
                        } else {
                            Log.i("Session service failed", response.getErrorDescription());
                            Toast toast = Toast.makeText(SavePaymentJavaSampleActivity.this.getApplicationContext(), "Session service failed", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }).execute();
                break;
            }
            case R.id.openNative: {
                    new InitializeSessionTaskJava(portalOneApiSessionServiceUrl, new SessionCreatingCompleteHandler() {
                    @Override
                    public void run() {
                        setupProgressBarVisibility(false);
                        if (response != null && response.getResponseCode().equals("Success") && !response.getPortalOneSessionKey().equals("")) {
                            sessionKey = response.getPortalOneSessionKey();
                            Log.i("SessionKey created", response.getPortalOneSessionKey());
                            SavePaymentMethodActivity
                                    .init(sessionKey, portalOneApiUrl)
                                    .savePaymentMethod(binding.getSavePaymentMethodRequest())
                                    .onLoad(eventService.onLoad)
                                    .onSaveComplete(eventService.onSaveComplete)
                                    .onSaveCanceled(eventService.onSaveCanceled)
                                    .onError(eventService.onError)
                                    .onClose(eventService.onClose)
                                    .setStyle(binding.getTheme(), colorScheme)
                                    .start(SavePaymentJavaSampleActivity.this);
                        } else {
                            Log.i("Session service failed", response.getResponseMessage());
                            Toast toast = Toast.makeText(SavePaymentJavaSampleActivity.this.getApplicationContext(), "Session service failed", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }).execute();
                break;
            }
            default:
                break;
        }
    }
}
