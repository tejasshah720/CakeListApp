package com.my.cakelistapp.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

//TODO can use this Interceptor with retrofit
abstract class NetworkConnectionInterceptor : Interceptor {

    abstract fun isInternetAvailable(): Boolean

    abstract fun onInternetUnavailable()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!isInternetAvailable()) {
            onInternetUnavailable()
        }
        return chain.proceed(request)
    }
}
