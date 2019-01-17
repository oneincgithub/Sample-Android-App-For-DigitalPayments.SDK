package com.digitalpayments.android.sample.javaSample;

import android.os.AsyncTask;
import com.digitalpayments.android.sample.services.SessionCreateResponse;
import com.digitalpayments.android.sample.services.SessionService;

public class InitializeSessionTaskJava extends AsyncTask<String, String, SessionCreateResponse> {
    SessionCreatingCompleteHandler _onCompleteHandler;
    String _sessionServiceUrl;

    InitializeSessionTaskJava(
            String sessionServiceUrl,
            SessionCreatingCompleteHandler onCompleteHandler){
        _onCompleteHandler = onCompleteHandler;
        _sessionServiceUrl = sessionServiceUrl;
    }

    @Override
    protected SessionCreateResponse doInBackground(String... strings) {
        SessionService sessionService = new SessionService(_sessionServiceUrl);
        SessionCreateResponse result = sessionService.initializeSession();
        return result;
    }

    @Override
    protected void onPostExecute(SessionCreateResponse result){
        super.onPostExecute(result);
        _onCompleteHandler.response = result;
        _onCompleteHandler.run();
    }
}

