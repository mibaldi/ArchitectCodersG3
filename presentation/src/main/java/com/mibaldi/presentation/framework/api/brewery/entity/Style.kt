package com.mibaldi.presentation.framework.api.brewery.entity

import com.google.gson.annotations.SerializedName

data class Style(

    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("categoryId")
    var categoryId: Int? = null,
    @SerializedName("category")
    var category: Category? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("shortName")
    var shortName: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("ibuMin")
    var ibuMin: String? = null,
    @SerializedName("ibuMax")
    var ibuMax: String? = null,
    @SerializedName("abvMin")
    var abvMin: String? = null,
    @SerializedName("abvMax")
    var abvMax: String? = null,
    @SerializedName("srmMin")
    var srmMin: String? = null,
    @SerializedName("srmMax")
    var srmMax: String? = null,
    @SerializedName("ogMin")
    var ogMin: String? = null,
    @SerializedName("fgMin")
    var fgMin: String? = null,
    @SerializedName("fgMax")
    var fgMax: String? = null,
    @SerializedName("createDate")
    var createDate: String? = null,
    @SerializedName("updateDate")
    var updateDate: String? = null
)
