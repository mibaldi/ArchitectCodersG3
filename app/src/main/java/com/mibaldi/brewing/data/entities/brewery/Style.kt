package com.mibaldi.brewing.data.entities.brewery

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Style {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("categoryId")
    @Expose
    var categoryId: Int? = null
    @SerializedName("category")
    @Expose
    var category: Category? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("shortName")
    @Expose
    var shortName: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("ibuMin")
    @Expose
    var ibuMin: String? = null
    @SerializedName("ibuMax")
    @Expose
    var ibuMax: String? = null
    @SerializedName("abvMin")
    @Expose
    var abvMin: String? = null
    @SerializedName("abvMax")
    @Expose
    var abvMax: String? = null
    @SerializedName("srmMin")
    @Expose
    var srmMin: String? = null
    @SerializedName("srmMax")
    @Expose
    var srmMax: String? = null
    @SerializedName("ogMin")
    @Expose
    var ogMin: String? = null
    @SerializedName("fgMin")
    @Expose
    var fgMin: String? = null
    @SerializedName("fgMax")
    @Expose
    var fgMax: String? = null
    @SerializedName("createDate")
    @Expose
    var createDate: String? = null
    @SerializedName("updateDate")
    @Expose
    var updateDate: String? = null

}
