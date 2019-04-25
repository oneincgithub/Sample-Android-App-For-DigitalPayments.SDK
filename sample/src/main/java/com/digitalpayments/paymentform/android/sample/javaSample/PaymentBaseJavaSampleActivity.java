package com.digitalpayments.paymentform.android.sample.javaSample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.digitalpayments.paymentform.android.sample.R;
import com.digitalpayments.paymentform.android.sdk.models.ColorType;

import java.util.HashMap;
import java.util.Map;

public class PaymentBaseJavaSampleActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EventServiceJava eventService;
    String sessionKey;
    String genericModalUrl;
    String portalOneApiUrl;
    String genericModalSessionServiceUrl;
    String portalOneApiSessionServiceUrl;
    Map<ColorType, String> colorScheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(PaymentBaseJavaSampleActivity.this, R.xml.pref_general, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(PaymentBaseJavaSampleActivity.this);
        eventService = new EventServiceJava(PaymentBaseJavaSampleActivity.this);
        getUrlsFromSettings();
        colorScheme = getCustomColors();
    }

    private void getUrlsFromSettings() {
        genericModalSessionServiceUrl = sharedPreferences.getString(
                "generic_modal_session_service_url",
                getResources().getString(R.string.generic_modal_session_service_url));
        portalOneApiSessionServiceUrl = sharedPreferences.getString(
                "portal_one_api_session_service_url",
                getResources().getString(R.string.portal_one_api_session_service_url));
        genericModalUrl = sharedPreferences.getString(
                "generic_modal_url",
                getResources().getString(R.string.generic_modal_url));
        portalOneApiUrl = sharedPreferences.getString(
                "portal_one_api_url",
                getResources().getString(R.string.portal_one_api_url));
    }

    private Map<ColorType, String> getCustomColors() {
        Map<ColorType, String> colorScheme  = new HashMap<>();

        for (ColorType colorType : ColorType.values()) {
            String customValue = sharedPreferences.getString(colorType.getDescription(), "");
            if (customValue != null && !customValue.isEmpty()){
                colorScheme.put(colorType, customValue);
            }
        }

        return colorScheme.isEmpty() ? null : colorScheme;
    }


    protected void setupProgressBarVisibility(Boolean visible){
        findViewById(R.id.progressBar).setVisibility(visible ? View.VISIBLE : View.GONE);
        findViewById(R.id.openNative).setEnabled(!visible);
        findViewById(R.id.openDialog).setEnabled(!visible);
    }
}
