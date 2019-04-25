package com.digitalpayments.paymentform.android.sample.services

import org.json.JSONObject

data class SessionCreateResponse (
    var isSuccessful: Boolean,
    var sessionKey: String,
    var errorDescription: String? = null,
    var responseCode: String,
    var portalOneSessionKey: String,
    var responseMessage: String? = null
){
    companion object {
        fun deserialize(data: String): SessionCreateResponse {
            val jsonObj = JSONObject(data)
            val keys = jsonObj.keys()
            var isSuccessful = true
            var sessionKey = ""
            var errorDescription = ""
            var responseCode = ""
            var portalOneSessionKey = ""
            var responseMessage = ""
            while(keys.hasNext()){
                val key = keys.next()
                if(key.equals("isSuccessful")) {
                    isSuccessful = jsonObj.getBoolean(key)
                }
                if(key.equals("sessionKey")) {
                    sessionKey = jsonObj.getString(key)
                }
                if(key.equals("errorDescription")) {
                    errorDescription = jsonObj.getString(key)
                }
                if(key.equals("responseCode")) {
                    responseCode = jsonObj.getString(key)
                }
                if(key.equals("portalOneSessionKey")) {
                    portalOneSessionKey = jsonObj.getString(key)
                }
                if(key.equals("responseMessage")) {
                    responseMessage = jsonObj.getString(key)
                }
            }
            return SessionCreateResponse(isSuccessful, sessionKey, errorDescription, responseCode, portalOneSessionKey, responseMessage)
        }
    }
}