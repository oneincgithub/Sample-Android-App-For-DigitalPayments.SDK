package com.digitalpayments.paymentform.android.sample.services

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SessionService(val baseUrl: String) {
    fun initializeSession(): SessionCreateResponse {
        var result = SessionCreateResponse(true,"", "", "", "")
        try {
            val url = URL(baseUrl)
            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "GET"

                val responseStream = if (responseCode < HttpURLConnection.HTTP_BAD_REQUEST) {
                    inputStream
                } else {
                    errorStream
                }

                BufferedReader(InputStreamReader(responseStream)).use {
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
            result.responseMessage = e.message
            result.errorDescription = e.message
        }

        return result
    }
}