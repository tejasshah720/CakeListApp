package com.my.cakelistapp.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.my.cakelistapp.BuildConfig
import com.my.cakelistapp.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


interface ApiClient {

    //private var retrofit: Retrofit?

    /**
     * Companion object for the factory
     */
    companion object Factory {

        fun getClientInstance(): ApiInterface {

            val builder = OkHttpClient.Builder()
            builder.readTimeout(30, TimeUnit.SECONDS)
            builder.connectTimeout(30, TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(loggingInterceptor)
            }

            //TODO can improve offline check code
//            builder.addInterceptor(object : NetworkConnectionInterceptor() {
//
//                override fun onInternetUnavailable() {
//                    // we can broadcast this event to activity/fragment/service
//                    // through LocalBroadcastReceiver or
//                    // RxBus/EventBus
//                    // also we can call our own interface method
//                    // like this.
//                    mInternetConnectionListener.onInternetUnavailable()
//                }
//
//                override fun isInternetAvailable(): Boolean {
//                    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
//                    val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
//                    return activeNetworkInfo != null && activeNetworkInfo.isConnected
//                }
//            })

            val okHttpClient = builder.build()

            val retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            Log.d("ApiClient", "retrofit ${retrofit.baseUrl().toString()}")

            return retrofit.create(ApiInterface::class.java)

        }
    }

    fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}
