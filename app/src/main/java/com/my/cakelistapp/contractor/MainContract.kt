package com.my.cakelistapp.contractor

import android.view.View
import com.my.cakelistapp.model.Cake

/**
 * Created by Tejas Shah on 28/02/19.
 */
interface MainContract {

    /*
    * Call when user interact with the view and other when view OnDestroy()
    * Call when user interact with the view and other when view OnDestroy()
    * */
    interface presenter1{
        fun onDestroy()
        fun requestDataFromServer()
    }

    interface presenter{
        fun attachView(view: MainContract.MainView)
        fun detachView()
        fun onDestroy()
    }

    /**
     * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
     * while the setDataToRecyclerView and onResponseFailure is fetched from the GetNoticeInteractorImpl class
     **/
    // Interface (acts as a contract between View and Presenter)
    interface MainView {
        fun showProgress()
        fun hideProgress()
        fun setData(arrUpdates: List<Cake>)
        fun setDataError(strError: String)
    }

    /**
     * Intractors are classes built for fetching data from your database, web services, or any other data source.
     **/
    //open class GetCakeIntractor {
    interface GetCakeIntractor{
        /*companion object {
            private val TAG: String = GetCakeIntractor::class.java.simpleName
        }*/

        interface OnFinishedListener {
            fun onResultSuccess(arrUpdates: List<Cake>)
            fun onResultFail(strError: String)
        }

        fun getCakeArrayList(onFinishedListener: OnFinishedListener)
    }
    //}

    interface RecyclerViewClickListener{
        fun onItemClick(adapterPosition: Int,imgDec: String)
    }

}