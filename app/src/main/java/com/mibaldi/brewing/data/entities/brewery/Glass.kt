package com.mibaldi.brewing.data.entities.brewery

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Glass {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("createDate")
    @Expose
    var createDate: String? = null

}
