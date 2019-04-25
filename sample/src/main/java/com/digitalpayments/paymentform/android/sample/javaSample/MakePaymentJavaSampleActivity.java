package com.digitalpayments.paymentform.android.sample.javaSample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.digitalpayments.paymentform.android.sample.databinding.ActivityMakePaymentTestBinding;
import com.digitalpayments.paymentform.android.sample.R;
import com.digitalpayments.paymentform.android.sdk.MakePaymentActivity;
import com.digitalpayments.paymentform.android.sdk.models.*;

public class MakePaymentJavaSampleActivity extends PaymentBaseJavaSampleActivity {
    private ActivityMakePaymentTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_make_payment_test);
        binding.setMakePaymentRequest(new MakePaymentRequest());
        binding.setTheme(ThemeType.Default);

        Spinner amountContext = findViewById(R.id.amountContext);
        amountContext.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, AmountContext.values()));

        Spinner paymentCategory = findViewById(R.id.paymentCategory);
        paymentCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, PaymentCategory.values()));

        Spinner feeContext = findViewById(R.id.feeContext);
        feeContext.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, FeeContext.values()));

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
                            MakePaymentActivity
                                    .init(sessionKey, genericModalUrl)
                                    .makePayment(binding.getMakePaymentRequest())
                                    .onLoad(eventService.onLoad)
                                    .onPaymentComplete(eventService.onPaymentComplete)
                                    .onPaymentCanceled(eventService.onPaymentCanceled)
                                    .onError(eventService.onError)
                                    .onClose(eventService.onClose)
                                    .startWebView(MakePaymentJavaSampleActivity.this);
                        } else {
                            Log.i("Session service failed", response.getErrorDescription());
                            Toast toast = Toast.makeText(MakePaymentJavaSampleActivity.this.getApplicationContext(), "Session service failed", Toast.LENGTH_SHORT);
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
                            MakePaymentActivity
                                    .init(sessionKey, portalOneApiUrl)
                                    .makePayment(binding.getMakePaymentRequest())
                                    .onLoad(eventService.onLoad)
                                    .onPaymentComplete(eventService.onPaymentComplete)
                                    .onPaymentCanceled(eventService.onPaymentCanceled)
                                    .onError(eventService.onError)
                                    .onClose(eventService.onClose)
                                    .setStyle(binding.getTheme(), colorScheme)
                                    .start(MakePaymentJavaSampleActivity.this);
                        } else {
                            Log.i("Session service failed", response.getResponseMessage());
                            Toast toast = Toast.makeText(MakePaymentJavaSampleActivity.this.getApplicationContext(), "Session service failed", Toast.LENGTH_SHORT);
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
