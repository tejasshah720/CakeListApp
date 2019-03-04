package com.my.cakelistapp.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Tejas Shah on 28/02/19.
 */
data class Cake(
    @SerializedName("title") var title: String? = null,
    @SerializedName("desc") var desc: String? = null,
    @SerializedName("image") var image: String? = null
)