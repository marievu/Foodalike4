package com.example.marievu.foodalike

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams

    class HttpUtils {
    /**
     * Created by student on 24/01/18.
     */
    object HttpUtils {
        val DEFAULT_BASE_URL = "https://foodalike4.herokuapp.com/"
        var baseUrl:String
        private val client = AsyncHttpClient()
        init{
            baseUrl = DEFAULT_BASE_URL
        }
        fun get(url:String, params:RequestParams, responseHandler:AsyncHttpResponseHandler) {
            client.get(getAbsoluteUrl(url), params, responseHandler)
        }
        fun post(url:String, params:RequestParams, responseHandler:AsyncHttpResponseHandler) {
            client.post(getAbsoluteUrl(url), params, responseHandler)
        }
        fun getByUrl(url:String, params:RequestParams, responseHandler:AsyncHttpResponseHandler) {
            client.get(url, params, responseHandler)
        }
        fun postByUrl(url:String, params:RequestParams, responseHandler:AsyncHttpResponseHandler) {
            client.post(url, params, responseHandler)
        }
        private fun getAbsoluteUrl(relativeUrl:String):String {
            return baseUrl + relativeUrl
        }
    }
}