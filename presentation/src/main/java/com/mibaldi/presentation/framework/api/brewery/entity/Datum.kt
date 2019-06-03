package com.mibaldi.presentation.framework.api.brewery.entity

import com.google.gson.annotations.SerializedName

data class Datum(

    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("nameDisplay")
    var nameDisplay: String? = null,
    @SerializedName("abv")
    var abv: String? = null,
    @SerializedName("glasswareId")
    var glasswareId: Int? = null,
    @SerializedName("styleId")
    var styleId: Int? = null,
    @SerializedName("isOrganic")
    var isOrganic: String? = null,
    @SerializedName("isRetired")
    var isRetired: String? = null,
    @SerializedName("labels")
    var labels: Labels? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("statusDisplay")
    var statusDisplay: String? = null,
    @SerializedName("createDate")
    var createDate: String? = null,
    @SerializedName("updateDate")
    var updateDate: String? = null,
    @SerializedName("glass")
    var glass: Glass? = null,
    @SerializedName("style")
    var style: Style? = null
)
