package com.my.cakelistapp

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.my.cakelistapp.receiver.ConnectivityReceiver
import com.my.cakelistapp.utils.showToast

/**
 * Created by Tejas Shah on 01/03/19.
 */
abstract class BaseActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener{

    private var relativeLayoutNetworkScreen: RelativeLayout? = null
    private var btnRetry: Button? = null
    private var isConnectionCheck: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(ConnectivityReceiver(),
            /**
             * TODO Can improve this code for new version
             */
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun setContentView(layoutResID: Int) {

        val parentView = layoutInflater.inflate(R.layout.activity_base, null)
        relativeLayoutNetworkScreen = parentView.findViewById(R.id.rl_no_network)
        btnRetry = parentView.findViewById(R.id.btn_retry)

        btnRetry?.setOnClickListener {
            showToast(parentView.context,"Network Connection not avialable, Please try again after some time.")
        }

        val contentFrameLayout = parentView.findViewById(R.id.contentFrame) as FrameLayout
        layoutInflater.inflate(layoutResID, contentFrameLayout, true)
        super.setContentView(layoutResID)
    }


    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        isConnectionCheck = isConnected

        if(!isConnected){
            //Offline
            showNetworkErrorScreen()
        }else{
            //Online
            hideNetworkErrorScreen()
        }
    }

    fun showNetworkErrorScreen() {
        if (relativeLayoutNetworkScreen != null) {
            relativeLayoutNetworkScreen?.setVisibility(View.VISIBLE)
        }
    }

    fun hideNetworkErrorScreen() {
        if (relativeLayoutNetworkScreen != null) {
            relativeLayoutNetworkScreen?.setVisibility(View.GONE)
        }
    }
}