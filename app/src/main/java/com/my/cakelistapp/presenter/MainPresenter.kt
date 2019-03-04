package com.my.cakelistapp.presenter

import android.view.View
import com.my.cakelistapp.contractor.MainContract
import com.my.cakelistapp.contractor.MainContract.presenter
import com.my.cakelistapp.model.Cake

/**
 * Created by Tejas Shah on 28/02/19.
 */
// Presenter has the object of both View and Model(Interactor)
// Implements OnFinishedListener to listen for Interactor response
class MainPresenter(private var mainView: MainContract.MainView?, private val getCakeIntractor: MainContract.GetCakeIntractor)
    : MainContract.GetCakeIntractor.OnFinishedListener {

    companion object {
        private val TAG: String = MainPresenter::class.java.simpleName
    }

    fun getData() {
        mainView?.showProgress()
        getCakeIntractor.getCakeArrayList(this)
    }

    override fun onResultSuccess(arrUpdates: List<Cake>) {
        mainView?.hideProgress()
        mainView?.setData(arrUpdates)
    }

    override fun onResultFail(strError: String) {
        mainView?.hideProgress()
        mainView?.setDataError(strError)
    }

    // Destroy View when Activity destroyed
    fun onDestroy() {
        mainView = null
    }
}