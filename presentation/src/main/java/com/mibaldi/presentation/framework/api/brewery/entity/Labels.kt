package com.mibaldi.presentation.framework.api.brewery.entity

import com.google.gson.annotations.SerializedName

data class Labels(
    @SerializedName("icon")
    var icon: String? = null,
    @SerializedName("medium")
    var medium: String? = null,
    @SerializedName("large")
    var large: String? = null,
    @SerializedName("contentAwareIcon")
    var contentAwareIcon: String? = null,
    @SerializedName("contentAwareMedium")
    var contentAwareMedium: String? = null,
    @SerializedName("contentAwareLarge")
    var contentAwareLarge: String? = null
)
