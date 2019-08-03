package com.template.app.data.remote


import java.net.HttpURLConnection
import javax.net.ssl.HttpsURLConnection

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


internal abstract class ErrorHandlingCallback<T> : Callback<T> {

    abstract fun onSuccess(response: T)
    abstract fun onFailed(throwable: Throwable)

    override fun onResponse(call: Call<T>, response: Response<T>) {
        when (response.code()) {
            HttpsURLConnection.HTTP_OK,
            HttpsURLConnection.HTTP_CREATED,
            HttpsURLConnection.HTTP_ACCEPTED,
            HttpsURLConnection.HTTP_NOT_AUTHORITATIVE
                -> onSuccess(response.body()!!)

            HttpURLConnection.HTTP_CONFLICT
                -> onFailed(Throwable("API " + response.code() + " " + response.errorBody()))

            else -> onFailed(Throwable("Default " + response.code() + " " + response.message()))
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        onFailed(t)
    }
}