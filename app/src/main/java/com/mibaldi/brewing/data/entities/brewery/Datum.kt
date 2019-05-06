package com.mibaldi.brewing.data.entities.brewery

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Datum {

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("nameDisplay")
    @Expose
    var nameDisplay: String? = null
    @SerializedName("abv")
    @Expose
    var abv: String? = null
    @SerializedName("glasswareId")
    @Expose
    var glasswareId: Int? = null
    @SerializedName("styleId")
    @Expose
    var styleId: Int? = null
    @SerializedName("isOrganic")
    @Expose
    var isOrganic: String? = null
    @SerializedName("isRetired")
    @Expose
    var isRetired: String? = null
    @SerializedName("labels")
    @Expose
    var labels: Labels? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("statusDisplay")
    @Expose
    var statusDisplay: String? = null
    @SerializedName("createDate")
    @Expose
    var createDate: String? = null
    @SerializedName("updateDate")
    @Expose
    var updateDate: String? = null
    @SerializedName("glass")
    @Expose
    var glass: Glass? = null
    @SerializedName("style")
    @Expose
    var style: Style? = null

}
