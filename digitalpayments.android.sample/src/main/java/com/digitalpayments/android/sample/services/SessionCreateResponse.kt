package com.digitalpayments.android.sample.services

import org.json.JSONObject

data class SessionCreateResponse (
    var isSuccessful: Boolean,
    var sessionKey: String,
    var errorDescription: String? = null
){
    companion object {
        fun deserialize(data: String): SessionCreateResponse {
            val jsonObj = JSONObject(data)
            val keys = jsonObj.keys()
            var isSuccessful = true
            var sessionKey = ""
            var errorDescription = ""
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
            }
            return SessionCreateResponse(isSuccessful, sessionKey, errorDescription)
        }
    }
}