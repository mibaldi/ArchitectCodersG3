package com.mibaldi.domain.entity

data class MyFirebaseUser(
    var email: String? = "",
    val name: String? = "",
    val photoUrl: String? = "",
    val phone: String? = ""
)