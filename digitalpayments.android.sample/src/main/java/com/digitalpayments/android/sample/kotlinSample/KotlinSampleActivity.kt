package com.digitalpayments.android.sample.kotlinSample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import com.digitalpayments.android.sample.R


class KotlinSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.makePaymentButton -> {
                val intent = Intent(this, MakePaymentKotlinSampleActivity::class.java)
                startActivity(intent)
            }
            R.id.savePaymentButton -> {
                val intent = Intent(this, SavePaymentKotlinSampleActivity::class.java)
                startActivity(intent)
            }
            else -> {
            }
        }
    }
}
