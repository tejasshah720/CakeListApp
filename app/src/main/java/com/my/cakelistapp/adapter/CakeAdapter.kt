package com.my.cakelistapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.my.cakelistapp.R
import com.my.cakelistapp.diffutil.CakeDiffCallback
import com.my.cakelistapp.model.Cake
import android.view.animation.AlphaAnimation
import android.util.Log
import com.my.cakelistapp.contractor.MainContract.RecyclerViewClickListener


/**
 * Created by Tejas Shah on 28/02/19.
 */
class CakeAdapter(cakeList: List<Cake>,listener: RecyclerViewClickListener) : RecyclerView.Adapter<CakeAdapter.CakeViewHolder>() {
    private val cakes = mutableListOf<Cake>()
    private val FADE_DURATION = 1000L
    private var lastPosition = -1
    private var mListener: RecyclerViewClickListener? = null


    companion object {
        private val TAG: String = CakeAdapter::class.java.simpleName
    }

    init {
        cakes.clear()
        cakes.addAll(cakeList)
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CakeViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_cake, parent, false)
        return CakeViewHolder(v);
    }

    override fun getItemCount(): Int {
        return cakes.size
    }

    override fun onBindViewHolder(holder: CakeViewHolder, position: Int) {
        holder.bind(cakes[position], mListener!!)
        setFadeAnimation(holder.itemView,position)
    }

    private fun setFadeAnimation(view: View,position: Int) {
        if (position > lastPosition) {
            val anim = AlphaAnimation(0.0f, 1.0f)
            anim.duration = FADE_DURATION
            view.startAnimation(anim)
            lastPosition = position;
        }
    }

    fun updateList(updatedcakes : List<Cake>){
        this.cakes.clear()
        this.cakes.addAll(updatedcakes)
        notifyDataSetChanged()
    }

    fun swap(updatedcakes : List<Cake>){
        try{
            val diffCallback = CakeDiffCallback(this.cakes, updatedcakes)
            val diffResult = DiffUtil.calculateDiff(diffCallback)

            this.cakes.clear()
            Log.d(TAG,"cakes size: ${cakes.size}")
            Log.d(TAG,"updatedcakes size: ${updatedcakes.size}")
            this.cakes.addAll(updatedcakes)
            diffResult.dispatchUpdatesTo(this)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    class CakeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(cakeItem: Cake,listener: RecyclerViewClickListener) = with(itemView){
            val imgCake = itemView.findViewById<ImageView>(R.id.imgCake)
            val tvCakeTitle = itemView.findViewById<TextView>(R.id.tvCakeTitle)

            Glide.with(imgCake.context).load(cakeItem.image).into(imgCake)
            tvCakeTitle.text = cakeItem.title

            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition, cakeItem.desc!!)
            }
        }
    }

}

