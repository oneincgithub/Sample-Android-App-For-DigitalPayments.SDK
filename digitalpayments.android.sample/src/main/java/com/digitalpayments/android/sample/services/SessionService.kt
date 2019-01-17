package com.digitalpayments.android.sample.services

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SessionService(val baseUrl: String) {
    fun initializeSession(): SessionCreateResponse {
        var result = SessionCreateResponse(false, "")
        try {
            val url = URL(baseUrl)
            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "GET"

                BufferedReader(InputStreamReader(inputStream)).use {
                    val response = StringBuffer()

                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    it.close()

                    result = SessionCreateResponse.deserialize(response.toString())
                }
            }
        }
        catch (e: Exception) {
            result.isSuccessful = false
            result.errorDescription = e.message
        }

        return result
    }
}