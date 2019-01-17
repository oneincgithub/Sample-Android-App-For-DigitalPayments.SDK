package com.digitalpayments.android.sample.javaSample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.digitalpayments.android.sample.R;

public class PaymentBaseJavaSampleActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EventServiceJava eventService;
    String sessionKey;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(PaymentBaseJavaSampleActivity.this, R.xml.pref_general, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(PaymentBaseJavaSampleActivity.this);
        eventService = new EventServiceJava(PaymentBaseJavaSampleActivity.this);
        String sessionServiceUrl = sharedPreferences.getString("session_service_url", getResources().getString(R.string.session_service_url));
        url = sharedPreferences.getString("generic_modal_url", getResources().getString(R.string.generic_modal_url));

        InitializeSessionTaskJava initializeSessionTask = new InitializeSessionTaskJava(sessionServiceUrl, new SessionCreatingCompleteHandler() {
            @Override
            public void run() {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                if (response != null && response.isSuccessful() && response.getSessionKey() != "") {
                    sessionKey = response.getSessionKey();
                    Log.i("SessionKey created", response.getSessionKey());
                    findViewById(R.id.openDialog).setEnabled(true);
                } else {
                    Log.i("Session service failed", response.getSessionKey());
                    Toast toast = Toast.makeText(PaymentBaseJavaSampleActivity.this.getApplicationContext(), "Session service failed", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        initializeSessionTask.execute();
    }
}
