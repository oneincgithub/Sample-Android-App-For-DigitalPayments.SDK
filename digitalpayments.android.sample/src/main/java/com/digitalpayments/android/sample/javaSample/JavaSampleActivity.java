package com.digitalpayments.android.sample.javaSample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.digitalpayments.android.sample.R;

public class JavaSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.makePaymentButton: {
                Intent intent = new Intent(JavaSampleActivity.this, MakePaymentJavaSampleActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.savePaymentButton: {
                Intent intent = new Intent(JavaSampleActivity.this, SavePaymentJavaSampleActivity.class);
                startActivity(intent);
                break;
            }
            default:
                break;
        }
    }
}


