package com.my.cakelistapp.intractor

import android.util.Log
import com.my.cakelistapp.contractor.MainContract
import com.my.cakelistapp.model.Cake
import com.my.cakelistapp.network.ApiClient

import retrofit2.Call;
import retrofit2.Callback
import retrofit2.Response



/**
 * Created by Tejas Shah on 28/02/19.
 */
class GetCakeIntractorImpl : MainContract.GetCakeIntractor {

    var call : Call<List<Cake>>? = null
    companion object {
        private val TAG: String = GetCakeIntractorImpl::class.java.simpleName
    }

    override fun getCakeArrayList(onFinishedListener: MainContract.GetCakeIntractor.OnFinishedListener) {

        /** Create handle for the RetrofitInstance interface*/
        val apiClient = ApiClient.getClientInstance()
        call = apiClient.getAllCakeInfo();

        call?.enqueue(object : Callback<List<Cake>> {
            override fun onFailure(call: Call<List<Cake>>, t: Throwable) {
                onFinishedListener.onResultFail(t.toString())
            }

            override fun onResponse(call: Call<List<Cake>>, response: Response<List<Cake>>) {
                response.body()?.let { onFinishedListener.onResultSuccess(it) }
            }
        })
    }
}