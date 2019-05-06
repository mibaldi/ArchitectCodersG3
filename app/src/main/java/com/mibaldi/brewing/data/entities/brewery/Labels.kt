package com.mibaldi.brewing.data.entities.brewery

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Labels {

    @SerializedName("icon")
    @Expose
    var icon: String? = null
    @SerializedName("medium")
    @Expose
    var medium: String? = null
    @SerializedName("large")
    @Expose
    var large: String? = null
    @SerializedName("contentAwareIcon")
    @Expose
    var contentAwareIcon: String? = null
    @SerializedName("contentAwareMedium")
    @Expose
    var contentAwareMedium: String? = null
    @SerializedName("contentAwareLarge")
    @Expose
    var contentAwareLarge: String? = null

}
