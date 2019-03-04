package com.my.cakelistapp.diffutil

import androidx.annotation.Nullable
//import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil
import com.my.cakelistapp.model.Cake



/**
 * Created by Tejas Shah on 28/02/19.
 */
class CakeDiffCallback(
    private val oldList: List<Cake>,
    private val newList: List<Cake>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].desc == newList[newItemPosition].desc && oldList[oldItemPosition].image == newList[oldItemPosition].image && oldList[oldItemPosition].title == newList[newItemPosition].title
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

}