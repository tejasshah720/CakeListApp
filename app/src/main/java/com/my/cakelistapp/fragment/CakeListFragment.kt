package com.my.cakelistapp.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.*
import androidx.fragment.app.Fragment

import com.my.cakelistapp.R
import com.my.cakelistapp.contractor.MainContract
import com.my.cakelistapp.model.Cake
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.my.cakelistapp.adapter.CakeAdapter
import com.my.cakelistapp.presenter.MainPresenter
import com.my.cakelistapp.utils.showToast
import com.my.cakelistapp.intractor.GetCakeIntractorImpl
import androidx.appcompat.app.AlertDialog
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.DividerItemDecoration


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CakeListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CakeListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
@SuppressLint("ParcelCreator")
class CakeListFragment() : Fragment(),MainContract.MainView,SwipeRefreshLayout.OnRefreshListener,MainContract.RecyclerViewClickListener, Parcelable {
    private var progressOverlay: View? = null
    private var progressBar: ProgressBar? = null
    private var tvProgressMessage: TextView? = null
    private var cakeAdapter: CakeAdapter?= null

    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    private val cakes = mutableListOf<Cake>()

    // Initialize Presenter (also Model in the constructor of Presenter) & has object of Presenter
    private lateinit var mainPresenter: MainPresenter
    //private var presenter1: MainContract.presenter1? = null


    // TODO: Rename and change types of parameters
    //private var param1: String? = null
    //private var param2: String? = null
    //private var listener: OnFragmentInteractionListener? = null
    private var recyclerView: RecyclerView? = null

    /*constructor(parcel: Parcel) : this() {
        param1 = parcel.readString()
        param2 = parcel.readString()
    }
*/
    override fun showProgress() {
        progressOverlay?.visibility = View.VISIBLE
        progressBar?.visibility = View.VISIBLE
        tvProgressMessage?.text = this.resources.getString(R.string.progress_msg)
        tvProgressMessage?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar?.visibility = View.GONE
        tvProgressMessage?.visibility = View.GONE
        progressOverlay?.visibility = View.GONE
    }

    override fun setData(arrUpdates: List<Cake>) {
        if(mSwipeRefreshLayout?.isRefreshing!!) mSwipeRefreshLayout?.setRefreshing(false)
        cakes.clear()
        cakes.addAll(arrUpdates)
//        cakeAdapter = CakeAdapter(arrUpdates){
//            arrUpdates.get(it).desc?.let { it1 -> mainPresenter.onItemClick(it, it1) }
//        }
        cakeAdapter = CakeAdapter(arrUpdates,this)
        recyclerView?.adapter = cakeAdapter
    }

    override fun onRefresh() {
        mSwipeRefreshLayout?.setRefreshing(true)
        mainPresenter = MainPresenter(this@CakeListFragment, GetCakeIntractorImpl())
        mainPresenter.getData()
    }

    override fun setDataError(strError: String) {
        showToast(context, "strError is: $strError")
        if(mSwipeRefreshLayout?.isRefreshing!!) mSwipeRefreshLayout?.setRefreshing(false)
    }

    override fun onItemClick(adapterPosition: Int, imgDec: String) {
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setTitle("CakeInfo Alert")
        builder?.setMessage(imgDec)
        builder?.setNegativeButton(android.R.string.cancel) { dialog, which ->
            dialog.dismiss()
        }
        builder?.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_cake_list, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        progressOverlay = view.findViewById(R.id.progressOverlay)
        progressBar = view.findViewById(R.id.progressBar);
        tvProgressMessage = view.findViewById(R.id.tvProgressMessage)
        mSwipeRefreshLayout = view.findViewById(R.id.mSwipeRefreshLayout);

        progressOverlay?.visibility = View.GONE

        recyclerView?.addItemDecoration(DividerItemDecoration(activity!!, DividerItemDecoration.VERTICAL))
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        mSwipeRefreshLayout?.setOnRefreshListener(this)

        mainPresenter = MainPresenter(this, GetCakeIntractorImpl())
        mainPresenter.getData()
    }

    // TODO: Rename method, update argument and hook method into UI eventonResultSuccess
    fun onButtonPressed(uri: Uri) {
        //listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        /*if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
        //listener = null
        mainPresenter.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
                R.id.sort_by_name -> {
                    val sortedList: List<Cake> = cakes.sortedWith(compareBy { it.title })
                    cakeAdapter?.swap(sortedList)
                    true
                }
                R.id.duplicate_remove -> {
                    val distinctList: List<Cake> = cakes.distinct()
                    cakeAdapter?.updateList(distinctList.sortedWith(compareBy { it.title }))
                    true
                }

            else -> super.onOptionsItemSelected(item)
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     *//*
    interface OnFragmentInteractionListener : (Int) -> Unit {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }*/

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CakeListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        //fun newInstance(param1: String, param2: String) =
        fun newInstance() =
            CakeListFragment().apply {
                arguments = Bundle().apply {
                    //putString(ARG_PARAM1, param1)
                    //putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        //parcel.writeString(param1)
        //parcel.writeString(param2)
    }

    override fun describeContents(): Int {
        return 0
    }

//    companion object CREATOR : Parcelable.Creator<CakeListFragment> {
//        override fun createFromParcel(parcel: Parcel): CakeListFragment {
//            return CakeListFragment(parcel)
//        }
//
//        override fun newArray(size: Int): Array<CakeListFragment?> {
//            return arrayOfNulls(size)
//        }
//    }
}
