package com.mibaldi.presentation.framework.api.brewery.entity

import com.google.gson.annotations.SerializedName

data class Glass(

    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("createDate")
    var createDate: String? = null
)