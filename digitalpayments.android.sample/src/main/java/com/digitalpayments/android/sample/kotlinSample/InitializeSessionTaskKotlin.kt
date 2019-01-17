package com.digitalpayments.android.sample.kotlinSample

import android.os.AsyncTask
import com.digitalpayments.android.sample.services.SessionCreateResponse
import com.digitalpayments.android.sample.services.SessionService

class InitializeSessionTaskKotlin(
    private val sessionServiceUrl: String,
    val onCompleteHandler: (result: SessionCreateResponse) -> Unit
): AsyncTask<String, String, SessionCreateResponse>() {

    override fun doInBackground(vararg params: String?): SessionCreateResponse {
        val sessionService = SessionService(sessionServiceUrl)
        val result = sessionService.initializeSession()
        return result
    }

    override fun onPostExecute(result: SessionCreateResponse) {
        super.onPostExecute(result)
        onCompleteHandler(result)
    }
}