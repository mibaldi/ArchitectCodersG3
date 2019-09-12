package com.mibaldi.domain.entity

data class Beer(
    val id: String,
    val title: String,
    val image: String,
    val observation: String?,
    val rating: Float = 0f
)